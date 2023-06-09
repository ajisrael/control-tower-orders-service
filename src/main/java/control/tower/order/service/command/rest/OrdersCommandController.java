package control.tower.order.service.command.rest;

import control.tower.core.model.OrderStatus;
import control.tower.order.service.command.commands.CancelOrderCommand;
import control.tower.order.service.command.commands.CreateOrderCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrdersCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping(path = "/create")
    public String createOrder(@Valid @RequestBody CreateOrderRestModel createOrderRestModel) {
        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .orderId(UUID.randomUUID().toString())
                .userId(createOrderRestModel.getUserId())
                .paymentId(createOrderRestModel.getPaymentId())
                .addressId(createOrderRestModel.getAddressId())
                .productId(createOrderRestModel.getProductId())
                .build();

        return commandGateway.sendAndWait(createOrderCommand);
    }

    @PostMapping(path = "/cancel")
    public String cancelOrder(@Valid @RequestBody CancelOrderRestModel cancelOrderRestModel) {
        CancelOrderCommand cancelOrderCommand = CancelOrderCommand.builder()
                .orderId(cancelOrderRestModel.getOrderId())
                .build();

        return commandGateway.sendAndWait(cancelOrderCommand);
    }
}


