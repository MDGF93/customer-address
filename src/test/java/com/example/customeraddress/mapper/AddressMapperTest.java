package com.example.customeraddress.mapper;

import com.example.customeraddress.ValidInfos;
import com.example.customeraddress.dto.AddressDTO;
import com.example.customeraddress.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressMapperTest extends ValidInfos {

    private final AddressMapper addressMapper;

    public AddressMapperTest(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Test
    public void shouldMapAddressDTOToAddress() {
        //given
        AddressDTO addressDTO = new AddressDTO(VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true, VALID_ADDRESS_NAME);
        Address address = addressMapper.toEntity(addressDTO);
        //when
        System.out.println("AddressDTO -> Address : " + address);
        //then
        assert address != null;
    }
}
