package lt.bit.products.ui.controller;

import java.security.AccessControlException;
import java.util.List;
import java.util.Locale;

import lt.bit.products.ui.model.User;
import lt.bit.products.ui.service.UserService;
import lt.bit.products.ui.service.domain.UserRole;
import lt.bit.products.ui.service.domain.UserStatus;
import lt.bit.products.ui.service.error.UserValidator;
import lt.bit.products.ui.service.error.ValidationException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static lt.bit.products.ui.controller.ControllerBase.ADMIN_PATH;

@Controller
@RequestMapping(ADMIN_PATH)
class UserController extends ControllerBase{
  protected static final String USERS_PATH = "/users";

  private final UserService userService;
  private final MessageSource messages;
  private final UserValidator userValidator;

  UserController(UserService userService, MessageSource messages, UserValidator userValidator) {
    this.userService = userService;
    this.messages = messages;
    this.userValidator = userValidator;
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
  String saveUser(@ModelAttribute User editedUser, Model model) {
    try {
      userValidator.validate(editedUser);
    } catch (ValidationException e) {
      model.addAttribute("errorMsg",
              messages.getMessage("validation.error." + e.getCode(), e.getParams(), Locale.getDefault()));
      model.addAttribute("user", editedUser);
      model.addAttribute("roles", UserRole.values());
      model.addAttribute("statuses", UserStatus.values());
      return "admin/userForm";
    }

    User userToSave;
    if(editedUser.getId() != null) {
      userToSave = userService.getUser(editedUser.getId());
    } else {
      userToSave = new User();
    }

    userToSave.setUsername(editedUser.getUsername());
    userToSave.setRole(editedUser.getRole());
    userToSave.setStatus(editedUser.getStatus());
    String password = editedUser.getPassword();
    if(StringUtils.hasLength(password)) {
      userToSave.setPassword(password);
    }
    userService.saveUser(userToSave);
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
  @GetMapping("/admin/users/activate")
  String activateUser(@RequestParam Integer id) {
    if (!userService.isAuthenticated()) {
      return "login";
    }
    userService.changeStatus(UserStatus.ACTIVE, id);
//    User user = userService.getUser(id);
//    user.setStatus(UserStatus.ACTIVE);
//    userService.saveUser(user);
    return "redirect:" + ADMIN_PATH + USERS_PATH;
  }
  @GetMapping("/admin/users/block")
  String blockUser(@RequestParam Integer id) {
    if (!userService.isAuthenticated()) {
      return "login";
    }
    userService.changeStatus(UserStatus.BLOCKED, id);
//    User user = userService.getUser(id);
//    user.setStatus(UserStatus.BLOCKED);
//    userService.saveUser(user);
    return "redirect:" + ADMIN_PATH + USERS_PATH;
  }
}
