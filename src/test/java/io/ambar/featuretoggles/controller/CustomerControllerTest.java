package io.ambar.featuretoggles.controller;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private CustomerController controller;


    @Autowired
    MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Test
    public void contextLoaded() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void getCustomers_smoke() throws Exception {
        mockMvc.perform(get(getUrl(port) + "/customer"))
                .andExpect(status().isOk());
    }

    @Test
    public void bindFeatureToCustomer_smoke() throws Exception {
        mockMvc.perform(
                patch(getUrl(port) + "/customer/feature")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\": 0, \"featureId\": 0}"))
                .andExpect(status().isOk());
    }

    @Ignore
    private String getUrl(int port) {
        return "http://localhost:" + port + "/";
    }
}
