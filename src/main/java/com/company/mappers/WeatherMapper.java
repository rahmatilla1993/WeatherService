package com.company.mappers;

import com.company.dao.LocationDao;
import com.company.dto.WeatherDto;
import com.company.entity.Location;
import com.company.entity.WeatherData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.time.LocalDate;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WeatherMapper {

    @Mappings({
            @Mapping(target = "date", expression = "java(dateToString(data.getDate()))"),
            @Mapping(target = "location", expression = "java(getLocation(data.getLocation()))")
    })
    WeatherDto toDto(WeatherData data);

    @Mapping(target = "location", expression = "java(dao.findByName(dto.getLocation()).get())")
    WeatherData toEntity(WeatherDto dto, LocationDao dao);

    default String dateToString(LocalDate date) {
        return date.toString();
    }

    default String getLocation(Location location) {
        return location.getName();
    }
}
