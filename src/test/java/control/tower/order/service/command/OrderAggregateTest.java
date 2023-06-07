package control.tower.order.service.command;

import control.tower.core.model.OrderStatus;
import control.tower.order.service.command.commands.CreateOrderCommand;
import control.tower.order.service.core.events.OrderCreatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderAggregateTest {

    private final String ORDER_ID = "orderId";
    private final String USER_ID = "userId";
    private final String PAYMENT_ID = "paymentId";
    private final String ADDRESS_ID = "addressId";
    private final String PRODUCT_ID = "productId";
    private OrderStatus orderStatus = OrderStatus.CREATED;

    private FixtureConfiguration<OrderAggregate> fixture;

    @BeforeEach
    void setup() {
        fixture = new AggregateTestFixture<>(OrderAggregate.class);
    }

    @Test
    void shouldCreateProductAggregate() {
        fixture.givenNoPriorActivity()
                .when(
                        CreateOrderCommand.builder()
                                .orderId(ORDER_ID)
                                .userId(USER_ID)
                                .paymentId(PAYMENT_ID)
                                .addressId(ADDRESS_ID)
                                .productId(PRODUCT_ID)
                                .orderStatus(orderStatus)
                                .build())
                .expectEvents(
                        OrderCreatedEvent.builder()
                                .orderId(ORDER_ID)
                                .userId(USER_ID)
                                .paymentId(PAYMENT_ID)
                                .addressId(ADDRESS_ID)
                                .productId(PRODUCT_ID)
                                .orderStatus(orderStatus)
                                .build())
                .expectState(
                        orderAggregate -> {
                            assertEquals(ORDER_ID, orderAggregate.getOrderId());
                            assertEquals(USER_ID, orderAggregate.getUserId());
                            assertEquals(PAYMENT_ID, orderAggregate.getPaymentId());
                            assertEquals(ADDRESS_ID, orderAggregate.getAddressId());
                            assertEquals(PRODUCT_ID, orderAggregate.getProductId());
                            assertEquals(orderStatus, orderAggregate.getOrderStatus());
                        }
                );
    }
}
