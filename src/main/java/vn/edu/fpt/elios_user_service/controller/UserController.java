package vn.edu.fpt.elios_user_service.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.elios_user_service.application.dto.request.RegisterProfileRequest;
import vn.edu.fpt.elios_user_service.application.dto.response.RegisterProfileResponse;
import vn.edu.fpt.elios_user_service.application.dto.response.UserProfileResponse;
import vn.edu.fpt.elios_user_service.application.usecasehandler.GetUserByIdHandler;
import vn.edu.fpt.elios_user_service.application.usecasehandler.RegisterProfileHandler;
import vn.edu.fpt.elios_user_service.application.dto.request.UpdateProfileRequest;
import vn.edu.fpt.elios_user_service.application.usecasehandler.UpdateProfileHandler;
import vn.edu.fpt.elios_user_service.application.usecasehandler.DeleteProfileHandler;
import vn.edu.fpt.elios_user_service.controller.api.ApiResponse;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final RegisterProfileHandler handler;
    private final GetUserByIdHandler getUserByIdHandler;
    private final UpdateProfileHandler updateProfileHandler;
    private final DeleteProfileHandler deleteProfileHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RegisterProfileResponse> register(@Valid @RequestBody RegisterProfileRequest req) {
        return new ApiResponse<>(201, "User profile created", handler.registerProfile(req));
    }

    @GetMapping("/{id}")
    public ApiResponse<UserProfileResponse> getOne(@PathVariable UUID id) {
        return new ApiResponse<>(200, "OK", getUserByIdHandler.getById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<UserProfileResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProfileRequest req
    ) {
        return new ApiResponse<>(200, "User profile updated", updateProfileHandler.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        deleteProfileHandler.delete(id);
        return new ApiResponse<>(200, "User profile deleted", null);
    }
}
