package com.lanc.planit.control;

import com.lanc.planit.model.MyUser;
import com.lanc.planit.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
public class MyUserController {

    @Autowired
    private MyUserService service;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody MyUser user){

        if(user.getUsername()==null||user.getPassword()==null){
            return new ResponseEntity<Object>("Invalid username or password", HttpStatus.BAD_REQUEST);
        }

        if(service.doesUserExist(user.getName())){
            return new ResponseEntity<Object>("Username already used", HttpStatus.FOUND);
        }

        service.signUpUser(user);

        return new ResponseEntity<Object>("Signup success", HttpStatus.OK);
    }

    @GetMapping("/exist/{name}")
    public ResponseEntity<Object> exist(@PathVariable String name){
        String msg = "not exist";
        if(service.doesUserExist(name)){
            msg = "exist";
        }
        return new ResponseEntity<Object>(msg, HttpStatus.OK);
    }


}
