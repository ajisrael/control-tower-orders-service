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

import static control.tower.core.utils.Helper.throwExceptionIfEntityDoesNotExist;
import static control.tower.order.service.core.constants.ExceptionMessages.ORDER_WITH_ID_DOES_NOT_EXIST;

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

        throwExceptionIfEntityDoesNotExist(orderLookupEntity,
                String.format(ORDER_WITH_ID_DOES_NOT_EXIST, event.getOrderId()));

        orderLookupEntity.setOrderStatus(OrderStatus.CANCELED);
        orderLookupRepository.save(orderLookupEntity);
    }
}
