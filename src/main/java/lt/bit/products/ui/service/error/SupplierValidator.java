package lt.bit.products.ui.service.error;

import lt.bit.products.ui.model.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SupplierValidator {

  public void validate(Supplier supplier) throws ValidationException {
    validateName(supplier.getName());
// TODO:   validateCompanyCode(supplier.getCompanyCode());
// TODO:   validateVatCode(supplier.getVatCode());
  }

  private void validateName(String name) throws ValidationException {
    if (!StringUtils.hasLength(name)) {
      throw new ValidationException(ErrorCode.EMPTY_VALUE, "Name");
    }

    if (name.length() < 3) {
      throw new ValidationException(ErrorCode.VALUE_TOO_SHORT, "Name", 3);
    }
  }
}
