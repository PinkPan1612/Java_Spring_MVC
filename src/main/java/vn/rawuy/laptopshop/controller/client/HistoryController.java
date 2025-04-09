package vn.rawuy.laptopshop.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.rawuy.laptopshop.domain.Order;
import vn.rawuy.laptopshop.domain.User;
import vn.rawuy.laptopshop.service.OrderService;

@Controller
public class HistoryController {
    private OrderService orderService;

    // constructor
    public HistoryController(OrderService orderService) {
        this.orderService = orderService;
    }

    // open history page
    @GetMapping("/history")
    public String getHistoryPage(Model model, HttpServletRequest request) {
        User user = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        user.setId(id);

        List<Order> order = this.orderService.fetchOrderByUser(user);

        if (order.isEmpty()) {
            model.addAttribute("message", "Bạn chưa có đơn hàng nào");
            return "client/history/show";
        }

        model.addAttribute("orders", order);
        return "client/history/show";
    }
}
