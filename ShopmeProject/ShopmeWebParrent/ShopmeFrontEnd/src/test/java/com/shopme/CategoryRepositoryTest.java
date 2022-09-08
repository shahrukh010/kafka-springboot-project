package com.shopme;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import com.shopme.category.CategoryRepository;
import com.shopme.common.entity.Categories;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepo;

	@Test
	public void enabledCategories() {

		List<Categories> enabledCategories = categoryRepo.findAll();
	}

	@Test
	public void aliasAndEnabled() {

		String alias = "computers";
		Categories listCategories = categoryRepo.FindByAlias(alias);

		System.out.println(listCategories.getAlias());
	}

}
