package service;

import lombok.RequiredArgsConstructor;
import mapper.LocationMapper;
import model.dto.LocationDto;
import model.dto.WeatherResponseDto;
import model.entity.Location;
import model.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
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

        public List<WeatherResponseDto> getUsersLocationsWithWeatherBySessionId(String sessionId) {
        List<WeatherResponseDto> weatherResponseDtos = new ArrayList<>();

        for (LocationDto location : getUsersLocationsBySessionId(sessionId)) {
            weatherResponseDtos.add(weatherApiService.getWeather(location.getLat(), location.getLon()));
        }

        return weatherResponseDtos;
    }

    @Transactional
    public void addLocation (LocationDto locationDto, String sessionId){
        User userId = userService.getUserBySessionId(sessionId);
        Location location = LocationMapper.toEntity(locationDto);
        location.setUserid(userId);

        locationRepository.save(location);
    }
}

