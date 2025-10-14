package vn.edu.fpt.elios_user_service.application.eventpublisher;

import vn.edu.fpt.elios_user_service.infra.messaging.event.EventWrapper;

public interface EventPublisher {
    void publishResponse(String topic, EventWrapper event);
}
