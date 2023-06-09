package control.tower.order.service.query;

import control.tower.order.service.core.data.OrderEntity;
import control.tower.order.service.core.data.OrderRepository;
import control.tower.order.service.query.queries.FindAllOrdersQuery;
import control.tower.order.service.query.queries.FindOrderQuery;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

import static control.tower.core.utils.Helper.throwErrorIfEntityDoesNotExist;

@Component
@AllArgsConstructor
public class OrdersQueryHandler {

    private final OrderRepository orderRepository;

    @QueryHandler
    public List<OrderEntity> findAllOrders(FindAllOrdersQuery query) {
        return orderRepository.findAll();
    }

    @QueryHandler
    public OrderEntity findOrder(FindOrderQuery query) {
        OrderEntity orderEntity = orderRepository.findByOrderId(query.getOrderId());
        throwErrorIfEntityDoesNotExist(orderEntity, String.format("Order %s does not exist", query.getOrderId()));
        return orderEntity;
    }
}
