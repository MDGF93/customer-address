package com.example.customeraddress.controller;


import com.example.customeraddress.ValidInfos;
import com.example.customeraddress.dto.AddressDTO;
import com.example.customeraddress.dto.CustomerAddressDTO;
import com.example.customeraddress.mapper.AddressMapper;
import com.example.customeraddress.mapper.CustomerAddressMapper;
import com.example.customeraddress.repository.CustomerRepository;
import com.example.customeraddress.service.impl.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //reset database after each test
public class CustomerControllerTest extends ValidInfos {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerAddressMapper customerAddressMapper;

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenNewValidCustomerAddressDTO_shouldSave() throws Exception {
        mockMvc.perform(post("/customer")
                        .content(objectMapper.writeValueAsString(VALID_CUSTOMERADDRESSDTO))
                        .contentType("application/json"))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenNewCustomerAddressDTOWithInvalidCPF_shouldNotSave() throws Exception {
        CustomerAddressDTO customerAddressDTO = VALID_CUSTOMERADDRESSDTO;
        customerAddressDTO.setCpf("12345678901");
        mockMvc.perform(post("/customer")
                        .content(objectMapper.writeValueAsString(customerAddressDTO))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenNewCustomerAddressDTOWithInvalidCep_shouldNotSave() throws Exception {
        CustomerAddressDTO customerAddressDTO = VALID_CUSTOMERADDRESSDTO;
        customerAddressDTO.setCep("123456789");
        mockMvc.perform(post("/customer")
                        .content(objectMapper.writeValueAsString(customerAddressDTO))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenNewCustomerAddressDTOWithNullStreet_shouldNotSave() throws Exception {
        CustomerAddressDTO customerAddressDTO = VALID_CUSTOMERADDRESSDTO;
        customerAddressDTO.setStreet(null);
        mockMvc.perform(post("/customer")
                        .content(objectMapper.writeValueAsString(customerAddressDTO))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenNewCustomerAddressDTOWithNullNumber_shouldNotSave() throws Exception {
        CustomerAddressDTO customerAddressDTO = VALID_CUSTOMERADDRESSDTO;
        customerAddressDTO.setNumber(null);
        mockMvc.perform(post("/customer")
                        .content(objectMapper.writeValueAsString(customerAddressDTO))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenNewCustomerAddressDTOWithNullCity_shouldNotSave() throws Exception {
        CustomerAddressDTO customerAddressDTO = VALID_CUSTOMERADDRESSDTO;
        customerAddressDTO.setCity(null);
        mockMvc.perform(post("/customer")
                        .content(objectMapper.writeValueAsString(customerAddressDTO))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenNewCustomerAddressDTOWithNullState_shouldNotSave() throws Exception {
        CustomerAddressDTO customerAddressDTO = VALID_CUSTOMERADDRESSDTO;
        customerAddressDTO.setState(null);
        mockMvc.perform(post("/customer")
                        .content(objectMapper.writeValueAsString(customerAddressDTO))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnAllCustomer() throws Exception {
        mockMvc.perform(post("/customer")
                        .content(objectMapper.writeValueAsString(VALID_CUSTOMERADDRESSDTO))
                        .contentType("application/json"))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().getContentAsString().contains(VALID_CPF));
    }

    @Test
    public void shouldReturnNotFoundWhenCustomerNotFound() throws Exception {
        mockMvc.perform(get("/customer/12345678901"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenNewAddressDTO_shouldAddToCustomerAddressList() throws Exception {
        AddressDTO addressDTO = new AddressDTO(VALID_CEP, VALID_CITY, VALID_STATE, VALID_STREET, VALID_NUMBER, VALID_EXTRAINFO, true, VALID_ADDRESS_NAME);
        mockMvc.perform(post("/customer")
                        .content(objectMapper.writeValueAsString(VALID_CUSTOMERADDRESSDTO))
                        .contentType("application/json"))
                .andExpect(status().isCreated());
        Long customerId = customerRepository.findByCpf(VALID_CPF).getId();
        mockMvc.perform(post("/customer/" + customerId + "/addAddress/")
                .content(objectMapper.writeValueAsString(addressDTO))
                .contentType("application/json"))
                .andExpect(status().isCreated());
    }

}
