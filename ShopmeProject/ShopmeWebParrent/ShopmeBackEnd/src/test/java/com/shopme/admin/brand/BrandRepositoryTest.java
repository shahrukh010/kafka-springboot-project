package com.shopme.admin.brand;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.categories.repository.CategoriesRepository;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Categories;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandRepositoryTest {

	@Autowired
	private BrandRepository repository;

	@Autowired
	private CategoriesRepository categoriesRepo;

	@Test
	public void createBand() {

		Categories categories = new Categories(22);
		Brand acer = new Brand("Mi");
		acer.getCategories().add(categories);
//
//		Brand amd = new Brand("AMD");
//		amd.getCategories().add(categories);
//
//		Brand asus = new Brand("ASUS");
//		asus.getCategories().add(categories);

//		repository.saveAll(List.of(acer, amd, asus));
		repository.save(acer);
	}

	@Test
	public void listBrand() {

		Iterable<Brand> listBrands = repository.findAll();
//		listBrands.forEach(brand -> System.out.println(brand.getName()));

//		Brand listBrand = repository.findById(2).get();
//		System.out.println(listBrand);

		Long id = repository.coutById(2);
		System.out.println(id);
	}

	@Test
	public void findByName() {

		Categories categories = categoriesRepo.getCategoriesByName("Laptops");
		System.out.println(categories.getId());
	}

	@Test
	public void pagenation() {

		int pageNo = 1;
		int pageSize = 4;

		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page page = repository.findAll(pageable);
		List<Brand> listBrand = page.getContent();
		listBrand.forEach(brand -> System.out.println(brand.getName()));
	}
	
	
	@Test
	public void findAll() {
		
		List<Brand>listBrand = repository.findAll();
		listBrand.forEach(System.out::println);
	}
	
	

}
