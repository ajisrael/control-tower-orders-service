package control.tower.order.service.query.rest;

import control.tower.core.rest.PageResponseType;
import control.tower.core.rest.PaginationResponse;
import control.tower.core.utils.PaginationUtility;
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

import java.util.concurrent.CompletableFuture;

import static control.tower.core.constants.DomainConstants.DEFAULT_PAGE;
import static control.tower.core.constants.DomainConstants.DEFAULT_PAGE_SIZE;

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
    public CompletableFuture<PaginationResponse<OrderQueryModel>> getOrders(
            @RequestParam(defaultValue = DEFAULT_PAGE) int currentPage,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        FindAllOrdersQuery findAllOrdersQuery = FindAllOrdersQuery.builder()
                .pageable(PaginationUtility.buildPageable(currentPage, pageSize))
                .build();

        return queryGateway.query(findAllOrdersQuery, new PageResponseType<>(OrderQueryModel.class))
                .thenApply(PaginationUtility::toPageResponse);
    }

    @GetMapping(params = "orderId")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get order by id")
    public CompletableFuture<OrderQueryModel> getOrder(String orderId) {
        return queryGateway.query(new FindOrderQuery(orderId),
                ResponseTypes.instanceOf(OrderQueryModel.class));
    }
}
