package vn.edu.fpt.elios_user_service.application.usecasehandler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.fpt.elios_user_service.application.dto.request.RegisterProfileRequest;
import vn.edu.fpt.elios_user_service.application.dto.response.RegisterProfileResponse;
import vn.edu.fpt.elios_user_service.application.mapper.UserDtoMapper;
import vn.edu.fpt.elios_user_service.application.repository.UserRepository;
import vn.edu.fpt.elios_user_service.application.usecase.RegisterProfile;
import vn.edu.fpt.elios_user_service.domain.model.User;

@AllArgsConstructor
@Service
public class RegisterProfileHandler implements RegisterProfile {
    private UserRepository repo;
    private UserDtoMapper mapper;

    @Override
    public RegisterProfileResponse registerProfile(RegisterProfileRequest request) {
        User user = User.create(request.id(),
                request.firstName(),
                request.lastName(),
                request.gender(),
                request.dateOfBirth()
        );
        repo.save(user);

        return mapper.toRegisterProfileResponse(user);
    }
}
