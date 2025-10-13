package old.elios_user_service.repository;

import old.elios_user_service.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Page<User> findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(
            String first, String last, Pageable pageable
    );
}
