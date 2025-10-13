package old.elios_user_service.mapper;

import old.elios_user_service.dto.ProfileRegisterRequest;
import old.elios_user_service.dto.ProfileUpdateRequest;
import old.elios_user_service.dto.UserProfile;
import old.elios_user_service.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(ProfileRegisterRequest req);

    UserProfile toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProfileUpdateRequest req, @MappingTarget User user);
}
