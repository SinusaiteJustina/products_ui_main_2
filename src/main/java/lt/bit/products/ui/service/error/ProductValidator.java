package lt.bit.products.ui.service.error;

import java.util.Set;
import lt.bit.products.ui.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ProductValidator {

  private static final Set<String> SUPPORTED_FILE_TYPES = Set.of("image/jpeg", "image/png");
  private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

  public void validate(Product product) throws ValidationException {
    validateName(product.getName());
    validateQuantity(product.getQuantity());
  }

  private void validateName(String name) throws ValidationException {
    if (!StringUtils.hasLength(name)) {
      throw new ValidationException(ErrorCode.EMPTY_VALUE, "Name");
    }

    if (name.length() < 3) {
      throw new ValidationException(ErrorCode.VALUE_TOO_SHORT, "Name", 3);
    }
  }

  private void validateQuantity(double quantity) throws ValidationException {
    if (quantity < 0) {
      throw new ValidationException(ErrorCode.NEGATIVE_NUMBER, "Quantity");
    }
  }

  public void validate(MultipartFile file) throws ValidationException {
    validateFileType(file.getContentType());
    validateFileSize(file.getSize());
  }

  private void validateFileType(String contentType) throws ValidationException {
    if (!SUPPORTED_FILE_TYPES.contains(contentType)) {
      throw new ValidationException(ErrorCode.INVALID_FILE_TYPE, SUPPORTED_FILE_TYPES);
    }
  }

  private void validateFileSize(long size) throws ValidationException {
    if (MAX_FILE_SIZE <= size) {
      throw new ValidationException(ErrorCode.FILE_TOO_LARGE, formattedFileSize(MAX_FILE_SIZE));
    }
  }

  private String formattedFileSize(long sizeInBytes) {
    int divider = 1024;
    long sizeInKb = sizeInBytes / divider;
    if (sizeInKb == 0) {
      return sizeInBytes + "B";
    }

    long sizeInMb = sizeInKb / divider;
    if (sizeInMb == 0) {
      return sizeInKb + "Kb";
    }
    return sizeInMb + "Mb";
  }
}
