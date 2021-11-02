package com.sfuit.Auth.contorller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @GetMapping("")
    public String getAllCategories(HttpServletRequest request)
    {
        String payload = (String) request.getAttribute("data");
        return "Authenticated! User: " + payload;
    }
}
