package vn.edu.fpt.elios_user_service.application.usecase;

import vn.edu.fpt.elios_user_service.application.dto.request.RegisterProfileRequest;
import vn.edu.fpt.elios_user_service.application.dto.response.RegisterProfileResponse;

public interface RegisterProfile {
    RegisterProfileResponse registerProfile(RegisterProfileRequest request);
}
