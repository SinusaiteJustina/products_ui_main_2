package lt.bit.products.ui.controller;

import java.security.AccessControlException;
import java.util.List;
import java.util.Locale;

import lt.bit.products.ui.model.User;
import lt.bit.products.ui.service.UserService;
import lt.bit.products.ui.service.domain.UserRole;
import lt.bit.products.ui.service.domain.UserStatus;
import lt.bit.products.ui.service.error.ValidationException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static lt.bit.products.ui.controller.ControllerBase.ADMIN_PATH;

@Controller
@RequestMapping(ADMIN_PATH)
class UserController extends ControllerBase{

  protected static final String USERS_PATH = "/users";

  private final UserService userService;
  private final MessageSource messages;

  UserController(UserService userService, MessageSource messages) {
    this.userService = userService;
    this.messages = messages;
  }

  @GetMapping("/users")
  String showUsers(Model model) {
    if (!userService.isAuthenticated()) {
      return "login";
    }

    List<User> users = userService.getUsers();

    model.addAttribute("userItems", users);
    return "admin/userList";
  }
  @GetMapping("/admin/users/{id}")
  String editUser(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
    if (!userService.isAuthenticated()) {
      return "login";
    }
    try {
      model.addAttribute("user", userService.getUser(id));
    } catch (AccessControlException e) {
      redirectAttributes.addFlashAttribute("errorMsg", messages.getMessage(e.getMessage(), null, Locale.getDefault()));
      return "redirect:" + ADMIN_PATH + USERS_PATH;
    }
    model.addAttribute("roles", UserRole.values());
    model.addAttribute("statuses", UserStatus.values());
    return "admin/userForm";
  }
  @PostMapping("/users/save")
  String saveUser(@ModelAttribute User editedUser)
          throws ValidationException {
    User existingUser = userService.getUser(editedUser.getId());
    existingUser.setUsername(editedUser.getUsername());
    existingUser.setRole(editedUser.getRole());
    existingUser.setStatus(editedUser.getStatus());
    userService.saveUser(existingUser);
    return "redirect:" + ADMIN_PATH + USERS_PATH;
  }
  @GetMapping("/admin/users/add")
  String addUser(Model model) {
    if (!userService.isAuthenticated()) {
      return "login";
    }
    model.addAttribute("user", new User());
    model.addAttribute("roles", UserRole.values());
    model.addAttribute("statuses", UserStatus.values());
    return "admin/userForm";
  }
  @GetMapping("/admin/users/delete")
  String deleteUser(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
    if (!userService.isAuthenticated()) {
      return "login";
    }
    try {
      userService.deleteUser(id);
    } catch (AccessControlException e) {
      redirectAttributes.addFlashAttribute("errorMsg", messages.getMessage(e.getMessage(), null, Locale.getDefault()));
    }
    return "redirect:" + ADMIN_PATH + USERS_PATH;
  }
}
