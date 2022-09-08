package com.shopme.admin.categories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.categories.repository.CategoriesRepository;
import com.shopme.common.entity.Categories;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(true)
public class CategoriesRepoTest {

	@Autowired
	private CategoriesRepository repository;

	@Test
	public void createCategories() {

		Categories categories = new Categories("PC Accessories");
		/*
		 * + "All Mouse\n" + "All Keyboards\n" + "Audio\n" + "Webcam\n" + "Bags\n" +
		 * "Office Essentials\n" + "Powerbank\n" + "Cables and Hubs\n" + "Networking\n"
		 * + "");
		 */

		Categories saveCategories = repository.save(categories);
		assertThat(saveCategories).isNotNull();
	}

	@Test
	public void subCategories() {

		Categories parent = new Categories(66);
		Categories lapcare = new Categories("Lapcare Smart Tank LPB-110 11000mAh Fast Charge Power Bank", parent);
		Categories corseca = new Categories(
				"Corseca Vogue 10000mAh Power Bank with Fast Charging Mobile and LED Display", parent);

		Categories adata = new Categories("ADATA 100M 10000W Power Bank", parent);

		repository.saveAll(List.of(lapcare, corseca, adata));

	}

	@Test
	public void getCategories() {

		Categories categories = repository.findById(1).get();
		Set<Categories> children = categories.getChildren();
		children.forEach(obj -> System.out.println(obj.getName()));
	}

	@Test
	public void hierarchicalCategories() {

		Iterable<Categories> root = repository.findAll();

		for (Categories parent : root) {
			if (parent.getParent() == null)
				System.out.println(parent.getAlias());

			Set<Categories> child = parent.getChildren();
			for (Categories sub : child) {
				if (parent.getParent() == null)
					System.out.println("\t" + sub.getAlias());
				printChild(sub, 1);
			}
		}

	}

	public void printChild(Categories parent, int level) {

		int newLevel = level + 1;
		Set<Categories> child = parent.getChildren();

		for (Categories sub : child) {

			for (int i = 0; i < newLevel; i++)
				System.out.print("\t");
			System.out.println(sub.getAlias());

			printChild(sub, 1);
		}

	}

	@Test
	public void printObject() {

		class A {

			public String name;
			public static String lastName = "Smith";

			public A(String name) {
				this.name = name;
			}

			public String getName() {
				return this.name;
			}
		}

		List<A> alist = new ArrayList<>();
		alist.add(new A("\t" + A.lastName));
		alist.forEach(str -> System.out.println(str.getName()));

	}

	@Test
	public List<Categories> listCategoriesUsedInForm(List<Categories> listCategories) {

		List<Categories> categoriesUsedInForm = new ArrayList<>();
		Iterable<Categories> categoriesInDB = listCategories;
//		Iterable<Categories> categoriesInDB = repository.findAll();

		for (Categories parent : categoriesInDB) {
			if (parent.getParent() == null)
				categoriesUsedInForm.add(Categories.copyIdAndName(parent));

			Set<Categories> child = parent.getChildren();
			for (Categories sub : child) {
				if (parent.getParent() == null) {
					String name = "---" + sub.getName();
					categoriesUsedInForm.add(Categories.copyIdAndName(sub.getId(), name));
				}
				printChild(categoriesUsedInForm, sub, 1);
			}
		}
		List<Categories> items = new ArrayList<>();
		categoriesUsedInForm.forEach(cat -> {
			if (cat.getName() == "Computer Components")
				items.add(cat);
		});
//		System.out.println(items);

		return categoriesUsedInForm;
	}

	private void printChild(List<Categories> categoriesUsedInForm, Categories parent, int level) {

		int newLevel = level + 1;
		Set<Categories> child = parent.getChildren();
		String name = "";

		for (Categories sub : child) {

			for (int i = 0; i < newLevel; i++)
				name = "-------" + sub.getName();
			categoriesUsedInForm.add(Categories.copyIdAndName(sub.getId(), name));

			printChild(categoriesUsedInForm, sub, 1);
		}
	}

	@Test
	public void rootListCategories() {

//		List<Categories>rootCategories = repository.findRootCategories();
//		rootCategories.forEach(cat->System.out.println(cat.getName()));
	}

	@Test
	public void testPagenation() {

		int noOfPage = 0;
		int pagesize = 4;

		Pageable pageable = PageRequest.of(noOfPage, pagesize);

		List<Categories> listCategories = repository.findRootCategories(Sort.by("name").ascending());
		// List<Categories> listCategories = page.getContent();
		listCategoriesUsedInForm(listCategories).forEach(cat -> {

			System.out.println(cat.getName());

		});

//		listCategoriesUsedInForm(listCategories).forEach(cat->{
//			System.out.println(cat.getName());
//		});

		System.out.println(listCategories);

		// listCategories.forEach(c->System.out.println(c.getName()));
//		System.out.println("total item:" + page.getTotalElements());
//		System.out.println("total page" + page.getTotalPages());
//		System.out.println("total " + page.getSize());

	}

	@Test
	public void testSearch() {

	

		Pageable pageable = PageRequest.of(0, 1);
		Page<Categories> page = repository.search("Comput", pageable);
		System.out.println(page);

		List<Categories> listCategories = page.getContent();
		listCategories.forEach(cat->System.out.println(cat.getName()));

	}

}
