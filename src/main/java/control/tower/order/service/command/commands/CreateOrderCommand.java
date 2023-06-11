package control.tower.order.service.command.commands;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import static control.tower.core.utils.Helper.throwExceptionIfParameterIsEmpty;
import static control.tower.order.service.core.constants.ExceptionMessages.*;

@Getter
@Builder
public class CreateOrderCommand {

    @TargetAggregateIdentifier
    private String orderId;
    private String userId;
    private String paymentId;
    private String addressId;
    private String productId;

    public void validate() {
        throwExceptionIfParameterIsEmpty(this.getOrderId(), ORDER_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getUserId(), USER_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getPaymentId(), PAYMENT_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getAddressId(), ADDRESS_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getProductId(), PRODUCT_ID_CANNOT_BE_EMPTY);
    }
}
