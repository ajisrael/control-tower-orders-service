package control.tower.order.service.core.constants;

import static control.tower.core.constants.ExceptionMessages.*;
import static control.tower.order.service.core.constants.DomainConstants.ORDER;

public class ExceptionMessages {

    private ExceptionMessages() {
        throw new IllegalStateException("Constants class");
    }

    public static final String ORDER_ID_CANNOT_BE_EMPTY = String.format(PARAMETER_CANNOT_BE_EMPTY, "orderId");
    public static final String USER_ID_CANNOT_BE_EMPTY = String.format(PARAMETER_CANNOT_BE_EMPTY, "userId");
    public static final String PAYMENT_ID_CANNOT_BE_EMPTY = String.format(PARAMETER_CANNOT_BE_EMPTY, "paymentId");
    public static final String ADDRESS_ID_CANNOT_BE_EMPTY = String.format(PARAMETER_CANNOT_BE_EMPTY, "addressId");
    public static final String PRODUCT_ID_CANNOT_BE_EMPTY = String.format(PARAMETER_CANNOT_BE_EMPTY, "productId");

    public static final String ORDER_WITH_ID_DOES_NOT_EXIST = String.format(ENTITY_WITH_ID_DOES_NOT_EXIST, ORDER, "%s");
    public static final String ORDER_WITH_ID_ALREADY_EXISTS = String.format(ENTITY_WITH_ID_ALREADY_EXISTS, ORDER, "%s");
    public static final String ORDER_WITH_ID_IS_ALREADY_CANCELED = "Order with id %s is already canceled";
}
