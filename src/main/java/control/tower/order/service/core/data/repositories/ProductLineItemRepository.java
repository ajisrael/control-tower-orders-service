package control.tower.order.service.core.data.repositories;

import control.tower.order.service.core.data.entities.ProductLineItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLineItemRepository extends JpaRepository<ProductLineItemEntity, String> {
}
