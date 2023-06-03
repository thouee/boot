package me.th.serializer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boot")
public class Controller {

    @GetMapping("/user")
    public User user() {
        User user = new User();
        user.setId(1);
        user.setType(1);
        return user;
    }
}
