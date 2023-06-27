package control.tower.order.service.command.commands;

import control.tower.order.service.core.valueobjects.ProductLineItem;
import control.tower.order.service.core.valueobjects.PromotionLineItem;
import control.tower.order.service.core.valueobjects.ServiceLineItem;
import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

import static control.tower.core.utils.Helper.*;
import static control.tower.order.service.core.constants.ExceptionMessages.*;

@Getter
@Builder
public class CreateOrderCommand {

    @TargetAggregateIdentifier
    private String orderId;
    private String userId;
    private String paymentId;
    private String addressId;
    private List<ProductLineItem> productLineItems;
    private List<PromotionLineItem> promotionLineItems;
    private List<ServiceLineItem> serviceLineItems;

    public void validate() {
        throwExceptionIfParameterIsEmpty(this.getOrderId(), ORDER_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getUserId(), USER_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getPaymentId(), PAYMENT_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getAddressId(), ADDRESS_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsNull(this.getProductLineItems(), PRODUCT_LINE_ITEMS_CANNOT_BE_NULL);
        throwExceptionIfListParameterIsEmpty(this.getProductLineItems(), PRODUCT_LINE_ITEMS_CANNOT_BE_EMPTY);

        for(ProductLineItem productLineItem: this.getProductLineItems()) {
            throwExceptionIfParameterIsEmpty(productLineItem.getProductId(), PRODUCT_LINE_ITEM_PRODUCT_ID_CANNOT_BE_EMPTY);
            throwExceptionIfParameterIsNull(productLineItem.getQuantity(), PRODUCT_LINE_ITEM_QUANTITY_CANNOT_BE_NULL);
            throwExceptionIfParameterIsNegative(productLineItem.getQuantity(), PRODUCT_LINE_ITEM_QUANTITY_CANNOT_BE_NEGATIVE);
            throwExceptionIfParameterIsNull(productLineItem.getUnitPrice(), PRODUCT_LINE_ITEM_UNIT_PRICE_CANNOT_BE_NULL);
            throwExceptionIfParameterIsNegative(productLineItem.getUnitPrice(), PRODUCT_LINE_ITEM_UNIT_PRICE_CANNOT_BE_NEGATIVE);
        }

        for(PromotionLineItem promotionLineItem: this.getPromotionLineItems()) {
            throwExceptionIfParameterIsEmpty(promotionLineItem.getPromotionId(), PROMOTION_LINE_ITEM_PROMOTION_ID_CANNOT_BE_EMPTY);
            throwExceptionIfParameterIsNull(promotionLineItem.getUnitPrice(), PROMOTION_LINE_ITEM_UNIT_PRICE_CANNOT_BE_NULL);
        }

        for(ServiceLineItem serviceLineItem: this.getServiceLineItems()) {
            throwExceptionIfParameterIsEmpty(serviceLineItem.getServiceId(), SERVICE_LINE_ITEM_PROMOTION_ID_CANNOT_BE_EMPTY);
            throwExceptionIfParameterIsNull(serviceLineItem.getUnitPrice(), SERVICE_LINE_ITEM_UNIT_PRICE_CANNOT_BE_NULL);
        }
    }
}
