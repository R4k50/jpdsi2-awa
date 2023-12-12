package com.pizza.backend.repositories;

import com.pizza.backend.dtos.users.UserDto;
import com.pizza.backend.entities.User;
import com.pizza.backend.mappers.UserMapper;
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

import java.util.List;

@AllArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom
{
  @PersistenceContext
  EntityManager entityManager;
  UserMapper userMapper;

  @Override
  public Page<UserDto> findAll(String searchParam, String searchValue, Pageable pageable)
  {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
    Root<User> user = criteriaQuery.from(User.class);

    Expression serachColumn = user.get(searchParam);
    Class columnType = user.get(searchParam).getModel().getBindableJavaType();


    criteriaQuery.select(user);

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
        user,
        criteriaBuilder
      )
    );

    TypedQuery<User> query = entityManager.createQuery(criteriaQuery);

    int count = query.getResultList().size();

    query.setMaxResults(pageable.getPageSize());
    query.setFirstResult((int) pageable.getOffset());

    List<UserDto> userDtos = userMapper.toUserDtoList(query.getResultList());

    return new PageImpl<>(userDtos, pageable, count);
  }
}
