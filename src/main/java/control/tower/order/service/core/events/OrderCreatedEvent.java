package control.tower.order.service.core.events;

import control.tower.order.service.core.valueobjects.ProductLineItem;
import control.tower.order.service.core.valueobjects.PromotionLineItem;
import control.tower.order.service.core.valueobjects.ServiceLineItem;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
public class OrderCreatedEvent {

    private String orderId;
    private String userId;
    private String paymentId;
    private String addressId;
    private Instant createdAt;
    private List<ProductLineItem> productLineItems;
    private List<PromotionLineItem> promotionLineItems;
    private List<ServiceLineItem> serviceLineItems;
}
