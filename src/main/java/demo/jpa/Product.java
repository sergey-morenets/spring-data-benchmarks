package demo.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Table
@Entity
public class Product {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;

	private boolean enabled;
}
