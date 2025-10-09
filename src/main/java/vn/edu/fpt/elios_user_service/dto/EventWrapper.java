package vn.edu.fpt.elios_user_service.dto;

import java.util.UUID;

public record EventWrapper(
        UUID eventId,
        String eventType,
        String modelType,
        Object payload
) {}
