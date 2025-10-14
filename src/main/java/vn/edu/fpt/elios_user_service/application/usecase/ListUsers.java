package vn.edu.fpt.elios_user_service.application.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.fpt.elios_user_service.application.dto.response.UserProfileResponse;

public interface ListUsers {
    Page<UserProfileResponse> list(Pageable pageable, String query);
}


