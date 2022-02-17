package com.nickbarak.taskerapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
import com.nickbarak.taskerapi.exception.ResourceNotFoundException;
import com.nickbarak.taskerapi.repository.TaskRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class TaskServiceTests {
    
	@Autowired
	private TaskService taskService;

	@MockBean
	private TaskRepository taskRepository;

	@Test
	public void saveOne() throws Exception {
		Task task = new Task(1L, "test", new Date(), false);
		when(taskRepository.save(task))
			.thenReturn(task);

		assertTrue(taskService.saveOne(task));

		verify(taskRepository).save(any(Task.class));
	}

	@Test
	public void getAll() throws Exception {
		List <Task> tasks = new LinkedList<>();
		tasks.add(new Task(1L, "test 1", new Date(), true));
		tasks.add(new Task(2L, "test 2", new Date(), true));
		tasks.add(new Task(3L, "test 3", new Date(), false));
		when(taskRepository.findAll())
			.thenReturn(tasks);

		assertEquals(tasks, taskService.getAll());

		verify(taskRepository).findAll();

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

		assertTrue(taskService.deleteOne(1L));
		assertThrows(ResourceNotFoundException.class, () -> taskService.deleteOne(-1L));	
		
		verify(taskRepository, times(2)).existsById(any(Long.class));
		verify(taskRepository, times(1)).deleteById(any(Long.class));
	}
}
