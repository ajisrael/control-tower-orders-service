package control.tower.order.service.query;

import control.tower.order.service.core.data.OrderEntity;
import control.tower.order.service.core.data.OrderRepository;
import control.tower.order.service.query.queries.FindAllOrdersQuery;
import control.tower.order.service.query.queries.FindOrderQuery;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

import static control.tower.order.service.core.constants.ExceptionMessages.ORDER_WITH_ID_DOES_NOT_EXIST;

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
        return orderRepository.findById(query.getOrderId()).orElseThrow(
                () -> new IllegalStateException(String.format(ORDER_WITH_ID_DOES_NOT_EXIST, query.getOrderId())));
    }
}
