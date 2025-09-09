package jpa.study.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jpa.study.domain.Order;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

	private final EntityManager em;

	public void save(Order order) {
		em.persist(order);
	}

	public Order findOne(Long id){
		return em.find(Order.class, id);
	}

	// public List<Order> findAll(OrderSearch orderSearch) {} -> 검색 필터 적용
}
