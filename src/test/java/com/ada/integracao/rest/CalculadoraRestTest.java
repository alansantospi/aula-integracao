package com.ada.integracao.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class CalculadoraRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void teste_somar() throws Exception {
        String n1 = "4";
        String n2 = "6";
        String retorno = "10.0";
        String uri = "/calculadora/somar";

        mockMvc.perform(
                get(uri)
                .contentType(MediaType.APPLICATION_JSON)
                        .param("n1", n1)
                        .param("n2", n2)
        ).andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.primeiroNumero").value(n1))
        .andExpect(jsonPath("$.segundoNumero").value(n2))
        .andExpect(jsonPath("$.resultados[0]").value(retorno))
        .andExpect(jsonPath("$.resultados[1]").value("2"))
        ;

    }

}