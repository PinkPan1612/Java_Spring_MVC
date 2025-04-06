package vn.rawuy.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.rawuy.laptopshop.domain.Order;
import vn.rawuy.laptopshop.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // find
    public List<Order> hanldeGetAllOrder() {
        return this.orderRepository.findAll();
    }

    // fetch order by id
    public Order fetchOrderById(long id) {
        return this.orderRepository.findById(id);
    }

    public void handleSaveOrder(Order order) {
        this.orderRepository.save(order);
    }

}
