package vn.edu.fpt.elios_user_service.infra.persistence.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.edu.fpt.elios_user_service.domain.model.User;
import vn.edu.fpt.elios_user_service.infra.persistence.model.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(User domainModel);

    User toDomain(UserEntity userEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDomain(User domainModel, @MappingTarget UserEntity userEntity);
}
