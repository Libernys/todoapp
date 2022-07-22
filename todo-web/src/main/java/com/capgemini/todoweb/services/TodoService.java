package com.capgemini.todoweb.services;

import com.capgemini.todoweb.data.TodoRepository;
import com.capgemini.todoweb.models.TodoModel;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    final TodoRepository todoModelRepository;

    public TodoService(TodoRepository todoModelRepository) {
        this.todoModelRepository = todoModelRepository;
    }

    public List<TodoModel> getAllTodos(String title) {
        List<TodoModel> todos = new ArrayList<>();
        if (title == null || title.isBlank())
            todoModelRepository.findAll(Sort.by(Sort.Direction.ASC, "done")
                    .and(Sort.by(Sort.Direction.ASC, "doneAt"))
                    .and(Sort.by(Sort.Direction.DESC, "updatedAt"))).forEach(todos::add);
        else
            todoModelRepository.findByTitleContainsIgnoreCaseOrderByDoneAscDoneAtAscUpdatedAtDesc(title).forEach(todos::add);

        return todos;
    }

    public Optional<TodoModel> findById(long id) {
        return todoModelRepository.findById(id);
    }

    public TodoModel create(TodoModel todoModel) {
        TodoModel dataTodo = new TodoModel(todoModel.getTitle(), todoModel.getDescription(), false);
        return todoModelRepository.save(dataTodo);
    }

    public TodoModel update(long id, TodoModel todoModel) {
        Optional<TodoModel> todoData = todoModelRepository.findById(id);
        if (todoData.isPresent()) {
            TodoModel todoToUpdate = todoData.get();
            todoToUpdate.setTitle(todoModel.getTitle());
            todoToUpdate.setDescription(todoModel.getDescription());
            if (!todoToUpdate.isDone() && todoModel.isDone()) {
                todoToUpdate.setDoneAt(new Date());
            } else {
                todoToUpdate.setDoneAt(null);
            }
            todoToUpdate.setUpdatedAt(new Date());
            todoToUpdate.setDone(todoModel.isDone());
            return todoModelRepository.save(todoToUpdate);
        }
        return null;
    }

    public void deleteById(long id) {
        todoModelRepository.deleteById(id);
    }

    public void deleteAll() {
        todoModelRepository.deleteAll();
    }

    public void deleteByTitle(String title) {
        if (title == null) {
            todoModelRepository.deleteAll();
        } else {
            todoModelRepository.deleteByTitleContainsIgnoreCase(title);
        }
    }

    public void deleteCompleted(String title) {
        if (title == null) {
            todoModelRepository.deleteByDoneTrue();
        } else {
            todoModelRepository.deleteByDoneTrueAndTitleContainsIgnoreCase(title);
        }
    }
}
