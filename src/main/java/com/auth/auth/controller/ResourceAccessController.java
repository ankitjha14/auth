package com.auth.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ResourceAccessController {


    @GetMapping("/resource")
    public String fetchData() {
        return "HelloWorld!!";
    }

    @GetMapping("/unauthresource")
    public String fetchData1() {
        return "HelloWorld!!";
    }

    @GetMapping("/resource1")
    public String fetchData2() {
        return "HelloWorld!!";
    }

    @GetMapping("/unauthresource1")
    public String fetchData3() {
        return "HelloWorld!!";
    }

}
