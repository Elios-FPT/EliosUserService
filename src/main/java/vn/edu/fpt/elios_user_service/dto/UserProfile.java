package vn.edu.fpt.elios_user_service.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record UserProfile(
        UUID id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        Instant createdAt,
        Instant updatedAt
) {}
