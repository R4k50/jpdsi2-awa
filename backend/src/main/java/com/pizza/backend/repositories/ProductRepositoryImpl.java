package com.pizza.backend.repositories;

import com.pizza.backend.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;

public class ProductRepositoryImpl implements ProductRepositoryCustom
{
  @PersistenceContext
  EntityManager entityManager;

  @Override
  public Page<Product> findAll(String searchParam, String searchValue, Pageable pageable)
  {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
    Root<Product> product = criteriaQuery.from(Product.class);

    Expression serachColumn = product.get(searchParam);
    Class columnType = product.get(searchParam).getModel().getBindableJavaType();


    criteriaQuery.select(product);

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
        product,
        criteriaBuilder
      )
    );

    TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);

    int count = query.getResultList().size();

    query.setMaxResults(pageable.getPageSize());
    query.setFirstResult((int) pageable.getOffset());

    return new PageImpl<>(query.getResultList(), pageable, count);
  }
}
