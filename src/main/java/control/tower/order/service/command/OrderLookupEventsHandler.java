package control.tower.order.service.command;

import control.tower.core.model.OrderStatus;
import control.tower.order.service.core.events.OrderCanceledEvent;
import control.tower.order.service.core.events.OrderCreatedEvent;
import control.tower.order.service.core.data.OrderLookupEntity;
import control.tower.order.service.core.data.OrderLookupRepository;
import lombok.AllArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import static control.tower.core.utils.Helper.throwErrorIfEntityDoesNotExist;

@Component
@AllArgsConstructor
@ProcessingGroup("order-group")
public class OrderLookupEventsHandler {

    private OrderLookupRepository orderLookupRepository;

    @EventHandler
    public void on(OrderCreatedEvent event) {
        orderLookupRepository.save(new OrderLookupEntity(event.getOrderId(), OrderStatus.CREATED));
    }

    @EventHandler
    public void on(OrderCanceledEvent event) {
        OrderLookupEntity orderLookupEntity = orderLookupRepository.findByOrderId(event.getOrderId());

        throwErrorIfEntityDoesNotExist(orderLookupEntity,
                String.format("Order %s does not exist", event.getOrderId()));

        orderLookupEntity.setOrderStatus(OrderStatus.CANCELED);
        orderLookupRepository.save(orderLookupEntity);
    }
}
