package vn.edu.fpt.elios_user_service.mapper;

import org.mapstruct.*;
import vn.edu.fpt.elios_user_service.dto.*;
import vn.edu.fpt.elios_user_service.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(ProfileRegisterRequest req);

    UserProfile toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProfileUpdateRequest req, @MappingTarget User user);
}
