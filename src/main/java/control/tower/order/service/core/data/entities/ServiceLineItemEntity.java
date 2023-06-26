package control.tower.order.service.core.data.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class ServiceLineItemEntity implements Serializable {

    private static final long serialVersionUID = 789856123987456333L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceId;
    private Double unitPrice;

    public ServiceLineItemEntity(String serviceId, Double unitPrice) {
        this.serviceId = serviceId;
        this.unitPrice = unitPrice;
    }

    public ServiceLineItemEntity() {
        this.serviceId = "serviceId";
        this.unitPrice = 0.0;
    }
}
