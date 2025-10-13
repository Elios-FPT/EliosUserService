package vn.edu.fpt.elios_user_service.application.usecasehandler;

import vn.edu.fpt.elios_user_service.application.dto.request.RegisterProfileRequest;
import vn.edu.fpt.elios_user_service.application.dto.response.RegisterProfileResponse;
import vn.edu.fpt.elios_user_service.application.repository.UserRepository;
import vn.edu.fpt.elios_user_service.application.usecase.RegisterProfile;
import vn.edu.fpt.elios_user_service.domain.model.User;

public class RegisterProfileHandler implements RegisterProfile {
    private UserRepository repo;

    @Override
    public RegisterProfileResponse registerProfile(RegisterProfileRequest request) {
        User user = User.create(request.id(),
                request.firstName(),
                request.lastName(),
                request.gender(),
                request.dateOfBirth()
        );
        repo.save(user);
        
        return new RegisterProfileResponse(request.id(),
                request.firstName(),
                request.lastName(),
                request.gender(),
                request.dateOfBirth()
        );
    }
}
