package control.tower.order.service.core.data.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class ProductLineItemEntity implements Serializable {

    private static final long serialVersionUID = 789656123987456333L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;
    private Integer quantity;
    private Double unitPrice;

    public ProductLineItemEntity(String productId, Integer quantity, Double unitPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public ProductLineItemEntity() {
        this.productId = "productId";
        this.quantity = 1;
        this.unitPrice = 0.0;
    }
}
