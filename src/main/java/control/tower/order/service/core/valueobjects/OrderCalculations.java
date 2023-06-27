package control.tower.order.service.core.valueobjects;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderCalculations {

    private Double orderTotal;
    private Double productTotal;
    private Double promotionTotal;
    private Double serviceTotal;
    private Double salesTax;
}
