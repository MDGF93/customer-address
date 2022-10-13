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
    Customer addAddressToCostumer(Long id, Address address);

    Customer removeAddressFromCustomer(Long id, Long addressId);
}
