package vn.rawuy.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.rawuy.laptopshop.domain.Product;
import vn.rawuy.laptopshop.service.ProductService;
import vn.rawuy.laptopshop.service.UploadService;

@Controller
public class ProductController {

    private final UploadService uploadService;
    private final ProductService productService;

    public ProductController(UploadService uploadService, ProductService productService) {
        this.uploadService = uploadService;
        this.productService = productService;
    }

    // table product
    @GetMapping("/admin/product")
    public String getProductTable(Model model) {
        List<Product> products = productService.handleGetAllProduct();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    // create product page
    @GetMapping("/admin/product/create") // GET
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    // after click button create product
    @PostMapping("/admin/product/create")
    public String createProductPage(
            @ModelAttribute("newProduct") @Valid Product newProduct,
            BindingResult newProductBindingResult,
            @RequestParam("productFile") MultipartFile file) {

        // Kiểm tra nếu có lỗi validation
        if (newProductBindingResult.hasErrors()) {
            System.out.println("Validation failed. Errors:");

            List<FieldError> errors = newProductBindingResult.getFieldErrors();
            for (FieldError error : errors) {
                System.out.println("Field: " + error.getField() + " - Error: " + error.getDefaultMessage());
            }

            return "admin/product/create";
        }
        String product_image = this.uploadService.handleSaveUploadFile(file, "product");
        newProduct.setImage(product_image);
        this.productService.handleCreateProduct(newProduct);
        return "redirect:/admin/product";
    }

    // detail product page
    @GetMapping("/admin/product/{id}") // GET
    public String getDetailProductPage(Model model,
            @PathVariable long id) {
        System.out.println("check path id = " + id);
        model.addAttribute("id", id);

        Product detailProduct = this.productService.handleGetOneProductById(id).get();
        model.addAttribute("product", detailProduct);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getProductUpdatePage(Model model, @PathVariable long id) {
        Optional<Product> currentproduct = this.productService.handleGetOneProductById(id);
        model.addAttribute("updateProduct", currentproduct.get());
        return "admin/product/update";
    }

    // done update product page
    @PostMapping("/admin/product/update") // Post
    public String postProductUpdate(
            @ModelAttribute("updateProduct") @Valid Product product,
            BindingResult bindingResult,
            @RequestParam("productFile") MultipartFile file) {
        // Kiểm tra nếu có lỗi validation
        if (bindingResult.hasErrors()) {
            // System.out.println("Validation failed. Errors:");
            // List<FieldError> errors = bindingResult.getFieldErrors();
            // for (FieldError error : errors) {
            // System.out.println("Field: " + error.getField() + " - Error: " +
            // error.getDefaultMessage());
            // }
            return "admin/product/update";
        }
        Product currentProduct = productService.handleGetOneProductById(product.getId()).get();
        if (currentProduct != null) {
            if (!file.isEmpty()) {
                String product_image = this.uploadService.handleSaveUploadFile(file, "product");
                currentProduct.setImage(product_image);
            }

            currentProduct.setDetailDesc(product.getDetailDesc());
            currentProduct.setFactory(product.getFactory());
            currentProduct.setName(product.getName());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setQuantity(product.getQuantity());
            currentProduct.setShortDesc(product.getShortDesc());
            currentProduct.setTarget(product.getTarget());

            this.productService.handleCreateProduct(currentProduct);
        }
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("id", id);
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDeleteProduct(@ModelAttribute("product") Product product) {
        this.productService.handleDeleteProductById(product.getId());
        return "redirect:/admin/product";
    }
}
