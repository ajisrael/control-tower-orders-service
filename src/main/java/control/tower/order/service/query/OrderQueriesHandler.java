package control.tower.order.service.query;

import control.tower.order.service.core.data.OrderEntity;
import control.tower.order.service.core.data.OrdersRepository;
import com.appsdeveloperblog.estore.core.model.OrderSummary;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderQueriesHandler {

    OrdersRepository ordersRepository;

    @QueryHandler
    public OrderSummary findOrder(FindOrderQuery findOrderQuery) {
        OrderEntity orderEntity = ordersRepository.findByOrderId(findOrderQuery.getOrderId());
        return new OrderSummary(orderEntity.getOrderId(), orderEntity.getOrderStatus());
    }
}
