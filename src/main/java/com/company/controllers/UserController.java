package com.company.controllers;

import com.company.config.security.SessionUser;
import com.company.dao.LocationDao;
import com.company.dto.LocationDto;
import com.company.entity.AuthUser;
import com.company.entity.Location;
import com.company.exception.NotFoundException;
import com.company.mappers.LocationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/users/locations")
@RequiredArgsConstructor
@PreAuthorize("isFullyAuthenticated()")
public class UserController {

    private final LocationDao locationDao;
    private final LocationMapper locationMapper;
    private final SessionUser sessionUser;

    @GetMapping
    public ModelAndView getLocationsView() {
        ModelAndView modelAndView = new ModelAndView();
        List<LocationDto> locations = locationDao.findAll()
                .stream()
                .map(locationMapper::toLocationReadDto)
                .toList();
        modelAndView.addObject("locations", locations);
        modelAndView.setViewName("user/locations");
        return modelAndView;
    }

    @GetMapping("/subscribe/{id}")
    public String subscribeLocation(@PathVariable("id") long id) {
        Location location = getLocation(id);
        AuthUser authUser = getAuthUser();
        List<AuthUser> subscribeUsers = location.getSubscribeUsers();
        if (subscribeUsers.contains(authUser)) {
            subscribeUsers.remove(authUser);
        } else {
            subscribeUsers.add(authUser);
        }
        locationDao.edit(location);
        return "redirect:/users/locations";
    }

    private Location getLocation(long id) {
        return locationDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Location with '%d' id not found".formatted(id)));
    }

    private AuthUser getAuthUser() {
        return sessionUser.getAuthUser()
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
