package br.com.welisson.atm;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;

public class AbstractTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Value("${spring.datasource.driverClassName}")
    private String driver;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.url.test}")
    private String urlBD;

    @Before
    public void config(){
        sqlExecuter("./src/test/resources/before.sql");
    }


    private void sqlExecuter(final String pathName){
        final SqlExecuter executer = new SqlExecuter();
        executer.setSrc(new File(pathName));
        executer.setDriver(driver);
        executer.setPassword(password);
        executer.setUserid(username);
        executer.setUrl(urlBD);
        executer.execute();
    }

    protected String post(final String url, final String content, final ResultMatcher status) throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        final MvcResult result;

        result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(content))
                .andExpect(status)
                .andReturn();

        return result.getResponse().getContentAsString();

    }

    protected String get(final String url, final ResultMatcher status) throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        final MvcResult result;

        result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status)
                .andReturn();

        return result.getResponse().getContentAsString();

    }

    protected String put(final String url, final String content, final ResultMatcher status) throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        final MvcResult result;

        result = mockMvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(content))
                .andExpect(status)
                .andReturn();

        return result.getResponse().getContentAsString();

    }

    protected HttpStatus delete(final String url, final ResultMatcher status) throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        final MvcResult result;

        result = mockMvc.perform(MockMvcRequestBuilders.delete(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status)
                .andReturn();

        return HttpStatus.valueOf(result.getResponse().getStatus());

    }

}

