package control.tower.order.service.command.rest;

import control.tower.order.service.command.commands.CancelOrderCommand;
import control.tower.order.service.command.commands.CreateOrderCommand;
import control.tower.order.service.command.commands.RemoveOrderCommand;
import control.tower.order.service.command.rest.requests.CancelOrderRestModel;
import control.tower.order.service.command.rest.requests.CreateOrderRestModel;
import control.tower.order.service.command.rest.requests.RemoveOrderRestModel;
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

    @PostMapping()
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create order")
    public String createOrder(@Valid @RequestBody CreateOrderRestModel createOrderRestModel) {
        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .orderId(UUID.randomUUID().toString())
                .userId(createOrderRestModel.getUserId())
                .paymentId(createOrderRestModel.getPaymentId())
                .addressId(createOrderRestModel.getAddressId())
                .productLineItems(createOrderRestModel.getProductLineItems())
                .promotionLineItems(createOrderRestModel.getPromotionLineItems())
                .serviceLineItems(createOrderRestModel.getServiceLineItems())
                .build();

        return commandGateway.sendAndWait(createOrderCommand);
    }

    @PutMapping()
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Cancel order")
    public void cancelOrder(@Valid @RequestBody CancelOrderRestModel cancelOrderRestModel) {
        CancelOrderCommand cancelOrderCommand = CancelOrderCommand.builder()
                .orderId(cancelOrderRestModel.getOrderId())
                .build();

        commandGateway.sendAndWait(cancelOrderCommand);
    }

    @DeleteMapping()
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove order")
    public void removeOrder(@Valid @RequestBody RemoveOrderRestModel removeOrderRestModel) {
        RemoveOrderCommand removeOrderCommand = RemoveOrderCommand.builder()
                .orderId(removeOrderRestModel.getOrderId())
                .build();

        commandGateway.sendAndWait(removeOrderCommand);
    }
}