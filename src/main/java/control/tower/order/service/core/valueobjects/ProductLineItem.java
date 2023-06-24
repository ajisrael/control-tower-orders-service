package control.tower.order.service.core.valueobjects;

import control.tower.order.service.core.models.LineItemType;
import lombok.Getter;

@Getter
public class ProductLineItem extends LineItem {

    private Integer quantity;

    public ProductLineItem(String productId, Integer quantity) {
        super(productId, LineItemType.PRODUCT);
        this.quantity = quantity;
    }
}
