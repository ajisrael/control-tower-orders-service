package control.tower.order.service.command.rest.models;

import control.tower.order.service.core.valueobjects.ProductLineItem;
import control.tower.order.service.core.valueobjects.PromotionLineItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CreateOrderRestModel {

    @NotBlank(message = "User id is a required field")
    private String userId;
    @NotBlank(message = "Payment id is a required field")
    private String paymentId;
    @NotBlank(message = "Address id is a required field")
    private String addressId;
    @NotNull(message = "Product line items is a required field")
    @NotEmpty(message = "Product line items is a required field")
    private List<ProductLineItem> productLineItems;
    private List<PromotionLineItem> promotionLineItems;

    public List<PromotionLineItem> getPromotionLineItems() {
        if (this.promotionLineItems == null) {
            return new ArrayList<>();
        }
        return this.promotionLineItems;
    }
}
