package lt.bit.products.ui.model;

import java.util.UUID;

public class Supplier {

  private UUID id;
  private String name;
  private String companyCode;
  private String vatCode;

  public Supplier(UUID id, String name) {
    this.id = id;
    this.name = name;
  }

  public Supplier() {
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCompanyCode() {
    return companyCode;
  }

  public void setCompanyCode(String companyCode) {
    this.companyCode = companyCode;
  }

  public String getVatCode() {
    return vatCode;
  }

  public void setVatCode(String vatCode) {
    this.vatCode = vatCode;
  }
}
