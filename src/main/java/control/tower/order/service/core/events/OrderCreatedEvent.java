package control.tower.order.service.core.events;

import control.tower.core.model.OrderStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderCreatedEvent {

    private String orderId;
    private String userId;
    private String paymentId;
    private String addressId;
    private String productId;
}
