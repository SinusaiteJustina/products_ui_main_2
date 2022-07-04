package lt.bit.products.ui.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class CustomerSiteController {

    @PostMapping("/cart/add")
    @ResponseBody
    void addToCart(@RequestParam("productId") String productId) {
        System.out.println(productId);
        }
    }

