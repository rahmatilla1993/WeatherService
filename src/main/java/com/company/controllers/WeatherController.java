package com.company.controllers;

import com.company.config.security.SessionUser;
import com.company.dao.LocationDao;
import com.company.dao.WeatherDao;
import com.company.dto.WeatherDto;
import com.company.entity.AuthUser;
import com.company.entity.Location;
import com.company.entity.WeatherData;
import com.company.exception.NotFoundException;
import com.company.mappers.WeatherMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/weathers")
@RequiredArgsConstructor
@PreAuthorize("isFullyAuthenticated()")
public class WeatherController {

    private final WeatherDao weatherDao;
    private final LocationDao locationDao;
    private final WeatherMapper weatherMapper;
    private final SessionUser sessionUser;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getWeatherData() {
        List<WeatherDto> weatherData = weatherDao.findAll()
                .stream()
                .map(weatherMapper::toDto)
                .toList();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/weather-list");
        modelAndView.addObject("weathers", weatherData);
        return modelAndView;
    }

    @GetMapping("/bySubscribe")
    public String weatherDataBySubscribeLocation(Model model, HttpServletRequest request) {
        String weatherType = Objects.requireNonNullElse(request.getParameter("weatherType"), "day");
        AuthUser authUser = getAuthUser();
        List<WeatherDto> data = weatherDao.findAllBySubscribeLocation(authUser, weatherType)
                .stream()
                .map(weatherMapper::toDto)
                .toList();
        model.addAttribute("weathers", data);
        return "user/weather-data";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getWeatherAddView(Model model) {
        List<String> cities = locationDao.findAll()
                .stream()
                .map(Location::getName)
                .toList();
        model.addAttribute("weather", new WeatherDto());
        model.addAttribute("cities", cities);
        return "admin/weather-add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addWeatherDate(@ModelAttribute("weather") WeatherDto dto) {
        WeatherData weatherData = weatherMapper.toEntity(dto, locationDao);
        weatherDao.save(weatherData);
        return "redirect:/weathers";
    }

    private AuthUser getAuthUser() {
        return sessionUser.getAuthUser()
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
