package vn.edu.fpt.elios_user_service.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.elios_user_service.application.dto.request.RegisterProfileRequest;
import vn.edu.fpt.elios_user_service.application.dto.response.RegisterProfileResponse;
import vn.edu.fpt.elios_user_service.application.dto.response.UserProfileResponse;
import vn.edu.fpt.elios_user_service.application.usecasehandler.*;
import vn.edu.fpt.elios_user_service.application.usecasehandler.ListUsersPageHandler;
import vn.edu.fpt.elios_user_service.application.dto.request.UpdateProfileRequest;
import vn.edu.fpt.elios_user_service.controller.api.ApiResponse;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final RegisterProfileHandler handler;
    private final GetUserByIdHandler getUserByIdHandler;
    private final UpdateProfileHandler updateProfileHandler;
    private final DeleteProfileHandler deleteProfileHandler;
    private final ListUsersPageHandler listUsersPageHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RegisterProfileResponse> register(@Valid @RequestBody RegisterProfileRequest req) {
        return new ApiResponse<>(201, "User profile created", handler.registerProfile(req));
    }

    @GetMapping("/{id}")
    public ApiResponse<UserProfileResponse> getOne(@PathVariable UUID id) {
        return new ApiResponse<>(200, "OK", getUserByIdHandler.getById(id));
    }

    @GetMapping("/me/profile")
    public ApiResponse<UserProfileResponse> getProfile(
            @RequestHeader(value = "X-Auth-Request-User") UUID authenticatedUserId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Principal = {}", auth.getName());
        log.info("Authorities = {}", auth.getAuthorities());

        UserProfileResponse tmp = getUserByIdHandler.getById(authenticatedUserId);

        String userRole = "User";
        for (GrantedAuthority authority : auth.getAuthorities()) {
            if (authority.getAuthority().equals("Admin")) {
                userRole = "Admin";
                break;
            } else if (authority.getAuthority().equals("Resource Manager")) {
                userRole = "Resource Manager";
                break;
            } else if (authority.getAuthority().equals("Content Moderator")) {
                userRole = "Content Moderator";
                break;
            }
        }


        UserProfileResponse userProfile = new UserProfileResponse(tmp.id(),
                userRole,
                tmp.firstName(),
                tmp.lastName(),
                tmp.dateOfBirth(),
                tmp.gender(),
                tmp.avatarUrl(),
                tmp.avatarPrefix(),
                tmp.avatarFileName(),
                tmp.createdAt(),
                tmp.updatedAt());

        return new ApiResponse<>(200, "OK", userProfile);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ApiResponse<Page<UserProfileResponse>> list(
            Pageable pageable,
            @RequestParam(required = false, name = "q") String q
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Principal = {}", auth.getName());
        log.info("Authorities = {}", auth.getAuthorities());
        return new ApiResponse<>(200, "OK", listUsersPageHandler.list(pageable, q));
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
