package lt.bit.products.ui.service.error;

public class ValidationException extends Exception {

  private ErrorCode code;
  private Object[] params;

  public ValidationException(ErrorCode code, Object... params) {
    super(code.name());
    this.code = code;
    this.params = params;
  }

  public ErrorCode getCode() {
    return code;
  }

  public void setCode(ErrorCode code) {
    this.code = code;
  }

  public Object[] getParams() {
    return params;
  }

  public void setParams(Object[] params) {
    this.params = params;
  }
}
