package com.example.customeraddress.controller;

import com.example.customeraddress.dto.AddressDTO;
import com.example.customeraddress.dto.CustomerAddressDTO;
import com.example.customeraddress.entity.Address;
import com.example.customeraddress.entity.Customer;
import com.example.customeraddress.mapper.AddressMapper;
import com.example.customeraddress.mapper.CustomerAddressMapper;
import com.example.customeraddress.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/addAddress/{id}")
    public Customer addAddressToCostumer(@RequestBody AddressDTO addressDTO, @PathVariable Long id) {
        Address address = AddressMapper.INSTANCE.toEntity(addressDTO);
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
            CustomerAddressDTO customerAddressDTO = CustomerAddressMapper.INSTANCE.toDto(customer);
            customerAddressDTOList.add(customerAddressDTO);
        }
        return customerAddressDTOList;
    }

    @DeleteMapping("/removeAddress/{id}/{addressId}")
    public Customer removeAddressFromCustomer(@PathVariable Long id, @PathVariable Long addressId) {
        return customerService.removeAddressFromCustomer(id, addressId);
    }

    @PostMapping("/save")
    public Customer save(@RequestBody CustomerAddressDTO customerAddressDTO) {
        Customer customer = CustomerAddressMapper.INSTANCE.toCustomerEntity(customerAddressDTO);
        Address address = CustomerAddressMapper.INSTANCE.toAddressEntity(customerAddressDTO);
        address.setMainAddress(true);
        address.setCustomer(customer);
        customer.setAddressList(List.of(address));
        customerService.save(customer);

        return customer;
    }

    @PutMapping("/update/{id}")
    public Customer update(@RequestBody CustomerAddressDTO customerAddressDTO, @PathVariable Long id) {
        Customer customer = CustomerAddressMapper.INSTANCE.toCustomerEntity(customerAddressDTO);
        customer.setId(id);
        customer.setAddressList(customerService.findById(id).getAddressList());
        return customerService.update(customer);
    }
}
