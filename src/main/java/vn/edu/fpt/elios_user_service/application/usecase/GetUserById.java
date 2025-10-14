package vn.edu.fpt.elios_user_service.application.usecase;

import vn.edu.fpt.elios_user_service.application.dto.response.UserProfileResponse;

import java.util.UUID;

public interface GetUserById {
    UserProfileResponse getById(UUID id);
}


