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
import vn.rawuy.laptopshop.service.UserService;

@Controller
public class HistoryController {
    private OrderService orderService;
    private final UserService userService;

    public HistoryController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/history")
    public String getHistory(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        User user = this.userService.getUserById(id);
        List<Order> order = this.orderService.handleGetOrderByUser(user);
        if (order == null) {
            model.addAttribute("message", "No order found for this user.");
            return "client/history/show";
        }

        model.addAttribute("orders", order);
        return "client/history/show";
    }
}
