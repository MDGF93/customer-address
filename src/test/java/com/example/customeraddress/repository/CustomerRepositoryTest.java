package com.example.customeraddress.repository;

import com.example.customeraddress.ValidInfos;
import com.example.customeraddress.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //reset database after each test
public class CustomerRepositoryTest extends ValidInfos {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void givenNewValidCustomer_shouldSaveCustomer() {
        Customer customer = VALID_CUSTOMER;
        customer.setAddressList(List.of(VALID_ADDRESS));
        customerRepository.save(customer);
        boolean isSaved = customerRepository.existsByCpf(VALID_CPF);
        assertThat(isSaved).isTrue();
    }

    @Test
    public void givenNewValidCustomer_shouldDeleteCustomer() {
        Customer customer = VALID_CUSTOMER;
        customer.setAddressList(List.of(VALID_ADDRESS));
        customerRepository.save(customer);
        customerRepository.delete(customer);
        boolean isSaved = customerRepository.existsByCpf(VALID_CPF);
        assertThat(isSaved).isFalse();
    }

    @Test
    public void givenNewValidCustomer_shouldFindCustomerByCpf() {
        Customer customer = VALID_CUSTOMER;
        customer.setAddressList(List.of(VALID_ADDRESS));
        customerRepository.save(customer);
        Customer foundCustomer = customerRepository.findByCpf(VALID_CPF);
        assertThat(foundCustomer).isNotNull();
    }

    @Test
    public void givenNewValidCustomer_shouldFindCustomerByCnpj() {
        Customer customer = VALID_CUSTOMER;
        customer.setAddressList(List.of(VALID_ADDRESS));
        customerRepository.save(customer);
        Customer foundCustomer = customerRepository.findByCnpj(VALID_CNPJ);
        assertThat(foundCustomer).isNotNull();
    }

    @Test
    public void givenNewValidCustomer_shouldFindCustomerByFirstNameAndLastName() {
        Customer customer = VALID_CUSTOMER;
        customer.setAddressList(List.of(VALID_ADDRESS));
        customerRepository.save(customer);
        List<Customer> foundCustomer = customerRepository.findByFirstNameAndLastName(VALID_FIRST_NAME, VALID_LAST_NAME);
        assertThat(foundCustomer).isNotNull();
    }
}
