package com.company.controllers;

import com.company.dao.LocationDao;
import com.company.dto.LocationDto;
import com.company.entity.Location;
import com.company.exception.NotFoundException;
import com.company.mappers.LocationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/locations")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class LocationController {

    private final LocationDao locationDao;
    private final LocationMapper locationMapper;

    @GetMapping
    public ModelAndView getLocationView() {
        ModelAndView modelAndView = new ModelAndView();
        List<LocationDto> locations = locationDao.findAll()
                .stream()
                .map(locationMapper::toLocationReadDto)
                .toList();
        modelAndView.addObject("locations", locations);
        modelAndView.setViewName("admin/locations-list");
        return modelAndView;
    }

    @GetMapping("/add")
    public String addView(Model model) {
        model.addAttribute("location", new LocationDto());
        return "admin/location-add";
    }

    @GetMapping("/edit/{id}")
    public String getEditView(@PathVariable("id") long id, Model model) {
        Location location = getLocation(id);
        LocationDto dto = locationMapper.toLocationReadDto(location);
        model.addAttribute("location", dto);
        return "admin/location-edit";
    }

    @PostMapping("/add")
    public String addLocation(@ModelAttribute("location") LocationDto dto) {
        Location location = locationMapper.toEntity(dto);
        locationDao.save(location);
        return "redirect:/locations";
    }

    @PutMapping("/edit/{id}")
    public String editLocation(@PathVariable("id") long id, @ModelAttribute("location") LocationDto dto) {
        Location location = getLocation(id);
        location.setLatitude(dto.getLatitude());
        location.setLongitude(dto.getLongitude());
        location.setName(dto.getName());
        locationDao.edit(location);
        return "redirect:/locations";
    }

    private Location getLocation(long id) {
        return locationDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Location with '%d' id not found".formatted(id)));
    }
}
