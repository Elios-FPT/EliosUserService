package vn.edu.fpt.elios_user_service.infra.messaging.event;

import vn.edu.fpt.elios_user_service.enums.EventType;

import java.util.UUID;

public record EventWrapper(
        UUID eventId,
        UUID correlationId,
        EventType eventType,
        String modelType,
        Object payload,
        Boolean success,
        String errorMessage
) {
    public static EventWrapper success(UUID eventId, UUID correlationId, EventType eventType, String modelType, Object payload) {
        return new EventWrapper(eventId, correlationId, eventType, modelType, payload, true, null);
    }

    public static EventWrapper error(UUID eventId, UUID correlationId, EventType eventType, String errorMessage) {
        return new EventWrapper(eventId, correlationId, eventType, null, null, false, errorMessage);
    }
}
