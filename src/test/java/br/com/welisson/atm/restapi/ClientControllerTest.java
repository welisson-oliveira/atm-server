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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringContextTestConfiguration.class, WebConfig.class, DatabaseConfig.class})
public class ClientControllerTest {

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
    public void createClient() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        final MvcResult result;

        result = mockMvc.perform(MockMvcRequestBuilders.post("/atm/client/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{\"name\":\"User Test\",\"login\":{" +
                        "\"account\":\"QWERT\",\"password\":\"QWERT\"},\"balance\":1000}"))
                .andExpect(status().isCreated())
                .andReturn();

        final String jsonResult = result.getResponse().getContentAsString();

        JSONAssert
                .assertEquals("{\"id\":3,\"name\":\"User Test\",\"login\":{\"id\":3,\"" +
                        "account\":\"QWERT\",\"password\":\"QWERT\"},\"balance\":1000}", jsonResult, true);
    }

    @Test
    public void updateClient()throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        final MvcResult result;

        result = mockMvc.perform(MockMvcRequestBuilders.put("/atm/client/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{\"id\":1,\"name\":\"Welisson\",\"login\":{\"id\":1,\"account\":\"123\",\"password\":\"123\"},\"balance\":1000}"))
                .andExpect(status().isOk())
                .andReturn();

        final String jsonResult = result.getResponse().getContentAsString();

        JSONAssert
                .assertEquals("{\"id\":1,\"name\":\"Welisson\",\"login\":{\"id\":1,\"account\":\"123\",\"password\":\"123\"},\"balance\":1000}", jsonResult, true);
    }

    @Test
    public void dropClient() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MvcResult result;
        result = mockMvc.perform(MockMvcRequestBuilders.delete("/atm/client/delete/1"))
                .andExpect(status().isNoContent())
                .andReturn();

        final int status = result.getResponse().getStatus();

        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), status);

        result = mockMvc.perform(MockMvcRequestBuilders.get("/atm/client/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        final String jsonResult = result.getResponse().getContentAsString();

        JSONAssert.assertEquals("[{\"id\":2,\"name\":\"User 2\",\"login\":{\"id\":2,\"account\":\"ABC\",\"password\":\"ABC\"},\"balance\":1000}]", jsonResult, true);
    }

    @Test
    public void listClient() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MvcResult result;
        result = mockMvc.perform(MockMvcRequestBuilders.get("/atm/client/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        final String jsonResult = result.getResponse().getContentAsString();

        JSONAssert.assertEquals("[{\"id\":1,\"name\":\"Welisson Oliveira\",\"login\":{\"id\":1,\"account\":\"123\",\"password\":\"123\"},\"balance\":1000},{\"id\":2,\"name\":\"User 2\",\"login\":{\"id\":2,\"account\":\"ABC\",\"password\":\"ABC\"},\"balance\":1000}]", jsonResult, true);
    }

}
