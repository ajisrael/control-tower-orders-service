package control.tower.order.service.command.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class CreateOrderRestModel {

    @NotBlank(message = "User id is a required field")
    private String userId;
    @NotBlank(message = "Payment id is a required field")
    private String paymentId;
    @NotBlank(message = "Address id is a required field")
    private String addressId;
    @NotBlank(message = "Product id is a required field")
    private String productId;
}
