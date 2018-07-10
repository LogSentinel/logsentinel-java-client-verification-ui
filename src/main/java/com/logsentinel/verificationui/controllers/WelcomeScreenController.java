package com.logsentinel.verificationui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;

@Controller
public class WelcomeScreenController {

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        return "welcomeScreen";
    }
}
