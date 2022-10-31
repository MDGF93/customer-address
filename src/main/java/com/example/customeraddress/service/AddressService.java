package com.example.customeraddress.service;

import com.example.customeraddress.entity.Address;

import java.util.List;

public interface AddressService {
    public Address save(Address address);
    public List<Address> findAll();
    public Address findById(Long id);
    public Address update(Long id, Address address);
    public Address setMainAddress(Long id);

    public Address delete(Address valid_address);
}
