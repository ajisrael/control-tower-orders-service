package control.tower.order.service.command.rest;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class CreateOrderRestModel {

    @NotBlank(message = "Product Id is a required field")
    private String productId;
    @Min(value = 1, message = "Quantity cannot be lower than 1")
    @Max(value = 5, message = "Quantity cannot be larger than 5")
    private int quantity;
    @NotBlank(message = "Product Id is a required field")
    private String addressId;
}
