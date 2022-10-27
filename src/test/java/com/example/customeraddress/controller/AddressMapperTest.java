package com.example.customeraddress.controller;

import com.example.customeraddress.dto.AddressDTO;
import com.example.customeraddress.entity.Address;
import com.example.customeraddress.mapper.AddressMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressMapperTest {
    String VALID_CPF = "03365100040";
    String VALID_CNPJ = "85677342000101";
    String VALID_PHONE = "(83) 99999-9999";
    String VALID_EMAIL = "johndoe@gmail.com";
    String VALID_FIRST_NAME = "John";
    String VALID_LAST_NAME = "Doe";
    String VALID_STREET = "Rua das Flores";
    String VALID_NUMBER = "123";
    String VALID_CITY = "João Pessoa";
    String VALID_STATE = "PB";
    String VALID_CEP = "58000-000";
    String VALID_EXTRAINFO = "Apto 201";

    private final AddressMapper addressMapper;

    public AddressMapperTest(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Test
    public void shouldMapAddressDTOToAddress() {
        //given
        AddressDTO addressDTO = new AddressDTO(VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true);
        Address address = addressMapper.toEntity(addressDTO);
        //when
        System.out.println("AddressDTO -> Address : " + address);
    }
}
