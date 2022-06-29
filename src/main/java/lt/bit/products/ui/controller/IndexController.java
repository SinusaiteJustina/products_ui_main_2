package lt.bit.products.ui.controller;

import lt.bit.products.ui.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class IndexController {

  private UserService userService;

  IndexController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/")
  String index(Model model) {
    if (!userService.isAuthenticated()) {
      return "login";
    }
    return "index";
  }
}
