package com.company.mappers;

import com.company.dto.UserDetailsDto;
import com.company.dto.UserReadDto;
import com.company.entity.AuthUser;
import com.company.entity.Location;
import com.company.enums.Status;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthUserMapper {

    @SubclassMapping(target = UserReadDto.class, source = AuthUser.class)
    UserReadDto toDto(AuthUser user);

    @Mappings({
            @Mapping(target = "status", expression = "java(statusToString(user.getStatus()))"),
            @Mapping(target = "citiesNames", expression = "java(getCitiesNames(user.getSubscribeLocations()))")
    })
    UserDetailsDto toUserDetailsDto(AuthUser user);

    default String statusToString(Status status) {
        return status.name();
    }

    default List<String> getCitiesNames(List<Location> locations) {
        return locations.stream()
                .map(Location::getName)
                .toList();
    }
}
