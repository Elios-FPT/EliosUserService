package vn.edu.fpt.elios_user_service.application.dto.response;

import vn.edu.fpt.elios_user_service.enums.Gender;

import java.time.Instant;
import java.util.UUID;

public record UserProfileResponse(
        UUID id,
        String role,
        String firstName,
        String lastName,
        Instant dateOfBirth,
        Gender gender,
        String avatarUrl,
        String avatarPrefix,
        String avatarFileName,
        Instant createdAt,
        Instant updatedAt
) {
}


