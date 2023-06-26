package control.tower.order.service.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLineItemRepository extends JpaRepository<ProductLineItemEntity, String> {
}
