package lt.bit.products.ui.controller;

import static lt.bit.products.ui.controller.ControllerBase.ADMIN_PATH;
import static lt.bit.products.ui.controller.ProductController.PRODUCTS_PATH;
import static org.springframework.util.StringUtils.hasLength;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lt.bit.products.ui.model.Product;
import lt.bit.products.ui.service.ProductService;
import lt.bit.products.ui.service.SupplierService;
import lt.bit.products.ui.service.UserService;
import lt.bit.products.ui.service.error.ProductValidator;
import lt.bit.products.ui.service.error.ValidationException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(ADMIN_PATH + PRODUCTS_PATH)
class ProductController extends ControllerBase{

  protected static final String PRODUCTS_PATH = "/products";
  private final ProductService service;
  private final UserService userService;
  private final SupplierService supplierService;
  private final ProductValidator validator;
  private final MessageSource messages;

  ProductController(ProductService service, UserService userService,
      SupplierService supplierService, ProductValidator validator,
      MessageSource messages) {
    this.service = service;
    this.userService = userService;
    this.supplierService = supplierService;
    this.validator = validator;
    this.messages = messages;
  }

  @GetMapping
  String showProducts(Model model, HttpServletRequest request) {
    if (!userService.isAuthenticated()) {
      return "login";
    }

    String id = request.getParameter("id");
    String name = request.getParameter("name");
    List<Product> products;
    if (hasLength(id) || hasLength(name)) {
      products = service.findProducts(id, name);
//      products = service.findProductsWithQuery(id, name);
    } else {
      products = service.getProducts();
    }

    model.addAttribute("searchCriteriaId", id);
    model.addAttribute("searchCriteriaName", name);
    model.addAttribute("productItems", products);
    return "productList";
  }

  @GetMapping("/{id}")
  String editProduct(@PathVariable UUID id, Model model) {
    if (!userService.isAuthenticated()) {
      return "login";
    }
    model.addAttribute("productItem", service.getProduct(id));
    model.addAttribute("suppliers", supplierService.getSuppliers());
    return "productForm";
  }

  @GetMapping("/{id}/image.png")
  ResponseEntity<byte[]> getProductImage(@PathVariable UUID id) {
    Product product = service.getProduct(id);
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_TYPE, product.getImageContentType());
    return new ResponseEntity<>(product.getImageFileContents(), headers, HttpStatus.OK);
  }

  @GetMapping("/add")
  String addProduct(Model model) {
    if (!userService.isAuthenticated()) {
      return "login";
    }
    model.addAttribute("productItem", new Product());
    model.addAttribute("suppliers", supplierService.getSuppliers());
    return "productForm";
  }

  @PostMapping("/save")
  String saveProduct(@ModelAttribute Product product,
      @RequestPart(name = "imageFile", required = false) MultipartFile file, Model model)
      throws ValidationException {

    try {
      validator.validate(product);
      if (file != null && !file.isEmpty()) {
        validator.validate(file);
        product.setImageName(file.getOriginalFilename());
        product.setImageContentType(file.getContentType());
        product.setImageFileContents(file.getBytes());
      }
    } catch (ValidationException e) {
      model.addAttribute("errorMsg",
          messages.getMessage("validation.error." + e.getCode(), e.getParams(),
              Locale.getDefault()));
      model.addAttribute("productItem", product);
      return "productForm";
    } catch (IOException ioe) {
      model.addAttribute("errorMsg",
          messages.getMessage("system.error.FILE_UPLOAD", null, Locale.getDefault()));
      model.addAttribute("productItem", product);
      return "productForm";
    }

    service.saveProduct(product);
    return "redirect:" + ADMIN_PATH + PRODUCTS_PATH;
  }

  @GetMapping("/delete")
  String deleteProduct(@RequestParam UUID id) {
    if (!userService.isAuthenticated()) {
      return "login";
    }
    service.deleteProduct(id);
    return "redirect:" + ADMIN_PATH + PRODUCTS_PATH;
  }
}
