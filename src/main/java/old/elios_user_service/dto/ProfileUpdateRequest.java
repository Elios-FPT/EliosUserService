package old.elios_user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import old.elios_user_service.enums.Gender;

import java.time.LocalDate;

public record ProfileUpdateRequest(
        @NotBlank @Size(max = 20) String firstName,
        @NotBlank @Size(max = 20) String lastName,
        @NotBlank Gender gender,
        @Past(message = "dateOfBirth must be in the past") LocalDate dateOfBirth
) {}
