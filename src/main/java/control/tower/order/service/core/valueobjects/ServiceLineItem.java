package control.tower.order.service.core.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceLineItem {

    private String serviceId;
    private Double unitPrice;
}
