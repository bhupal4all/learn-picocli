package com.learning.cli.service;

import com.learning.cli.service.model.Status;
import com.learning.cli.service.model.Todo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TodoService {

    Todo createTodo(String message);

    Todo createTodo(String todoMessage, Date createdDate);

    Todo updateMessage(Long taskId, String message);

    Todo updateStatus(Long taskId, Status status);

    boolean markCompletedById(Long id);

    boolean deleteTodo(Todo todo);

    boolean deleteById(Long id);

    List<Todo> findAll();

    List<Todo> findByIds(List<Long> ids);

    List<Todo> findByStatus(Status status);

    Optional<Todo> findById(Long id);

    List<Todo> findByStatus(List<Status> statuses);
}
