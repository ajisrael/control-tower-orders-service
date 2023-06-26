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
public class OrderDtoToOrderEntityConverter {

    public OrderEntity convert(OrderDto orderDto) {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(orderDto, orderEntity);

        orderEntity.setProductLineItemEntities(
                convertProductLineItemsToProductLineItemEntities(
                        orderDto.getProductLineItems()));

        orderEntity.setPromotionLineItemEntities(
                convertPromotionLineItemsToPromotionLineItemEntities(
                        orderDto.getPromotionLineItems()));

        orderEntity.setServiceLineItemEntities(
                convertServiceLineItemsToServiceLIneItemEntitites(
                        orderDto.getServiceLineItems()));

        return orderEntity;
    }

    private List<ProductLineItemEntity> convertProductLineItemsToProductLineItemEntities(List<ProductLineItem> productLineItems) {
        List<ProductLineItemEntity> productLineItemEntities = new ArrayList<>(productLineItems.size());

        for (ProductLineItem productLineItem: productLineItems) {
            productLineItemEntities.add(new ProductLineItemEntity(
                    productLineItem.getProductId(), productLineItem.getQuantity(), productLineItem.getUnitPrice()));
        }

        return productLineItemEntities;
    }

    private List<PromotionLineItemEntity> convertPromotionLineItemsToPromotionLineItemEntities(List<PromotionLineItem> promotionLineItems) {
        List<PromotionLineItemEntity> promotionLineItemEntities = new ArrayList<>(promotionLineItems.size());

        for (PromotionLineItem promotionLineItem: promotionLineItems) {
            promotionLineItemEntities.add(new PromotionLineItemEntity(
                    promotionLineItem.getPromotionId(), promotionLineItem.getUnitPrice()));
        }

        return promotionLineItemEntities;
    }

    private List<ServiceLineItemEntity> convertServiceLineItemsToServiceLIneItemEntitites(List<ServiceLineItem> serviceLineItems) {
        List<ServiceLineItemEntity> serviceLineItemEntities = new ArrayList<>(serviceLineItems.size());

        for (ServiceLineItem serviceLineItem: serviceLineItems) {
            serviceLineItemEntities.add(new ServiceLineItemEntity(serviceLineItem.getServiceId(), serviceLineItem.getUnitPrice()));
        }

        return serviceLineItemEntities;
    }
}
