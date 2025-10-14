package vn.edu.fpt.elios_user_service.infra.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vn.edu.fpt.elios_user_service.application.eventpublisher.UserEventPublisher;
import vn.edu.fpt.elios_user_service.application.dto.event.EventWrapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEventPublisher implements UserEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishResponse(String topic, EventWrapper event) {
        try {
            log.info("Publishing response to topic: {}, eventId: {}, correlationId: {}", 
                topic, event.eventId(), event.correlationId());
            
            kafkaTemplate.send(topic, event);
            
            log.info("Successfully published response to topic: {}", topic);
        } catch (Exception e) {
            log.error("Failed to publish response to topic: {}, error: {}", topic, e.getMessage(), e);
            throw new RuntimeException("Failed to publish Kafka message", e);
        }
    }
}
