package vn.edu.fpt.elios_user_service.infra.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import vn.edu.fpt.elios_user_service.application.dto.event.EventWrapper;
import vn.edu.fpt.elios_user_service.infra.messaging.KafkaEventListener;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaListenerContainerManager {
    
    private final KafkaTopicProperties kafkaTopicProperties;
    private final KafkaConfig kafkaConfig;
    private final KafkaEventListener kafkaEventListener;
    
    @PostConstruct
    public void initializeKafkaListeners() {
        log.info("Initializing dynamic Kafka listeners for source services: {}", kafkaTopicProperties.getSourceServices());
        
        List<String> sourceServices = kafkaTopicProperties.getSourceServices();
        if (sourceServices == null || sourceServices.isEmpty()) {
            log.warn("No source services configured. Skipping Kafka listener initialization.");
            return;
        }
        
        for (String sourceService : sourceServices) {
            try {
                createListenerContainerForService(sourceService);
                log.info("Successfully created Kafka listener for source service: {}", sourceService);
            } catch (Exception e) {
                log.error("Failed to create Kafka listener for source service {}: {}", sourceService, e.getMessage(), e);
            }
        }
        
        log.info("Completed initialization of {} Kafka listeners", sourceServices.size());
    }
    
    private void createListenerContainerForService(String sourceService) {
        String commandTopic = kafkaTopicProperties.getCommandTopic(sourceService);
        String consumerGroup = kafkaTopicProperties.getConsumerGroup(sourceService);
        int concurrency = kafkaTopicProperties.getConcurrency(sourceService);
        
        log.info("Creating listener container for service: {}, topic: {}, consumerGroup: {}, concurrency: {}", 
            sourceService, commandTopic, consumerGroup, concurrency);
        
        // Create a consumer factory with service-specific consumer group
        ConsumerFactory<String, EventWrapper> consumerFactory = kafkaConfig.createConsumerFactory(consumerGroup);
        
        // Create a container factory
        ConcurrentKafkaListenerContainerFactory<String, EventWrapper> containerFactory = 
            new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory);
        
        // Create the listener container
        ConcurrentMessageListenerContainer<String, EventWrapper> container = 
            containerFactory.createContainer(commandTopic);
        
        // Set concurrency for this specific service
        container.setConcurrency(concurrency);
        
        // Set the message listener using proper MessageListener interface
        container.setupMessageListener(new MessageListener<String, EventWrapper>() {
            @Override
            public void onMessage(@NonNull org.apache.kafka.clients.consumer.ConsumerRecord<String, EventWrapper> data) {
                try {
                    String topicName = data.topic();
                    EventWrapper eventWrapper = data.value();
                    kafkaEventListener.handleUserEvents(eventWrapper, topicName);
                } catch (Exception e) {
                    log.error("Error in message listener for service {}: {}", sourceService, e.getMessage(), e);
                }
            }
        });
        
        // Start the container
        container.start();
        
        log.info("Started Kafka listener container for service: {} with topic: {} and concurrency: {}", 
            sourceService, commandTopic, concurrency);
    }
}
