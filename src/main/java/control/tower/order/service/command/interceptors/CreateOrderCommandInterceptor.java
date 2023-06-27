package control.tower.order.service.command.interceptors;

import control.tower.core.query.queries.*;
import control.tower.order.service.command.commands.CreateOrderCommand;
import control.tower.order.service.core.data.repositories.OrderLookupRepository;
import control.tower.order.service.core.valueobjects.ProductLineItem;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static control.tower.core.constants.LogMessages.INTERCEPTED_COMMAND;
import static control.tower.core.utils.Helper.throwExceptionIfEntityDoesExist;
import static control.tower.order.service.core.constants.ExceptionMessages.ORDER_WITH_ID_ALREADY_EXISTS;

@Component
public class CreateOrderCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateOrderCommandInterceptor.class);

    @Autowired
    private QueryGateway queryGateway;

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

                String orderId = createOrderCommand.getOrderId();

                throwExceptionIfEntityDoesExist(
                        orderLookupRepository.findByOrderId(orderId),
                        String.format(ORDER_WITH_ID_ALREADY_EXISTS, orderId));

                String userId = createOrderCommand.getUserId();

                validateUserExists(userId);
                validateAddressExistsForUser(createOrderCommand.getAddressId(), userId);
                validatePaymentMethodExistsForUser(createOrderCommand.getPaymentId(), userId);

                List<String> productIds = createOrderCommand.getProductLineItems().stream()
                        .map(ProductLineItem::getProductId)
                        .collect(Collectors.toList());

                validateProductsExist(productIds);
            }

            return command;
        };
    }

    private void validateUserExists(String userId) {
        boolean doesUserExist = queryGateway.query(new DoesUserExistQuery(userId),
                ResponseTypes.instanceOf(boolean.class)).join();

        if (!doesUserExist) {
            throw new IllegalArgumentException(
                    "User " + userId + " does not exist, cannot create order");
        }
    }

    private void validateAddressExistsForUser(String addressId, String userId) {
        boolean addressExistsForUser = queryGateway.query(new DoesAddressExistForUserQuery(addressId, userId),
                ResponseTypes.instanceOf(boolean.class)).join();

        if (!addressExistsForUser) {
            throw new IllegalArgumentException(
                    "Address " + addressId + " not registered for user " + userId + ", cannot create order");
        }
    }

    private void validatePaymentMethodExistsForUser(String paymentId, String userId) {
        boolean paymentMethodExistsForUser = queryGateway.query(new DoesPaymentMethodExistForUserQuery(paymentId, userId),
                ResponseTypes.instanceOf(boolean.class)).join();

        if (!paymentMethodExistsForUser) {
            throw new IllegalArgumentException(
                    "Payment method " + paymentId + " does not exist for user " + userId + ", cannot create order");
        }
    }

    private void validateProductsExist(List<String> productId) {
        boolean productsExist = queryGateway.query(new DoProductsExistQuery(productId),
                ResponseTypes.instanceOf(boolean.class)).join();

        if (!productsExist) {
            throw new IllegalArgumentException("At least one product in order doesn't exist, cannot create order");
        }
    }
}
