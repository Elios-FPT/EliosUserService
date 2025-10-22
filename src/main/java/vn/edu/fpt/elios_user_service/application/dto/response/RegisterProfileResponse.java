package vn.edu.fpt.elios_user_service.application.dto.response;

import vn.edu.fpt.elios_user_service.enums.Gender;

import java.time.Instant;
import java.util.UUID;

public record RegisterProfileResponse (
        UUID id,
        String firstName,
        String lastName,
        Gender gender,
        Instant dateOfBirth,
        Instant createdAt,
        Instant updatedAt
) {}
