package lt.bit.products.ui.controller;

import lt.bit.products.ui.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    String addToCart(@RequestParam UUID productId, @RequestParam String productName) {
        cartService.addToCart(productId, productName);
        return "Product has been added to the cart! ";
        }
    }

