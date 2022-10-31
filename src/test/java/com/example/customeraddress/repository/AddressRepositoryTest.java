package com.example.customeraddress.repository;

import com.example.customeraddress.ValidInfos;
import com.example.customeraddress.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //reset database after each test
public class AddressRepositoryTest extends ValidInfos {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void givenNewValidAddress_shouldSaveAddress() {
        addressRepository.save(VALID_ADDRESS);
        boolean isSaved = addressRepository.existsByCep(VALID_CEP);
        assertThat(isSaved).isTrue();
    }

    @Test
    public void givenNewValidAddress_shouldDeleteAddress() {
        addressRepository.save(VALID_ADDRESS);
        addressRepository.delete(VALID_ADDRESS);
        boolean isSaved = addressRepository.existsByCep(VALID_CEP);
        assertThat(isSaved).isFalse();
    }

    @Test
    public void givenNewValidAddress_shouldFindAddressByCep() {
        addressRepository.save(VALID_ADDRESS);
        Address foundAddress = addressRepository.findByCep(VALID_CEP);
        assertThat(foundAddress).isNotNull();
    }

    @Test
    public void givenNewValidAddress_shouldFindAddressByCity() {
        addressRepository.save(VALID_ADDRESS);
        Address foundAddress = addressRepository.findByCity(VALID_CITY);
        assertThat(foundAddress).isNotNull();
    }
    
}
