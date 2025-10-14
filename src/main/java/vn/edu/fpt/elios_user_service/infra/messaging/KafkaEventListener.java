package vn.edu.fpt.elios_user_service.infra.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vn.edu.fpt.elios_user_service.application.usecase.ProcessUserEvent;
import vn.edu.fpt.elios_user_service.infra.messaging.event.EventWrapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEventListener {
    private final ProcessUserEvent processUserEvent;

    @KafkaListener(topics = "user")
    public void handleUserEvents(EventWrapper requestEvent) {
        try {
            log.info("Received event: eventId={}, eventType={}, correlationId={}", 
                requestEvent.eventId(), requestEvent.eventType(), requestEvent.correlationId());

            EventWrapper responseEvent = processUserEvent.processEvent(requestEvent);
            
            log.info("Processed event: eventId={}, success={}", 
                responseEvent.eventId(), responseEvent.success());
            
        } catch (Exception e) {
            log.error("Error handling Kafka event: {}", e.getMessage(), e);
        }
    }
}
