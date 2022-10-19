package com.example.customeraddress.mapper;

import com.example.customeraddress.dto.CustomerAddressDTO;
import com.example.customeraddress.entity.Address;
import com.example.customeraddress.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerAddressMapper {

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "cpf", target = "cpf")
    @Mapping(source = "cnpj", target = "cnpj")
    Customer toCustomerEntity(CustomerAddressDTO customerAddressDTO);

    @Mapping(source = "cep", target = "cep")
    @Mapping(source = "city", target = "city")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "street", target = "street")
    @Mapping(source = "number", target = "number")
    @Mapping(source = "extraInfo", target = "extraInfo")
    @Mapping(source = "mainAddress", target = "mainAddress")
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
    CustomerAddressDTO toDto(Customer customer, Address address);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "cpf", target = "cpf")
    @Mapping(source = "cnpj", target = "cnpj")
    CustomerAddressDTO toDto(Customer customer);
}
