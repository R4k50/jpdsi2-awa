package com.pizza.backend.repositories;

import com.pizza.backend.dtos.orderedProducts.OrderedProductDto;
import com.pizza.backend.dtos.orders.FullOrderDto;
import com.pizza.backend.entities.Order;
import com.pizza.backend.mappers.OrderMapper;
import com.pizza.backend.mappers.OrderedProductMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom
{
  @PersistenceContext
  EntityManager entityManager;
  OrderMapper orderMapper;
  OrderedProductMapper orderedProductMapper;

  @Override
  public Page<FullOrderDto> findAll(String searchParam, String searchValue, Pageable pageable)
  {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
    Root<Order> order = criteriaQuery.from(Order.class);

    Expression serachColumn = order.get(searchParam);
    Class columnType = order.get(searchParam).getModel().getBindableJavaType();


    criteriaQuery.select(order);

    if (columnType.isAssignableFrom(String.class))
      criteriaQuery.where(
        criteriaBuilder.like(
          serachColumn,
          "%" + searchValue + "%"
        )
      );
    else
      criteriaQuery.where(
        criteriaBuilder.equal(
          serachColumn,
          searchValue
        )
      );

    criteriaQuery.orderBy(
      QueryUtils.toOrders(
        pageable.getSort(),
        order,
        criteriaBuilder
      )
    );

    TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);

    int count = query.getResultList().size();

    query.setMaxResults(pageable.getPageSize());
    query.setFirstResult((int) pageable.getOffset());


    List<FullOrderDto> fullOrderDtos = new ArrayList<>();

    for (Order orderResult : query.getResultList())
    {
      List<OrderedProductDto> orderedProductDtos = orderedProductMapper.toOrderedProductDtoList(null);
      FullOrderDto fullOrderDto = orderMapper.toFullOrderDto(orderResult, orderedProductDtos);

      fullOrderDtos.add(fullOrderDto);
    }

    return new PageImpl<>(fullOrderDtos, pageable, count);
  }
}
