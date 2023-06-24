package control.tower.order.service.core.events;

import control.tower.order.service.core.valueobjects.ProductLineItem;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderCreatedEvent {

    private String orderId;
    private String userId;
    private String paymentId;
    private String addressId;
    private List<ProductLineItem> productLineItems;
}
