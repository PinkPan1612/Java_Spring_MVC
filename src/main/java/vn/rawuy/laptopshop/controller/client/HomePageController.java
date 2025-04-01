package vn.rawuy.laptopshop.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.rawuy.laptopshop.domain.Product;
import vn.rawuy.laptopshop.service.ProductService;

@Controller
public class HomePageController {

    private final ProductService productService;

    public HomePageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        List<Product> products = this.productService.handleGetAllProduct();
        model.addAttribute("products", products);

        return "client/homepage/show";
    }

    @GetMapping("/access-deny")
    public String getDenyPage() {

        return "client/auth/deny";
    }

}
