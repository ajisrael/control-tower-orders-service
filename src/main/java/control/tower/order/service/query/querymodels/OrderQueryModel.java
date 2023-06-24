package control.tower.order.service.query.querymodels;

import control.tower.order.service.core.data.ProductLineItemEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderQueryModel {

    private String orderId;
    private String userId;
    private String paymentId;
    private String addressId;
    private List<ProductLineItemEntity> productLineItems;
    private String orderStatus;
}
