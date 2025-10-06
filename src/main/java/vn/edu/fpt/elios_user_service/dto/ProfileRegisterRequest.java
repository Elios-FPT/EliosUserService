package vn.edu.fpt.elios_user_service.dto;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record ProfileRegisterRequest(
        @NotBlank @Size(max = 20) String firstName,
        @NotBlank @Size(max = 20) String lastName,
        @Past(message = "dateOfBirth must be in the past")
        LocalDate dateOfBirth
) {}
