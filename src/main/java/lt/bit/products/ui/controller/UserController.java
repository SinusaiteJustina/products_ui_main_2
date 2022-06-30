package lt.bit.products.ui.controller;

import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import lt.bit.products.ui.model.User;
import lt.bit.products.ui.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static lt.bit.products.ui.controller.ControllerBase.ADMIN_PATH;

@Controller
@RequestMapping(ADMIN_PATH)
class UserController extends ControllerBase{

  private final UserService userService;
  private final MessageSource messages;

  UserController(UserService userService, MessageSource messages) {
    this.userService = userService;
    this.messages = messages;
  }

  @GetMapping( "/users")
  String showUsers(Model model) {
    if (!userService.isAuthenticated()) {
      return "login";
    }

    List<User> users = userService.getUsers();

    model.addAttribute("userItems", users);
    return "userList";
  }
}
