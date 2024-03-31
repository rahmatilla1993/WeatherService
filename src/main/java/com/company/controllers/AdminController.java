package com.company.controllers;

import com.company.dao.AuthUserDao;
import com.company.dto.UserDetailsDto;
import com.company.dto.UserReadDto;
import com.company.entity.AuthUser;
import com.company.enums.Status;
import com.company.exception.NotFoundException;
import com.company.mappers.AuthUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final AuthUserDao authUserDao;
    private final AuthUserMapper authUserMapper;

    @GetMapping("/users")
    public String userList(Model model) {
        List<UserReadDto> users = authUserDao.findAll()
                .stream()
                .map(authUserMapper::toDto)
                .toList();
        model.addAttribute("users", users);
        return "admin/user-list";
    }

    @GetMapping("/user_details/{id}")
    public String getUserDetailsView(@PathVariable("id") long id, Model model) {
        UserDetailsDto dto = authUserMapper.toUserDetailsDto(getUser(id));
        model.addAttribute("user", dto);
        return "admin/user-detail";
    }

    @PutMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") long id, @ModelAttribute("user") UserDetailsDto dto) {
        AuthUser user = getUser(id);
        user.setStatus(Status.valueOf(dto.getStatus()));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        authUserDao.edit(user);
        return "redirect:/admin/users";
    }

    private AuthUser getUser(long id) {
        return authUserDao.findById(id)
                .orElseThrow(() -> new NotFoundException("User with '%d' id not found!".formatted(id)));
    }
}
