package com.learning.cli.service.impl;

import com.learning.cli.service.TodoService;
import com.learning.cli.service.model.Status;
import com.learning.cli.service.model.Todo;
import org.mapdb.Atomic;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class TodoServiceImpl implements TodoService {
    public static final String TODO_MAPDB = "todo.mapdb";
    public static final String TODO = "todo";
    DB db = null;
    ConcurrentMap<Long, Todo> map = null;
    Comparator<Todo> todoComparator = (o1, o2) -> o1.getCreatedOn().compareTo(o2.getCreatedOn());

    private void start() {
        this.db = DBMaker.fileDB(TODO_MAPDB).make();
        this.map = db.hashMap(TODO, Serializer.LONG, Serializer.JAVA).createOrOpen();
    }

    private void shutdown() {
        this.db.close();
    }

    @Override
    public Todo createTodo(String message) {
        this.start();

        Todo todo = new Todo(message);
        todo.setId(getNewTaskId());
        map.put(todo.getId(), todo);
        this.shutdown();

        return todo;
    }

    @Override
    public Todo createTodo(String message, Date createdDate) {
        this.start();

        Todo todo = new Todo(message, createdDate);
        todo.setId(getNewTaskId());
        map.put(todo.getId(), todo);
        this.shutdown();

        return todo;
    }

    private Long getNewTaskId() {
        Atomic.Long id = db.atomicLong("id").createOrOpen();
        return id.addAndGet(1);
    }

    @Override
    public Todo updateMessage(Long taskId, String message) {
        Optional<Todo> byId = findById(taskId);

        if (byId.isPresent()) {
            Todo task = byId.get();
            task.setMessage(message);
            this.start();
            map.put(task.getId(), task);
            this.shutdown();
            return task;
        }

        return null;
    }

    @Override
    public Todo updateStatus(Long taskId, Status status) {
        Optional<Todo> byId = findById(taskId);

        if (byId.isPresent()) {
            Todo task = byId.get();
            task.setStatus(status);
            this.start();
            map.put(task.getId(), task);
            this.shutdown();
            return task;
        }

        return null;
    }

    @Override
    public boolean markCompletedById(Long taskId) {
        Optional<Todo> task = findById(taskId);
        if (task.isPresent()) {
            Todo updatedTodo = updateStatus(taskId, Status.COMPLETED);
            return Objects.nonNull(updatedTodo);
        }

        return false;
    }

    @Override
    public boolean deleteTodo(Todo todo) {
        if (Objects.nonNull(todo.getId())) {
            this.start();
            map.remove(todo.getId());
            this.shutdown();
        }
        return true;
    }

    @Override
    public boolean deleteById(Long taskId) {
        if (Objects.nonNull(taskId)) {
            this.start();
            map.remove(taskId);
            this.shutdown();
        }
        return true;
    }

    @Override
    public List<Todo> findAll() {
        this.start();
        List<Todo> collect = map.values().stream()
                .sorted(todoComparator)
                .collect(Collectors.toList());
        this.shutdown();
        return collect;
    }

    @Override
    public List<Todo> findByIds(List<Long> ids) {
        List<Todo> todoList = new ArrayList<>();

        this.start();
        ids.stream().map(map::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        this.shutdown();

        return todoList;
    }

    @Override
    public List<Todo> findByStatus(Status status) {
        List<Todo> todoList = new ArrayList<>();

        findAll().forEach(todo -> {
            if (todo.getStatus() == status) {
                todoList.add(todo);
            }
        });

        return todoList;
    }

    @Override
    public Optional<Todo> findById(Long taskId) {
        this.start();
        Todo todo = map.get(taskId);
        this.shutdown();
        return Optional.of(todo);
    }
}
