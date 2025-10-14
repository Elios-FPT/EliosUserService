package vn.edu.fpt.elios_user_service.application.usecasehandler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.fpt.elios_user_service.application.repository.UserRepository;
import vn.edu.fpt.elios_user_service.application.usecase.DeleteProfile;
import vn.edu.fpt.elios_user_service.domain.exception.NotFoundException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteProfileHandler implements DeleteProfile {
    private final UserRepository repo;

    @Override
    public void delete(UUID id) {
        if (repo.findById(id).isEmpty()) {
            throw new NotFoundException("User not found: " + id);
        }
        repo.deactivateById(id);
    }
}


