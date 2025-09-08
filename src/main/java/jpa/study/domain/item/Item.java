package jpa.study.domain.item;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jpa.study.domain.Category;
import jpa.study.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

	@Id @GeneratedValue
	@Column(name = "item_id")
	private Long id;

	private String name;
	private int price;
	private int stockQuantity;

	@ManyToMany(mappedBy = "items")
	private List<Category> categories = new ArrayList<>();

	// 비즈니스 로직
	public void addStock(int quantity){
		this.stockQuantity += quantity;
	}

	public void removeStock(int quantity){
		int restStock = stockQuantity - quantity;
		if(restStock < 0 ){
			throw new NotEnoughStockException("need more stock");
		}
		this.stockQuantity = restStock;
	}
}
