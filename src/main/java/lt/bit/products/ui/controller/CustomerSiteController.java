package lt.bit.products.ui.controller;

import lt.bit.products.ui.model.CartItem;
import lt.bit.products.ui.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;
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
    ModelAndView addToCart(@RequestParam UUID productId, @RequestParam String productName, @RequestParam BigDecimal productPrice) {
        cartService.addToCart(productId, productName, productPrice);
        List<CartItem> cartItems = cartService.getCartItems();
        ModelAndView mv = new ModelAndView("cartItems");
        mv.addObject("cartItems",cartItems);
        return mv;
        }
    }

