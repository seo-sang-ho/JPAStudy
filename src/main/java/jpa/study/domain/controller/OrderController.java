package jpa.study.domain.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jpa.study.domain.Member;
import jpa.study.domain.Order;
import jpa.study.domain.item.Item;
import jpa.study.repository.OrderSearch;
import jpa.study.service.ItemService;
import jpa.study.service.MemberService;
import jpa.study.service.OrderService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
	private final MemberService memberService;
	private final ItemService itemService;

	@GetMapping("/order")
	public String createForm(Model model){

		List<Member> members = memberService.findAll();
		List<Item> items = itemService.findItems();

		model.addAttribute("members", members);
		model.addAttribute("items", items);

		return "order/orderForm";
	}

	@PostMapping("/order")
	public String order(@RequestParam("memberId") Long memberId,
						@RequestParam("itemId")Long itemId,
						@RequestParam("count")int count) {

		orderService.order(memberId,itemId,count);

		return "redirect:/orders";
	}

	@GetMapping("/orders")
	public String orderList(@ModelAttribute("orderSearch")OrderSearch orderSearch, Model model){
		List<Order> orders = orderService.findOrder(orderSearch);
		model.addAttribute("orders",orders);

		return "order/orderList";
	}
}
