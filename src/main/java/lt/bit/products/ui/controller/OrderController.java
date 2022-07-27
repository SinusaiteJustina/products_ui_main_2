package lt.bit.products.ui.controller;

import lt.bit.products.ui.service.OrderService;
import lt.bit.products.ui.service.UserService;
import lt.bit.products.ui.service.domain.OrderStatus;
import lt.bit.products.ui.service.domain.UserStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import static lt.bit.products.ui.controller.ControllerBase.ADMIN_PATH;
import static lt.bit.products.ui.controller.OrderController.ORDERS_PATH;


@Controller
@RequestMapping(ADMIN_PATH)
class OrderController extends ControllerBase{

    protected static final String ORDERS_PATH = "/orders";
    private final OrderService orderService;

    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/orders")
    String showOrders(Model model, HttpServletRequest request) {
        if (!userService.isAuthenticated()) {
            return "login";
        }

        model.addAttribute("orders",  orderService.getOrders());
        return "admin/orderList";
    }
    @GetMapping("admin/orders/confirm")
    String confirmOrder(@RequestParam String id) {
        if (!userService.isAuthenticated()) {
            return "login";
        }
        orderService.changeStatus(OrderStatus.CONFIRMED, id);
        return "redirect:" + ADMIN_PATH + ORDERS_PATH;
    }
    @GetMapping("admin/orders/reject")
    String rejectOrder(@RequestParam String id) {
        if (!userService.isAuthenticated()) {
            return "login";
        }
        orderService.changeStatus(OrderStatus.REJECTED, id);
        return "redirect:" + ADMIN_PATH + ORDERS_PATH;
    }
    @GetMapping("admin/orders/complete")
    String completeOrder(@RequestParam String id) {
        if (!userService.isAuthenticated()) {
            return "login";
        }
        orderService.changeStatus(OrderStatus.COMPLETED, id);
        return "redirect:" + ADMIN_PATH + ORDERS_PATH;
    }
}
