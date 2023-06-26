package control.tower.order.service.command.commands;

import control.tower.order.service.core.valueobjects.ProductLineItem;
import control.tower.order.service.core.valueobjects.PromotionLineItem;
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

    public void validate() {
        throwExceptionIfParameterIsEmpty(this.getOrderId(), ORDER_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getUserId(), USER_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getPaymentId(), PAYMENT_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getAddressId(), ADDRESS_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsNull(this.getProductLineItems(), "Product line items cannot be null");
        throwExceptionIfListParameterIsEmpty(this.getProductLineItems(), "Product line items cannot be empty");
        for(ProductLineItem productLineItem: this.getProductLineItems()) {
            throwExceptionIfParameterIsEmpty(productLineItem.getProductId(), PRODUCT_ID_CANNOT_BE_EMPTY);
            throwExceptionIfParameterIsNull(productLineItem.getQuantity(), "Product line item quantity cannot be null");
            throwExceptionIfParameterIsNull(productLineItem.getUnitPrice(), "Product line item unit price cannot be null");
        }
        for(PromotionLineItem promotionLineItem: this.getPromotionLineItems()) {
            throwExceptionIfParameterIsEmpty(promotionLineItem.getPromotionId(), "Promotion line item cannot be empty");
            throwExceptionIfParameterIsNull(promotionLineItem.getUnitPrice(), "Promotion line item unit price cannot be null");
        }
    }
}
