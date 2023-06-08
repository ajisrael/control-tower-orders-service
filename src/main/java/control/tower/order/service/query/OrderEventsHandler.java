package control.tower.order.service.query;

import control.tower.core.model.OrderStatus;
import control.tower.order.service.core.data.OrderEntity;
import control.tower.order.service.core.data.OrderRepository;
import control.tower.order.service.core.events.OrderCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("order-group")
public class OrderEventsHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventsHandler.class);

    private final OrderRepository orderRepository;

    public OrderEventsHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception exception) throws Exception {
        throw exception;
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(IllegalArgumentException exception) {
        LOGGER.error(exception.getLocalizedMessage());
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(event, orderEntity);
        orderEntity.setOrderStatus(OrderStatus.CREATED);
        orderRepository.save(orderEntity);
    }
}
