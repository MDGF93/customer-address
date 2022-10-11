package com.example.customeraddress.controller;

import com.example.customeraddress.dto.AddressDTO;
import com.example.customeraddress.dto.CustomerAddressDTO;
import com.example.customeraddress.entity.Address;
import com.example.customeraddress.entity.Customer;
import com.example.customeraddress.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    public CustomerController(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    public CustomerAddressDTO convertToDto(Customer customer) {
        assert modelMapper != null;
        return modelMapper.map(customer, CustomerAddressDTO.class);
    }

    public Customer convertToEntity(CustomerAddressDTO customerAddressDTO) {
        assert modelMapper != null;
        return modelMapper.map(customerAddressDTO, Customer.class);
    }

    public Address callViaCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        String result = restTemplate.getForObject(url, String.class);
        Gson gson = new Gson();
        return gson.fromJson(result, Address.class);
    }

//    @PostMapping("/save")
//    public CustomerAddressDTO save(@RequestBody CustomerAddressDTO customerAddressDTO) {
//        Customer customer = new Customer();
//        Address address = new Address(customerAddressDTO.getCep(), customerAddressDTO.getExtraInfo(), customerAddressDTO.getNumber(), true, customer);
//        customer.setFirstName(customerAddressDTO.getFirstName());
//        customer.setLastName(customerAddressDTO.getLastName());
//        customer.setAddressList(List.of(address));
//        customer.setCpf(customerAddressDTO.getCpf());
//        customer.setCnpj(customerAddressDTO.getCnpj());
//        customer.setPhone(customerAddressDTO.getPhone());
//        customer.setEmail(customerAddressDTO.getEmail());
//        customerService.save(customer);
//        return customerAddressDTO;
//    }

    @PostMapping("/save")
    public Customer save(@RequestBody CustomerAddressDTO customerAddressDTO) {
        Customer customer = modelMapper.map(customerAddressDTO, Customer.class);
        Address address = new Address(customerAddressDTO.getCep(), customerAddressDTO.getExtraInfo(),
                          customerAddressDTO.getNumber(), true, customer);
        customer.setAddressList(List.of(address)); // Evita NPE
        customerService.save(customer);
        return customer;
    }

    @PostMapping("/addAddress/{id}")
    public Customer addAddressToCostumer(@RequestBody AddressDTO addressDTO, @PathVariable Long id) {
        Address address = callViaCep(addressDTO.getCep());
        address.setNumber(addressDTO.getNumber());
        address.setExtraInfo(addressDTO.getExtraInfo());
        address.setMainAddress(addressDTO.isMainAddress());
        address.setCustomer(customerService.findById(id));
        return customerService.addAddressToCostumer(id, address);
    }
    @GetMapping("/findAllDTO")
    public List<CustomerAddressDTO> findAllDTO() {
        //convert entity to dto
        List<CustomerAddressDTO> customerAddressDTOList = new ArrayList<>();
        for(Customer customer : customerService.findAll()){
              customerAddressDTOList.add(modelMapper.map(customer, CustomerAddressDTO.class));
        }
        return customerAddressDTOList;
    }
    @GetMapping("/findAll")
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        customerService.delete(id);
    }

    @DeleteMapping("/removeAddress/{id}/{addressId}")
    public Customer removeAddressFromCustomer(@PathVariable Long id, @PathVariable Long addressId) {
        return customerService.removeAddressFromCustomer(id, addressId);
    }

}
