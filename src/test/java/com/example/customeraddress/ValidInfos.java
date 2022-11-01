package com.example.customeraddress;

import com.example.customeraddress.entity.Address;
import com.example.customeraddress.entity.Customer;
import com.example.customeraddress.entity.dto.CustomerAddressDTO;

import java.util.List;

public class ValidInfos {
    public final String VALID_CPF = "03365100040";
    public final String VALID_CNPJ = "85677342000101";
    public final String VALID_PHONE = "(83) 99999-9999";
    public final String VALID_EMAIL = "johndoe@gmail.com";
    public final String VALID_FIRST_NAME = "John";
    public final String VALID_LAST_NAME = "Doe";
    public final String VALID_STREET = "Rua das Flores";
    public final String VALID_NUMBER = "123";
    public final String VALID_CITY = "João Pessoa";
    public final String VALID_STATE = "PB";
    public final String VALID_CEP = "58000-000";
    public final String VALID_EXTRAINFO = "Apto 201";

    public final String VALID_ADDRESS_NAME = "Casa";

    public final Customer VALID_CUSTOMER = new Customer(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_CPF, VALID_CNPJ, VALID_PHONE, VALID_EMAIL);
    public final Address VALID_ADDRESS = new Address(VALID_CEP,VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true, VALID_ADDRESS_NAME, VALID_CUSTOMER);
    public final List<Address> VALID_ADDRESS_LIST = List.of(VALID_ADDRESS);
    public final Customer VALID_CUSTOMER_WITH_ADDRESS = new Customer(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_CPF, VALID_CNPJ, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS_LIST);
    public final CustomerAddressDTO VALID_CUSTOMERADDRESSDTO = new CustomerAddressDTO(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL, VALID_PHONE, VALID_CPF, VALID_CNPJ, VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true, VALID_ADDRESS_NAME);
}
