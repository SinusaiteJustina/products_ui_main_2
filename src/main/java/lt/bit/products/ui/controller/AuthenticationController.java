package lt.bit.products.ui.controller;

import lt.bit.products.ui.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static lt.bit.products.ui.controller.ControllerBase.ADMIN_PATH;

@Controller
public class AuthenticationController extends ControllerBase {

    private final UserService userService;
    private final MessageSource messages;

    public AuthenticationController(UserService userService, MessageSource messages) {
        this.userService = userService;
        this.messages = messages;
    }

    private String redirectToHome() {
        return "redirect:" + (userService.isAdmin() ? "/admin" : "/");
    }
    @GetMapping("/auth/login")
    String loginForm() {
        if (userService.isAuthenticated()) {
            return redirectToHome();
        }
        return "login";
    }

    @PostMapping("/auth/login")
    String login(HttpServletRequest request, Model model) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        userService.login(username, password);

        if (userService.isAuthenticated()) {
            return redirectToHome();
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
}
