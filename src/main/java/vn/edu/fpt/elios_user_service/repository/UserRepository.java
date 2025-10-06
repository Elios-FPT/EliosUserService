package vn.edu.fpt.elios_user_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.elios_user_service.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Page<User> findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(
            String first, String last, Pageable pageable
    );
}
