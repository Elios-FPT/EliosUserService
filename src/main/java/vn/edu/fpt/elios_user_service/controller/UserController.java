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
import vn.edu.fpt.elios_user_service.controller.api.ApiResponse;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final RegisterProfileHandler handler;
    private final GetUserByIdHandler getUserByIdHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RegisterProfileResponse> register(@Valid @RequestBody RegisterProfileRequest req) {
        return new ApiResponse<>(201, "User profile created", handler.registerProfile(req));
    }

    @GetMapping("/{id}")
    public ApiResponse<UserProfileResponse> getOne(@PathVariable UUID id) {
        return new ApiResponse<>(200, "OK", getUserByIdHandler.getById(id));
    }
}
