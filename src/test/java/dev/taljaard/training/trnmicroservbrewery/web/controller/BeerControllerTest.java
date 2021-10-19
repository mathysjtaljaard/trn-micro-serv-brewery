package dev.taljaard.training.trnmicroservbrewery.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.StringUtils;

import dev.taljaard.training.trnmicroservbrewery.services.BeerService;
import dev.taljaard.training.trnmicroservbrewery.web.model.BeerDto;

@ExtendWith(RestDocumentationExtension.class)
// Ability to customize the output rest doc location
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "beer.dev.taljaard", uriPort = 443)
@WebMvcTest(BeerController.class)
public class BeerControllerTest {

    private static final String API_PATH = BeerController.PATH;
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

        mockMvc.perform(RestDocumentationRequestBuilders.get(API_PATH + "{beerId}", UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(beerDto.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.beerName", Is.is(beerDto.getBeerName())))
                .andDo(MockMvcRestDocumentation.document("v1/beer-get",
                        RequestDocumentation.pathParameters(RequestDocumentation.parameterWithName("beerId")
                                .description("UUID of desired beer to get")),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("Id of Beer"),
                                // PayloadDocumentation.fieldWithPath("version").description("Version number"),
                                // PayloadDocumentation.fieldWithPath("createdDate").description("Date
                                // Created"),
                                // PayloadDocumentation.fieldWithPath("lastModifiedDate").description("Date
                                // Updated"),
                                PayloadDocumentation.fieldWithPath("beerName").description("Beer Name"),
                                PayloadDocumentation.fieldWithPath("beerStyle").description("Beer Style"),
                                PayloadDocumentation.fieldWithPath("upc").description("UPC of Beer"))));
        // PayloadDocumentation.fieldWithPath("price").description("Price"),
        // PayloadDocumentation.fieldWithPath("quantityOnHand").description("Quantity On
        // hand"))));
    }

    @Test
    public void createNewBeer() throws Exception {

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        beerDto.setId(null);
        String beerJson = objectMapper.writeValueAsString(beerDto);

        beerDto.setId(UUID.randomUUID());
        when(beerService.saveBeer(any(BeerDto.class))).thenReturn(beerDto);

        mockMvc.perform(RestDocumentationRequestBuilders
                .post(API_PATH).content(beerJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcRestDocumentation.document("v1/beer-new",
                        PayloadDocumentation.requestFields(fields.withPath("beerName").description("Name of the beer"),
                                fields.withPath("beerStyle").description("Style of Beer"),
                                fields.withPath("upc").description("Beer UPC").attributes())));
        ;
    }

    @Test
    public void updateBeerById() throws Exception {
        beerDto.setId(null);
        String beerJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(RestDocumentationRequestBuilders.put(API_PATH + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON).content(beerJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return PayloadDocumentation.fieldWithPath(path).attributes(Attributes.key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path), ". ")));
        }
    }
}
