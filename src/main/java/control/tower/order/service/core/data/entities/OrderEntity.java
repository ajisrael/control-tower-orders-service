package control.tower.order.service.core.data.entities;

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
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductLineItemEntity> productLineItemEntities;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PromotionLineItemEntity> promotionLineItemEntities;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
