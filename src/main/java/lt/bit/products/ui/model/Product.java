package lt.bit.products.ui.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

  private UUID id;
  private String name;
  private BigDecimal price;
  private double quantity;
  private String description;
  private String imageName;
  private String imageContentType;
  private byte[] imageFileContents;
  private UUID supplierId;

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

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImageName() {
    return imageName;
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
  }

  public String getImageContentType() {
    return imageContentType;
  }

  public void setImageContentType(String imageContentType) {
    this.imageContentType = imageContentType;
  }

  public byte[] getImageFileContents() {
    return imageFileContents;
  }

  public void setImageFileContents(byte[] imageFileContents) {
    this.imageFileContents = imageFileContents;
  }

  public UUID getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(UUID supplierId) {
    this.supplierId = supplierId;
  }
}
