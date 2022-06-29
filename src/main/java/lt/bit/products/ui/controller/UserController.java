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

@Controller
class UserController {

  private final UserService userService;
  private final MessageSource messages;

  UserController(UserService userService, MessageSource messages) {
    this.userService = userService;
    this.messages = messages;
  }

  @GetMapping("/auth/login")
  String loginForm() {
    if (userService.isAuthenticated()) {
      return "redirect:/";
    }
    return "login";
  }

  @PostMapping("/auth/login")
  String login(HttpServletRequest request, Model model) {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    userService.login(username, password);

    if (userService.isAuthenticated()) {
      return "redirect:/";
    }
    model.addAttribute("errorMsg",
        messages.getMessage("login.error.INVALID_CREDENTIALS", null, Locale.getDefault()));
    return "login";
  }

  @GetMapping("/auth/logout")
  String logout() {
    userService.logout();
    return "login";
  }

  @GetMapping("/users")
  String showSuppliers(Model model) {
    if (!userService.isAuthenticated()) {
      return "login";
    }

    List<User> users = userService.getUsers();

    model.addAttribute("userItems", users);
    return "userList";
  }
}
