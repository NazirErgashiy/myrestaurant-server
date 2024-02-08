package uz.pikosolutions.myrestaurant.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uz.pikosolutions.myrestaurant.dto.request.OrderRequest;
import uz.pikosolutions.myrestaurant.dto.response.OrderResponse;
import uz.pikosolutions.myrestaurant.entities.Order;
import uz.pikosolutions.myrestaurant.mappers.util.OrderMapperUtil;

@Mapper(componentModel = "spring",uses = {OrderMapperUtil.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OrderMapper {

    @Mapping(target = "cost", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    //@Mapping(target = "dishes",source = )
    Order dtoToEntity(OrderRequest request);

    OrderResponse entityToDto(Order entity);

    @Mapping(target = "cost", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    void toEntity(OrderRequest request, @MappingTarget Order order);
}
