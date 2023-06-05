package control.tower.order.service.query;

import control.tower.order.service.core.data.OrderEntity;
import control.tower.order.service.core.data.OrdersRepository;
import control.tower.order.service.core.events.OrderApprovedEvent;
import control.tower.order.service.core.events.OrderCreatedEvent;
import control.tower.order.service.core.events.OrderRejectedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("order-group")
public class OrderEventsHandler {

    private final OrdersRepository ordersRepository;

    public OrderEventsHandler(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {

        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(event, orderEntity);

        ordersRepository.save(orderEntity);
    }

    @EventHandler
    public void on(OrderApprovedEvent event) {

        OrderEntity orderEntity = ordersRepository.findByOrderId(event.getOrderId());

        if (orderEntity == null) {
            //TODO: do something about it
            return;
        }

        orderEntity.setOrderStatus(event.getOrderStatus());

        ordersRepository.save(orderEntity);
    }

    @EventHandler
    public void on(OrderRejectedEvent event) {

        OrderEntity orderEntity = ordersRepository.findByOrderId(event.getOrderId());

        orderEntity.setOrderStatus(event.getOrderStatus());

        ordersRepository.save(orderEntity);
    }
}
