package com.springboot.demo.app.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunRestController {
    // inject properties for : coach.name and team.name

    @Value("${coach.name}")
    private String coachName;

    @Value("${team.name}")
    private String teamName;

    // expose new endpoint for "team info"
    @GetMapping("/teaminfo")
    public String getTeamInfo()
    {
        return "Coach - " + coachName + ", Team Name - " + teamName;
    }

    @GetMapping("/")
    public String sayHello(){
        return "Hello World";
    }

    @GetMapping("/workout")
    public String getdailyWorkout(){
        return "Run Hard";
    }
}
