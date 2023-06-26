package control.tower.order.service.core.data.converters;

import control.tower.order.service.core.data.OrderEntity;
import control.tower.order.service.core.data.ProductLineItemEntity;
import control.tower.order.service.core.data.PromotionLineItemEntity;
import control.tower.order.service.core.data.dtos.OrderDto;
import control.tower.order.service.core.valueobjects.ProductLineItem;
import control.tower.order.service.core.valueobjects.PromotionLineItem;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderEntityToOrderDtoConverter {

    public OrderDto convert(OrderEntity orderEntity) {
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderEntity, orderDto);

        List<ProductLineItem> productLineItems = new ArrayList<>();

        for (ProductLineItemEntity productLineItemEntity: orderEntity.getProductLineItemEntities()) {
            productLineItems.add(new ProductLineItem(productLineItemEntity.getProductId(), productLineItemEntity.getQuantity(), productLineItemEntity.getUnitPrice()));
        }

        List<PromotionLineItem> promotionLineItems = new ArrayList<>();

        for (PromotionLineItemEntity promotionLineItemEntity: orderEntity.getPromotionLineItemEntities()) {
            promotionLineItems.add(new PromotionLineItem(promotionLineItemEntity.getPromotionId(), promotionLineItemEntity.getUnitPrice()));
        }

        orderDto.setProductLineItems(productLineItems);
        orderDto.setPromotionLineItems(promotionLineItems);

        return orderDto;
    }
}
