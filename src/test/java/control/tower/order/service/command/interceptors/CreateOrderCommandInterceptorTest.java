package control.tower.order.service.command.interceptors;

import control.tower.core.model.OrderStatus;
import control.tower.order.service.command.commands.CreateOrderCommand;
import control.tower.order.service.core.data.OrderLookupEntity;
import control.tower.order.service.core.data.OrderLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class CreateOrderCommandInterceptorTest {

    private CreateOrderCommandInterceptor interceptor;
    private OrderLookupRepository lookupRepository;

    @BeforeEach
    void setUp() {
        lookupRepository = mock(OrderLookupRepository.class);
        interceptor = new CreateOrderCommandInterceptor(lookupRepository);
    }

    @Test
    void testHandle_ValidCommand() {
        CreateOrderCommand validCommand = CreateOrderCommand.builder()
                .orderId("orderId")
                .userId("userId")
                .paymentId("paymentId")
                .addressId("addressId")
                .productId("productId")
                .build();

        CommandMessage<CreateOrderCommand> commandMessage = new GenericCommandMessage<>(validCommand);

        BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> result = interceptor.handle(List.of(commandMessage));

        CommandMessage<?> processedCommand = result.apply(0, commandMessage);

        assertEquals(commandMessage, processedCommand);
    }

    @Test
    void testHandle_DuplicateOrderId_ThrowsException() {
        String orderId = "orderId";
        CreateOrderCommand duplicateCommand = CreateOrderCommand.builder()
                .orderId(orderId)
                .userId("userId")
                .paymentId("paymentId")
                .addressId("addressId")
                .productId("productId")
                .build();

        CommandMessage<CreateOrderCommand> commandMessage = new GenericCommandMessage<>(duplicateCommand);

        OrderLookupEntity existingEntity = new OrderLookupEntity(orderId, OrderStatus.CREATED);
        when(lookupRepository.findByOrderId(orderId)).thenReturn(existingEntity);

        BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> result = interceptor.handle(List.of(commandMessage));

        assertThrows(IllegalStateException.class, () -> result.apply(0, commandMessage));

        verify(lookupRepository).findByOrderId(orderId);
    }
}
