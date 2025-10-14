package vn.edu.fpt.elios_user_service.infra.persistence.repository.base;

import java.util.UUID;

public interface UserSoftDeleteRepository {
    void deactivateById(UUID id);
}


