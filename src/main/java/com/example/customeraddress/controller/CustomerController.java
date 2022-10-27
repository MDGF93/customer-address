package com.example.customeraddress.controller;

import com.example.customeraddress.dto.AddressDTO;
import com.example.customeraddress.dto.CustomerAddressDTO;
import com.example.customeraddress.entity.Address;
import com.example.customeraddress.entity.Customer;
import com.example.customeraddress.mapper.AddressMapper;
import com.example.customeraddress.mapper.CustomerAddressMapper;
import com.example.customeraddress.service.CustomerService;
import com.example.customeraddress.service.exception.InvalidAddressIdException;
import com.example.customeraddress.service.exception.InvalidCustomerIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
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

    @PostMapping("/{id}/addAddress")
    public ResponseEntity<Customer> addAddressToCostumer(@Valid @RequestBody AddressDTO addressDTO, @PathVariable Long id) {
        try {
            Address address = addressMapper.toEntity(addressDTO);
            return new ResponseEntity<>(customerService.addAddressToCostumer(id, address), HttpStatus.CREATED);
        }
        catch (ConstraintViolationException moreThanFiveAddresses) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer can't have more than 5 addresses at the same time.", moreThanFiveAddresses);
        }
        catch (InvalidCustomerIdException customerNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't add an address to a Costumer that doesn't exists.", customerNotFound);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try{
            String customerFullName = customerService.findById(id).getFirstName() + " " + customerService.findById(id).getLastName();
            customerService.delete(id);
            return new ResponseEntity<>("Customer " + customerFullName + " deleted", HttpStatus.OK);
        }
        catch (InvalidCustomerIdException customerNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't delete a Customer that doesn't exists", customerNotFound);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e);
        }
    }

    @GetMapping
    public ResponseEntity<List<Customer>> findAll() {
        try {
            return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't return customer list", e);
        }
    }

    @GetMapping("/DTO")
    public ResponseEntity<List<CustomerAddressDTO>> findAllDTO() {
        try {
            // convert entity to dto
            List<CustomerAddressDTO> customerAddressDTOList = new ArrayList<>();
            for (Customer customer : customerService.findAll()) {
                CustomerAddressDTO customerAddressDTO = customerAddressMapper.toDto(customer);
                customerAddressDTOList.add(customerAddressDTO);
            }

            return new ResponseEntity<>(customerAddressDTOList, HttpStatus.OK);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't return customer list as DTO", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(customerService.findById(id), HttpStatus.OK);
        }
        catch (InvalidCustomerIdException customerNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find a customer with id number " + id.toString(), customerNotFound);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e);
        }
    }

    @GetMapping("/{id}/DTO")
    public ResponseEntity<CustomerAddressDTO> findByIdDTO(@PathVariable Long id) {
        try {
            // convert entity to dto
            CustomerAddressDTO customerAddressDTO = customerAddressMapper.toDto(customerService.findById(id));
            return new ResponseEntity<>(customerAddressDTO, HttpStatus.OK);
        }
        catch (InvalidCustomerIdException customerNotFound) {
            throw new InvalidCustomerIdException(HttpStatus.NOT_FOUND, "Can't find a customer with id number " + id.toString(), customerNotFound);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e);
        }
    }

    @DeleteMapping("/{id}/removeAddress/{addressId}")
    public ResponseEntity<Customer> removeAddressFromCustomer(@PathVariable Long id, @PathVariable Long addressId) {
        try {
            return new ResponseEntity<>(customerService.removeAddressFromCustomer(id, addressId), HttpStatus.OK);
        }
        catch (ConstraintViolationException mustHaveAtLeasOneAddress){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer must have at least one address", mustHaveAtLeasOneAddress);
        }
        catch (InvalidCustomerIdException customerNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer id not found", customerNotFound);
        }
        catch (InvalidAddressIdException addressNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address id not found", addressNotFound);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e);
        }
    }

    @PostMapping
    public ResponseEntity<Customer> save(@Valid @RequestBody CustomerAddressDTO customerAddressDTO) {
        try {
            Customer customer = customerAddressMapper.toCustomerEntity(customerAddressDTO);
            Address address = customerAddressMapper.toAddressEntity(customerAddressDTO);

            address.setMainAddress(true);
            address.setCustomer(customer);
            customer.setAddressList(List.of(address));
            customerService.save(customer);
            return new ResponseEntity<>(customer, HttpStatus.CREATED);

        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@RequestBody CustomerAddressDTO customerAddressDTO, @PathVariable Long id) {
        try {
            Customer customer = customerAddressMapper.toCustomerEntity(customerAddressDTO);
            customer.setId(id);
            customer.setAddressList(customerService.findById(id).getAddressList());
            return new ResponseEntity<>(customerService.update(customer), HttpStatus.OK);
        }
        catch (InvalidCustomerIdException customerNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer id not found", customerNotFound);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e);
        }
    }

    @GetMapping("/generate-fake-data-from-csv")
    public void generateFakeDataFromCSV() {
        try {
            customerService.generateFakeDataFromCSV();
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Customer>> search(@RequestParam(value = "firstName", defaultValue = "") String firstName,
                                                 @RequestParam(value = "lastName", defaultValue = "") String lastName,
                                                 @RequestParam(value = "phone", defaultValue = "") String phone)
    {
        try {
            return new ResponseEntity<>(customerService.search(firstName, lastName, phone), HttpStatus.OK);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e);
        }
    }

}
