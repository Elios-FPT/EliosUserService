package vn.edu.fpt.elios_user_service.dto;

import vn.edu.fpt.elios_user_service.enums.Gender;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record UserProfile(
        UUID id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        Gender gender,
        String avatarUrl,
        Instant createdAt,
        Instant updatedAt
) {}
