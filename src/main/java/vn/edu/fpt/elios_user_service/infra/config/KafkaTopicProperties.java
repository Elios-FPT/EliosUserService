package vn.edu.fpt.elios_user_service.infra.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaTopicProperties {
    
    private List<String> sourceServices;
    private String currentService;
    private String modelType;
    private Consumer consumer = new Consumer();
    private Map<String, Integer> serviceConcurrency;
    
    @PostConstruct
    public void logConfiguration() {
        log.info("KafkaTopicProperties loaded:");
        log.info("  sourceServices: {}", sourceServices);
        log.info("  currentService: {}", currentService);
        log.info("  modelType: {}", modelType);
        log.info("  consumer.bootstrapServers: {}", consumer.getBootstrapServers());
        log.info("  consumer.defaultConcurrency: {}", consumer.getDefaultConcurrency());
        log.info("  serviceConcurrency: {}", serviceConcurrency);
    }
    
    @Data
    public static class Consumer {
        private String bootstrapServers;
        private int defaultConcurrency = 3;
    }
    
    /**
     * Get the concurrency level for a specific source service.
     * Returns the service-specific concurrency if configured, otherwise the default.
     */
    public int getConcurrency(String sourceService) {
        if (serviceConcurrency != null && serviceConcurrency.containsKey(sourceService)) {
            return serviceConcurrency.get(sourceService);
        }
        return consumer.getDefaultConcurrency();
    }
    
    /**
     * Generate command topic name for a source service.
     * Format: {source}-{current}-{model}
     */
    public String getCommandTopic(String sourceService) {
        return String.format("%s-%s-%s", sourceService, currentService, modelType);
    }
    
    /**
     * Generate response topic name for a source service.
     * Format: {current}-{source}-{model}
     */
    public String getResponseTopic(String sourceService) {
        return String.format("%s-%s-%s", currentService, sourceService, modelType);
    }
    
    /**
     * Generate consumer group name for a source service.
     * Format: {current}-{source}
     */
    public String getConsumerGroup(String sourceService) {
        return String.format("%s-%s", currentService, sourceService);
    }
    
    /**
     * Extract source service name from a command topic.
     * Assumes topic format: {source}-{current}-{model}
     */
    public String extractSourceServiceFromTopic(String topicName) {
        if (topicName == null || !topicName.contains("-")) {
            throw new IllegalArgumentException("Invalid topic format: " + topicName);
        }
        
        String[] parts = topicName.split("-");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Topic must have at least 3 parts separated by '-': " + topicName);
        }
        
        return parts[0]; // First part is the source service
    }
}
