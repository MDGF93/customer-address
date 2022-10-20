package com.example.customeraddress.controller;

import com.example.customeraddress.dto.AddressDTO;
import com.example.customeraddress.entity.Address;
import com.example.customeraddress.mapper.AddressMapper;
import com.example.customeraddress.service.AddressService;
import com.example.customeraddress.service.exception.InvalidAddressIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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
    public ResponseEntity<List<Address>> findAll() {
        try{
            return ResponseEntity.ok(addressService.findAll());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.", e);
        }
    }

    @GetMapping("/DTO")
    public ResponseEntity<List<AddressDTO>> findAllDTO() {
        try {
            List<AddressDTO> addressDTOList = new ArrayList<>();

            for (Address address : addressService.findAll()) {
                AddressDTO addressDTO = addressMapper.toDto(address);

                addressDTOList.add(addressDTO);
            }
            return new ResponseEntity<>(addressDTOList, HttpStatus.OK);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.", e);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(addressService.findById(id));
        }
        catch (InvalidAddressIdException addressNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find address with this id.", addressNotFound);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.", e);
        }
    }

    @GetMapping("{id}/DTO")
    public ResponseEntity<AddressDTO> findByIdDTO(@PathVariable Long id) {
        try {
            Address address = addressService.findById(id);
            AddressDTO addressDTO = addressMapper.toDto(address);
            return new ResponseEntity<>(addressDTO, HttpStatus.OK);
        }
        catch (InvalidAddressIdException addressNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find address with this id.", addressNotFound);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.", e);
        }
    }

    @PutMapping
    public ResponseEntity<Address> update(@Valid @RequestBody Address address) {
        try {
            return ResponseEntity.ok(addressService.update(address.getId(), address));
        }
        catch (InvalidAddressIdException addressNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find address with this id.", addressNotFound);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.", e);
        }

    }

    @PostMapping("/{id}/setMainAddress")
    public ResponseEntity<Address> setMainAddress(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(addressService.setMainAddress(id));
        }
        catch (InvalidAddressIdException addressNotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find address with this id.", addressNotFound);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.", e);
        }
    }
}