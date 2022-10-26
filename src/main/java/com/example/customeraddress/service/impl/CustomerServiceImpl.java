package com.example.customeraddress.service.impl;

import com.example.customeraddress.dto.CustomerAddressDTO;
import com.example.customeraddress.entity.Address;
import com.example.customeraddress.entity.Customer;
import com.example.customeraddress.repository.AddressRepository;
import com.example.customeraddress.repository.CustomerRepository;
import com.example.customeraddress.service.CustomerService;
import com.example.customeraddress.service.exception.InvalidCustomerIdException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;


    public CustomerServiceImpl(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    public void save(Customer customer) {
        //If both cpf and cnpj are null, throw exception
        if (Objects.isNull(customer.getCpf()) && Objects.isNull(customer.getCnpj())) {
            throw new ConstraintViolationException("Must provide at least a CPF or CNPJ", null);
        }

        //If CPF already in database throw exception
        if (customer.getCpf() != null && Objects.nonNull(customerRepository.findByCpf(customer.getCpf()))) {
            throw new RuntimeException("CPF already in database");
        }
        //If CNPJ already in database throw exception
        if (customer.getCnpj() != null && Objects.nonNull(customerRepository.findByCnpj(customer.getCnpj()))) {
            throw new RuntimeException("CNPJ already in database");
        }
        if (customer.getAddressList().size() == 1) {
            customer.getAddressList().get(0).setMainAddress(true);
        }
        customerRepository.save(customer);
    }

    public Customer update(Customer customer) {
        Customer customerFromDB = customerRepository.findById(customer.getId()).orElseThrow();
        customerFromDB.setFirstName(customer.getFirstName());
        customerFromDB.setLastName(customer.getLastName());
        customerFromDB.setCpf(customer.getCpf());
        customerFromDB.setCnpj(customer.getCnpj());
        customerFromDB.setPhone(customer.getPhone());
        customerFromDB.setEmail(customer.getEmail());
        customerRepository.save(customerFromDB);
        return customerFromDB;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Long id) {
        if (customerRepository.findById(id).isPresent()) {
            return customerRepository.findById(id).get();
        }
        throw new InvalidCustomerIdException();
    }

    public void delete(Long id) {
        if (customerRepository.findById(id).isPresent()) {
            customerRepository.deleteById(id);
        } else throw new InvalidCustomerIdException();
    }

    @SneakyThrows
    public Customer addAddressToCostumer(Long id, Address address) {
        if (customerRepository.findById(id).isPresent()) {
            if (customerRepository.findById(id).get().getAddressList().size() >= 5) {
                throw new ConstraintViolationException(null);
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
                throw new ConstraintViolationException(null);
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

    public void generateFakeDataFromCSV() {
        String path = "src/main/resources/fakedata.csv";
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            List<String[]> r = reader.readAll();
            for (int i = 1; i < r.size(); i++) {
                CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO();
                customerAddressDTO.setFirstName(r.get(i)[0]);
                customerAddressDTO.setLastName(r.get(i)[1]);
                customerAddressDTO.setEmail(r.get(i)[2]);
                customerAddressDTO.setPhone(r.get(i)[3]);
                customerAddressDTO.setCpf(r.get(i)[4]);
                customerAddressDTO.setCnpj(r.get(i)[5]);
                customerAddressDTO.setCep(r.get(i)[6]);
                customerAddressDTO.setCity(r.get(i)[7]);
                customerAddressDTO.setState(r.get(i)[8]);
                customerAddressDTO.setStreet(r.get(i)[9]);
                customerAddressDTO.setNumber(r.get(i)[10]);
                customerAddressDTO.setExtraInfo(r.get(i)[11]);
                customerAddressDTO.setMainAddress(true);
                Customer customer = new Customer(customerAddressDTO.getFirstName(), customerAddressDTO.getLastName(), customerAddressDTO.getEmail(), customerAddressDTO.getPhone(), customerAddressDTO.getCpf(), customerAddressDTO.getCnpj());
                Address address = new Address(customerAddressDTO.getCep(), customerAddressDTO.getCity(), customerAddressDTO.getState(), customerAddressDTO.getStreet(), customerAddressDTO.getNumber(), customerAddressDTO.getExtraInfo(), customerAddressDTO.isMainAddress(), customer);
                customer.setAddressList(new ArrayList<>());
                customer.getAddressList().add(address);
                customerRepository.save(customer);
                addressRepository.save(address);
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> searchByFirstAndLastName(String firstName, String lastName) {
        return customerRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public List<Customer> searchByCity(String city) {
        return customerRepository.findByAddressListCity(city);
    }

    @Override
    public List<Customer> search(String firstName, String lastName, String phone) {
        return customerRepository.findByFirstNameAndLastNameAndPhone(firstName, lastName, phone);
    }
}