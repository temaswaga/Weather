package model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {
    private Long id;
    private Main main;
    private List<Weather> weather;
    private Wind wind;
    private Clouds clouds;
    private String name;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        private Double temp;

        @JsonProperty("feels_like")
        private Double feelsLike;

        @JsonProperty("temp_min")
        private Double tempMin;

        @JsonProperty("temp_max")
        private Double tempMax;

        private Integer pressure;
        private Integer humidity;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        private Integer id;
        private String main;
        private String description;
        private String icon;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Wind {
        private Double speed;
        private Integer deg;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Clouds {
        private Integer all;
    }

    public Double getTemperature() {
        if (main != null && main.getTemp() != null) {
            return main.getTemp();
        }
        return null;
    }

    public String getDescription() {
        if (weather != null && !weather.isEmpty()) {
            return weather.get(0).getDescription();
        }
        return "";
    }

    public String getIcon() {
        if (weather != null && !weather.isEmpty()) {
            return weather.get(0).getIcon();
        }
        return null;
    }

    public Double getWindSpeed() {
        if (wind != null) {
            return wind.getSpeed();
        }
        return null;
    }

    public Integer getHumidity() {
        if (main != null) {
            return main.getHumidity();
        }
        return null;
    }
}
