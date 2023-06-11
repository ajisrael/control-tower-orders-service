package control.tower.order.service.query.rest;

import control.tower.order.service.query.queries.FindAllOrdersQuery;
import control.tower.order.service.core.data.OrderEntity;
import control.tower.order.service.query.queries.FindOrderQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersQueryController {

    @Autowired
    QueryGateway queryGateway;

    @GetMapping
    public List<OrderRestModel> getOrders() {
        List<OrderEntity> orderEntities = queryGateway.query(new FindAllOrdersQuery(),
                ResponseTypes.multipleInstancesOf(OrderEntity.class)).join();

        return convertOrderEntitiesToOrderRestModels(orderEntities);
    }

    @GetMapping(params = "orderId")
    public OrderRestModel getOrder(String orderId) {
        OrderEntity orderEntity = queryGateway.query(new FindOrderQuery(orderId),
                ResponseTypes.instanceOf(OrderEntity.class)).join();

        return convertOrderEntityToOrderRestModel(orderEntity);
    }

    private List<OrderRestModel> convertOrderEntitiesToOrderRestModels(
            List<OrderEntity> orderEntities) {
        List<OrderRestModel> orderRestModels = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntities) {
            orderRestModels.add(convertOrderEntityToOrderRestModel(orderEntity));
        }

        return orderRestModels;
    }

    private OrderRestModel convertOrderEntityToOrderRestModel(OrderEntity orderEntity) {
        return new OrderRestModel(
                orderEntity.getOrderId(),
                orderEntity.getUserId(),
                orderEntity.getPaymentId(),
                orderEntity.getAddressId(),
                orderEntity.getProductId(),
                orderEntity.getOrderStatus().toString()
        );
    }
}
