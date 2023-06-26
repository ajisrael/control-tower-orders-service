package control.tower.order.service.command.interceptors;

import control.tower.order.service.command.commands.CreateOrderCommand;
import control.tower.order.service.core.data.entities.OrderLookupEntity;
import control.tower.order.service.core.data.repositories.OrderLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

import static control.tower.core.constants.LogMessages.INTERCEPTED_COMMAND;
import static control.tower.core.utils.Helper.throwExceptionIfEntityDoesExist;
import static control.tower.order.service.core.constants.ExceptionMessages.ORDER_WITH_ID_ALREADY_EXISTS;

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

            if (CreateOrderCommand.class.equals(command.getPayloadType())) {
                LOGGER.info(String.format(INTERCEPTED_COMMAND, command.getPayloadType()));

                CreateOrderCommand createOrderCommand = (CreateOrderCommand) command.getPayload();

                createOrderCommand.validate();

                OrderLookupEntity orderLookupEntity = orderLookupRepository.findByOrderId(
                        createOrderCommand.getOrderId());

                throwExceptionIfEntityDoesExist(orderLookupEntity,
                        String.format(ORDER_WITH_ID_ALREADY_EXISTS, createOrderCommand.getOrderId()));
            }

            return command;
        };
    }
}
