package control.tower.order.service.command.interceptors;

import control.tower.core.model.OrderStatus;
import control.tower.order.service.command.commands.CancelOrderCommand;
import control.tower.order.service.command.commands.CreateOrderCommand;
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
public class CancelOrderCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CancelOrderCommandInterceptor.class);

    private final OrderLookupRepository orderLookupRepository;

    public CancelOrderCommandInterceptor(OrderLookupRepository orderLookupRepository) {
        this.orderLookupRepository = orderLookupRepository;
    }

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {

            if (CancelOrderCommand.class.equals(command.getPayloadType())) {
                LOGGER.info("Intercepted command: " + command.getPayloadType());

                CancelOrderCommand cancelOrderCommand = (CancelOrderCommand) command.getPayload();

                OrderLookupEntity orderLookupEntity = orderLookupRepository.findByOrderId(
                        cancelOrderCommand.getOrderId());

                if (orderLookupEntity == null) {
                    throw new IllegalStateException(
                            String.format("Order with id %s does not exist",
                                    cancelOrderCommand.getOrderId())
                    );
                }

                if (orderLookupEntity.getOrderStatus().equals(OrderStatus.CANCELED)) {
                    throw new IllegalStateException(
                            String.format("Order with id %s is already canceled",
                                    cancelOrderCommand.getOrderId())
                    );
                }
            }

            return command;
        };
    }
}
