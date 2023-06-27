package control.tower.order.service.command.rest;

import control.tower.order.service.command.commands.CancelOrderCommand;
import control.tower.order.service.command.commands.CreateOrderCommand;
import control.tower.order.service.command.commands.RemoveOrderCommand;
import control.tower.order.service.command.rest.models.CancelOrderRestModel;
import control.tower.order.service.command.rest.models.CreateOrderRestModel;
import control.tower.order.service.command.rest.models.RemoveOrderRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrdersCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping()
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
    public String cancelOrder(@Valid @RequestBody CancelOrderRestModel cancelOrderRestModel) {
        CancelOrderCommand cancelOrderCommand = CancelOrderCommand.builder()
                .orderId(cancelOrderRestModel.getOrderId())
                .build();

        return commandGateway.sendAndWait(cancelOrderCommand);
    }

    @DeleteMapping()
    public String removeOrder(@Valid @RequestBody RemoveOrderRestModel removeOrderRestModel) {
        RemoveOrderCommand removeOrderCommand = RemoveOrderCommand.builder()
                .orderId(removeOrderRestModel.getOrderId())
                .build();

        return commandGateway.sendAndWait(removeOrderCommand);
    }
}