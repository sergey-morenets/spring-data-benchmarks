package demo.jdbc;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository 
	extends CrudRepository<Product, Integer> {

	Product findByName(String name);
	
}

