package com.example.customeraddress.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testReturnAllCustomers() throws Exception {
        mockMvc.perform(get("/customer/findAll"))
                .andExpect(status().isOk());
    }


    @Test
    public void save_SavingNewCustomer_ShouldSave() throws Exception {
        mockMvc.perform(post("/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"John\", \"lastName\": \"Doe\"" +
                        "\"email\": \"johndoe@gmail.com\", \"phone\": \"(83) 99611-8479\"" +
                        "\"cpf\": \"09731700420\", \"cnpj\": null, \"cep\": \"58102-051\"" +
                        "\"number\": \"110\", \"extraInfo\": \"Apt 820\"}")).andDo(print());
    }
}
