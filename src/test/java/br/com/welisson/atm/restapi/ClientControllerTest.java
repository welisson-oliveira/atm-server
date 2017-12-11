package br.com.welisson.atm.restapi;

import br.com.welisson.atm.AbstractTests;
import br.com.welisson.atm.config.DatabaseConfigTest;
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
import org.springframework.web.util.NestedServletException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringContextTestConfiguration.class, WebConfig.class, DatabaseConfigTest.class})
public class ClientControllerTest extends AbstractTests {

    @Test
    public void createClient() throws Exception {

        final String content = "{\"name\":\"User Test\",\"login\":{\"" +
                "account\":\"QWERT\",\"password\":\"QWERT\"},\"balance\":1000}";

        final String jsonResult = post("/atm/client", content, status().isCreated());

        JSONAssert
                .assertEquals("{\"id\":3,\"name\":\"User Test\",\"login\":{\"id\":3,\"" +
                        "account\":\"QWERT\",\"password\":\"QWERT\"},\"balance\":1000}", jsonResult, true);
    }

    @Test
    public void createClientWithExistingAccount() throws Exception {

        final String content = "{\"name\":\"User Test\",\"login\":{\"" +
                "account\":\"123\",\"password\":\"QWERT\"},\"balance\":1000}";

        try {
            final String jsonResult = post("/atm/client", content, status().isCreated());
            Assert.assertTrue(false);
        } catch (NestedServletException e) {
            Assert.assertEquals(e.getCause().getMessage(), "Account already exists!");
        }
    }

    @Test
    public void updateClientWithExistingAccount() throws Exception {

        final String content = "{\"id\":1,\"name\":\"Welisson\",\"login\":{\"id\":1,\"account\":\"ABC\",\"password\":\"123\"},\"balance\":1000}";

        try {
            final String jsonResult = put("/atm/client/1", content, status().isOk());
            Assert.assertTrue(false);
        } catch (NestedServletException e) {
            Assert.assertEquals(e.getCause().getMessage(), "Account already exists");
        }
    }

    @Test
    public void updateClient() throws Exception {

        final String content = "{\"id\":1,\"name\":\"Welisson\",\"login\":{\"id\":1,\"account\":\"123\",\"password\":\"123\"},\"balance\":1000}";

        final String jsonResult = put("/atm/client/1", content, status().isOk());

        JSONAssert
                .assertEquals("{\"id\":1,\"name\":\"Welisson\",\"login\":{\"id\":1,\"account\":\"123\",\"password\":\"123\"},\"balance\":1000}", jsonResult, true);
    }

    @Test
    public void updateBalance() throws Exception {

        final String content = "{\"id\":1,\"name\":\"Welisson\",\"login\":{\"id\":1,\"account\":\"123\",\"password\":\"123\"},\"balance\":10000}";
        try {
            final String jsonResult = put("/atm/client/1", content, status().isInternalServerError());
            Assert.assertTrue(false);
        } catch (NestedServletException e) {
            Assert.assertEquals(e.getCause().getMessage(), "Balance can not be changed");
        }

    }

    @Test
    public void dropClient() throws Exception {
        final HttpStatus status = delete("/atm/client/1", status().isNoContent());

        Assert.assertEquals(HttpStatus.NO_CONTENT, status);

        final String jsonResult = get("/atm/client", status().isOk());

        JSONAssert.assertEquals("[{\"id\":2,\"name\":\"User 2\",\"login\":{\"id\":2,\"account\":\"ABC\",\"password\":\"ABC\"},\"balance\":1000}]", jsonResult, true);
    }

    @Test
    public void listClient() throws Exception {

        final String jsonResult = get("/atm/client", status().isOk());

        JSONAssert.assertEquals("[{\"id\":1,\"name\":\"Welisson Oliveira\",\"login\":{\"id\":1,\"account\":\"123\",\"password\":\"123\"},\"balance\":1000},{\"id\":2,\"name\":\"User 2\",\"login\":{\"id\":2,\"account\":\"ABC\",\"password\":\"ABC\"},\"balance\":1000}]", jsonResult, true);
    }

}
