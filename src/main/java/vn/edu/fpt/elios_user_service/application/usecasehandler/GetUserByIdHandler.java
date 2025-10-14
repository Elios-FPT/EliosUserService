package vn.edu.fpt.elios_user_service.application.usecasehandler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.fpt.elios_user_service.application.dto.response.UserProfileResponse;
import vn.edu.fpt.elios_user_service.application.mapper.UserResponseMapper;
import vn.edu.fpt.elios_user_service.application.repository.UserRepository;
import vn.edu.fpt.elios_user_service.application.usecase.GetUserById;
import vn.edu.fpt.elios_user_service.domain.exception.NotFoundException;
import vn.edu.fpt.elios_user_service.domain.model.User;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUserByIdHandler implements GetUserById {
    private final UserRepository repo;
    private final UserResponseMapper mapper;

    @Override
    public UserProfileResponse getById(UUID id) {
        User u = repo.findById(id).orElseThrow(() -> new NotFoundException("User not found: " + id));
        return mapper.toResponse(u);
    }
}


