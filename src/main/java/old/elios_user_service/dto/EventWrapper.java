package old.elios_user_service.dto;

import old.elios_user_service.enums.EventType;

import java.util.UUID;

public record EventWrapper(
        UUID eventId,
        UUID correlationId,
        EventType eventType,
        String modelType,
        Object payload
) {}
