package com.example.customeraddress.controller;

import com.example.customeraddress.dto.AddressDTO;
import com.example.customeraddress.entity.Address;
import com.example.customeraddress.mapper.AddressMapper;
import com.example.customeraddress.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;
    private final AddressMapper addressMapper;

    @Autowired
    public AddressController(AddressService addressService, AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
        this.addressService = addressService;
    }

    @GetMapping
    public List<Address> findAll() {
        return addressService.findAll();
    }

    @GetMapping("/DTO/")
    public List<AddressDTO> findAllDTO() {
        List<AddressDTO> addressDTOList = new ArrayList<>();

        for (Address address : addressService.findAll()) {
            AddressDTO addressDTO = addressMapper.toDto(address);

            addressDTOList.add(addressDTO);
        }

        return addressDTOList;
    }

    @GetMapping("/{id}/")
    public Address findById(@PathVariable Long id) {
        return addressService.findById(id);
    }

    @GetMapping("{id}/DTO/")
    public AddressDTO findByIdDTO(@PathVariable Long id) {
        return addressMapper.toDto(addressService.findById(id));
    }

    @PostMapping
    public Address update(@RequestBody Address address) {
        return addressService.update(address.getId(), address);
    }

    @PostMapping("/{id}/setMainAddress/")
    public Address setMainAddress(@PathVariable Long id) {
        return addressService.setMainAddress(id);
    }
}