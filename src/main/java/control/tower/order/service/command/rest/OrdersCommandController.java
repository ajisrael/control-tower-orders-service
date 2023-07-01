package control.tower.order.service.command.rest;

import control.tower.order.service.command.commands.CancelOrderCommand;
import control.tower.order.service.command.commands.CreateOrderCommand;
import control.tower.order.service.command.commands.RemoveOrderCommand;
import control.tower.order.service.command.rest.requests.CancelOrderRequestModel;
import control.tower.order.service.command.rest.requests.CreateOrderRequestModel;
import control.tower.order.service.command.rest.requests.RemoveOrderRequestModel;
import control.tower.order.service.command.rest.responses.OrderCreatedResponseModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders Command API")
public class OrdersCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create order")
    public OrderCreatedResponseModel createOrder(@Valid @RequestBody CreateOrderRequestModel createOrderRequestModel) {
        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .orderId(UUID.randomUUID().toString())
                .userId(createOrderRequestModel.getUserId())
                .paymentId(createOrderRequestModel.getPaymentId())
                .addressId(createOrderRequestModel.getAddressId())
                .productLineItems(createOrderRequestModel.getProductLineItems())
                .promotionLineItems(createOrderRequestModel.getPromotionLineItems())
                .serviceLineItems(createOrderRequestModel.getServiceLineItems())
                .build();

        String orderId = commandGateway.sendAndWait(createOrderCommand);

        return OrderCreatedResponseModel.builder().orderId(orderId).build();
    }

    @PutMapping()
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Cancel order")
    public void cancelOrder(@Valid @RequestBody CancelOrderRequestModel cancelOrderRequestModel) {
        CancelOrderCommand cancelOrderCommand = CancelOrderCommand.builder()
                .orderId(cancelOrderRequestModel.getOrderId())
                .build();

        commandGateway.sendAndWait(cancelOrderCommand);
    }

    @DeleteMapping()
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove order")
    public void removeOrder(@Valid @RequestBody RemoveOrderRequestModel removeOrderRequestModel) {
        RemoveOrderCommand removeOrderCommand = RemoveOrderCommand.builder()
                .orderId(removeOrderRequestModel.getOrderId())
                .build();

        commandGateway.sendAndWait(removeOrderCommand);
    }
}