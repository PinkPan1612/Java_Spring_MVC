package vn.rawuy.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.rawuy.laptopshop.domain.Product;
import vn.rawuy.laptopshop.domain.dto.ProductCriteriaDTO;
import vn.rawuy.laptopshop.service.ProductService;

@Controller
public class HomePageController {

    private final ProductService productService;

    public HomePageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        // List<Product> products = this.productService.fetchProducts();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> prs = this.productService.fetchProducts(pageable);
        List<Product> products = prs.getContent();

        model.addAttribute("products", products);

        return "client/homepage/show";
    }

    @GetMapping("/access-deny")
    public String getDenyPage() {

        return "client/auth/deny";
    }

    @GetMapping("/products")
    public String getProductsPage(Model model,
            ProductCriteriaDTO productCriteriaDTO) {

        int page = 1;
        try {
            if (productCriteriaDTO.getPage().isPresent()) {
                // convert from String to int
                page = Integer.parseInt(productCriteriaDTO.getPage().get());
            } else {
                // page = 1;
            }
        } catch (Exception e) {
            // page = 1
        }

        Pageable pageable = PageRequest.of(page - 1, 6);
        Page<Product> products = productService.fetchProductsWithSpec(pageable, productCriteriaDTO);
        // chuyển từ kiểu Page sang List để hiển thị hợp lệ lên view
        List<Product> productList = products.getContent().size() > 0 ? products.getContent() : new ArrayList<Product>();

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("products", productList);
        return "client/product/show";
    }

}
