package com.nickbarak.taskerapi.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.nickbarak.taskerapi.entity.User;
import com.nickbarak.taskerapi.exception.ResourceNotFoundException;
import com.nickbarak.taskerapi.model.AuthenticationRequest;
import com.nickbarak.taskerapi.model.UserRequest;
import com.nickbarak.taskerapi.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceTests {
        
	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

    private static User user = new User(1L, "user", "pass", "USER");

    @BeforeEach
    public void configure() {
        doReturn(true)
			.when(userRepository)
			.existsByUsername("user");

		doReturn(user)
			.when(userRepository)
			.findByUsername("user");

		doReturn(false)
			.when(userRepository)
			.existsByUsername("user2");
			
		doThrow(new RuntimeException("Error finding user with username=-1"))
			.when(userRepository)
			.findByUsername("user2");


        doReturn(true)
			.when(userRepository)
			.existsById(1L);

		doReturn(Optional.of(user))
			.when(userRepository)
			.findById(1L);

		doReturn(false)
			.when(userRepository)
			.existsById(-1L);
			
		doThrow(new RuntimeException("Error finding user with ID=-1"))
			.when(userRepository)
			.findById(-1L);
    }

    @Test
    public void loadUserByUsername() throws Exception {
        assertEquals(user, userService.loadUserByUsername("user"));
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("user2"));

        verify(userRepository, Mockito.times(2)).existsByUsername(anyString());
        verify(userRepository, Mockito.times(1)).findByUsername(anyString());
    }

    @Test
	public void saveOne() throws Exception {
		User user = new User(1L, "user", "pass", "USER");
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("user", "pass");
		when(userRepository.save(any(User.class)))
			.thenReturn(user);

		assertEquals(user, userService.saveOne(authenticationRequest));

		verify(userRepository).save(Mockito.any(User.class));
	}

    @Test
    public void updateOne() throws Exception {
        UserRequest userRequest = new UserRequest(1L, "user", "newpass", "USER");
        UserRequest userRequest2 = new UserRequest(2L, "invaliduser", "newpass", "USER");
        User updatedUser = new User(userRequest.getId(), userRequest.getUsername(), userRequest.getPassword(), userRequest.getRole());

        when(userService.loadUserByUsername("user"))
            .thenReturn(user);

        when(userRepository.save(any(User.class)))
            .thenReturn(updatedUser);

        doReturn(true)
            .when(userRepository)
            .existsByUsername(userRequest.getUsername());

        doThrow(new UsernameNotFoundException("User resource not found with username=invaliduser"))
            .when(userRepository)
            .existsByUsername(userRequest2.getUsername());
        
        assertEquals(updatedUser, userService.updateOne(userRequest));
        assertThrows(UsernameNotFoundException.class, () -> userService.updateOne(userRequest2));

        verify(userRepository, Mockito.times(4)).existsByUsername(anyString());
        verify(userRepository).save(Mockito.any(User.class));
    }

	@Test
	public void getAll() throws Exception {
		List<User> users = new LinkedList<>();
		users.add(new User(1L, "user", "pass", "USER"));
		users.add(new User(2L, "user2", "pass", "ADMIN"));
		users.add(new User(3L, "user3", "pass3", "USER"));
		when(userRepository.findAll())
			.thenReturn(users);

		assertEquals(users, userService.getAll());

		verify(userRepository).findAll();
	}

    @Test
    public void getOne() throws Exception {
        assertEquals(user, userService.getOne(1L).get());
        assertThrows(ResourceNotFoundException.class, () -> userService.getOne(-1L));

        verify(userRepository, Mockito.times(2)).existsById(anyLong());
        verify(userRepository, Mockito.times(1)).findById(anyLong());
    }

	@Test
	public void deleteOne() throws Exception {
		assertDoesNotThrow(() -> userService.deleteOne(1L));
		assertThrows(ResourceNotFoundException.class, () -> userService.deleteOne(-1L));	
		
		verify(userRepository, Mockito.times(2)).existsById(anyLong());
		verify(userRepository, Mockito.times(1)).deleteById(anyLong());
	}
}
