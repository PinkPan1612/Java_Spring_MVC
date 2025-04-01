package vn.rawuy.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.rawuy.laptopshop.domain.Cart;
import vn.rawuy.laptopshop.domain.CartDetail;
import vn.rawuy.laptopshop.domain.Product;
import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    boolean existsByCartAndProduct(Cart cart, Product product);

    CartDetail findByCartAndProduct(Cart cart, Product product);

    List<CartDetail> findByCart(Cart cart);
}