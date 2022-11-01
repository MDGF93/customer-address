package com.example.customeraddress.mapper;

import com.example.customeraddress.ValidInfos;
import com.example.customeraddress.entity.Address;
import com.example.customeraddress.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerAddressMapperTest extends ValidInfos {
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private CustomerAddressMapper customerAddressMapper;

    @Test
    public void shouldMapCustomerAddressDTOToCustomer() {
        Customer customer = customerAddressMapper.toCustomerEntity(VALID_CUSTOMERADDRESSDTO);
        assertThat(customer.getFirstName()).isEqualTo(VALID_FIRST_NAME);
        assertThat(customer.getLastName()).isEqualTo(VALID_LAST_NAME);
        assertThat(customer.getEmail()).isEqualTo(VALID_EMAIL);
        assertThat(customer.getPhone()).isEqualTo(VALID_PHONE);
        assertThat(customer.getCpf()).isEqualTo(VALID_CPF);
        assertThat(customer.getCnpj()).isEqualTo(VALID_CNPJ);
        System.out.println("CustomerAddressDTO -> Customer : " + customer);
    }


    @Test
    public void shouldMapCustomerAddressDTOToAddress(){
        Address address = customerAddressMapper.toAddressEntity(VALID_CUSTOMERADDRESSDTO);
        assertThat(address.getCep()).isEqualTo(VALID_CEP);
        assertThat(address.getCity()).isEqualTo(VALID_CITY);
        assertThat(address.getState()).isEqualTo(VALID_STATE);
        assertThat(address.getStreet()).isEqualTo(VALID_STREET);
        assertThat(address.getNumber()).isEqualTo(VALID_NUMBER);
        assertThat(address.getExtraInfo()).isEqualTo(VALID_EXTRAINFO);
        assertThat(address.getAddressName()).isEqualTo(VALID_ADDRESS_NAME);
        assertThat(address.isMainAddress()).isEqualTo(true);
        System.out.println("CustomerAddressDTO -> Address : " + address);
    }
}
