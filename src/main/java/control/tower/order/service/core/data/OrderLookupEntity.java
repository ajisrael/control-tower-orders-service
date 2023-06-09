package control.tower.order.service.core.data;

import control.tower.core.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orderlookup")
public class OrderLookupEntity implements Serializable {

    private static final long serialVersionUID = -4787108556148621716L;

    @Id
    @Column(unique = true)
    private String orderId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
