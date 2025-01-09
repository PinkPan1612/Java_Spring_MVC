package vn.hoidanit.laptopshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {
    // DI: dependency injection
    private UserService userService = null;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/hoidanit")
    public String getHomePage() {
        String text = this.userService.handleHello();
        return "eric.html";
    }

}

// @RestController
// public class UserController {
// // DI: dependency injection
// private UserService userService = null;

// public UserController(UserService userService) {
// this.userService = userService;
// }

// @GetMapping("/")
// public String getHomePage() {
// return this.userService.handleHello();
// }
// }
