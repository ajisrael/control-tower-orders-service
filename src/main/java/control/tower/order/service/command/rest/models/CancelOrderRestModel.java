package control.tower.order.service.command.rest.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CancelOrderRestModel {

    @NotBlank(message = "Order id is required")
    private String orderId;
}
