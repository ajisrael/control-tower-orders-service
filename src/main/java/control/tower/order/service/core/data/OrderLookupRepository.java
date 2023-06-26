package control.tower.order.service.core.data;

import control.tower.order.service.core.data.entities.OrderLookupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLookupRepository extends JpaRepository<OrderLookupEntity, String> {

    OrderLookupEntity findByOrderId(String orderId);
}
