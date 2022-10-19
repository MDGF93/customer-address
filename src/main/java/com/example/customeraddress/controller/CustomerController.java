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
    private final CustomerAddressMapper customerAddressMapper;
    private final AddressMapper addressMapper;
    @Autowired
    public CustomerController(CustomerService customerService, CustomerAddressMapper customerAddressMapper,
                              AddressMapper addressMapper) {
        this.customerAddressMapper = customerAddressMapper;
        this.customerService = customerService;
        this.addressMapper = addressMapper;
    }

    @PostMapping("/{id}/addAddress/")
    public Customer addAddressToCostumer(@RequestBody AddressDTO addressDTO, @PathVariable Long id) {
        Address address = addressMapper.toEntity(addressDTO);

        return customerService.addAddressToCostumer(id, address);
    }

    @DeleteMapping("/{id}/")
    public void delete(@PathVariable Long id) {
        customerService.delete(id);
    }

    @GetMapping
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/DTO/")
    public List<CustomerAddressDTO> findAllDTO() {

        // convert entity to dto
        List<CustomerAddressDTO> customerAddressDTOList = new ArrayList<>();

        for (Customer customer : customerService.findAll()) {
            CustomerAddressDTO customerAddressDTO = customerAddressMapper.toDto(customer);
            customerAddressDTOList.add(customerAddressDTO);
        }

        return customerAddressDTOList;
    }

    @GetMapping("/{id}/")
    public Customer findById(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @GetMapping("/{id}/DTO/")
    public CustomerAddressDTO findByIdDTO(@PathVariable Long id) {
        return customerAddressMapper.toDto(customerService.findById(id));
    }

    @DeleteMapping("/removeAddress/{id}/{addressId}")
    public Customer removeAddressFromCustomer(@PathVariable Long id, @PathVariable Long addressId) {
        return customerService.removeAddressFromCustomer(id, addressId);
    }

    @PostMapping("/save")
    public Customer save(@RequestBody CustomerAddressDTO customerAddressDTO) {
        Customer customer = customerAddressMapper.toCustomerEntity(customerAddressDTO);
        Address  address  = customerAddressMapper.toAddressEntity(customerAddressDTO);

        address.setMainAddress(true);
        address.setCustomer(customer);
        customer.setAddressList(List.of(address));
        customerService.save(customer);

        return customer;
    }

    @PutMapping("/update/{id}")
    public Customer update(@RequestBody CustomerAddressDTO customerAddressDTO, @PathVariable Long id) {
        Customer customer = customerAddressMapper.toCustomerEntity(customerAddressDTO);

        customer.setId(id);
        customer.setAddressList(customerService.findById(id).getAddressList());

        return customerService.update(customer);
    }
}