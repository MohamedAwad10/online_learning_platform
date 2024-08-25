package com.gdsc.OnlineLearningPlatform.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/onlinelearning")
public class HomeController {

    @RequestMapping("/")
    public String sayHello(){
        return "Hello World!!";
    }
}
