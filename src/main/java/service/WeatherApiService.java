package service;

import lombok.RequiredArgsConstructor;
import model.dto.LocationDto;
import model.dto.WeatherDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WeatherApiService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private static final String GEO_API_URL = "https://api.openweathermap.org/geo/1.0/direct";
    private static final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather";

    public List<LocationDto> findLocations(String query) {
        String url = UriComponentsBuilder.fromHttpUrl(GEO_API_URL)
                .queryParam("q", query)
                .queryParam("limit", 5)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .toUriString();

        try {
            ResponseEntity<LocationDto[]> response = restTemplate.getForEntity(
                    url,
                    LocationDto[].class
            );

            if (response.getBody() != null) {
                return Arrays.asList(response.getBody());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No connection to API");
        }

        return Collections.emptyList();
    }

    public WeatherDto getWeather(BigDecimal latitude, BigDecimal longitude) {
        String url = UriComponentsBuilder.fromHttpUrl(WEATHER_API_URL)
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .toUriString();

        try {
            ResponseEntity<WeatherDto> response = restTemplate.getForEntity(
                    url,
                    WeatherDto.class
            );

            if (response.getBody() != null) {
                return response.getBody();
            }

        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("No connection to API");
        }
        return null;
    }
}
