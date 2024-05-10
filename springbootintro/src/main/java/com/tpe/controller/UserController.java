package com.tpe.controller;

import com.tpe.domain.User;
import com.tpe.dto.UserRequest;
import com.tpe.repository.UserRepository;
import com.tpe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/register") // http://localhost:8081/register
public class UserController {

    @Autowired
    private UserService userService;

    //Not: Register() ********************************************
    @PostMapping // http://localhost:8081/register  + POST + JSON
    public ResponseEntity<String> register(@Valid @RequestBody UserRequest userRequest){
        userService.saveUser(userRequest);
        String myResponse = "User Registered Successfully";
        return new ResponseEntity<>(myResponse, HttpStatus.CREATED);

    }
}
