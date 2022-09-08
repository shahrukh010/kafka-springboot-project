package com.shopme.admin.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.product.repository.ProductRepository;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Categories;
import com.shopme.common.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	public void createProduct() {

		Brand brand = testEntityManager.find(Brand.class, 14);
		Categories categories = testEntityManager.find(Categories.class, 4);

		Product product = new Product();
		product.setName("Lenevo Ideapad Gaming 3i");
		product.setAlias("Lenevo Ideapad Gaming 3i");

		product.setShortName(
				"Lenovo IdeaPad Gaming 3 Intel Core i5 10th Gen 15.6\" (39.62cm) FHD IPS Gaming Laptop (8GB/512GB SSD/4GB");

		product.setFullDescription(
				"Play on for hours without overheating your laptop, thanks to the 5th generation thermal engineering which features robust built-in dual vents and dual-action fans. Furthermore, the enhanced heat sinks with more support for higher CPU and GPU temperatures. So, keep it cool and game on.");

		product.setBrand(brand);
		product.setCategories(categories);
		product.setPrice(53490.00f);
		product.setCost(50555.00f);
		product.setEnabled(true);
		product.setInStock(true);
		product.setCreatedTime(new Date());
		product.setUpdatedTime(new Date());

		Product saveProduct = repository.save(product);

		assertThat(saveProduct).isNotNull();
		assertThat(saveProduct.getId()).isGreaterThan(0);
	}

	@Test
	public void listAll() {

		Iterable<Product> itr = repository.findAll();
		itr.forEach(System.out::println);
	}

	@Test
	public void findById() {

		Product product = repository.findById(4).get();
		System.out.println(product.getName());
	}

	@Test
	public void updateProduct() {

		Product product = repository.findById(4).get();
		product.setPrice(30000.00f);
		repository.save(product);
	}

	@Test
	public void deleteProduct() {

		repository.deleteById(4);

		Optional<Product> product = repository.findById(4);

		assertThat(!product.isPresent());
	}

	@Test
	public void updateEnabled() {

		repository.updateEnableStatus(6, false);

	}

	@Test
	public void createProductImage() {

		Product product = repository.findById(1).get();
//		product.extraImages("Image1-extra.png");
//		product.extraImages("Image2-extra.png");
//		product.extraImages("Image3-extra.png");

		repository.save(product);
		assertThat(product.getImages().size()).isEqualTo(3);
	}

	@Test
	public void saveProductWithDetail() {

		Integer productId = 18;
		Product product = repository.findById(productId).get();
		product.addDetail("Model Name", "‎IdeaPad 5 14ITL05");
		product.addDetail("Batteries", "‎1 Lithium Polymer batteries required. (included)");
		product.addDetail("Processor Type", "Core i5");

		repository.save(product);

		assertThat(product.getDetails()).isNotEmpty();

	}

	@Test
	public void printBasedOnCategories() {

		Integer categoriesId = 1;
		String parentId = "-1" + "-";

		Pageable pageable = PageRequest.of(0, 4);
		Page<Product> products = repository.findAllInCategories(categoriesId, parentId, pageable);

		List<Product> listProduct = products.getContent();
		System.out.println("----->"+listProduct+"---->");
		listProduct.forEach(ProductRepositoryTest::printProduct);
	}

	private static void printProduct(Product product) {

		System.out.println(product.getName());
	}
	
	@Test
	public void filterFromCategories() {
		Integer categoriesId = 1;
		String parentId = "-1" + "-";

		Pageable pageable = PageRequest.of(0, 4);
//		Page<Product> products = repository.searchInCategory(categoriesId, parentId, "Computers", pageable);
		Page<Product> products = repository.searchInCategory(categoriesId, parentId, "Lenovo IdeaPad Slim 3", pageable);
		products.getContent().forEach(ProductRepositoryTest::printProduct);
		
	}

}
