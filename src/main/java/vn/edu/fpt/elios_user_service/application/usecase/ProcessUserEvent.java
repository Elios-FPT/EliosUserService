package vn.edu.fpt.elios_user_service.application.usecase;

import vn.edu.fpt.elios_user_service.application.dto.event.EventWrapper;

public interface ProcessUserEvent {
    EventWrapper processEvent(EventWrapper requestEvent);
}
