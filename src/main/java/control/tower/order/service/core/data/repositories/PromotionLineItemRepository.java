package control.tower.order.service.core.data.repositories;

import control.tower.order.service.core.data.entities.PromotionLineItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionLineItemRepository extends JpaRepository<PromotionLineItemEntity, String> {
}
