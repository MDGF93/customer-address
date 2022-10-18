package com.example.customeraddress.controller;

import com.example.customeraddress.dto.AddressDTO;
import com.example.customeraddress.dto.CustomerAddressDTO;
import com.example.customeraddress.entity.Address;
import com.example.customeraddress.entity.Customer;
import com.example.customeraddress.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerController(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/addAddress/{id}")
    public Customer addAddressToCostumer(@RequestBody AddressDTO addressDTO, @PathVariable Long id) {
        Address address = modelMapper.map(addressDTO, Address.class);

        return customerService.addAddressToCostumer(id, address);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        customerService.delete(id);
    }

    @GetMapping("/findAll")
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/findAllDTO")
    public List<CustomerAddressDTO> findAllDTO() {

        // convert entity to dto
        List<CustomerAddressDTO> customerAddressDTOList = new ArrayList<>();

        for (Customer customer : customerService.findAll()) {
            customerAddressDTOList.add(modelMapper.map(customer, CustomerAddressDTO.class));
        }

        return customerAddressDTOList;
    }

    @DeleteMapping("/removeAddress/{id}/{addressId}")
    public Customer removeAddressFromCustomer(@PathVariable Long id, @PathVariable Long addressId) {
        return customerService.removeAddressFromCustomer(id, addressId);
    }

    @PostMapping("/save")
    public Customer save(@RequestBody CustomerAddressDTO customerAddressDTO) {
        Customer customer = modelMapper.map(customerAddressDTO, Customer.class);
        Address address = modelMapper.map(customerAddressDTO, Address.class);

        address.setMainAddress(true);
        address.setCustomer(customer);
        customer.setAddressList(List.of(address));
        customerService.save(customer);

        return customer;
    }

    @PutMapping("/update/{id}")
    public Customer update(@RequestBody CustomerAddressDTO customerAddressDTO, @PathVariable Long id) {
        Customer customer = modelMapper.map(customerAddressDTO, Customer.class);

        customer.setId(id);
        customer.setAddressList(customerService.findById(id).getAddressList());

        return customerService.update(customer);
    }
}
