package jpa.study.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpa.study.domain.Delivery;
import jpa.study.domain.Member;
import jpa.study.domain.Order;
import jpa.study.domain.OrderItem;
import jpa.study.domain.item.Item;
import jpa.study.repository.ItemRepository;
import jpa.study.repository.MemberRepository;
import jpa.study.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;

	// 주문
	public Long order(Long memberId, Long itemId, int count) {

		// 엔티티 조회
		Member member = memberRepository.findOne(memberId);
		Item item = itemRepository.findOne(itemId);

		// 배송정보 생성
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());

		// 주문상품 생성
		OrderItem orderItem = OrderItem.orderItem(item, item.getPrice(), count);

		// 주문 생성
		Order order = Order.createOrder(member, delivery, orderItem);

		// 주문 저장
		orderRepository.save(order);

		return order.getId();
	}

	// 취소
	@Transactional
	public void cancelOrder(Long orderId) {

		// 주문 엔티티 조회
		Order order = orderRepository.findOne(orderId);

		// 주문 취소
		order.cancel();
	}

	// 검색
}
