package control.tower.order.service.core.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductLineItem {

    private String productId;
    private Integer quantity;
}
