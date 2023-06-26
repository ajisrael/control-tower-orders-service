package control.tower.order.service.query.querymodels;

import control.tower.order.service.core.valueobjects.ProductLineItem;
import control.tower.order.service.core.valueobjects.PromotionLineItem;
import control.tower.order.service.core.valueobjects.ServiceLineItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderQueryModel {

    private String orderId;
    private String userId;
    private String paymentId;
    private String addressId;
    private Instant createdAt;
    private List<ProductLineItem> productLineItems;
    private List<PromotionLineItem> promotionLineItems;
    private List<ServiceLineItem> serviceLineItems;
    private String orderStatus;
}
