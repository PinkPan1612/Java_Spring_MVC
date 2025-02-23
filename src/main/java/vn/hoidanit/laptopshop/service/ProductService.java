package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // save product
    public Product handleCreateProduct(Product product) {
        System.out.println(product.toString());
        return this.productRepository.save(product);
    }

    public Product handleUpdateProduct(Product product) {
        System.out.println(product.toString());
        return this.productRepository.save(product);
    }

    // getAll product
    public List<Product> handleGetAllProduct() {
        return this.productRepository.findAll();
    }

    // get one product
    public Optional<Product> handleGetOneProductById(long id) {
        return this.productRepository.findById(id);
    }

    // delete one product
    public Product handleDeleteProductById(long id) {
        return this.productRepository.deleteById(id);
    }
}
