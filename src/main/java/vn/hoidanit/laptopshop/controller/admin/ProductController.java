package vn.hoidanit.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.hoidanit.laptopshop.domain.Product;

@Controller
public class ProductController {

    @GetMapping("/admin/product")
    public String getDashboard() {
        return "admin/product/show";
    }

    // create user page
    @GetMapping("/admin/product/create") // GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product/create";
    }

    // after click button create user
    @PostMapping("/admin/product/create")
    public String createProductPage(
            @ModelAttribute("newUser") Product product,
            @RequestParam("productFile") MultipartFile file) {

        // String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        // String hashPassword = this.passwordEncoder.encode(hoidanIT.getPassword());
        // hoidanIT.setAvatar(avatar);
        // hoidanIT.setPassword(hashPassword);
        // hoidanIT.setRole(this.userService.getRoleByName(hoidanIT.getRole().getName()));
        // this.userService.handleSaveUser(hoidanIT);
        return "redirect:/admin/product";
    }

}
