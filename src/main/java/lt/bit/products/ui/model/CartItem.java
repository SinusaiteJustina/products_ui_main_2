package lt.bit.products.ui.model;

import java.math.BigDecimal;
import java.util.UUID;

public class CartItem {

     private UUID productId;
     private String productName;
     private int count;

     private BigDecimal productPrice;

     public CartItem(UUID productId, String productName, BigDecimal productPrice, int count) {
          this.productId = productId;
          this.productName = productName;
          this.count = count;
          this.productPrice = productPrice;
     }

     @Override
     public String toString() {
          return "CartItem{" +
                  "productName='" + productName + '\'' +
                  ", count=" + count +
                  '}';
     }

     public UUID getProductId() {
          return productId;
     }

     public void setProductId(UUID productId) {
          this.productId = productId;
     }

     public String getProductName() {
          return productName;
     }

     public void setProductName(String productName) {
          this.productName = productName;
     }

     public int getCount() {
          return count;
     }

     public void setCount(int count) {
          this.count = count;
     }


     public BigDecimal getProductPrice() {
          return productPrice;
     }

     public void setProductPrice(BigDecimal productPrice) {
          this.productPrice = productPrice;
     }

     public BigDecimal getTotalPrice() {
          return productPrice.multiply(BigDecimal.valueOf(getCount()));
     }
}
