package control.tower.order.service.core.data.converters;

import control.tower.order.service.core.data.entities.OrderEntity;
import control.tower.order.service.core.data.entities.ProductLineItemEntity;
import control.tower.order.service.core.data.entities.PromotionLineItemEntity;
import control.tower.order.service.core.data.dtos.OrderDto;
import control.tower.order.service.core.data.entities.ServiceLineItemEntity;
import control.tower.order.service.core.valueobjects.ProductLineItem;
import control.tower.order.service.core.valueobjects.PromotionLineItem;
import control.tower.order.service.core.valueobjects.ServiceLineItem;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderEntityToOrderDtoConverter {

    public OrderDto convert(OrderEntity orderEntity) {
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderEntity, orderDto);

        orderDto.setProductLineItems(
                convertProductLineItemEntitiesToProductLineItems(
                        orderEntity.getProductLineItemEntities()));

        orderDto.setPromotionLineItems(
                convertPromotionLineItemEntitiesToPromotionLineItems(
                        orderEntity.getPromotionLineItemEntities()));

        orderDto.setServiceLineItems(
                convertServiceLineItemEntitiesToServiceLineItems(
                        orderEntity.getServiceLineItemEntities()));

        return orderDto;
    }

    private List<ProductLineItem> convertProductLineItemEntitiesToProductLineItems(List<ProductLineItemEntity> productLineItemEntities) {
        List<ProductLineItem> productLineItems = new ArrayList<>(productLineItemEntities.size());

        for (ProductLineItemEntity productLineItemEntity: productLineItemEntities) {
            productLineItems.add(new ProductLineItem(productLineItemEntity.getProductId(), productLineItemEntity.getQuantity(), productLineItemEntity.getUnitPrice()));
        }

        return productLineItems;
    }

    private List<PromotionLineItem> convertPromotionLineItemEntitiesToPromotionLineItems(List<PromotionLineItemEntity> promotionLineItemEntities) {
        List<PromotionLineItem> promotionLineItems = new ArrayList<>(promotionLineItemEntities.size());

        for (PromotionLineItemEntity promotionLineItemEntity: promotionLineItemEntities) {
            promotionLineItems.add(new PromotionLineItem(promotionLineItemEntity.getPromotionId(), promotionLineItemEntity.getUnitPrice()));
        }

        return promotionLineItems;
    }

    private List<ServiceLineItem> convertServiceLineItemEntitiesToServiceLineItems(List<ServiceLineItemEntity> serviceLineItemEntities) {
        List<ServiceLineItem> serviceLineItems = new ArrayList<>(serviceLineItemEntities.size());

        for (ServiceLineItemEntity serviceLineItemEntity: serviceLineItemEntities) {
            serviceLineItems.add(new ServiceLineItem(serviceLineItemEntity.getServiceId(), serviceLineItemEntity.getUnitPrice()));
        }

        return serviceLineItems;
    }

}
