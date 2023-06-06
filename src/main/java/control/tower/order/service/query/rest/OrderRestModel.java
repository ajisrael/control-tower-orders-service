package control.tower.order.service.query.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderRestModel {

    private String orderId;
    private String userId;
    private String paymentId;
    private String addressId;
    private String productId;
    private String orderStatus;
}
