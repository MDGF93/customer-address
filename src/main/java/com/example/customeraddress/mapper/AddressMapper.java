package com.example.customeraddress.mapper;

import com.example.customeraddress.dto.AddressDTO;
import com.example.customeraddress.entity.Address;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "id", ignore = true)
    Address toEntity(AddressDTO addressDTO);
    @InheritInverseConfiguration
    AddressDTO toDto(Address address);
}
