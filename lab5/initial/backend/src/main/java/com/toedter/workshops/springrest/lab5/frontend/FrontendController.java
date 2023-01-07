package com.toedter.workshops.springrest.lab5.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {
    @RequestMapping({"/movies", "/movies/{id:\\w+}", "/directors", "/about"})
    public String index() {
        return "forward:/index.html";
    }
}