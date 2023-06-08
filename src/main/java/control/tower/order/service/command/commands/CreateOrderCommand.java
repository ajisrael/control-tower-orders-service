package control.tower.order.service.command.commands;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import control.tower.core.model.OrderStatus;

import static control.tower.core.utils.Helper.isNullOrBlank;

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
        if (isNullOrBlank(this.getOrderId())) {
            throw new IllegalArgumentException("OrderId cannot be empty");
        }

        if (isNullOrBlank(this.getUserId())) {
            throw new IllegalArgumentException("UserId cannot be empty");
        }

        if (isNullOrBlank(this.getPaymentId())) {
            throw new IllegalArgumentException("PaymentId cannot be empty");
        }

        if (isNullOrBlank(this.getAddressId())) {
            throw new IllegalArgumentException("AddressId cannot be empty");
        }

        if (isNullOrBlank(this.getProductId())) {
            throw new IllegalArgumentException("ProductId cannot be empty");
        }
    }
}
