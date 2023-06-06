package control.tower.order.service.command.interceptors;

import control.tower.order.service.command.CreateOrderCommand;
import control.tower.order.service.core.data.OrderLookupEntity;
import control.tower.order.service.core.data.OrderLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

@Component
public class CreateOrderCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateOrderCommandInterceptor.class);

    private final OrderLookupRepository orderLookupRepository;

    public CreateOrderCommandInterceptor(OrderLookupRepository orderLookupRepository) {
        this.orderLookupRepository = orderLookupRepository;
    }

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {

            LOGGER.info("Intercepted command: " + command.getPayloadType());

            if (CreateOrderCommand.class.equals(command.getPayloadType())) {

                CreateOrderCommand createOrderCommand = (CreateOrderCommand) command.getPayload();

                OrderLookupEntity orderLookupEntity = orderLookupRepository.findByOrderId(
                        createOrderCommand.getOrderId());

                if (orderLookupEntity != null) {
                    throw new IllegalStateException(
                            String.format("Order with id %s already exists",
                                    createOrderCommand.getOrderId())
                    );
                }
            }

            return command;
        };
    }
}
