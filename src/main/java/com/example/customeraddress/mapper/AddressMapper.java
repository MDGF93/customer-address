package com.example.customeraddress.mapper;

import com.example.customeraddress.dto.AddressDTO;
import com.example.customeraddress.entity.Address;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toEntity(AddressDTO addressDTO);

    @InheritInverseConfiguration
    AddressDTO toDto(Address address);
}
