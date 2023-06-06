package control.tower.order.service.command.rest;

import control.tower.core.model.OrderStatus;
import control.tower.order.service.command.CreateOrderCommand;
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

    @PostMapping
    public String createOrder(@Valid @RequestBody CreateOrderRestModel createOrderRestModel) {
        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .orderId(UUID.randomUUID().toString())
                .userId(createOrderRestModel.getUserId())
                .paymentId(createOrderRestModel.getPaymentId())
                .addressId(createOrderRestModel.getAddressId())
                .productId(createOrderRestModel.getProductId())
                .orderStatus(OrderStatus.CREATED)
                .build();

        return commandGateway.sendAndWait(createOrderCommand);
    }
}


