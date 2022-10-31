package com.example.customeraddress.mapper;

import com.example.customeraddress.dto.CustomerAddressDTO;
import com.example.customeraddress.entity.Address;
import com.example.customeraddress.entity.Customer;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CustomerAddressMapper {


    @Mapping(target = "addressList", ignore = true)
    @Mapping(target = "id", ignore = true)
    Customer toCustomerEntity(CustomerAddressDTO customerAddressDTO);

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "id", ignore = true)
    Address toAddressEntity(CustomerAddressDTO customerAddressDTO);

    @Mapping(source = "customer.firstName", target = "firstName")
    @Mapping(source = "customer.lastName", target = "lastName")
    @Mapping(source = "customer.email", target = "email")
    @Mapping(source = "customer.phone", target = "phone")
    @Mapping(source = "customer.cpf", target = "cpf")
    @Mapping(source = "customer.cnpj", target = "cnpj")
    @Mapping(source = "address.cep", target = "cep")
    @Mapping(source = "address.city", target = "city")
    @Mapping(source = "address.state", target = "state")
    @Mapping(source = "address.street", target = "street")
    @Mapping(source = "address.number", target = "number")
    @Mapping(source = "address.extraInfo", target = "extraInfo")
    @Mapping(source = "address.mainAddress", target = "mainAddress")
    @Mapping(source = "address.addressName", target = "addressName")
    CustomerAddressDTO toDto(Customer customer, Address address);


    @Mapping(target = "extraInfo", ignore = true)
    @Mapping(target = "number", ignore = true)
    @Mapping(target = "street", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "mainAddress", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "cep", ignore = true)
    @Mapping(target = "addressName", ignore = true)
    CustomerAddressDTO toDto(Customer customer);

    default List<CustomerAddressDTO> toDtoList(List<Customer> customerList, List<Address> addressList) {
        return customerList.stream().map(customer -> toDto(customer, addressList.stream().filter(address -> address.getCustomer().getId().equals(customer.getId())).findFirst().orElse(null))).collect(Collectors.toList());
    }

    default List<CustomerAddressDTO> toDtoList(List<Customer> customerList) {
        return customerList.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "cnpj", ignore = true)
    CustomerAddressDTO updateDtoFromAddress(Address address, @MappingTarget CustomerAddressDTO customerAddressDTO);
}
