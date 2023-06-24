package control.tower.order.service.core.data;

import control.tower.order.service.core.models.LineItemType;
import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class ProductLineItemEntity extends LineItemEntity{

    private Integer quantity;
    private Double unitPrice;

    public ProductLineItemEntity(String productId, Integer quantity, Double unitPrice) {
        this.setLineItemId(productId);
        this.setLineItemType(LineItemType.PRODUCT);
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public ProductLineItemEntity() {
        this.setLineItemId("productId");
        this.setLineItemType(LineItemType.PRODUCT);
        this.quantity = 1;
        this.unitPrice = 0.0;
    }
}
