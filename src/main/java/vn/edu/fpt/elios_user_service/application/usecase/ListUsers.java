package vn.edu.fpt.elios_user_service.application.usecase;

import vn.edu.fpt.elios_user_service.application.dto.response.UserProfileResponse;

import java.util.List;

public interface ListUsers {
    List<UserProfileResponse> list();
}


