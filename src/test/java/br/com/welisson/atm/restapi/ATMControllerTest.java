package br.com.welisson.atm.restapi;

import br.com.welisson.atm.AbstractTests;
import br.com.welisson.atm.config.DatabaseConfig;
import br.com.welisson.atm.config.SpringContextTestConfiguration;
import br.com.welisson.atm.config.WebConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.util.NestedServletException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringContextTestConfiguration.class, WebConfig.class, DatabaseConfig.class})
public class ATMControllerTest extends AbstractTests {

    @Test
    public void loginSuccess() throws Exception {

        final String content = "{\"account\":\"123\", \"password\":\"123\"}";

        final String jsonResult = post("/atm/login", content, status().isOk());

        JSONAssert
                .assertEquals("{\"id\":1,\"name\":\"Welisson Oliveira\",\"login\":{\"id\":1,\"" +
                        "account\":\"123\",\"password\":\"123\"},\"balance\":1000}", jsonResult, true);

    }

    @Test
    public void loginFail() throws Exception {

        try {
            post("/atm/login", "{\"account\":124, \"password\":123}", status().isInternalServerError());

            Assert.assertTrue(false);
        } catch (NestedServletException e) {
            Assert.assertEquals(e.getCause().getMessage(), "Conta e/ou senha inválida");
        }

    }

    @Test
    public void withdraw() throws Exception {

        final String jsonResult = post("/atm/withdraw", "{\"idClient\":\"1\", \"value\":\"180\"}", status().isOk());
        JSONAssert.assertEquals("{\"hundred\":1,\"fifty\":1,\"twenty\":1,\"ten\":1}", jsonResult, true);

    }

    @Test
    public void withdrawAll() throws Exception {

        final String jsonResult = post("/atm/withdraw", "{\"idClient\":\"1\", \"value\":\"1000\"}", status().isOk());
        JSONAssert.assertEquals("{\"hundred\":10,\"fifty\":0,\"twenty\":0,\"ten\":0}", jsonResult, true);
    }

    @Test
    public void insufficientBalance() throws Exception {
        try {

            post("/atm/withdraw", "{\"idClient\":\"1\", \"value\":\"1010\"}", status().isInternalServerError());

            Assert.assertTrue(false);
        } catch (NestedServletException e) {
            Assert.assertEquals(e.getCause().getMessage(), "Saldo insuficiente");
        }
    }

    @Test
    public void wrongValueForWithdraw() throws Exception {
        try {

            post("/atm/withdraw", "{\"idClient\":\"1\", \"value\":\"13\"}", status().isInternalServerError());

            Assert.assertTrue(false);
        } catch (NestedServletException e) {
            Assert.assertEquals(e.getCause().getMessage(), "Informe um valor multiplo de 10");
        }
    }

    @Test
    public void negativeValueForWithdraw() throws Exception {
        try {
            post("/atm/withdraw", "{\"idClient\":\"1\", \"value\":\"-1000\"}", status().isInternalServerError());

            Assert.assertTrue(false);
        } catch (NestedServletException e) {
            Assert.assertEquals(e.getCause().getMessage(), "Impossivel sacar valor negativo");
        }
    }

    @Test
    public void negativeAndNotMultipleOfTenValueForWithdraw() throws Exception {
        try {

            post("/atm/withdraw", "{\"idClient\":\"1\", \"value\":\"-13\"}", status().isInternalServerError());

            Assert.assertTrue(false);
        } catch (NestedServletException e) {
            Assert.assertEquals(e.getCause().getMessage(), "Impossivel sacar valor negativo");
        }
    }

}
