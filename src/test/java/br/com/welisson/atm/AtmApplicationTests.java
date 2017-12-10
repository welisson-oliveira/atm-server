package br.com.welisson.atm;

import br.com.welisson.atm.config.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringContextTestConfiguration.class, WebConfig.class})
public class AtmApplicationTests {

	@Test
	public void contextLoads() {
	}

}
