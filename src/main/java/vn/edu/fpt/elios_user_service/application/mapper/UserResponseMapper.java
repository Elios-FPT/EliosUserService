package vn.edu.fpt.elios_user_service.application.mapper;

import org.mapstruct.Mapper;
import vn.edu.fpt.elios_user_service.application.dto.response.UserProfileResponse;
import vn.edu.fpt.elios_user_service.domain.model.User;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {
    UserProfileResponse toResponse(User user);
}


