package control.tower.order.service.command;

import control.tower.order.service.core.events.OrderCreatedEvent;
import control.tower.order.service.core.data.OrderLookupEntity;
import control.tower.order.service.core.data.OrderLookupRepository;
import lombok.AllArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@ProcessingGroup("order-group")
public class OrderLookupEventsHandler {

    private OrderLookupRepository orderLookupRepository;

    @EventHandler
    public void on(OrderCreatedEvent event) {
        orderLookupRepository.save(new OrderLookupEntity(event.getOrderId()));
    }
}
