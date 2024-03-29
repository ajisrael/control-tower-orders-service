package control.tower.order.service.command.rest.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class RemoveOrderRestModel {

    @NotBlank(message = "Order id is a required field")
    private String orderId;
}
