package control.tower.order.service.core.data.repositories;

import control.tower.order.service.core.data.entities.ServiceLineItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceLineItemRepository extends JpaRepository<ServiceLineItemEntity, String> {
}
