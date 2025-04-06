package vn.rawuy.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.rawuy.laptopshop.domain.Order;
import vn.rawuy.laptopshop.domain.OrderDetail;
import vn.rawuy.laptopshop.repository.OrderDetailRepository;

@Service
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    public void deleteOrderDetailById(Long id) {
        orderDetailRepository.deleteById(id);
    }

    public List<OrderDetail> getAllOrderDetailsByOrderID(Order order) {
        return orderDetailRepository.findByOrder(order);
    }
}
