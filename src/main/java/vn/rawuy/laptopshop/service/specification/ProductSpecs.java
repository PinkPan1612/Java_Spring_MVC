package vn.rawuy.laptopshop.service.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import vn.rawuy.laptopshop.domain.Product;
import vn.rawuy.laptopshop.domain.Product_;

public class ProductSpecs {
    // filter product by name
    public static Specification<Product> matchName(String string) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), "%" + string + "%");
    }

    // filter product by minprice
    public static Specification<Product> minPrice(double price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.ge(root.get(Product_.PRICE), price);
    }

    // filter product by maxprice
    public static Specification<Product> maxPrice(double price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.le(root.get(Product_.PRICE), price);
    }

    // filter product by one Factory
    public static Specification<Product> equalFactory(String factory) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Product_.FACTORY), factory);
    }

    // filter product by more Factory
    public static Specification<Product> inFactory(List<String> factories) {
        return (root, query, criteriaBuilder) -> {
            if (factories == null || factories.isEmpty()) {
                return criteriaBuilder.conjunction(); // no filtering if list is empty
            }

            CriteriaBuilder.In<String> inClause = criteriaBuilder.in(root.get(Product_.FACTORY));
            for (String factory : factories) {
                inClause.value(factory);
            }
            return inClause;
        };
    }

    // filter product by more target
    public static Specification<Product> inTarget(List<String> target) {
        return (root, query, criteriaBuilder) -> {
            if (target == null || target.isEmpty()) {
                return criteriaBuilder.conjunction(); // no filtering if list is empty
            }

            CriteriaBuilder.In<String> inClause = criteriaBuilder.in(root.get(Product_.TARGET));
            for (String ta : target) {
                inClause.value(ta);
            }
            return inClause;
        };
    }

    // search at min- max price
    // cách viết 1
    public static Specification<Product> minMaxPrice(double min, double max) {
        return (root, query, criteriaBuilder) -> {
            if (min >= max) {
                return criteriaBuilder.conjunction(); // no filtering if parameter is invalid
            }
            return criteriaBuilder.between(root.get(Product_.PRICE), min, max);
        };
    }

    // // cách viết 2
    // public static Specification<Product> minMaxPrice2(double min, double max) {
    // return (root, query, criteriaBuilder) ->
    // criteriaBuilder.between(root.get(Product_.PRICE), min, max);
    // };

    // // cách viết 3
    // public static Specification<Product> minMaxPrice3(double min, double max) {
    // return (root, query, criteriaBuilder) ->
    // criteriaBuilder.and(criteriaBuilder.ge(root.get(Product_.PRICE), min),
    // criteriaBuilder.le(root.get(Product_.PRICE), max));
    // };
   
}
