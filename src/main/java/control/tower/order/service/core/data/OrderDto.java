package control.tower.order.service.core.data;

import control.tower.core.model.OrderStatus;
import control.tower.order.service.core.valueobjects.ProductLineItem;
import control.tower.order.service.core.valueobjects.PromotionLineItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {

    private String orderId;
    private String userId;
    private String paymentId;
    private String addressId;
    private List<ProductLineItem> productLineItems;
    private List<PromotionLineItem> promotionLineItems;
    private OrderStatus orderStatus;
}
