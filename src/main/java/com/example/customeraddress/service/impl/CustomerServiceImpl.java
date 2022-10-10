package com.example.customeraddress.service.impl;

import com.example.customeraddress.entity.Address;
import com.example.customeraddress.entity.Customer;
import com.example.customeraddress.repository.CustomerRepository;
import com.example.customeraddress.service.CustomerService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void save(Customer customer) {
        if (customer.getAddressList().size() == 1) {
            customer.getAddressList().get(0).setMainAddress(true);
        }
        customerRepository.save(customer);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Long id) {
        if (customerRepository.findById(id).isPresent()) {
            return customerRepository.findById(id).get();
        }
        throw new RuntimeException("Customer not found");
    }

    public void delete(Long id) {
        if (customerRepository.findById(id).isPresent()) {
            customerRepository.deleteById(id);
        } else throw new RuntimeException("Customer id not found");
    }

    public Customer addAddressToCostumer(Long id, Address address) {
        if (customerRepository.findById(id).isPresent()) {
            if (customerRepository.findById(id).get().getAddressList().size() >= 5) {
                throw new RuntimeException("Customer can't have more than 5 addresses");
            }
            if (address.isMainAddress()) {
                Customer customer = customerRepository.findById(id).get();
                for (Address address1 : customer.getAddressList()) {
                    address1.setMainAddress(false);
                }
            }
            Customer customer = customerRepository.findById(id).get();
            customer.getAddressList().add(address);
            return customerRepository.save(customer);
        }
        throw new RuntimeException("addAddressToCostumer: Customer not found");
    }

    public Customer removeAddressFromCustomer(Long id, Long addressId) {
        /*
         * If customer has only one address, it can't be removed
         * If address is main address, set next address as main address
         * If address is not main address, just remove it
         */

        if (customerRepository.findById(id).isPresent()) {
            Customer customer = customerRepository.findById(id).get();
            if (customer.getAddressList().size() == 1) {
                throw new RuntimeException("Customer can't have less than 1 address");
            }
            for (Address address : customer.getAddressList()) {
                if (Objects.equals(address.getId(), addressId)) {
                    if (address.isMainAddress()) {
                        for (Address address1 : customer.getAddressList()) {
                            if (!Objects.equals(address1.getId(), addressId)) {
                                address1.setMainAddress(true);
                                break;
                            }
                        }
                    }
                    customer.getAddressList().remove(address);
                    return customerRepository.save(customer);
                }
            }
            throw new RuntimeException("Address not found");
        }
        throw new RuntimeException("Customer not found");
    }
}