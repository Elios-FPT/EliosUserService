package vn.edu.fpt.elios_user_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.elios_user_service.dto.*;
import vn.edu.fpt.elios_user_service.entity.User;
import vn.edu.fpt.elios_user_service.mapper.UserMapper;
import vn.edu.fpt.elios_user_service.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository repo;
    private final UserMapper mapper;
    
    public UserProfile create(ProfileRegisterRequest req) {
        User user = mapper.toEntity(req);
        return mapper.toDto(repo.save(user));
    }

    public UserProfile update(UUID id, ProfileUpdateRequest req) {
        User existing = repo.findById(id).orElseThrow(
                () -> new NoSuchElementException("User not found: " + id)
        );
        mapper.updateEntityFromDto(req, existing);
        return mapper.toDto(repo.save(existing));
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserProfile get(UUID id) {
        User user = repo.findById(id).orElseThrow(
                () -> new NoSuchElementException("User not found: " + id)
        );
        return mapper.toDto(user);
    }

    @Transactional(readOnly = true)
    public Page<UserProfile> list(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<UserProfile> searchByName(String keyword, Pageable pageable) {
        String k = (keyword == null) ? "" : keyword;
        return repo
                .findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(k, k, pageable)
                .map(mapper::toDto);
    }
}
