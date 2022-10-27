package com.example.customeraddress.controller;

import com.example.customeraddress.dto.CustomerAddressDTO;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //reset database after each test
public class CustomerControllerTest {


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

    //Before each test, load the following mappers: CustomerMapper, AddressMapper, CustomerAddressMapper
    @BeforeEach



    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testReturnAllCustomers() throws Exception {
        mockMvc.perform(get("/customer/findAll"))
                .andExpect(status().isOk());
    }

    @Test
    public void save_SavingNewCustomer_ShouldSave() throws Exception {
        CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL,
                VALID_PHONE, VALID_CPF, VALID_CNPJ, VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true);
        mockMvc.perform(post("/customer/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json().build().writeValueAsString(customerAddressDTO)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void save_SavingNewCustomerWithoutFirstName_ShouldNotSave() throws Exception {
    }

    @Test
    public void addAddress_AddingNewAddressToCustomer_ShouldAdd() throws Exception {
    }

    @Test
    public void save_AddingNewCustomerWithInvalidCpf_ShouldNotSave() throws Exception {
    }


    @Test
    public void save_AddingNewCustomerWithInvalidCnpj_ShouldNotSave() throws Exception {
    }

    @Test
    public void save_AddingNewCustomerWithInvalidEmail_ShouldNotSave() throws Exception {
    }

    @Test
    public void save_AddingNewCustomerWithInvalidPhone_ShouldNotSave() {
    }

    @Test
    public void save_AddingNewCustomerWithInvalidCep_ShouldNotSave() {
    }

    @Test
    public void save_AddingNewCustomerWithBlankCity_ShouldNotSave() {
    }

    @Test
    public void save_AddingNewCustomerWithBlankState_ShouldNotSave() {
    }

    @Test
    public void save_AddingNewCustomerWithBlankNumber_ShouldNotSave() {
    }

    @Test
    public void save_SavingCustomersWithSameCnpj_ShouldNotSave() throws Exception {
    }

    @Test
    public void save_SavingCustomersWithSameCpf_ShouldNotSave() throws Exception {
    }

    @Test
    public void addAddress_AddingMoreThanFiveAddressesToCustomer_ShouldNotSave() throws Exception {
    }

    @Test
    public void removeAddressFromCustomer_RemovingMoreTimesThanCurrentAddressListSize_ShouldNotRemoveLastAddress() throws Exception {
    }
}
