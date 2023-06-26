package control.tower.order.service.core.utils;

import control.tower.order.service.core.data.dtos.OrderDto;
import control.tower.order.service.core.valueobjects.OrderCalculations;
import control.tower.order.service.core.valueobjects.ProductLineItem;
import control.tower.order.service.core.valueobjects.PromotionLineItem;
import control.tower.order.service.core.valueobjects.ServiceLineItem;
import lombok.Getter;

import static control.tower.order.service.core.constants.DomainConstants.MI_SALES_TAX_RATE;

@Getter
public class OrderCalculationService {

    private OrderCalculationService() {
        throw new IllegalStateException("Utility class");
    }

    public static OrderCalculations calculateOrder(OrderDto orderDto) {
        Double productTotal = 0d;
        for (ProductLineItem productLineItem: orderDto.getProductLineItems()) {
            productTotal += productLineItem.getUnitPrice() * productLineItem.getQuantity();
        }

        Double promotionTotal = 0d;
        for (PromotionLineItem promotionLineItem: orderDto.getPromotionLineItems()) {
            promotionTotal += promotionLineItem.getUnitPrice();
        }

        Double serviceTotal = 0d;
        for (ServiceLineItem serviceLineItem: orderDto.getServiceLineItems()) {
            serviceTotal += serviceLineItem.getUnitPrice();
        }

        Double lineItemTotal = productTotal + promotionTotal + serviceTotal;

        Double salesTax = lineItemTotal * MI_SALES_TAX_RATE;

        Double orderTotal = lineItemTotal + salesTax;

        return OrderCalculations.builder()
                .orderTotal(orderTotal)
                .productTotal(productTotal)
                .promotionTotal(promotionTotal)
                .serviceTotal(serviceTotal)
                .salesTax(salesTax)
                .build();
    }
}
