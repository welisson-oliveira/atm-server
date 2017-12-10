package br.com.welisson.atm.restapi;

import br.com.welisson.atm.AbstractTests;
import br.com.welisson.atm.config.DatabaseConfig;
import br.com.welisson.atm.config.SpringContextTestConfiguration;
import br.com.welisson.atm.config.WebConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringContextTestConfiguration.class, WebConfig.class, DatabaseConfig.class})
public class ClientControllerTest extends AbstractTests {

    @Test
    public void createClient() throws Exception{

        final String content = "{\"name\":\"User Test\",\"login\":{\"" +
                "account\":\"QWERT\",\"password\":\"QWERT\"},\"balance\":1000}";

        final String jsonResult = post("/atm/client/create", content, status().isCreated());

        JSONAssert
                .assertEquals("{\"id\":3,\"name\":\"User Test\",\"login\":{\"id\":3,\"" +
                        "account\":\"QWERT\",\"password\":\"QWERT\"},\"balance\":1000}", jsonResult, true);
    }

    @Test
    public void updateClient()throws Exception{

        final String content = "{\"id\":1,\"name\":\"Welisson\",\"login\":{\"id\":1,\"account\":\"123\",\"password\":\"123\"},\"balance\":1000}";

        final String jsonResult = put("/atm/client/update", content, status().isOk());

        JSONAssert
                .assertEquals("{\"id\":1,\"name\":\"Welisson\",\"login\":{\"id\":1,\"account\":\"123\",\"password\":\"123\"},\"balance\":1000}", jsonResult, true);
    }

    @Test
    public void dropClient() throws Exception{
        final HttpStatus status = delete("/atm/client/delete/1", status().isNoContent());

        Assert.assertEquals(HttpStatus.NO_CONTENT, status);

        final String jsonResult = get("/atm/client/list", status().isOk());

        JSONAssert.assertEquals("[{\"id\":2,\"name\":\"User 2\",\"login\":{\"id\":2,\"account\":\"ABC\",\"password\":\"ABC\"},\"balance\":1000}]", jsonResult, true);
    }

    @Test
    public void listClient() throws Exception{

        final String jsonResult = get("/atm/client/list", status().isOk());

        JSONAssert.assertEquals("[{\"id\":1,\"name\":\"Welisson Oliveira\",\"login\":{\"id\":1,\"account\":\"123\",\"password\":\"123\"},\"balance\":1000},{\"id\":2,\"name\":\"User 2\",\"login\":{\"id\":2,\"account\":\"ABC\",\"password\":\"ABC\"},\"balance\":1000}]", jsonResult, true);
    }

}
