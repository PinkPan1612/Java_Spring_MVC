package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.ui.Model;

import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;
import vn.hoidanit.laptopshop.domain.User;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    // DI: dependency injection
    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UploadService uploadService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    // home Page
    @RequestMapping("/")
    public String getHomePage(Model model) {
        // List<User> arrUsers = this.userService.getAllUsersByEmail("2@gmail.com");
        // System.out.println(arrUsers);
        model.addAttribute("text", "text");
        model.addAttribute("name", "from controller with model");
        return "hello";
    }

    // table user
    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user/show";
    }

    // detail user
    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        System.out.println("check path id = " + id);

        model.addAttribute("id", id);
        User detailsUser = this.userService.getUserById(id);
        model.addAttribute("user", detailsUser);
        return "admin/user/detail";
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
    @GetMapping("/admin/user/create") // GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    // after click button create user
    @PostMapping("/admin/user/create")
    public String createUserPage(Model mode,
            @ModelAttribute("newUser") User hoidanIT,
            @RequestParam("hoidanitFile") MultipartFile file) {

        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(hoidanIT.getPassword());
        hoidanIT.setAvatar(avatar);
        hoidanIT.setPassword(hashPassword);
        hoidanIT.setRole(this.userService.getRoleByName(hoidanIT.getRole().getName()));
        this.userService.handleSaveUser(hoidanIT);
        return "redirect:/admin/user";
    }

    // open delete user page
    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("user", new User());
        return "admin/user/delete";

    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("user") User hoidanIT) {
        this.userService.deleteAUser(hoidanIT.getId());
        return "redirect:/admin/user";
    }

    //

}
