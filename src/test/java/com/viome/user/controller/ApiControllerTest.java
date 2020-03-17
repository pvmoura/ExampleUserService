package com.viome.user.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.user.entity.User;
import com.example.user.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void givenRequestOnSecuredApi_shouldFailWith401() throws Exception {
        mvc.perform(get("/participants").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser("${participant_api_user_name}")
    public void givenAuthRequestOnSecuredApi_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/participants").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("${participant_api_user_name}")
    public void givenStudyConsentParticipants_whenGetAllConsentParticipants_thenReturnJsonArray() throws Exception {
        List<User> allUsers = getTestUsers();
        given(userService.getAllUser()).willReturn(allUsers);
        mvc.perform(get("/participants")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(allUsers.size())))
                .andExpect(jsonPath("$[0].participant", is(allUsers.get(0).getId().intValue())));
    }

    private List<User> getTestUsers() {
    	User u1 = new User();
    	u1.setId(Long.valueOf(1));
        return Arrays.asList(u1);
    }
}