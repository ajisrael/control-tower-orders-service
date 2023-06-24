package control.tower.order.service.query;

import control.tower.order.service.core.data.OrderEntity;
import control.tower.order.service.core.data.OrderRepository;
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

    @QueryHandler
    public List<OrderQueryModel> findAllOrders(FindAllOrdersQuery query) {
        List<OrderEntity> orderEntities = orderRepository.findAll();

        return convertOrderEntitiesToOrderQueryModels(orderEntities);
    }

    @QueryHandler
    public OrderQueryModel findOrder(FindOrderQuery query) {
       OrderEntity orderEntity = orderRepository.findById(query.getOrderId()).orElseThrow(
                () -> new IllegalStateException(String.format(ORDER_WITH_ID_DOES_NOT_EXIST, query.getOrderId())));

       return convertOrderEntityToOrderQueryModel(orderEntity);
    }

    private List<OrderQueryModel> convertOrderEntitiesToOrderQueryModels(
            List<OrderEntity> orderEntities) {
        List<OrderQueryModel> orderQueryModels = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntities) {
            orderQueryModels.add(convertOrderEntityToOrderQueryModel(orderEntity));
        }

        return orderQueryModels;
    }

    private OrderQueryModel convertOrderEntityToOrderQueryModel(OrderEntity orderEntity) {
        return new OrderQueryModel(
                orderEntity.getOrderId(),
                orderEntity.getUserId(),
                orderEntity.getPaymentId(),
                orderEntity.getAddressId(),
                orderEntity.getProductLineItemEntities(),
                orderEntity.getOrderStatus().toString()
        );
    }
}
