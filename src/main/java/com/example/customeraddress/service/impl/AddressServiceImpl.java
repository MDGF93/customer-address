package com.example.customeraddress.service.impl;

import com.example.customeraddress.entity.Address;
import com.example.customeraddress.repository.AddressRepository;
import com.example.customeraddress.service.AddressService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public Address findById(Long id) {
        if (addressRepository.findById(id).isPresent()) {
            return addressRepository.findById(id).get();
        }
        throw new RuntimeException("Address not found");
    }

    public Address update(Long id, Address address) {
        return addressRepository.save(address);
    }


    public Address setMainAddress(Long id) {
        if (addressRepository.findById(id).isPresent()) {
            Address address = addressRepository.findById(id).get();
            if (address.isMainAddress()) {
                throw new RuntimeException("Address is already main address");
            }
            for (Address address1 : address.getCustomer().getAddressList()) {
                address1.setMainAddress(false);
            }
            address.setMainAddress(true);
            return addressRepository.save(address);
        }
        throw new RuntimeException("Address not found");
    }
}
