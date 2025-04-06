package vn.rawuy.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.rawuy.laptopshop.domain.Order;
import vn.rawuy.laptopshop.domain.OrderDetail;
import vn.rawuy.laptopshop.service.OrderDetailService;
import vn.rawuy.laptopshop.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    public OrderController(OrderService orderService, OrderDetailService orderDetailService) {
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/admin/order")
    public String getDashboard(Model model) {
        List<Order> orders = this.orderService.hanldeGetAllOrder();
        model.addAttribute("orders", orders);
        return "admin/order/show";
    }

    @GetMapping("/admin/order/{id}")
    public String getOrderDetailPage(Model model, @PathVariable("id") long id) {
        Order order = this.orderService.fetchOrderById(id);
        List<OrderDetail> orderDetails = this.orderDetailService.getAllOrderDetailsByOrderID(order);
        model.addAttribute("orderDetails", orderDetails);
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getOrderUpdatePage(Model model, @PathVariable("id") long id) {
        Order order = this.orderService.fetchOrderById(id);
        model.addAttribute("orderUpdate", order);
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String postUpdateOrder(@ModelAttribute("orderUpdate") Order newOrder) {
        String status = newOrder.getStatus();
        Order order = this.orderService.fetchOrderById(newOrder.getId());
        order.setStatus(status);
        this.orderService.handleSaveOrder(order);
        return "redirect:/admin/order";
    }

}
