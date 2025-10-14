package vn.edu.fpt.elios_user_service.infra.persistence.mapper;

import org.mapstruct.*;
import vn.edu.fpt.elios_user_service.domain.model.User;
import vn.edu.fpt.elios_user_service.infra.persistence.model.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(User domainModel);

    User toDomain(UserEntity userEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDomain(User domainModel, @MappingTarget UserEntity userEntity);

    @ObjectFactory
    default User rehydrateUser(UserEntity e) {
        return User.rehydrate(
                e.getId(),
                e.getFirstName(),
                e.getLastName(),
                e.getGender(),
                e.getDateOfBirth(),
                e.getAvatarUrl(),
                e.getAvatarPrefix(),
                e.getAvatarFileName(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }
}
