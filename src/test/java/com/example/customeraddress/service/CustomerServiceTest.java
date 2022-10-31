package com.example.customeraddress.service;


import com.example.customeraddress.ValidInfos;
import com.example.customeraddress.entity.Address;
import com.example.customeraddress.entity.Customer;
import com.example.customeraddress.repository.AddressRepository;
import com.example.customeraddress.repository.CustomerRepository;
import com.example.customeraddress.service.impl.AddressServiceImpl;
import com.example.customeraddress.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest extends ValidInfos {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @InjectMocks
    private AddressServiceImpl addressService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerRepository, addressRepository);
        addressService = new AddressServiceImpl(addressRepository);
    }

    @Test
    public void givenNewValidCustomer_shouldSaveCustomer() {
        customerService.save(VALID_CUSTOMER_WITH_ADDRESS);
        verify(customerRepository).save(VALID_CUSTOMER_WITH_ADDRESS);
    }

    @Test
    public void shouldReturnAllCustomers() {
        customerService.findAll();
        verify(customerRepository).findAll();
    }

    @Test
    public void shouldReturnAllAddresses() {
        addressService.findAll();
        verify(addressRepository).findAll();
    }

    @Test
    public void givenNewValidAddress_shouldDeleteAddress() {
        addressService.delete(VALID_ADDRESS);
        verify(addressRepository).delete(VALID_ADDRESS);
    }

    @Test
    public void givenNewValidAddress_shouldFindAddressByCep() {
        addressService.findByCep(VALID_CEP);
        verify(addressRepository).findByCep(VALID_CEP);
    }

    @Test
    void givenNewValidAddressToCustomer_shouldAddAddressToCustomerAddressList() {
        VALID_CUSTOMER.setId(1L);
        VALID_ADDRESS.setId(1L);
        VALID_CUSTOMER.setAddressList(new ArrayList<>());
        when(customerRepository.findById(1L)).thenReturn(Optional.of(VALID_CUSTOMER));
        when(addressRepository.findById(1L)).thenReturn(Optional.of(VALID_ADDRESS));
        when(customerRepository.save(any(Customer.class))).then(returnsFirstArg());
        Customer customer = customerService.addAddressToCustomer(1L, addressService.findById(1L));
        assertThat(customer.getAddressList().size()).isEqualTo(1);
    }

    @Test
    void givenNewValidAddressToCustomerWithMoreThanFiveAddresses_shouldThrowException() {
        VALID_CUSTOMER.setId(1L);
        VALID_ADDRESS.setId(1L);
        VALID_CUSTOMER.setAddressList(new ArrayList<>(List.of(VALID_ADDRESS, VALID_ADDRESS, VALID_ADDRESS, VALID_ADDRESS, VALID_ADDRESS)));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(VALID_CUSTOMER));
        when(addressRepository.findById(1L)).thenReturn(Optional.of(VALID_ADDRESS));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(VALID_CUSTOMER));
        when(addressRepository.findById(1L)).thenReturn(Optional.of(VALID_ADDRESS));
        Exception e = assertThrows(ConstraintViolationException.class, () -> {
            customerService.addAddressToCustomer(1L, addressService.findById(1L));
        });
        assertThat(e.getClass()).isEqualTo(ConstraintViolationException.class);
    }

    @Test
    public void givenNewValidMainAddressToCustomerThatAlreadyHasMainAddress_shouldChangeTheMainAddressToTheNewAddress() {
        VALID_CUSTOMER.setId(1L);
        VALID_ADDRESS.setId(1L); //addressList().get(0)
        VALID_ADDRESS.setMainAddress(true);
        VALID_CUSTOMER.setAddressList(new ArrayList<>(List.of(VALID_ADDRESS)));
        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(VALID_CUSTOMER));
        when(customerRepository.save(any(Customer.class))).then(returnsFirstArg());
        Address newAddress = new Address("01111-111", "Curitiba", "PR", "Rua de teste", "111", "Apt 201", true, "Meu apartamento", VALID_CUSTOMER);
        newAddress.setId(2L);
        Customer customer = customerService.addAddressToCustomer(1L, newAddress);
        assertThat(customer.getAddressList().get(0).isMainAddress()).isEqualTo(false);
        assertThat(customer.getAddressList().get(1).isMainAddress()).isEqualTo(true);
    }

    @Test
    public void shouldNotRemoveAddressWhenCustomerHasOnlyOneAddress() {
        VALID_CUSTOMER.setId(1L);
        VALID_ADDRESS.setId(1L);
        VALID_CUSTOMER.setAddressList(new ArrayList<>(List.of(VALID_ADDRESS)));
        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(VALID_CUSTOMER));
        Exception e = assertThrows(ConstraintViolationException.class, () -> {
            customerService.removeAddressFromCustomer(1L, 1L);
        });
        assertThat(e.getClass()).isEqualTo(ConstraintViolationException.class);
    }

    @Test
    public void shouldRemoveAddressWhenCustomerHasMoreThanOneAddress() {
        VALID_CUSTOMER.setId(1L);
        VALID_ADDRESS.setId(1L);
        Address newAddress = new Address("01111-111", "Curitiba", "PR", "Rua de teste", "111", "Apt 201", true, "Meu apartamento", VALID_CUSTOMER);
        newAddress.setId(2L);
        VALID_CUSTOMER.setAddressList(new ArrayList<>(List.of(VALID_ADDRESS, newAddress)));
        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(VALID_CUSTOMER));
        when(customerRepository.save(any(Customer.class))).then(returnsFirstArg());
        Customer customer = customerService.removeAddressFromCustomer(1L, 1L);
        assertThat(customer.getAddressList().size()).isEqualTo(1);
    }

    @Test
    public void shouldRemoveAddressWhenCustomerHasMoreThanOneAddressAndTheMainAddressIsTheOneToRemove() {
        VALID_CUSTOMER.setId(1L);
        VALID_ADDRESS.setId(1L);
        Address newAddress = new Address("01111-111", "Curitiba", "PR", "Rua de teste", "111", "Apt 201", true, "Meu apartamento", VALID_CUSTOMER);
        newAddress.setId(2L);
        VALID_CUSTOMER.setAddressList(new ArrayList<>(List.of(VALID_ADDRESS, newAddress)));
        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(VALID_CUSTOMER));
        when(customerRepository.save(any(Customer.class))).then(returnsFirstArg());
        Customer customer = customerService.removeAddressFromCustomer(1L, 2L);
        assertThat(customer.getAddressList().size()).isEqualTo(1);
        assertThat(customer.getAddressList().get(0).isMainAddress()).isEqualTo(true);
    }

    @Test
    public void givenFirstAndLastName_shouldReturnCustomer() {
        VALID_CUSTOMER.setId(1L);
        VALID_CUSTOMER.setFirstName("John");
        VALID_CUSTOMER.setLastName("Doe");
        when(customerRepository.findByFirstNameAndLastName(VALID_CUSTOMER.getFirstName(), VALID_CUSTOMER.getLastName())).thenReturn(List.of(VALID_CUSTOMER));
        List<Customer> customersList = customerService.searchByFirstAndLastName("John", "Doe");
        assertThat(customersList.size()).isEqualTo(1);
        assertThat(customersList.get(0).getFirstName()).isEqualTo("John");
        assertThat(customersList.get(0).getLastName()).isEqualTo("Doe");
    }

    @Test
    public void givenCity_shouldReturnCustomer() {
        VALID_CUSTOMER.setId(1L);
        VALID_ADDRESS.setId(1L);
        VALID_ADDRESS.setCity("Curitiba");
        VALID_CUSTOMER.setAddressList(new ArrayList<>(List.of(VALID_ADDRESS)));
        when(customerRepository.findByAddressListCity(VALID_ADDRESS.getCity())).thenReturn(List.of(VALID_CUSTOMER));
        List<Customer> customersList = customerService.searchByCity("Curitiba");
        assertThat(customersList.size()).isEqualTo(1);
        assertThat(customersList.get(0).getAddressList().get(0).getCity()).isEqualTo("Curitiba");
    }

    @Test
    public void givenCustomersWithSameFirstName_shouldReturnCustomersWithSameFirstName() {
        VALID_CUSTOMER.setId(1L);
        VALID_CUSTOMER.setFirstName("John");
        VALID_CUSTOMER.setLastName("Doe");
        Customer customer2 = new Customer("John", "Doe", "johndoe@email.com", "(11) 11111-1111", "09125111482", "22435443000148");
        customer2.setId(2L);
        when(customerRepository.findByFirstNameAndLastName(VALID_CUSTOMER.getFirstName(), VALID_CUSTOMER.getLastName())).thenReturn(List.of(VALID_CUSTOMER, customer2));
        List<Customer> customersList = customerService.searchByFirstAndLastName("John", "Doe");
        assertThat(customersList.size()).isEqualTo(2);
        assertThat(customersList.get(0).getFirstName()).isEqualTo("John");
        assertThat(customersList.get(0).getLastName()).isEqualTo("Doe");
    }
}
