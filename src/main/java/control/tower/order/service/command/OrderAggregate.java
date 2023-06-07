package control.tower.order.service.command;

import control.tower.core.model.OrderStatus;
import control.tower.order.service.command.commands.CreateOrderCommand;
import control.tower.order.service.core.events.OrderCreatedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

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
        command.validate();

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
}
