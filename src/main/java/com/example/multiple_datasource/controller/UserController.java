package com.example.multiple_datasource.controller;


import com.example.multiple_datasource.constants.Constants;
import com.example.multiple_datasource.dto.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class UserController {

    @GetMapping("/v1/hello")
    public ResponseEntity<APIResponse> sayHello(@RequestParam(name = "name") String name){
        return ResponseEntity.ok().body(new APIResponse(Constants.SUCCESS, "Hello " + name, new ArrayList<>()));
    }
}
