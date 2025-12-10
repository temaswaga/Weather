package controller;

import lombok.RequiredArgsConstructor;
import model.dto.LocationDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.LocationService;
import service.UserService;
import service.WeatherApiService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class WeatherController {
    private final LocationService locationService;
    private final UserService userService;
    private final WeatherApiService weatherApiService;

    @GetMapping("/")
    public String index(@CookieValue(value = "MY_SESSION_ID", required = false) String sessionId, Model model) {
        if (sessionId != null) {
            model.addAttribute("user", userService.getUserBySessionId(sessionId));
            model.addAttribute("locations", locationService.getUsersLocationsWithWeatherBySessionId(sessionId));
            return "index";
        }
        return "redirect:/sign-in";
    }

    @GetMapping("/search")
    public String search(@CookieValue(value = "MY_SESSION_ID", required = false) String sessionId, @RequestParam("name") String locationName, Model model) {
        List<LocationDto> foundLocations = weatherApiService.findLocations(locationName);

        model.addAttribute("user", userService.getUserBySessionId(sessionId));
        model.addAttribute("searchResults", foundLocations);
        model.addAttribute("searchQuery", locationName);

        return "search-results";
    }

    @PostMapping("/location/add")
    public String addLocation(@ModelAttribute("locationDto") LocationDto locationDto, @CookieValue(value = "MY_SESSION_ID", required = false) String sessionId, Model model) {
        locationService.addLocation(locationDto, sessionId);
        return "redirect:/";
    }
}
