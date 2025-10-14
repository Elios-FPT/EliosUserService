package vn.edu.fpt.elios_user_service.application.eventpublisher;

import vn.edu.fpt.elios_user_service.application.dto.event.EventWrapper;

public interface UserEventPublisher {
    void publishResponse(String topic, EventWrapper event);
}
