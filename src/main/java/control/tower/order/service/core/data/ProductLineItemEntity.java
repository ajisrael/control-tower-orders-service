package control.tower.order.service.core.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
public class ProductLineItemEntity {

    private String productId;
    private Integer quantity;
    private Double unitPrice;

    public ProductLineItemEntity() {
        this.productId = "productId";
        this.quantity = 1;
        this.unitPrice = 0.0;
    }
}
