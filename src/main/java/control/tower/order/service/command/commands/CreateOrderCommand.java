package control.tower.order.service.command.commands;

import control.tower.order.service.core.valueobjects.ProductLineItem;
import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

import static control.tower.core.utils.Helper.throwExceptionIfParameterIsEmpty;
import static control.tower.core.utils.Helper.throwExceptionIfParameterIsNull;
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

    public void validate() {
        throwExceptionIfParameterIsEmpty(this.getOrderId(), ORDER_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getUserId(), USER_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getPaymentId(), PAYMENT_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getAddressId(), ADDRESS_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsNull(this.getProductLineItems(), "Product line items cannot be null");
        for(ProductLineItem productLineItem: this.getProductLineItems()) {
            throwExceptionIfParameterIsEmpty(productLineItem.getLineItemId(), PRODUCT_ID_CANNOT_BE_EMPTY);
            throwExceptionIfParameterIsNull(productLineItem.getQuantity(), "Product line item quantity cannot be null");
        }
    }
}
