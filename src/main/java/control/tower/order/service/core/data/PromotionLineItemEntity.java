package control.tower.order.service.core.data;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class PromotionLineItemEntity implements Serializable {

    private static final long serialVersionUID = 789856123987456333L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String promotionId;
    private Double unitPrice;

    public PromotionLineItemEntity(String promotionId, Double unitPrice) {
        this.promotionId = promotionId;
        this.unitPrice = unitPrice;
    }

    public PromotionLineItemEntity() {
        this.promotionId = "promotionId";
        this.unitPrice = 0.0;
    }
}
