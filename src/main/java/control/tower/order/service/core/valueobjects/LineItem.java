package control.tower.order.service.core.valueobjects;

import control.tower.order.service.core.models.LineItemType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LineItem {

    private String lineItemId;
    private LineItemType lineItemType;
}
