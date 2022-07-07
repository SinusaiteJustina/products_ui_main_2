package lt.bit.products.ui.controller;

import lt.bit.products.ui.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
class CustomerSiteController {
    private final CartService cartService;

    private final static Logger LOG = LoggerFactory.getLogger(CustomerSiteController.class);

    CustomerSiteController(CartService cartService) {
        this.cartService = cartService;
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

}

