package model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDto {
    private long id;
    private String name;

    @JsonProperty("local_names")
    private java.util.Map<String, String> localNames;

    private BigDecimal lat;
    private BigDecimal lon;
    private String country;
    private String state;
}
