package control.tower.order.service.command.rest.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderCreatedResponseModel {

    private String orderId;
}
