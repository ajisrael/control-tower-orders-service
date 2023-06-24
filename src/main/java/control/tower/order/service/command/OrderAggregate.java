package control.tower.order.service.command;

import control.tower.core.model.OrderStatus;
import control.tower.order.service.command.commands.CancelOrderCommand;
import control.tower.order.service.command.commands.CreateOrderCommand;
import control.tower.order.service.core.events.OrderCanceledEvent;
import control.tower.order.service.core.events.OrderCreatedEvent;
import control.tower.order.service.core.valueobjects.ProductLineItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.List;

@Aggregate
@NoArgsConstructor
@Getter
public class OrderAggregate {

    @AggregateIdentifier

    private String orderId;
    private String userId;
    private String paymentId;
    private String addressId;
    private List<ProductLineItem> productLineItems;
    private OrderStatus orderStatus;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(command.getOrderId())
                .userId(command.getUserId())
                .paymentId(command.getPaymentId())
                .addressId(command.getAddressId())
                .productLineItems(command.getProductLineItems())
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(CancelOrderCommand command) {
        OrderCanceledEvent event = OrderCanceledEvent.builder()
                .orderId(command.getOrderId())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.userId = event.getUserId();
        this.paymentId = event.getPaymentId();
        this.addressId = event.getAddressId();
        this.productLineItems = event.getProductLineItems();
        this.orderStatus = OrderStatus.CREATED;
    }

    @EventSourcingHandler
    public void on(OrderCanceledEvent event) {
        this.orderStatus = OrderStatus.CANCELED;
    }
}
