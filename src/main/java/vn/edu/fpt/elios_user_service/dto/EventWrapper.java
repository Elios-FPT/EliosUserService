package vn.edu.fpt.elios_user_service.dto;

import vn.edu.fpt.elios_user_service.enums.EventType;

import java.util.UUID;

public record EventWrapper(
        UUID eventId,
        UUID correlationId,
        EventType eventType,
        String modelType,
        Object payload
) {}
