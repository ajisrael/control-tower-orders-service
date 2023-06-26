package control.tower.order.service.query;

import control.tower.core.model.OrderStatus;
import control.tower.order.service.core.data.converters.OrderDtoToOrderEntityConverter;
import control.tower.order.service.core.data.dtos.OrderDto;
import control.tower.order.service.core.data.entities.OrderEntity;
import control.tower.order.service.core.data.repositories.OrderRepository;
import control.tower.order.service.core.data.repositories.ProductLineItemRepository;
import control.tower.order.service.core.data.repositories.PromotionLineItemRepository;
import control.tower.order.service.core.data.repositories.ServiceLineItemRepository;
import control.tower.order.service.core.events.OrderCanceledEvent;
import control.tower.order.service.core.events.OrderCreatedEvent;
import control.tower.order.service.core.events.OrderRemovedEvent;
import control.tower.order.service.core.valueobjects.ProductLineItem;
import control.tower.order.service.core.valueobjects.PromotionLineItem;
import control.tower.order.service.core.valueobjects.ServiceLineItem;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import static control.tower.core.utils.Helper.throwExceptionIfEntityDoesNotExist;
import static control.tower.order.service.core.constants.ExceptionMessages.ORDER_WITH_ID_DOES_NOT_EXIST;

@Component
@ProcessingGroup("order-group")
public class OrderEventsHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventsHandler.class);

    private final OrderRepository orderRepository;
    private final ProductLineItemRepository productLineItemRepository;
    private final PromotionLineItemRepository promotionLineItemRepository;
    private final ServiceLineItemRepository serviceLineItemRepository;
    private final OrderDtoToOrderEntityConverter orderDtoToOrderEntityConverter;

    public OrderEventsHandler(OrderRepository orderRepository, ProductLineItemRepository productLineItemRepository, PromotionLineItemRepository promotionLineItemRepository, ServiceLineItemRepository serviceLineItemRepository, OrderDtoToOrderEntityConverter orderDtoToOrderEntityConverter) {
        this.orderRepository = orderRepository;
        this.productLineItemRepository = productLineItemRepository;
        this.promotionLineItemRepository = promotionLineItemRepository;
        this.serviceLineItemRepository = serviceLineItemRepository;
        this.orderDtoToOrderEntityConverter = orderDtoToOrderEntityConverter;
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
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(event, orderDto);
        orderDto.setOrderStatus(OrderStatus.CREATED);
        orderDto.setTotalPrice(calculateTotalPrice(orderDto));

        OrderEntity orderEntity = orderDtoToOrderEntityConverter.convert(orderDto);

        orderRepository.save(orderEntity);
        productLineItemRepository.saveAll(orderEntity.getProductLineItemEntities());
        promotionLineItemRepository.saveAll(orderEntity.getPromotionLineItemEntities());
        serviceLineItemRepository.saveAll(orderEntity.getServiceLineItemEntities());
    }

    @EventHandler
    public void on(OrderCanceledEvent event) {
        OrderEntity orderEntity = orderRepository.findByOrderId(event.getOrderId());

        throwExceptionIfEntityDoesNotExist(orderEntity,
                String.format(ORDER_WITH_ID_DOES_NOT_EXIST, event.getOrderId()));

        orderEntity.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.save(orderEntity);
    }

    @EventHandler
    public void on(OrderRemovedEvent event) {
        OrderEntity orderEntity = orderRepository.findByOrderId(event.getOrderId());

        throwExceptionIfEntityDoesNotExist(orderEntity,
                String.format(ORDER_WITH_ID_DOES_NOT_EXIST, event.getOrderId()));

        orderRepository.delete(orderEntity);
    }

    private Double calculateTotalPrice(OrderDto orderDto) {
        Double totalPrice = 0d;

        for (ProductLineItem productLineItem: orderDto.getProductLineItems()) {
            totalPrice += productLineItem.getUnitPrice() * productLineItem.getQuantity();
        }

        for (PromotionLineItem promotionLineItem: orderDto.getPromotionLineItems()) {
            totalPrice += promotionLineItem.getUnitPrice();
        }

        for (ServiceLineItem serviceLineItem: orderDto.getServiceLineItems()) {
            totalPrice += serviceLineItem.getUnitPrice();
        }

        if (totalPrice < 0d) {
            totalPrice = 0d;
        }

        return totalPrice;
    }
}
