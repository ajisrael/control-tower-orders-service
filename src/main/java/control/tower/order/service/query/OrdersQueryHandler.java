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
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static control.tower.order.service.core.constants.ExceptionMessages.ORDER_WITH_ID_DOES_NOT_EXIST;

@Component
@AllArgsConstructor
public class OrdersQueryHandler {

    private final OrderRepository orderRepository;
    private final OrderEntityToOrderDtoConverter orderEntityToOrderDtoConverter;

    @QueryHandler
    public Page<OrderQueryModel> findAllOrders(FindAllOrdersQuery query) {
         return orderRepository.findAll(query.getPageable())
                 .map(orderEntityToOrderDtoConverter::convert)
                 .map(this::convertOrderDtoToOrderQueryModel);
    }

    @QueryHandler
    public OrderQueryModel findOrder(FindOrderQuery query) {
       OrderEntity orderEntity = orderRepository.findById(query.getOrderId()).orElseThrow(
                () -> new IllegalStateException(String.format(ORDER_WITH_ID_DOES_NOT_EXIST, query.getOrderId())));
       OrderDto orderDto =  orderEntityToOrderDtoConverter.convert(orderEntity);
       return convertOrderDtoToOrderQueryModel(orderDto);
    }

    private OrderQueryModel convertOrderDtoToOrderQueryModel(OrderDto orderDto) {
        OrderQueryModel orderQueryModel = new OrderQueryModel();

        BeanUtils.copyProperties(orderDto, orderQueryModel);
        orderQueryModel.setOrderStatus(orderDto.getOrderStatus().toString());

        return orderQueryModel;
    }
}
