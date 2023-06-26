package control.tower.order.service.core.data.dtos;

import control.tower.core.model.OrderStatus;
import control.tower.order.service.core.valueobjects.ProductLineItem;
import control.tower.order.service.core.valueobjects.PromotionLineItem;
import control.tower.order.service.core.valueobjects.ServiceLineItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {

    private String orderId;
    private String userId;
    private String paymentId;
    private String addressId;
    private Instant createdAt;
    private List<ProductLineItem> productLineItems;
    private List<PromotionLineItem> promotionLineItems;
    private List<ServiceLineItem> serviceLineItems;
    private OrderStatus orderStatus;
}
