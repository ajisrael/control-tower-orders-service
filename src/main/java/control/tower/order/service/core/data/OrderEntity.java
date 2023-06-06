package control.tower.order.service.core.data;

import control.tower.core.model.OrderStatus;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name="orders")
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = 789654123987456333L;

    @Id
    @Column(unique = true)
    private String orderId;
    private String userId;
    private String paymentId;
    private String addressId;
    private String productId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
