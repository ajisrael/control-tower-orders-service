package control.tower.order.service.command.commands;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import static control.tower.core.utils.Helper.throwExceptionIfParameterIsEmpty;
import static control.tower.order.service.core.constants.ExceptionMessages.ORDER_ID_CANNOT_BE_EMPTY;

@Getter
@Builder
public class RemoveOrderCommand {

    @TargetAggregateIdentifier
    private String orderId;

    public void validate() {
        throwExceptionIfParameterIsEmpty(this.getOrderId(), ORDER_ID_CANNOT_BE_EMPTY);
    }
}
