package lt.bit.products.ui.model;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.UUID;

@Embeddable
public class OrderItem {

    private UUID productId;
    private double quantity;


    private UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    private double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

}
