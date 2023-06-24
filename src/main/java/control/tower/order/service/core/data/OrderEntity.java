package control.tower.order.service.core.data;

import control.tower.core.model.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="`order`")
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = 789654123987456333L;

    @Id
    @Column(unique = true)
    private String orderId;
    private String userId;
    private String paymentId;
    private String addressId;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<ProductLineItemEntity> productLineItemEntities;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
