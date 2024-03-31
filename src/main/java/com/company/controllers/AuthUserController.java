package com.company.controllers;

import com.company.dao.AuthUserDao;
import com.company.dto.RegisterDto;
import com.company.entity.AuthUser;
import com.company.enums.Role;
import com.company.enums.Status;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthUserController {

    private final AuthUserDao authUserDao;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String getRegisterView(Model model) {
        model.addAttribute("dto", new RegisterDto());
        return "auth/register";
    }

    @GetMapping("/login")
    public ModelAndView getLoginView(@RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/login");
        modelAndView.addObject("errorMsg", error);
        return modelAndView;
    }

    @GetMapping("/logout")
    public String getLogoutPage() {
        return "auth/logout";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("dto") RegisterDto dto,
                           BindingResult errors) {
        System.out.println(dto);
        if (errors.hasErrors()) {
            return "auth/register";
        }
        authUserDao.findByEmail(dto.getEmail())
                .ifPresentOrElse(authUser -> {
                    errors.rejectValue("email", "", "This email already taken");
                }, () -> {
                    authUserDao.save(AuthUser.builder()
                            .firstName(dto.getFirstname())
                            .lastName(dto.getLastname())
                            .email(dto.getEmail())
                            .password(passwordEncoder.encode(dto.getPassword()))
                            .role(Role.ROLE_USER)
                            .status(Status.ACTIVE)
                            .build());
                });
        if (errors.hasErrors()) {
            return "auth/register";
        }
        return "redirect:/api/auth/login";
    }
}
