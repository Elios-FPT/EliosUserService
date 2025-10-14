package vn.edu.fpt.elios_user_service.application.usecasehandler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.fpt.elios_user_service.application.dto.request.UpdateProfileRequest;
import vn.edu.fpt.elios_user_service.application.dto.response.UserProfileResponse;
import vn.edu.fpt.elios_user_service.application.mapper.UserDtoMapper;
import vn.edu.fpt.elios_user_service.application.repository.UserRepository;
import vn.edu.fpt.elios_user_service.application.usecase.UpdateProfile;
import vn.edu.fpt.elios_user_service.domain.exception.NotFoundException;
import vn.edu.fpt.elios_user_service.domain.model.User;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateProfileHandler implements UpdateProfile {
    private final UserRepository repo;
    private final UserDtoMapper mapper;

    @Override
    public UserProfileResponse update(UUID id, UpdateProfileRequest request) {
        User user = repo.findById(id).orElseThrow(() -> new NotFoundException("User not found: " + id));
        user.rename(request.firstName(), request.lastName());
        user.updateGender(request.gender());
        user.updateDateOfBirth(request.dateOfBirth());
        repo.save(user);
        return mapper.toResponse(user);
    }
}


