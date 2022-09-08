package com.shopme;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.shopme.category.ProductRepository;
import com.shopme.common.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductCategoryTest {

	@Autowired
	ProductRepository prepo;

	@Test
	public void findByCategory() {

		Integer id = 3;
		String categoryMatch = "laptop_computers";

		Pageable pageable = PageRequest.of(1, 3);
		Page<Product> product = prepo.ListByCategory(id, "-3", pageable);
		
		product.get().forEach(p->System.out.print(p.getName()));
		System.out.println(product+"----");

		assertThat(product).isNotNull();

	}

}
