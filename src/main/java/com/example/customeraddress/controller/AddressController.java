package com.example.customeraddress.controller;

import com.example.customeraddress.dto.AddressDTO;
import com.example.customeraddress.entity.Address;
import com.example.customeraddress.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;
    private final ModelMapper modelMapper;

    public AddressController(AddressService addressService, ModelMapper modelMapper) {
        this.addressService = addressService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/findDTO/{id}")
    public AddressDTO findByIdDTO(@PathVariable Long id) {
        return modelMapper.map(addressService.findById(id), AddressDTO.class);
    }

    @GetMapping("/findAllDTO")
    public List<AddressDTO> findAllDTO() {
        List<AddressDTO> addressDTOList = new ArrayList<>();
        for (Address address : addressService.findAll()) {
            addressDTOList.add(modelMapper.map(address, AddressDTO.class));
        }
        return addressDTOList;
    }

    @GetMapping("/find/{id}")
    public Address findById(@PathVariable Long id) {
        return addressService.findById(id);
    }

    @GetMapping("/findAll")
    public List<Address> findAll() {
        return addressService.findAll();
    }

    @PostMapping("/update")
    public Address update(@RequestBody Address address) {
        return addressService.update(address.getId(), address);
    }

    @PostMapping("/setMainAddress/{id}")
    public Address setMainAddress(@PathVariable Long id) {
        return addressService.setMainAddress(id);
    }
}
