package vn.hoidanit.laptopshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {
    // DI: dependency injection
    private UserService userService = null;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        String text = this.userService.handleHello();
        model.addAttribute("text", text);
        model.addAttribute("name", "from controller with model");
        return "hello";
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
