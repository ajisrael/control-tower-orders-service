package control.tower.order.service.query;

import control.tower.core.model.OrderStatus;
import control.tower.order.service.core.data.LineItemEntity;
import control.tower.order.service.core.data.OrderEntity;
import control.tower.order.service.core.data.OrderRepository;
import control.tower.order.service.core.data.ProductLineItemEntity;
import control.tower.order.service.core.events.OrderCanceledEvent;
import control.tower.order.service.core.events.OrderCreatedEvent;
import control.tower.order.service.core.valueobjects.ProductLineItem;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static control.tower.core.utils.Helper.throwExceptionIfEntityDoesNotExist;
import static control.tower.order.service.core.constants.ExceptionMessages.ORDER_WITH_ID_DOES_NOT_EXIST;

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

        List<ProductLineItemEntity> productLineItemEntities = new ArrayList<>();

        for (ProductLineItem productLineItem: event.getProductLineItems()) {
            productLineItemEntities.add( new ProductLineItemEntity(
                    productLineItem.getLineItemId(), productLineItem.getQuantity(), 10.0));
        }
        orderEntity.setProductLineItemEntities(productLineItemEntities);

        orderEntity.setOrderStatus(OrderStatus.CREATED);
        orderRepository.save(orderEntity);
    }

    @EventHandler
    public void on(OrderCanceledEvent event) {
        OrderEntity orderEntity = orderRepository.findByOrderId(event.getOrderId());

        throwExceptionIfEntityDoesNotExist(orderEntity,
                String.format(ORDER_WITH_ID_DOES_NOT_EXIST, event.getOrderId()));

        orderEntity.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.save(orderEntity);
    }
}
