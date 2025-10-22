package vn.edu.fpt.elios_user_service.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import vn.edu.fpt.elios_user_service.enums.Gender;

import java.time.Instant;

public record UpdateProfileRequest(
        @NotBlank @Size(max = 20) String firstName,
        @NotBlank @Size(max = 20) String lastName,
        @NotNull Gender gender,
        @PastOrPresent(message = "dateOfBirth must not be in the future") Instant dateOfBirth
) {}


