package vn.rawuy.laptopshop.controller.client;

import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import vn.rawuy.laptopshop.domain.User;
import vn.rawuy.laptopshop.domain.dto.RegisterDTO;
import vn.rawuy.laptopshop.service.UserService;

@Controller
public class RegisterController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
            BindingResult bindingResult) {
        // List<FieldError> errors = bindingResult.getFieldErrors();
        //// for (FieldError error : errors) {
        //// System.out.println("Field: " + error.getField() + " - Error: " +
        //// error.getDefaultMessage());
        //// }
        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }
        String hashPassword = this.passwordEncoder.encode(registerDTO.getPassword());
        User user = this.userService.registerDTOtoUser(registerDTO);
        user.setPassword(hashPassword);
        user.setRole(userService.getRoleByName("USER"));

        this.userService.handleSaveUser(user);
        return "redirect:/login";
    }

}
