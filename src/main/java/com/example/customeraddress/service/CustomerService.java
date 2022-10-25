package com.example.customeraddress.service;

import com.example.customeraddress.entity.Address;
import com.example.customeraddress.entity.Customer;

import java.util.List;

public interface CustomerService {
    public void save(Customer customer);
    public Customer update(Customer customer);
    public List<Customer> findAll();
    public Customer findById(Long id);
    public void delete(Long id);
    public Customer addAddressToCostumer(Long id, Address address);
    public Customer removeAddressFromCustomer(Long id, Long addressId);
    public void generateFakeDataFromCSV();
}
