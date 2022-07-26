package lt.bit.products.ui.controller;

import lt.bit.products.ui.service.OrderService;
import lt.bit.products.ui.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;

import static lt.bit.products.ui.controller.ControllerBase.ADMIN_PATH;
import static lt.bit.products.ui.controller.OrderController.ORDERS_PATH;


@Controller
@RequestMapping(ADMIN_PATH + ORDERS_PATH)
class OrderController extends ControllerBase{

    protected static final String ORDERS_PATH = "/orders";
    private final OrderService orderService;

    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    String showOrders(Model model, HttpServletRequest request) {
        if (!userService.isAuthenticated()) {
            return "login";
        }

        model.addAttribute("orders",  orderService.getOrders());
        return "admin/orderList";
    }
}
