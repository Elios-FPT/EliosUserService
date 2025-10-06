package vn.edu.fpt.elios_user_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.elios_user_service.common.ApiResponse;
import vn.edu.fpt.elios_user_service.dto.*;
import vn.edu.fpt.elios_user_service.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserProfile> register(@Valid @RequestBody ProfileRegisterRequest req) {
        return new ApiResponse<>(201, "User profile created", service.create(req));
    }

    @PutMapping("/{id}")
    public ApiResponse<UserProfile> update(
            @PathVariable UUID id,
            @Valid @RequestBody ProfileUpdateRequest req
    ) {
        return new ApiResponse<>(200, "User profile updated", service.update(id, req));
    }

    @GetMapping("/{id}")
    public ApiResponse<UserProfile> getOne(@PathVariable UUID id) {
        return new ApiResponse<>(200, "OK", service.get(id));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return new ApiResponse<>(200, "User profile deleted", null);
    }

    @GetMapping
    public ApiResponse<Page<UserProfile>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt,DESC") String sort
    ) {
        Sort s = Sort.by(Sort.Order.desc("createdAt"));
        if (sort != null && !sort.isBlank()) {
            String[] parts = sort.split(",");
            String field = parts[0];
            boolean asc = parts.length < 2 || !"DESC".equalsIgnoreCase(parts[1]);
            s = asc ? Sort.by(field).ascending() : Sort.by(field).descending();
        }
        Pageable pageable = PageRequest.of(page, size, s);
        return new ApiResponse<>(200, "OK", service.list(pageable));
    }

    @GetMapping("/search")
    public ApiResponse<Page<UserProfile>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return new ApiResponse<>(200, "OK", service.searchByName(keyword, pageable));
    }
}
