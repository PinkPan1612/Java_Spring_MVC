package vn.rawuy.laptopshop.service.specification;

import org.springframework.data.jpa.domain.Specification;

import vn.rawuy.laptopshop.domain.Product;
import vn.rawuy.laptopshop.domain.Product_;

public class ProductSpecs {
      // search product by name
    public static Specification<Product> nameLike(String string) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), "%" + string + "%");
    }

    
}
