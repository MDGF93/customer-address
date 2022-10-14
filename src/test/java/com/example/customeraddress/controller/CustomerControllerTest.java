package com.example.customeraddress.controller;

import com.example.customeraddress.dto.AddressDTO;
import com.example.customeraddress.dto.CustomerAddressDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO("", VALID_LAST_NAME, VALID_EMAIL,
                VALID_PHONE, VALID_CPF, VALID_CNPJ, VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true);
        assertThatThrownBy(() -> {
            mockMvc.perform(post("/customer/save")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json().build().writeValueAsString(customerAddressDTO)))
                    .andDo(print())
                    .andExpect(status().isOk());
        }).isInstanceOf(NestedServletException.class);
    }

    @Test
    public void addAddress_AddingNewAddressToCustomer_ShouldAdd() throws Exception {
        CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL,
                VALID_PHONE, VALID_CPF, VALID_CNPJ, VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true);
        AddressDTO addressDTO = new AddressDTO(VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true);
        mockMvc.perform(post("/customer/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json().build().writeValueAsString(customerAddressDTO)))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(post("/customer/addAddress/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json().build().writeValueAsString(addressDTO)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void save_AddingNewCustomerWithInvalidCpf_ShouldNotSave() throws Exception {
        CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL,
                VALID_PHONE, "1111111111", VALID_CNPJ, VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true);
        assertThatThrownBy(() -> mockMvc.perform(post("/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json().build().writeValueAsString(customerAddressDTO))))
                .isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(ConstraintViolationException.class);
    }


    @Test
    public void save_AddingNewCustomerWithInvalidCnpj_ShouldNotSave() throws Exception {
        CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL,
                VALID_PHONE, VALID_CPF, "85677342000102", VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true);
        assertThatThrownBy(() -> mockMvc.perform(post("/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json().build().writeValueAsString(customerAddressDTO))))
                .isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void save_AddingNewCustomerWithInvalidEmail_ShouldNotSave() throws Exception {
        CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO(VALID_FIRST_NAME, VALID_LAST_NAME, "johndoe",
                VALID_PHONE, VALID_CPF, VALID_CNPJ, VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true);
        assertThatThrownBy(() -> mockMvc.perform(post("/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json().build().writeValueAsString(customerAddressDTO))))
                .isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void save_AddingNewCustomerWithInvalidPhone_ShouldNotSave() {
        CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL,
                "(83) 9999-9999", VALID_CPF, VALID_CNPJ, VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true);
        assertThatThrownBy(() -> mockMvc.perform(post("/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json().build().writeValueAsString(customerAddressDTO))))
                .isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void save_AddingNewCustomerWithInvalidCep_ShouldNotSave() {
        CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL,
                VALID_PHONE, VALID_CPF, VALID_CNPJ, "58102051", VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true);
        assertThatThrownBy(() -> mockMvc.perform(post("/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json().build().writeValueAsString(customerAddressDTO))))
                .isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void save_AddingNewCustomerWithBlankCity_ShouldNotSave() {
        CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL,
                VALID_PHONE, VALID_CPF, VALID_CNPJ, VALID_CEP, "", VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true);
        assertThatThrownBy(() -> mockMvc.perform(post("/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json().build().writeValueAsString(customerAddressDTO))))
                .isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void save_AddingNewCustomerWithBlankState_ShouldNotSave() {
        CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL,
                VALID_PHONE, VALID_CPF, VALID_CNPJ, VALID_CEP, VALID_CITY, "", VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true);
        assertThatThrownBy(() -> mockMvc.perform(post("/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json().build().writeValueAsString(customerAddressDTO))))
                .isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void save_AddingNewCustomerWithBlankNumber_ShouldNotSave() {
        CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL,
                VALID_PHONE, VALID_CPF, VALID_CNPJ, VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, "", VALID_EXTRAINFO, true);
        assertThatThrownBy(() -> mockMvc.perform(post("/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json().build().writeValueAsString(customerAddressDTO))))
                .isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(ConstraintViolationException.class);
    }

    @Test
    public void save_SavingCustomersWithSameCnpj_ShouldNotSave() throws Exception {
        CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL,
                VALID_PHONE, VALID_CPF, VALID_CNPJ, VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true);
        CustomerAddressDTO customerAddressDTO2 = new CustomerAddressDTO(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL,
                VALID_PHONE, "20244173028", VALID_CNPJ, VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true);
        mockMvc.perform(post("/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json().build().writeValueAsString(customerAddressDTO)));
        assertThatThrownBy(() -> mockMvc.perform(post("/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json().build().writeValueAsString(customerAddressDTO2))))
                .isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(RuntimeException.class)
                .hasMessageContaining("CNPJ already in database");
    }

    @Test
    public void save_SavingCustomersWithSameCpf_ShouldNotSave() throws Exception {
        CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL,
                VALID_PHONE, VALID_CPF, VALID_CNPJ, VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true);
        CustomerAddressDTO customerAddressDTO2 = new CustomerAddressDTO(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL,
                VALID_PHONE, VALID_CPF, "04295808000102", VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true);
        mockMvc.perform(post("/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json().build().writeValueAsString(customerAddressDTO)));
        assertThatThrownBy(() -> mockMvc.perform(post("/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json().build().writeValueAsString(customerAddressDTO2))))
                .isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(RuntimeException.class)
                .hasMessageContaining("CPF already in database");
    }

    @Test
    public void addAddress_AddingMoreThanFiveAddressesToCustomer_ShouldNotSave() throws Exception {
        CustomerAddressDTO customerAddressDTO = new CustomerAddressDTO(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_EMAIL,
                VALID_PHONE, VALID_CPF, VALID_CNPJ, VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true);
        AddressDTO addressDTO = new AddressDTO(VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, false);
        mockMvc.perform(post("/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json().build().writeValueAsString(customerAddressDTO)));
        for (int i = 0; i < 4; i++) {
            mockMvc.perform(post("/customer/addAddress/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json().build().writeValueAsString(addressDTO)));
        }
        assertThatThrownBy(() -> mockMvc.perform(post("/customer/addAddress/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json().build().writeValueAsString(addressDTO))))
                .isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(ConstraintViolationException.class);
    }
}
