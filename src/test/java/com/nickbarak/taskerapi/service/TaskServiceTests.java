package com.nickbarak.taskerapi.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.nickbarak.taskerapi.entity.Task;
import com.nickbarak.taskerapi.entity.User;
import com.nickbarak.taskerapi.exception.ResourceNotFoundException;
import com.nickbarak.taskerapi.repository.TaskRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application-test.properties")
public class TaskServiceTests {
    
	@Autowired
	private TaskService taskService;

	@MockBean
	private TaskRepository taskRepository;

	@MockBean
	private UserService userService;

	@Test
	public void saveOne() throws Exception {
		Task task = new Task("test", new Date(), false, new User());
		when(taskRepository.save(task))
			.thenReturn(task);

		assertEquals(task, taskService.saveOne(task));

		verify(taskRepository).save(any(Task.class));
	}

	@Test
	public void getAllByUser() throws Exception {
		List <Task> tasks = new LinkedList<>();
		User user = new User();
		tasks.add(new Task("test 1", new Date(), true, user));
		tasks.add(new Task("test 2", new Date(), true, user));
		tasks.add(new Task("test 3", new Date(), false, user));
		when(taskRepository.findAllByAuthor(any(User.class)))
			.thenReturn(tasks);

		when(userService.loadUserByUsername(anyString()))
			.thenReturn(user);

		assertEquals(tasks, taskService.getAllByUser(anyString()));

		verify(taskRepository).findAllByAuthor(any(User.class));

	}

	@Test
	public void deleteOne() throws Exception {
		doReturn(true)
			.when(taskRepository)
			.existsById(1L);

		doNothing()
			.when(taskRepository)
			.deleteById(1L);

		doReturn(false)
			.when(taskRepository)
			.existsById(-1L);
			
		doThrow(new RuntimeException("Error deleting task with ID=-1"))
			.when(taskRepository)
			.deleteById(-1L);

		assertDoesNotThrow(() -> taskService.deleteOne(1L));
		assertThrows(ResourceNotFoundException.class, () -> taskService.deleteOne(-1L));	
		
		verify(taskRepository, times(2)).existsById(any(Long.class));
		verify(taskRepository, times(1)).deleteById(any(Long.class));
	}
}
