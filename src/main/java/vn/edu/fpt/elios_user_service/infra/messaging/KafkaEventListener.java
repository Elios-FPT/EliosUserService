package vn.edu.fpt.elios_user_service.infra.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.fpt.elios_user_service.application.usecase.ProcessUserEvent;
import vn.edu.fpt.elios_user_service.application.dto.event.EventWrapper;
import vn.edu.fpt.elios_user_service.infra.config.KafkaTopicProperties;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEventListener {
    private final ProcessUserEvent processUserEvent;
    private final KafkaTopicProperties kafkaTopicProperties;

    public void handleUserEvents(EventWrapper requestEvent, String topicName) {
        try {
            log.info("Received event: eventId={}, eventType={}, correlationId={}, topic={}", 
                requestEvent.eventId(), requestEvent.eventType(), requestEvent.correlationId(), topicName);

            // Extract source service from topic name
            String sourceService = kafkaTopicProperties.extractSourceServiceFromTopic(topicName);
            
            EventWrapper responseEvent = processUserEvent.processEvent(requestEvent, sourceService);

            log.info("Processed event: eventId={}, success={}, sourceService={}", 
                responseEvent.eventId(), responseEvent.success(), sourceService);

        } catch (Exception e) {
            log.error("Error handling Kafka event from topic {}: {}", topicName, e.getMessage(), e);
        }
    }
}
