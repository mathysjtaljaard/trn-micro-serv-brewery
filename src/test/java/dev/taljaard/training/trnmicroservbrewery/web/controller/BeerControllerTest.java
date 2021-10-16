package dev.taljaard.training.trnmicroservbrewery.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import dev.taljaard.training.trnmicroservbrewery.services.BeerService;
import dev.taljaard.training.trnmicroservbrewery.web.model.BeerDto;

@WebMvcTest(BeerController.class)
public class BeerControllerTest {

    @MockBean
    private BeerService beerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private BeerDto beerDto;

    @BeforeEach
    public void setup() {
        beerDto = BeerDto.builder().beerName("Skunky Monkey").beerStyle("Stout").upc(12345L).id(UUID.randomUUID())
                .build();
    }

    @Test
    public void getBeerById() throws Exception {
        when(beerService.getBeerById(any(UUID.class))).thenReturn(beerDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/" + UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(beerDto.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.beerName", Is.is(beerDto.getBeerName())));
    }

    @Test
    public void createNewBeer() throws Exception {
        beerDto.setId(null);
        String beerJson = objectMapper.writeValueAsString(beerDto);

        beerDto.setId(UUID.randomUUID());
        when(beerService.saveBeer(any(BeerDto.class))).thenReturn(beerDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/beer/").content(beerJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void updateBeerById() throws Exception {
        beerDto.setId(null);
        String beerJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON).content(beerJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
