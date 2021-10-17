package dev.taljaard.training.trnmicroservbrewery.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import dev.taljaard.training.trnmicroservbrewery.services.CustomerService;
import dev.taljaard.training.trnmicroservbrewery.web.model.CustomerDto;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    private static final String API_PATH = CustomerController.PATH;
    @MockBean
    private CustomerService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerDto customer;

    @BeforeEach
    public void setup() {
        customer = CustomerDto.builder().id(UUID.randomUUID()).name("Strange Panda").build();
        when(service.createCustomer(any(CustomerDto.class))).thenReturn(customer);
    }

    @Test
    public void testGetCustomerById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + UUID.randomUUID()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateNewCustomer() throws Exception {

        customer.setId(null);
        String json = objectMapper.writeValueAsString(customer);

        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists(HttpHeaders.LOCATION));
    }

    @Test
    public void testCreateNewCustomerNameLengthIncorrect() throws Exception {

        String json = objectMapper.writeValueAsString(CustomerDto.builder().name("12").build());

        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        customer.setId(null);
        String json = objectMapper.writeValueAsString(customer);

        mockMvc.perform(MockMvcRequestBuilders.put(API_PATH + UUID.randomUUID()).content(json)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteCustomer() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(API_PATH + UUID.randomUUID()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
