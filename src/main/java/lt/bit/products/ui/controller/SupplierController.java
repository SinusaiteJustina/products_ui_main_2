package lt.bit.products.ui.controller;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lt.bit.products.ui.model.Product;
import lt.bit.products.ui.model.Supplier;
import lt.bit.products.ui.service.SupplierService;
import lt.bit.products.ui.service.UserService;
import lt.bit.products.ui.service.error.ValidationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SupplierController {

  private final SupplierService service;
  private final UserService userService;

  public SupplierController(SupplierService service, UserService userService) {
    this.service = service;
    this.userService = userService;
  }

  @GetMapping("/suppliers")
  String showSuppliers(Model model) {
    if (!userService.isAuthenticated()) {
      return "login";
    }

    List<Supplier> suppliers = service.getSuppliers();

    model.addAttribute("supplierItems", suppliers);
    return "supplierList";
  }

  @GetMapping("/suppliers/{id}")
  String editSupplier(@PathVariable UUID id, Model model) {
    if (!userService.isAuthenticated()) {
      return "login";
    }
    model.addAttribute("supplierItem", service.getSupplier(id));
    return "supplierForm";
  }

  @PostMapping("/suppliers/save")
  String saveSupplier(@ModelAttribute Supplier supplier, Model model) throws ValidationException {
/*    try {
      validator.validate(product);
    } catch (ValidationException e) {
      model.addAttribute("errorMsg",
          messages.getMessage("validation.error." + e.getCode(), e.getParams(),
              Locale.getDefault()));
      model.addAttribute("productItem", product);
      return "productForm";
    }*/
    service.saveSupplier(supplier);
    return "redirect:/suppliers";
  }
}
