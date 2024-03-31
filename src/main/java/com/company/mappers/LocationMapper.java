package com.company.mappers;

import com.company.dto.LocationDto;
import com.company.entity.AuthUser;
import com.company.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.SubclassMapping;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper {

    Location toEntity(LocationDto dto);

    @SubclassMapping(target = LocationDto.class, source = Location.class)
    @Mapping(
            target = "subscribeUserEmails",
            expression = "java(getSubscribeUsers(location.getSubscribeUsers()))"
    )
    LocationDto toLocationReadDto(Location location);

    default List<String> getSubscribeUsers(List<AuthUser> authUsers) {
        return authUsers.stream()
                .map(AuthUser::getEmail)
                .toList();
    }
}
