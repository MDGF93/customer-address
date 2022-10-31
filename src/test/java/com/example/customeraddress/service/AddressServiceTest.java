package com.example.customeraddress.service;


import com.example.customeraddress.ValidInfos;
import com.example.customeraddress.entity.Address;
import com.example.customeraddress.repository.AddressRepository;
import com.example.customeraddress.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest extends ValidInfos {

    @Test
    public void givenNewAddress_shouldSave(@Mock AddressRepository addressRepository){
        AddressServiceImpl addressService = new AddressServiceImpl(addressRepository);
        Address address = new Address("11111-111", "Rio de Janeiro", "RJ", "Rua das Lagoas", "123", "Apto 1", true, VALID_ADDRESS_NAME, VALID_CUSTOMER_WITH_ADDRESS);
        when(addressRepository.save(any(Address.class))).then(returnsFirstArg());
        Address savedAddress = addressService.save(address);
        assertNotNull(savedAddress.getAddressName());
    }

    @Test
    public void givenNewAddress_shouldFindAll(@Mock AddressRepository addressRepository){
        AddressServiceImpl addressService = new AddressServiceImpl(addressRepository);
        addressService.findAll();
        verify(addressRepository).findAll();
        assertThat(addressService.findAll()).isEmpty();
    }

    @Test
    public void givenNewAddress_shouldFindByCep(@Mock AddressRepository addressRepository){
        AddressServiceImpl addressService = new AddressServiceImpl(addressRepository);
        when(addressRepository.findByCep(any(String.class))).thenReturn(VALID_ADDRESS);
        Address address = addressService.findByCep(VALID_CEP);
        assertNotNull(address);
    }

    @Test
    public void givenNewAddress_shouldFindById(@Mock AddressRepository addressRepository){
        AddressServiceImpl addressService = new AddressServiceImpl(addressRepository);
        VALID_ADDRESS.setId(1L);
        addressRepository.save(VALID_ADDRESS);
        when(addressRepository.findById(any(Long.class))).thenReturn(java.util.Optional.ofNullable(VALID_ADDRESS));
        assertThat(addressService.findById(1L)).isNotNull();
    }

    @Test
    public void givenNewAddress_shouldSetAsMainAddress(@Mock AddressRepository addressRepository){
        AddressServiceImpl addressService = new AddressServiceImpl(addressRepository);
        Address address = VALID_ADDRESS;
        address.setId(1L);
        address.setCustomer(VALID_CUSTOMER_WITH_ADDRESS);
        address.setMainAddress(false);
        addressService.save(address);
        when(addressRepository.save(any(Address.class))).then(returnsFirstArg());
        when(addressRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(address));
        assertNotNull(addressService.setMainAddress(address.getId()));
    }

    @Test
    public void givenNewAddress_shouldDelete(@Mock AddressRepository addressRepository){
        AddressServiceImpl addressService = new AddressServiceImpl(addressRepository);
        Address address = VALID_ADDRESS;
        address.setId(address.getId());
        addressService.save(address);
        addressRepository.deleteById(address.getId());
        verify(addressRepository).deleteById(address.getId());
        assertNull(addressRepository.findById(address.getId()).orElse(null));
    }

}
