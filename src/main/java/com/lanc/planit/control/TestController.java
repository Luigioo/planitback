package com.lanc.planit.control;

import com.lanc.planit.model.MyUserRole;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String welcome(){
        return "welcome";
    }

    @GetMapping("/user")
    public String user(){
        return "user";
    }


}
