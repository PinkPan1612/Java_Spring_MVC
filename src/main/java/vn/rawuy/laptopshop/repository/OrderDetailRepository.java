package vn.rawuy.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.rawuy.laptopshop.domain.Order;
import vn.rawuy.laptopshop.domain.OrderDetail;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrder(Order order);

    void deleteByOrder(Order order); // Xóa tất cả OrderDetail theo Order
}
