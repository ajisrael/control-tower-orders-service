package control.tower.order.service.query;

import control.tower.order.service.core.data.dtos.OrderDto;
import control.tower.order.service.core.data.entities.OrderEntity;
import control.tower.order.service.core.data.converters.OrderEntityToOrderDtoConverter;
import control.tower.order.service.core.data.repositories.OrderRepository;
import control.tower.order.service.query.queries.FindAllOrdersQuery;
import control.tower.order.service.query.queries.FindOrderQuery;
import control.tower.order.service.query.querymodels.OrderQueryModel;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static control.tower.order.service.core.constants.ExceptionMessages.ORDER_WITH_ID_DOES_NOT_EXIST;

@Component
@AllArgsConstructor
public class OrdersQueryHandler {

    private final OrderRepository orderRepository;
    private final OrderEntityToOrderDtoConverter orderEntityToOrderDtoConverter;

    @QueryHandler
    public List<OrderQueryModel> findAllOrders(FindAllOrdersQuery query) {
        List<OrderEntity> orderEntities = orderRepository.findAll();

        List<OrderDto> orderDtos = new ArrayList<>();

        for (OrderEntity orderEntity: orderEntities) {
            orderDtos.add(orderEntityToOrderDtoConverter.convert(orderEntity));
        }

        return convertOrderDtosToOrderQueryModels(orderDtos);
    }

    @QueryHandler
    public OrderQueryModel findOrder(FindOrderQuery query) {
       OrderEntity orderEntity = orderRepository.findById(query.getOrderId()).orElseThrow(
                () -> new IllegalStateException(String.format(ORDER_WITH_ID_DOES_NOT_EXIST, query.getOrderId())));
       OrderDto orderDto =  orderEntityToOrderDtoConverter.convert(orderEntity);
       return convertOrderDtoToOrderQueryModel(orderDto);
    }

    private List<OrderQueryModel> convertOrderDtosToOrderQueryModels(
            List<OrderDto> orderDtos) {
        List<OrderQueryModel> orderQueryModels = new ArrayList<>();

        for (OrderDto orderDto: orderDtos) {
            orderQueryModels.add(convertOrderDtoToOrderQueryModel(orderDto));
        }

        return orderQueryModels;
    }

    private OrderQueryModel convertOrderDtoToOrderQueryModel(OrderDto orderDto) {
        return new OrderQueryModel(
                orderDto.getOrderId(),
                orderDto.getUserId(),
                orderDto.getPaymentId(),
                orderDto.getAddressId(),
                orderDto.getProductLineItems(),
                orderDto.getPromotionLineItems(),
                orderDto.getOrderStatus().toString()
        );
    }
}
