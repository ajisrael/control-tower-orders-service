package control.tower.order.service.command.interceptors;

import control.tower.core.model.OrderStatus;
import control.tower.order.service.command.commands.RemoveOrderCommand;
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
import static control.tower.core.utils.Helper.throwExceptionIfEntityDoesNotExist;
import static control.tower.order.service.core.constants.ExceptionMessages.ORDER_WITH_ID_DOES_NOT_EXIST;
import static control.tower.order.service.core.constants.ExceptionMessages.ORDER_WITH_ID_IS_ALREADY_CANCELED;

@Component
public class RemoveOrderCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoveOrderCommandInterceptor.class);

    private final OrderLookupRepository orderLookupRepository;

    public RemoveOrderCommandInterceptor(OrderLookupRepository orderLookupRepository) {
        this.orderLookupRepository = orderLookupRepository;
    }

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {

            if (RemoveOrderCommand.class.equals(command.getPayloadType())) {
                LOGGER.info(String.format(INTERCEPTED_COMMAND, command.getPayloadType()));

                RemoveOrderCommand removeOrderCommand = (RemoveOrderCommand) command.getPayload();

                removeOrderCommand.validate();

                String orderId = removeOrderCommand.getOrderId();

                throwExceptionIfEntityDoesNotExist(orderLookupRepository.findByOrderId(orderId),
                        String.format(ORDER_WITH_ID_DOES_NOT_EXIST, orderId));
            }

            return command;
        };
    }
}
