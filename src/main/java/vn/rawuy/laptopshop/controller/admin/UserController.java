package vn.rawuy.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import vn.rawuy.laptopshop.service.UploadService;
import vn.rawuy.laptopshop.service.UserService;
import vn.rawuy.laptopshop.domain.User;
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

    // // home Page
    // @RequestMapping("/")
    // public String getHomePage(Model model) {
    // // List<User> arrUsers = this.userService.getAllUsersByEmail("2@gmail.com");
    // // System.out.println(arrUsers);
    // model.addAttribute("text", "text");
    // model.addAttribute("name", "from controller with model");
    // return "hello";
    // }

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
    public String postUserUpdate(Model model, @ModelAttribute("user") User rawuy,
            @RequestParam("rawuyFile") MultipartFile file) {

        User currentUser = this.userService.getUserById(rawuy.getId());
        if (currentUser != null) {
            if (!file.isEmpty()) {
                String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
                currentUser.setAvatar(avatar);
            }
            currentUser.setFullName(rawuy.getFullName());
            currentUser.setAddress(rawuy.getAddress());
            currentUser.setPhone(rawuy.getPhone());
            currentUser.setRole(this.userService.getRoleByName(rawuy.getRole().getName()));
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

    @PostMapping("/admin/user/create")
    public String createUserPage(Model model,
            @ModelAttribute("newUser") @Valid User rawuy, BindingResult newUserBindingResult,
            @RequestParam("rawuyFile") MultipartFile file) {

        // Kiểm tra nếu có lỗi validation
        if (newUserBindingResult.hasErrors()) {
            System.out.println("Validation failed. Errors:");
            return "admin/user/create";
        }

        // Nếu không có lỗi, tiếp tục xử lý dữ liệu
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(rawuy.getPassword());
        rawuy.setAvatar(avatar);
        rawuy.setPassword(hashPassword);
        rawuy.setRole(this.userService.getRoleByName(rawuy.getRole().getName()));

        this.userService.handleSaveUser(rawuy);
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
    public String postDeleteUser(Model model, @ModelAttribute("user") User rawuy) {
        this.userService.deleteAUser(rawuy.getId());
        return "redirect:/admin/user";
    }

}
