package com.example.customeraddress.mapper;

import com.example.customeraddress.dto.AddressDTO;
import com.example.customeraddress.entity.Address;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(source = "cep", target = "cep")
    @Mapping(source = "city", target = "city")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "street", target = "street")
    @Mapping(source = "number", target = "number")
    @Mapping(source = "extraInfo", target = "extraInfo")
    @Mapping(source = "mainAddress", target = "mainAddress")
    Address toEntity(AddressDTO addressDTO);

    @InheritInverseConfiguration
    AddressDTO toDto(Address address);
}
