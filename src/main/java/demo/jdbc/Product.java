package demo.jdbc;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table("PRODUCTS")
public class Product  {

	@Id
	private Integer id;

	private String name;

	private boolean enabled;
	
}
