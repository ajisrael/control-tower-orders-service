package control.tower.order.service.query.rest;

import control.tower.order.service.query.queries.FindAllOrdersQuery;
import control.tower.order.service.query.queries.FindOrderQuery;
import control.tower.order.service.query.querymodels.OrderQueryModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders Query API")
public class OrdersQueryController {

    @Autowired
    QueryGateway queryGateway;

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all orders")
    public List<OrderQueryModel> getOrders() {
        return queryGateway.query(new FindAllOrdersQuery(),
                ResponseTypes.multipleInstancesOf(OrderQueryModel.class)).join();
    }

    @GetMapping(params = "orderId")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get order by id")
    public OrderQueryModel getOrder(String orderId) {
        return queryGateway.query(new FindOrderQuery(orderId),
                ResponseTypes.instanceOf(OrderQueryModel.class)).join();
    }
}
