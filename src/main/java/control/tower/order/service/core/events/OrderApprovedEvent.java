package control.tower.order.service.core.events;

import control.tower.core.model.OrderStatus;
import lombok.Value;

@Value
public class OrderApprovedEvent {

    private final String orderId;
    private final OrderStatus orderStatus = OrderStatus.APPROVED;
}
