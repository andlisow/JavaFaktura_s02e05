package io.github.javafaktura.s02e05.demo5;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChildNameControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void should_get_200_as_unauthorized_on_main_page() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is(200));
    }

    @Test
    public void should_get_302_as_unauthorized_on_all_page() throws Exception {
        mockMvc.perform(get("/all"))
                .andExpect(status().is(302));
    }

    @WithMockUser(roles = "USER")
    @Test
    public void should_get_403_as_USER_on_all_page() throws Exception {
        mockMvc.perform(get("/all"))
                .andExpect(status().is(403));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void should_get_200_as_ADMIN_on_all_page() throws Exception {
        mockMvc.perform(get("/all"))
                .andExpect(status().is(200));
    }
}
