package com.example.customeraddress.mapper;

import com.example.customeraddress.ValidInfos;
import com.example.customeraddress.dto.CustomerAddressDTO;
import com.example.customeraddress.entity.Address;
import com.example.customeraddress.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerAddressMapperTest extends ValidInfos {
    private final AddressMapper addressMapper;
    private final CustomerAddressMapper customerAddressMapper;
    public CustomerAddressMapperTest(AddressMapper addressMapper, CustomerAddressMapper customerAddressMapper) {
        this.addressMapper = addressMapper;
        this.customerAddressMapper = customerAddressMapper;
    }

    @Test
    public void shouldMapCustomerAddressDTOToCustomer() {
        //given
        CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL,
                VALID_PHONE, VALID_CPF, VALID_CNPJ, VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true, VALID_ADDRESS_NAME);
        //when
        Customer customer = customerAddressMapper.toCustomerEntity(customerAddressDTO);
        System.out.println("CustomerAddressDTO -> Customer : " + customer);
    }


    @Test
    public void shouldMapCustomerAddressDTOToAddress(){
        //given
        CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL,
                VALID_PHONE, VALID_CPF, VALID_CNPJ, VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true, VALID_ADDRESS_NAME);
        //when
        Address address = customerAddressMapper.toAddressEntity(customerAddressDTO);
        System.out.println("CustomerAddressDTO -> Address : " + address);
    }

    public AddressMapper getAddressMapper() {
        return addressMapper;
    }
}
