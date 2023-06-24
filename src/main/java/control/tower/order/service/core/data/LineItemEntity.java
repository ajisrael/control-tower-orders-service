package control.tower.order.service.core.data;

import control.tower.order.service.core.models.LineItemType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Data
@NoArgsConstructor
public class LineItemEntity {

    private String lineItemId;
    @Enumerated(EnumType.STRING)
    private LineItemType lineItemType;
}
