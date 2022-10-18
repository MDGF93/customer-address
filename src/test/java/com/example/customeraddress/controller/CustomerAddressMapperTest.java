package com.example.customeraddress.controller;

import com.example.customeraddress.dto.CustomerAddressDTO;
import com.example.customeraddress.entity.Address;
import com.example.customeraddress.entity.Customer;
import com.example.customeraddress.mapper.CustomerAddressMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerAddressMapperTest {
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

    @Test
    public void shouldMapCustomerAddressDTOToCustomer() {
        //given
        CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL,
                VALID_PHONE, VALID_CPF, VALID_CNPJ, VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true);
        //when
        Customer customer = CustomerAddressMapper.INSTANCE.toCustomerEntity(customerAddressDTO);
        System.out.println("CustomerAddressDTO -> Customer : " + customer);
    }


    @Test
    public void shouldMapCustomerAddressDTOToAddress(){
        //given
        CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL,
                VALID_PHONE, VALID_CPF, VALID_CNPJ, VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true);
        //when
        Address address = CustomerAddressMapper.INSTANCE.toAddressEntity(customerAddressDTO);
        System.out.println("CustomerAddressDTO -> Address : " + address);
    }

}
