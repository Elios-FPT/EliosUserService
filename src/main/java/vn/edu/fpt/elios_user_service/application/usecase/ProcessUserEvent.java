package vn.edu.fpt.elios_user_service.application.usecase;

import vn.edu.fpt.elios_user_service.infra.messaging.event.EventWrapper;

public interface ProcessUserEvent {
    EventWrapper processEvent(EventWrapper requestEvent);
}
