package com.shopme.admin.categories;

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
@AutoConfigureTestDatabase(replace = Replace.NONE) // because of by default testing to InMemory database
@Rollback(false)
public class CategoriesRepositoryTest {

	@Autowired
	private CategoriesRepository repository;

	@Test
	public void createCategories() {

//		Categories categories = new Categories("Computer");
//		repository.save(categories);
//		assertThat(categories.getId()).isGreaterThan(0);
//		Categories categories = new Categories("Computer");
//		Categories categories = new Categories("Component");
//		repository.save(categories);
//		assertThat(categories.getId()).isGreaterThan(0);

	}

	@Test
	public void createSubCategories() {

		Categories parent = new Categories(13);
//		Categories subCategories = new Categories("Desktop", parent);
//		Categories laptop = new Categories("Laptop",parent);
//		Categories component = new Categories("Electronic Component",parent);
//		Categories camera = new Categories("Camera", new Categories(2));
//		Categories smartPhones = new Categories("SmartPhones", new Categories(2));
//		Categories saveCat = repository.save(subCategories);

		// crete component subcategories
//		Categories pro = new Categories("Processor", parent);
//		Categories mother = new Categories("Motherboards", parent);
//		Categories cabinet = new Categories("Cabninets", parent);
//		Categories mem = new Categories("Memory", parent);
//		Categories Gc = new Categories("Graphic Cards", parent);
//		Categories cool = new Categories("Cooling", parent);
//		Categories ps = new Categories("Power Supply", parent);
//		Iterable<Categories> saveCat = repository.saveAll(List.of(pro, mother, cabinet, mem, Gc, cool, ps));

//		Categories pcOrLapOrEtc = new Categories("RGB", parent);

//		Categories tel21 = new Categories("21inch", parent);
//		Categories tel23 = new Categories("23inch", parent);
//		Categories lcdDisplay = new Categories("Lcd Display", parent);
//		repository.saveAll(List.of(tel21, tel23, lcdDisplay));
//		Categories mp3 = new Categories("mp3 player", parent);
//		Categories vcd = new Categories("vcd player", parent);
//		Categories dvd = new Categories("dvc player", parent);
//		Categories hd = new Categories("Hd quality", parent);
//		repository.saveAll(List.of(mp3, vcd, dvd, hd));
//		Categories desk = new Categories("Desktop", parent);
//		Categories pc = new Categories("Laptop", parent);
//		repository.saveAll(List.of(desk, pc));

//		Categories ssd = new Categories("SSD", parent);
//		Categories ram = new Categories("RAM", parent);
//		Categories cpu = new Categories("Processor", parent);
//		Categories battery = new Categories("battery", parent);
//		Categories processor = new Categories("Processors", parent);
//		Categories motherbord = new Categories("Motherboards", parent);
//		Categories cabinates = new Categories("Cabinates", parent);
//		Categories memory = new Categories("Memorys", parent);
//		Categories gc = new Categories("Graphic Cards", parent);
//		Categories cooling = new Categories("Cooling", parent);
//		Categories ps = new Categories("Power Supply", parent);
//		repository.saveAll(List.of(processor, motherbord, cabinates, memory, gc, cooling, ps));

//		Categories amd = new Categories("AMD",parent);
//		Categories intel = new Categories("Intel",parent);
//		Categories am = new Categories("AMD Chipset",parent);
//		Categories inte = new Categories("Intel Chipset",parent);
//		Categories gaming = new Categories("Gaming Chipset",parent);
//		repository.saveAll(List.of(am,inte,gaming));
//		Categories laptop = new Categories("Laptops", parent);
//		Categories dekstop = new Categories("Desktops", parent);
//		Categories gaming = new Categories("Gaming", parent);
//		Categories rgb = new Categories("RGB", parent);
//		repository.saveAll(List.of(laptop, dekstop, gaming, rgb));
//		Categories external = new Categories("Internal HD", parent);
//		Categories internal = new Categories("Internal SD", parent);
//		Categories nas = new Categories("NAS Storage", parent);
//		repository.saveAll(List.of(external, internal));
//		Categories audio = new Categories("Audio",parent);
//		Categories speaker = new Categories("Speaker", parent);
//		Categories headphone = new Categories("Headphone", parent);
//		Categories microphone = new Categories("Microphone", parent);
//		repository.saveAll(List.of(speaker, headphone,microphone));

//		Categories ic = new Categories("Instant Camera", parent);
//		Categories dslr = new Categories("DSLR Camera", parent);
//		Categories lens = new Categories("Camera Lens", parent);
//		Categories cc = new Categories("Cameracoders", parent);
//		Categories cctv = new Categories("CCTV Video Security", parent);
//		Categories full_fram_cameral = new Categories("Full-Frame Camera", parent);
//		Categories cameraBattery = new Categories("Camera Battery", parent);
//		repository.saveAll(List.of(ic, dslr, lens, cc, cctv, full_fram_cameral, cameraBattery));
		Categories storage = new Categories("Storage", parent);
		repository.save(storage);
	}

	@Test
	public void getCategories() {

		Categories categories = repository.findById(1).get();// orElseThrow(()->new ResourceNotFoundException("resource
//		System.out.println(categories.getName());
		// not found"));
		Set<Categories> children = categories.getChildren();
		for (Categories cat : children)
			System.out.println(cat.getName());
	}

	@Test
	public void hierarchicalCategories() {

		Pageable pageable = PageRequest.of(1, 4);
		Page<Categories> page = repository.findAll(pageable);
		Iterable<Categories> rootCategories = page.getContent();

		for (Categories root : rootCategories) {

			if (root.getParent() == null)
				System.out.println(root.getName());

			Set<Categories> subCategories = root.getChildren();
			for (Categories subCat : subCategories) {

//				System.out.println(subCat.getName());
				printChild(subCat, 1);
//				System.out.println(toStringHierarchy(subCat, 1));
//				toStringHierarchy(subCat, 1);
			}
		}
	}

	public void printChild(Categories parent, int subLevel) {

		int newLevel = subLevel + 1;

		Set<Categories> child = parent.getChildren();

		for (Categories sub : child) {

			for (int i = 0; i < newLevel; i++)
				System.out.print("--");
//			System.out.println(sub.getName());

			printChild(sub, 1);
		}
	}

	public String toStringHierarchy(Categories parent, int tabLevel) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < tabLevel; i++) {
			builder.append("\t");
		}
		builder.append("-" + parent.getName());
		builder.append("\n");
		for (Categories nextChild : parent.getChildren()) {
			builder.append(toStringHierarchy(nextChild, tabLevel + 1));
		}
		System.out.println(builder);
		return builder.toString();
	}

	@Test
	public void getAllCategories() {

		Iterable<Categories> categories = repository.findAll();

		for (Categories c : categories)
			System.out.println(c.getName());
	}

	@Test
	public void listRootCategories() {

		// List<Categories> root = repository.findRootCategories();
		// root.forEach(categories -> System.out.println(categories.getName()));
	}

}
