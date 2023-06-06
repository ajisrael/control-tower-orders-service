package control.tower.order.service.command;

import control.tower.core.model.OrderStatus;
import control.tower.order.service.core.events.OrderCreatedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import static control.tower.order.service.core.utils.Helper.isNullOrBlank;

@Aggregate
@NoArgsConstructor
@Getter
public class OrderAggregate {

    @AggregateIdentifier

    private String orderId;
    private String userId;
    private String paymentId;
    private String addressId;
    private String productId;
    private OrderStatus orderStatus;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        validateCreateOrderCommand(command);

        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(command.getOrderId())
                .userId(command.getUserId())
                .paymentId(command.getPaymentId())
                .addressId(command.getAddressId())
                .productId(command.getProductId())
                .orderStatus(command.getOrderStatus())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.userId = event.getUserId();
        this.paymentId = event.getPaymentId();
        this.addressId = event.getAddressId();
        this.productId = event.getProductId();
        this.orderStatus = event.getOrderStatus();
    }

    private void validateCreateOrderCommand(CreateOrderCommand command) {
        if (isNullOrBlank(command.getOrderId())) {
            throw new IllegalArgumentException("OrderId cannot be empty");
        }

        if (isNullOrBlank(command.getUserId())) {
            throw new IllegalArgumentException("UserId cannot be empty");
        }

        if (isNullOrBlank(command.getPaymentId())) {
            throw new IllegalArgumentException("PaymentId cannot be empty");
        }

        if (isNullOrBlank(command.getAddressId())) {
            throw new IllegalArgumentException("AddressId cannot be empty");
        }

        if (isNullOrBlank(command.getProductId())) {
            throw new IllegalArgumentException("ProductId cannot be empty");
        }

        if (command.getOrderStatus() == null) {
            throw new IllegalArgumentException("OrderStatus cannot be null");
        }
    }
}
