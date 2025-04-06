package vn.rawuy.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.rawuy.laptopshop.domain.Order;
import vn.rawuy.laptopshop.domain.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUser(User user);
    Order findById(long id);
    void deleteById(long id);

}
