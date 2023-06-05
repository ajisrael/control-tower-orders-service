package control.tower.order.service.core.events;

import control.tower.core.model.OrderStatus;
import lombok.Value;

@Value
public class OrderRejectedEvent {

    private final String orderId;
    private final String reason;
    private final OrderStatus orderStatus = OrderStatus.REJECTED;
}
