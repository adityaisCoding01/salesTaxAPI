package com.salestaxapi;

import com.salestaxapi.model.TaxRate;
import com.salestaxapi.repository.TaxRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaxRateControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaxRateRepository taxRateRepository;

    @BeforeEach
    public void setup() {
        taxRateRepository.deleteAll();

        TaxRate taxRate1 = new TaxRate();
        taxRate1.setState("California");
        taxRate1.setCity("Los Angeles");
        taxRate1.setRate(9.5);
        taxRateRepository.save(taxRate1);

        TaxRate taxRate2 = new TaxRate();
        taxRate2.setState("New York");
        taxRate2.setCity("New York City");
        taxRate2.setRate(8.875);
        taxRateRepository.save(taxRate2);
    }

    @Test
    public void testGetAllTaxRates() throws Exception {
        mockMvc.perform(get("/api/taxRates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].state", is("California")))
                .andExpect(jsonPath("$[1].state", is("New York")));
    }

    @Test
    public void testAddTaxRate() throws Exception {
        String newTaxRate = "{\"state\":\"Texas\",\"city\":\"Austin\",\"rate\":8.25}";

        mockMvc.perform(post("/api/taxRates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newTaxRate))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.state", is("Texas")))
                .andExpect(jsonPath("$.city", is("Austin")))
                .andExpect(jsonPath("$.rate", is(8.25)));
    }

    @Test
    public void testUpdateTaxRate() throws Exception {
        List<TaxRate> taxRates = taxRateRepository.findAll();
        TaxRate taxRate = taxRates.get(0);

        String updatedTaxRate = "{\"state\":\"California\",\"city\":\"Los Angeles\",\"rate\":9.75}";

        mockMvc.perform(put("/api/taxRates/" + taxRate.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedTaxRate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rate", is(9.75)));
    }

    @Test
    public void testDeleteTaxRate() throws Exception {
        List<TaxRate> taxRates = taxRateRepository.findAll();
        TaxRate taxRate = taxRates.get(0);

        mockMvc.perform(delete("/api/taxRates/" + taxRate.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testFindTaxRates() throws Exception {
        mockMvc.perform(get("/api/taxRates/search?location=California"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].state", is("California")));

        mockMvc.perform(get("/api/taxRates/search?location=New York City"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].city", is("New York City")));

        mockMvc.perform(get("/api/taxRates/search?location=Nonexistent"))
                .andExpect(status().isNotFound());
    }
}