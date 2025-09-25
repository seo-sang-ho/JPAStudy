package jpa.study;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jpa.study.controller.MemberController;
import jpa.study.domain.*;
import jpa.study.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 *  userA
 *  JPA1 BOOK
 *  JPA2 BOOK
 *  
 *  userB
 *  SPRING1 BOOK
 *  SPRING1 BOOK
 */

@Component
@RequiredArgsConstructor
public class InitDB {
    
    private final InitService initService;
    
    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        
        private final EntityManager em;
        public void dbInit1(){
            Member member = createMember("userA","서울","111","11");
            em.persist(member);

            Book book1 = createBook("JPA1 BOOK",20000,100);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 10000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2(){
            Member member = createMember("userB","전주","222","22");
            em.persist(member);

            Book book1 = createBook("SPRING BOOK",20000,200);
            em.persist(book1);

            Book book2 = createBook("SPRING BOOK", 40000, 300);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = createDelivery(member);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private static Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private Book createBook(String name, int price, int stockQuantity){
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }
        
        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            Address address = new Address(city, street, zipcode);
            member.setAddress(address);
            return member;
        }
    }
}
