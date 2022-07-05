package lt.bit.products.ui.controller;

import lt.bit.products.ui.service.CartService;
import lt.bit.products.ui.service.ProductService;
import lt.bit.products.ui.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class IndexControllerBase extends ControllerBase {

  private UserService userService;

  private ProductService productService;

  private CartService cartService;

  IndexControllerBase(UserService userService, ProductService productService, CartService cartService) {
    this.userService = userService;
    this.productService = productService;
    this.cartService = cartService;
  }

  @GetMapping("/")
  String index(Model model) {
    model.addAttribute("products", productService.getProducts());
    model.addAttribute("cartItems", cartService.getCartItems());
    return "index" ;
  }
  @GetMapping(ADMIN_PATH)
  String admin(Model model) {
    if (!userService.isAuthenticated()) {
      return "login";
    }
    return "admin/index";
  }
}
