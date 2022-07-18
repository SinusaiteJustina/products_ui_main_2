package lt.bit.products.ui.controller;

import lt.bit.products.ui.model.User;
import lt.bit.products.ui.service.CartService;
import lt.bit.products.ui.service.UserService;
import lt.bit.products.ui.service.domain.UserRole;
import lt.bit.products.ui.service.domain.UserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.UUID;

import static lt.bit.products.ui.controller.ControllerBase.ADMIN_PATH;

@Controller
class CustomerSiteController {
    protected static final String PRODUCTS_PATH = "/products";
    private final CartService cartService;
    private final UserService userService;

    private final static Logger LOG = LoggerFactory.getLogger(CustomerSiteController.class);

    CustomerSiteController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PostMapping("/cart/add")
    @ResponseBody
    ModelAndView addToCart(@RequestParam UUID productId, @RequestParam String productName,
                           @RequestParam BigDecimal productPrice) {
        cartService.addToCart(productId, productName, productPrice);
        cartService.getCartAmount();
        return getCartItemsWithModelAndView();

    }

    @GetMapping("/cart/{id}/remove")
    @ResponseBody
    int removeFromCart(@PathVariable("id") UUID productId) {
        cartService.removeFromCart(productId);
        return cartService.getTotalCartItems();

    }

    @GetMapping("/cart/amount")
    @ResponseBody
    BigDecimal getCartAmount() {
        return cartService.getCartAmount();

    }

    private ModelAndView getCartItemsWithModelAndView() {
        ModelAndView mv = new ModelAndView("cartItems");
        mv.addObject("cartItems", cartService.getCartItems());
        return mv;
    }
    @PostMapping("/cart/items/count")
    @ResponseBody
    ModelAndView updateItemCount(@RequestParam UUID productId, @RequestParam Integer itemCount) {
        cartService.updateItemCount(productId, itemCount);
        cartService.getCartAmount();
        return getCartItemsWithModelAndView();
    }
    @GetMapping("/register")
    String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "clientRegistrationForm";
    }
    @PostMapping("/register")
    String submitRegistrationForm(@ModelAttribute User newUser, Model model) {
        newUser.setRole(UserRole.USER);
        newUser.setStatus(UserStatus.INACTIVE);
        userService.saveUser(newUser);
        return "redirect:/";
    }

}

