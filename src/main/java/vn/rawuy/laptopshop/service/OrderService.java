package vn.rawuy.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.rawuy.laptopshop.domain.Order;
import vn.rawuy.laptopshop.domain.OrderDetail;
import vn.rawuy.laptopshop.domain.User;
import vn.rawuy.laptopshop.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;

    public OrderService(OrderRepository orderRepository, OrderDetailService orderDetailService) {
        this.orderRepository = orderRepository;
        this.orderDetailService = orderDetailService;
    }

    // find
    public List<Order> hanldeGetAllOrder() {
        return this.orderRepository.findAll();
    }

    // fetch order by id
    public Order fetchOrderById(long id) {
        return this.orderRepository.findById(id);
    }

    // lưu đơn hàng
    public void handleSaveOrder(Order order) {
        this.orderRepository.save(order);
    }

    // delete order by id
    public void handleDeleteOrder(long id) {

        Order order = this.orderRepository.findById(id);
        List<OrderDetail> orderDetails = this.orderDetailService.getAllOrderDetailsByOrderID(order);

        if (orderDetails != null) {
            for (OrderDetail orderDetail : orderDetails) {
                this.orderDetailService.deleteOrderDetail(orderDetail);

            }
            this.orderRepository.deleteById(id);
        }
    }

    // find by user
    public List<Order> fetchOrderByUser(User user) {
        return this.orderRepository.findByUser(user);
    }

}
