package control.tower.order.service.command;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import control.tower.core.model.OrderStatus;

@Getter
@Builder
public class CreateOrderCommand {

    @TargetAggregateIdentifier
    private String orderId;
    private String userId;
    private String paymentId;
    private String addressId;
    private String productId;
    private OrderStatus orderStatus;
}
