package com.example.customeraddress.service;

import com.example.customeraddress.entity.Address;
import com.example.customeraddress.entity.Customer;

import java.util.List;

public interface CustomerService {
    public Customer save(Customer customer);
    public Customer update(Customer customer);
    public List<Customer> findAll();
    public Customer findById(Long id);
    public void delete(Long id);
    public Customer addAddressToCustomer(Long id, Address address);
    public Customer removeAddressFromCustomer(Long id, Long addressId);
    public void generateFakeDataFromCSV();
    List<Customer> searchByFirstAndLastName(String firstName, String lastName);

    List<Customer> searchByCity(String city);

    List<Customer> search(String firstName, String lastName, String phone);

}
