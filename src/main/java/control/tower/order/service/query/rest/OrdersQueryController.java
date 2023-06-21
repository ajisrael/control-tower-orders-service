package control.tower.order.service.query.rest;

import control.tower.order.service.query.queries.FindAllOrdersQuery;
import control.tower.order.service.query.queries.FindOrderQuery;
import control.tower.order.service.query.querymodels.OrderQueryModel;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersQueryController {

    @Autowired
    QueryGateway queryGateway;

    @GetMapping
    public List<OrderQueryModel> getOrders() {
        return queryGateway.query(new FindAllOrdersQuery(),
                ResponseTypes.multipleInstancesOf(OrderQueryModel.class)).join();
    }

    @GetMapping(params = "orderId")
    public OrderQueryModel getOrder(String orderId) {
        return queryGateway.query(new FindOrderQuery(orderId),
                ResponseTypes.instanceOf(OrderQueryModel.class)).join();
    }
}
