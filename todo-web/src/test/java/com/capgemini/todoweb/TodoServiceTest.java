package com.capgemini.todoweb;

import com.capgemini.todoweb.data.TodoRepository;
import com.capgemini.todoweb.services.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    private TodoRepository userRepository;

    private TodoService todoService;

    @BeforeEach
    void initUseCase() {
        todoService = new TodoService(userRepository);
    }

    @Test
    void getAllTodosWithNullTitleDoesNotFilterByTitle(){
        todoService.getAllTodos(null);
        verify(userRepository, times(1)).findAll(Mockito.any(Sort.class));
    }

    @Test
    void getAllTodosWithEmptyTitleDoesNotFilterByTitle(){
        todoService.getAllTodos("");
        verify(userRepository, times(1)).findAll(Mockito.any(Sort.class));
    }
}
