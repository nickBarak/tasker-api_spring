package com.nickbarak.taskerapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nickbarak.taskerapi.entity.User;
import com.nickbarak.taskerapi.model.AuthenticationRequest;
import com.nickbarak.taskerapi.service.UserService;
import com.nickbarak.taskerapi.util.JwtUtility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application-test.properties")
public class LoginControllerTests {
    
    @Autowired
    private LoginController loginController;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtility jwtUtility;

    @MockBean
    private AuthenticationManager authenticationManager;

    private MockMvc mockMvc;

    class AuthenticationStub implements Authentication {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
            return null;
            }

            @Override
            public Object getPrincipal() {
            return null;
            }

            @Override
            public Object getDetails() {
            return null;
            }

            @Override
            public String getName() {
            return null;
            }

            @Override
            public boolean isAuthenticated() {
            return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            }
    }

    @BeforeEach
    public void configure() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController)
        .build();
    }

    @Test
    public void register() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("newuser", "pass");
        AuthenticationRequest authenticationRequest2 = new AuthenticationRequest("user", "pass");
        String json = new ObjectMapper().writeValueAsString(authenticationRequest);
        String json2 = new ObjectMapper().writeValueAsString(authenticationRequest2);
        User user = new User(1L, "user", "pass", "USER");

        doThrow(new UsernameNotFoundException("User resource not found with username=newuser"))
            .when(userService)
            .loadUserByUsername("newuser");

        doReturn(user)
            .when(userService)
            .loadUserByUsername("user");

        when(userService.saveOne(any(AuthenticationRequest.class)))
            .thenReturn(user);

        when(jwtUtility.generateToken(any(User.class)))
            .thenReturn("jwt");

        mockMvc.perform(MockMvcRequestBuilders.post("/login/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(MockMvcResultMatchers.status().isCreated());
        
        mockMvc.perform(MockMvcRequestBuilders.post("/login/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json2))
            .andExpect(MockMvcResultMatchers.status().isConflict());

        verify(userService).saveOne(any(AuthenticationRequest.class));
    }

    @Test
    public void login() throws Exception {
        User user = new User(1L, "validuser", "pass", "USER");
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("validuser", "pass");
        AuthenticationRequest authenticationRequest2 = new AuthenticationRequest("invaliduser", "pass");
        String json = new ObjectMapper().writeValueAsString(authenticationRequest);
        String json2 = new ObjectMapper().writeValueAsString(authenticationRequest2);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        UsernamePasswordAuthenticationToken authToken2 = new UsernamePasswordAuthenticationToken(authenticationRequest2.getUsername(), authenticationRequest2.getPassword());

        doReturn(new AuthenticationStub())
            .when(authenticationManager)
            .authenticate(authToken);
        
        doThrow(new BadCredentialsException("Bad credentials for username=user2"))
            .when(authenticationManager)
            .authenticate(authToken2);
        
        when(userService.loadUserByUsername("validuser"))
            .thenReturn(user);

        doThrow(new UsernameNotFoundException("User resource not found with username=invaliduser"))
            .when(userService)
            .loadUserByUsername("invaliduser");

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json2))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        verify(userService).loadUserByUsername(anyString());
    }
}
