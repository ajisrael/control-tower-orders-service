package control.tower.order.service.command.interceptors;

import control.tower.core.model.OrderStatus;
import control.tower.order.service.command.commands.CancelOrderCommand;
import control.tower.order.service.core.data.OrderLookupEntity;
import control.tower.order.service.core.data.OrderLookupRepository;
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
                LOGGER.info(String.format(INTERCEPTED_COMMAND, command.getPayloadType()));

                CancelOrderCommand cancelOrderCommand = (CancelOrderCommand) command.getPayload();

                OrderLookupEntity orderLookupEntity = orderLookupRepository.findByOrderId(
                        cancelOrderCommand.getOrderId());

                throwExceptionIfEntityDoesNotExist(orderLookupEntity,
                        String.format(ORDER_WITH_ID_DOES_NOT_EXIST, cancelOrderCommand.getOrderId()));

                throwExceptionIfOrderIsCanceled(orderLookupEntity.getOrderStatus(),
                        String.format(ORDER_WITH_ID_IS_ALREADY_CANCELED, cancelOrderCommand.getOrderId()));
            }

            return command;
        };
    }

    private void throwExceptionIfOrderIsCanceled(OrderStatus orderStatus, String message) {
        if (orderStatus.equals(OrderStatus.CANCELED)) {
            throw new IllegalStateException(message);
        }
    }
}
