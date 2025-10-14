package vn.edu.fpt.elios_user_service.infra.persistence.repository;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.elios_user_service.application.repository.UserRepository;
import vn.edu.fpt.elios_user_service.domain.model.User;
import vn.edu.fpt.elios_user_service.infra.persistence.mapper.UserMapper;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository jpaRepository;
    private final UserMapper userMapper;

    @Override
    public void save(User user) {
        jpaRepository.save(userMapper.toEntity(user));
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findByIdAndIsActiveTrue(id).map(userMapper::toDomain);
    }

    public void deactivateById(UUID id) {
        jpaRepository.deactivateById(id);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return jpaRepository.findAllByIsActiveTrue(pageable).map(userMapper::toDomain);
    }

    @Override
    public Page<User> findByName(String first, String last, Pageable pageable) {
        return jpaRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseAndIsActiveTrue(first, last, pageable)
                .map(userMapper::toDomain);
    }
}
