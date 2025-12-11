package util.mapper;

import model.dto.LocationDto;
import model.entity.Location;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LocationMapper {

    public static Location toEntity(LocationDto locationDto) {
        String name = locationDto.getName();
        BigDecimal latitude = locationDto.getLat();
        BigDecimal longitude = locationDto.getLon();

        return new Location(name, latitude, longitude);
    }

    public static ArrayList<LocationDto> toDtoList(List<Location> locationEntities) {
        ArrayList<LocationDto> dtos = new ArrayList<>();

        for (Location entity : locationEntities) {
            LocationDto dto = new LocationDto();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setLat(entity.getLatitude());
            dto.setLon(entity.getLongitude());
            dtos.add(dto);
        }

        return dtos;
    }
}
