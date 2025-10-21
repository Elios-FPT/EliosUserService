package vn.edu.fpt.elios_user_service.infra.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.fpt.elios_user_service.infra.persistence.model.UserEntity;
import vn.edu.fpt.elios_user_service.infra.persistence.repository.base.UserSoftDeleteRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID>, UserSoftDeleteRepository {
    Optional<UserEntity> findByIdAndIsActiveTrue(UUID id);
    List<UserEntity> findAllByIsActiveTrue();
    Page<UserEntity> findAllByIsActiveTrue(Pageable pageable);
    Page<UserEntity> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseAndIsActiveTrue(
            String first, String last, Pageable pageable);
}
