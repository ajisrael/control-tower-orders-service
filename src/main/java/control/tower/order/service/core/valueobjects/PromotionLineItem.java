package control.tower.order.service.core.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PromotionLineItem {

    private String promotionId;
    private Double unitPrice;
}
