package com.kamil.TaskManagement.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping({"/login","/tasks","/tasks/**"})
    public String forwardToWeb() {
        return "forward:/index.html";
    }

}
