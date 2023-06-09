package control.tower.order.service.command.commands;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import static control.tower.core.utils.Helper.isNullOrBlank;

@Getter
@Builder
public class CancelOrderCommand {

    @TargetAggregateIdentifier
    private String orderId;

    public void validate() {
        if (isNullOrBlank(this.getOrderId())) {
            throw new IllegalArgumentException("OrderId cannot be empty");
        }
    }
}
