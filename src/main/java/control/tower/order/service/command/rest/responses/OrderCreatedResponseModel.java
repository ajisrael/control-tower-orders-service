package control.tower.order.service.command.rest.responses;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderCreatedResponseModel {

    private String orderId;
}
