package br.com.welisson.atm.restapi;

import br.com.welisson.atm.config.DatabaseConfig;
import br.com.welisson.atm.config.SpringContextTestConfiguration;
import br.com.welisson.atm.config.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringContextTestConfiguration.class, WebConfig.class, DatabaseConfig.class})
public class ATMControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Test
    public void loginSuccess() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        final MvcResult result;

            result = mockMvc.perform(MockMvcRequestBuilders.post("/atm/login")
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content("{\"account\":\"123\", \"password\":\"123\"}"))
                    .andReturn();

            final String jsonResult = result.getResponse().getContentAsString();

            JSONAssert
                    .assertEquals("{\"id\":1,\"name\":\"Welisson Oliveira\",\"login\":{\"id\":1,\"" +
                            "account\":\"123\",\"password\":\"123\"},\"balance\":1000}", jsonResult, true);

    }

    @Test
    public void loginFail() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        final MvcResult result;

        result = mockMvc.perform(MockMvcRequestBuilders.post("/atm/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{\"account\":\"124\", \"password\":\"123\"}"))
                .andReturn();

        final String jsonResult = result.getResponse().getContentAsString();
        JSONAssert.assertEquals("{\"errorCode\":500,\"message\":\"Conta e/ou senha inválida\"}", jsonResult, true);

    }

}
