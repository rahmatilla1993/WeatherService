package com.company.handlers;

import com.company.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice("com.company")
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ModelAndView handle(NotFoundException e, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("path", request.getRequestURI());
        modelAndView.setViewName("error/404");
        return modelAndView;
    }
}
