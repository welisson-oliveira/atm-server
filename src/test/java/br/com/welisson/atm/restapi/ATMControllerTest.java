package br.com.welisson.atm.restapi;

import br.com.welisson.atm.config.DatabaseConfig;
import br.com.welisson.atm.config.SpringContextTestConfiguration;
import br.com.welisson.atm.config.SqlExecuter;
import br.com.welisson.atm.config.WebConfig;
import org.junit.Assert;
import org.junit.Before;
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
import org.springframework.web.util.NestedServletException;

import java.io.File;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringContextTestConfiguration.class, WebConfig.class, DatabaseConfig.class})
public class ATMControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    //TODO - abstrair
    @Before
    public void config(){
        SqlExecuter executer = new SqlExecuter();
        File file = new File("./src/test/resources/dropTable.sql");
        executer.setSrc(file);
        file.isFile();
        executer.setDriver("org.h2.Driver");
        executer.setPassword("");
        executer.setUserid("sa");
        executer.setUrl("jdbc:h2:file:~/h2/testdb");
        executer.execute();

        executer = new SqlExecuter();
        executer.setSrc(new File("./src/test/resources/createTable.sql"));
        executer.setDriver("org.h2.Driver");
        executer.setPassword("");
        executer.setUserid("sa");
        executer.setUrl("jdbc:h2:file:~/h2/testdb");
        executer.execute();

        executer = new SqlExecuter();
        executer.setSrc(new File("./src/main/resources/data.sql"));
        executer.setDriver("org.h2.Driver");
        executer.setPassword("");
        executer.setUserid("sa");
        executer.setUrl("jdbc:h2:file:~/h2/testdb");
        executer.execute();
    }

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

        try {
            mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
            mockMvc.perform(MockMvcRequestBuilders.post("/atm/login")
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content("{\"account\":124, \"password\":123}"))
                    .andReturn();
            Assert.assertTrue(false);
        }catch (NestedServletException e){
            Assert.assertEquals(e.getCause().getMessage(), "Conta e/ou senha inválida");
        }

    }

    @Test
    public void withdraw() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        final MvcResult result;

        result = mockMvc.perform(MockMvcRequestBuilders.post("/atm/withdraw")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{\"idClient\":\"1\", \"value\":\"180\"}"))
                .andReturn();

        final String jsonResult = result.getResponse().getContentAsString();
        JSONAssert.assertEquals("{\"hundred\":1,\"fifty\":1,\"twenty\":1,\"ten\":1}", jsonResult, true);

    }

    @Test
    public void withdrawAll() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        final MvcResult result;

        result = mockMvc.perform(MockMvcRequestBuilders.post("/atm/withdraw")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{\"idClient\":\"1\", \"value\":\"1000\"}"))
                .andReturn();

        final String jsonResult = result.getResponse().getContentAsString();
        JSONAssert.assertEquals("{\"hundred\":10,\"fifty\":0,\"twenty\":0,\"ten\":0}", jsonResult, true);
    }

    @Test
    public void insufficientBalance() throws Exception {
        try {
            mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
            mockMvc.perform(MockMvcRequestBuilders.post("/atm/withdraw")
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content("{\"idClient\":\"1\", \"value\":\"1010\"}"))
                    .andReturn();
            Assert.assertTrue(false);
        }catch (NestedServletException e){
            Assert.assertEquals(e.getCause().getMessage(), "Saldo insuficiente");
        }
    }

    @Test
    public void wrongValueForWithdraw() throws Exception {
        try {
            mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
            mockMvc.perform(MockMvcRequestBuilders.post("/atm/withdraw")
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content("{\"idClient\":\"1\", \"value\":\"13\"}"))
                    .andReturn();
            Assert.assertTrue(false);
        }catch (NestedServletException e){
            Assert.assertEquals(e.getCause().getMessage(), "Informe um valor multiplo de 10");
        }
    }

    @Test
    public void negativeValueForWithdraw() throws Exception {
        try {
            mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
            mockMvc.perform(MockMvcRequestBuilders.post("/atm/withdraw")
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content("{\"idClient\":\"1\", \"value\":\"-1000\"}"))
                    .andReturn();
            Assert.assertTrue(false);
        }catch (NestedServletException e){
            Assert.assertEquals(e.getCause().getMessage(), "Impossivel sacar valor negativo");
        }
    }

    @Test
    public void negativeValueForWithdrawNotMultipleOfTen() throws Exception {
        try {
            mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
            mockMvc.perform(MockMvcRequestBuilders.post("/atm/withdraw")
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content("{\"idClient\":\"1\", \"value\":\"-13\"}"))
                    .andReturn();
            Assert.assertTrue(false);
        }catch (NestedServletException e){
            Assert.assertEquals(e.getCause().getMessage(), "Impossivel sacar valor negativo");
        }
    }

}
