package vn.edu.fpt.elios_user_service.application.usecasehandler;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.fpt.elios_user_service.application.dto.response.UserProfileResponse;
import vn.edu.fpt.elios_user_service.application.mapper.UserDtoMapper;
import vn.edu.fpt.elios_user_service.application.repository.UserRepository;
import vn.edu.fpt.elios_user_service.application.usecase.ListUsersPage;

@Service
@RequiredArgsConstructor
public class ListUsersPageHandler implements ListUsersPage {
    private final UserRepository repo;
    private final UserDtoMapper mapper;

    @Override
    public Page<UserProfileResponse> list(Pageable pageable, String query) {
        return (query == null || query.isBlank()
                ? repo.findAll(pageable)
                : repo.findByName(query, query, pageable))
            .map(mapper::toResponse);
    }
}


