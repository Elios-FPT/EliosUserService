package vn.edu.fpt.elios_user_service.application.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.fpt.elios_user_service.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(UUID id);
    void deactivateById(UUID id);
    Page<User> findAll(Pageable pageable);
    Page<User> findByName(String first, String last, Pageable pageable);
}
