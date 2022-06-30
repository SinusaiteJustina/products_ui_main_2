package lt.bit.products.ui.controller;

import lt.bit.products.ui.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class IndexControllerBase extends ControllerBase {

  private UserService userService;

  IndexControllerBase(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/")
  String index(Model model) {
    if (!userService.isAuthenticated()) {
      return "login";
    }
    return "index";
  }
  @GetMapping(ADMIN_PATH)
  String admin(Model model) {
    if (!userService.isAuthenticated()) {
      return "login";
    }
    return "index";
  }
}
