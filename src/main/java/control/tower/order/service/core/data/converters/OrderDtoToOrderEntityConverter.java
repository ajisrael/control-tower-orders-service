package control.tower.order.service.core.data.converters;

import control.tower.order.service.core.data.entities.OrderEntity;
import control.tower.order.service.core.data.entities.ProductLineItemEntity;
import control.tower.order.service.core.data.entities.PromotionLineItemEntity;
import control.tower.order.service.core.data.dtos.OrderDto;
import control.tower.order.service.core.valueobjects.ProductLineItem;
import control.tower.order.service.core.valueobjects.PromotionLineItem;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDtoToOrderEntityConverter {

    public OrderEntity convert(OrderDto orderDto) {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(orderDto, orderEntity);

        List<ProductLineItemEntity> productLineItemEntities = new ArrayList<>(orderDto.getProductLineItems().size());

        for (ProductLineItem productLineItem: orderDto.getProductLineItems()) {
            productLineItemEntities.add( new ProductLineItemEntity(
                    productLineItem.getProductId(), productLineItem.getQuantity(), productLineItem.getUnitPrice()));
        }

        orderEntity.setProductLineItemEntities(productLineItemEntities);

        List<PromotionLineItemEntity> promotionLineItemEntities = new ArrayList<>(orderDto.getPromotionLineItems().size());

        for (PromotionLineItem promotionLineItem: orderDto.getPromotionLineItems()) {
            promotionLineItemEntities.add( new PromotionLineItemEntity(
                    promotionLineItem.getPromotionId(), promotionLineItem.getUnitPrice()));
        }

        orderEntity.setPromotionLineItemEntities(promotionLineItemEntities);

        return orderEntity;
    }
}
