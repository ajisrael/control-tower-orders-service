package control.tower.order.service.query;

import control.tower.order.service.core.data.OrderEntity;
import control.tower.order.service.core.data.OrderRepository;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class OrdersQueryHandler {

    private final OrderRepository orderRepository;

    @QueryHandler
    public List<OrderEntity> findAllOrders(FindAllOrdersQuery query) {
        return orderRepository.findAll();
    }
}
