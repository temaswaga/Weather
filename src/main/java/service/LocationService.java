package service;

import lombok.RequiredArgsConstructor;
import util.mapper.LocationMapper;
import model.dto.LocationDto;
import model.dto.WeatherDto;
import model.entity.Location;
import model.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.LocationRepository;

import java.util.*;

@Transactional
@RequiredArgsConstructor
@Service
public class LocationService {
    private final UserService userService;
    private final LocationRepository locationRepository;
    private final WeatherApiService weatherApiService;

    public List<LocationDto> getUsersLocationsBySessionId(String sessionId) {
        User userId = userService.getUserBySessionId(sessionId);
        List<Location> locationEntities = locationRepository.getUsersLocations(userId);
        return LocationMapper.toDtoList(locationEntities);
    }

    public List<WeatherDto> getUsersLocationsWithWeatherBySessionId(String sessionId) {
    List<WeatherDto> weatherDtos = new ArrayList<>();
    List<LocationDto> savedLocations = getUsersLocationsBySessionId(sessionId);

    for (LocationDto location : savedLocations) {
        WeatherDto weather = weatherApiService.getWeather(location.getLat(), location.getLon());

        if (weather == null) {
            weather = new WeatherDto();
        }

        weather.setId(location.getId());
        weather.setName(location.getName());
        weatherDtos.add(weather);
    }

    return weatherDtos;
    }

    public void addLocation (LocationDto locationDto, String sessionId){
        User userId = userService.getUserBySessionId(sessionId);
        Location location = LocationMapper.toEntity(locationDto);
        location.setUserid(userId);

        locationRepository.save(location);
    }

    public void deleteLocation(long id){
        locationRepository.deleteLocation(id);
    }
}

