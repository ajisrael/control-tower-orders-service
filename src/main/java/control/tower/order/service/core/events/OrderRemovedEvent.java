package control.tower.order.service.core.events;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderRemovedEvent {

    private String orderId;
}
