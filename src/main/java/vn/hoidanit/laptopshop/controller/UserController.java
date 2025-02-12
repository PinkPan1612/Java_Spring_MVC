package vn.hoidanit.laptopshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.ui.Model;

import vn.hoidanit.laptopshop.service.UserService;
import vn.hoidanit.laptopshop.domain.User;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    // DI: dependency injection
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // home Page
    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUsers = this.userService.getAllUsersByEmail("2@gmail.com");
        System.out.println(arrUsers);
        model.addAttribute("text", "text");
        model.addAttribute("name", "from controller with model");
        return "hello";
    }

    // table user
    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user/table-user";
    }

    // detail user
    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        System.out.println("check path id = " + id);

        model.addAttribute("id", id);
        User detailsUser = this.userService.getUserById(id);
        model.addAttribute("user", detailsUser);
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/update/{id}")
    public String getUserUpdatePage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("user", currentUser);
        return "admin/user/update";
    }

    // after click button update user
    @PostMapping("/admin/user/update")
    public String postUserUpdate(Model model, @ModelAttribute("user") User hoidanIT) {
        User currentUser = this.userService.getUserById(hoidanIT.getId());
        if (currentUser != null) {
            currentUser.setFullName(hoidanIT.getFullName());
            currentUser.setAddress(hoidanIT.getAddress());
            currentUser.setPhone(hoidanIT.getPhone());
            // không cập nhật email vi email disable
            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    // create user page
    @RequestMapping("/admin/user/create") // GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    // after click button create user
    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model mode, @ModelAttribute("newUser") User hoidanIT) {
        this.userService.handleSaveUser(hoidanIT);
        return "redirect:/admin/user";
    }
}
