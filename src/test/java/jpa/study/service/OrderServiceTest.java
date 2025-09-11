package jpa.study.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jpa.study.domain.Address;
import jpa.study.domain.Member;
import jpa.study.domain.Order;
import jpa.study.domain.OrderStatus;
import jpa.study.domain.item.Book;
import jpa.study.domain.item.Item;
import jpa.study.exception.NotEnoughStockException;
import jpa.study.repository.OrderRepository;

@SpringBootTest
@Transactional
class OrderServiceTest {

	@Autowired OrderRepository orderRepository;
	@Autowired OrderService orderService;
	@Autowired EntityManager em;

	@Test
	public void 상품주문() throws Exception{

		Member member = createMember();

		Book book = createBook();

		int orderCount = 2;

		Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

		Order getOrder = orderRepository.findOne(orderId);

		assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
		assertEquals(1,getOrder.getOrderItems().size(),"주문한 상품 종류 수가 정확해야한다.");
		assertEquals(10000 * orderCount, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다.");
		assertEquals(8, book.getStockQuantity(), "주문 수량 만큼 재고가 줄어야한다.");
	}

	@Test()
	public void 상품주문_재고수량초과() throws Exception{

		Member member = createMember();
		Item item = createBook();

		assertThatThrownBy(() ->
			orderService.order(member.getId(),item.getId(),15))
			.isInstanceOf(NotEnoughStockException.class);
	}

	@Test
	public void 주문취소() throws Exception{

		Member member = createMember();
		Item item = createBook();

		int orderCount = 2;
		Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

		orderService.cancelOrder(orderId);

		Order getOrder = orderRepository.findOne(orderId);

		assertEquals(OrderStatus.CANCEL,getOrder.getStatus(),"주문 상태가 CANCEL이 되야한다.");
		assertEquals(10, item.getStockQuantity(),"주문이 취소된 상품은 그만큼 재고가 다시 증가해야 한다.");

	}

	private Book createBook() {
		Book book = new Book();
		book.setName("JPA BOOK");
		book.setPrice(10000);
		book.setStockQuantity(10);
		em.persist(book);
		return book;
	}

	private Member createMember() {
		Member member = new Member();
		member.setName("member1");
		member.setAddress(new Address("서울","강가","123-123"));
		em.persist(member);
		return member;
	}
}
