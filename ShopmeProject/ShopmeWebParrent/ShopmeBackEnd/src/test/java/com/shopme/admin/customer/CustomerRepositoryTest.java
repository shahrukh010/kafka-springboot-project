package com.shopme.admin.customer;

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

import com.shopme.common.entity.Customer;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	public void findByMailCustomer() {

		String email = "shahrukh.khan@cognitivzen.com";
		Customer customer = customerRepository.findByEmail(email);

		System.out.println(customer.getFirstName() + " " + customer.getLastName());
	}

	public void createCustomer() {

	}

	@Test
	public void findAllTest() {

		String keyword = "Delhi";
		Pageable pageable = PageRequest.of(0, 5);

		Page page = customerRepository.findAll(keyword, pageable);

		List<Customer> listCustomer = page.getContent();

		listCustomer.forEach(name -> System.out.println(name.getFirstName() + " " + name.getLastName()));

	}

}
