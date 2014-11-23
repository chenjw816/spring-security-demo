package com.demo.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by yamorn on 2014/11/23.
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    @RequestMapping("/websocket")
    public String webSocket(){
        return "demo/websocket";
    }
}
