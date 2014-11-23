package com.demo.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yamorn on 2014/11/22.
 */
@Controller
public class AuthenticationController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(required = false) String error,HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }
        model.setViewName("authentication/login");
        return model;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String loginSuccess() {
        return "index";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(@RequestParam(required = false) String logout) {
        return "authentication/login";
    }

    @RequestMapping(value = "/expire", method = RequestMethod.POST)
    public String expire() {
        return "authentication/expire";
    }

}
