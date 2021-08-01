package com.learning.cli.service;

import com.learning.cli.service.model.Status;
import com.learning.cli.service.model.Todo;

public class DatabaseApplication {
    public static void main(String[] args) {
        TodoService service = TodoFactory.getService();

        boolean clearDatabase = true;
        if (clearDatabase) {
            System.err.println("Clearing Database");
            service.findAll().forEach(service::deleteTodo);
        }

        Todo helloCommandTask = service.createTodo("Create Hello Command");
        Todo todoCommandTask = service.createTodo("Create Todo Command");
        Todo addCommandTask = service.createTodo("Create Add Command");
        Todo listCommandTask = service.createTodo("Create List Command");
        Todo serviceTask = service.createTodo("Create Service for Storing data");

        System.out.println("Printing All Tasks");
        service.findAll().forEach(System.out::println);

        service.updateStatus(helloCommandTask.getId(), Status.COMPLETED);
        service.updateStatus(todoCommandTask.getId(), Status.IN_PROGRESS);
        service.updateStatus(addCommandTask.getId(), Status.IN_PROGRESS);
        service.updateStatus(listCommandTask.getId(), Status.IN_PROGRESS);
        service.updateStatus(serviceTask.getId(), Status.COMPLETED);

        System.out.println("Printing All In Progress Tasks");
        service.findByStatus(Status.IN_PROGRESS).forEach(System.out::println);

        System.out.println("\n\n-- Dummy Task --");
        Todo dummyTask = service.createTodo("Dummy Task to be deleted");
        System.out.println("dummyTask = " + dummyTask);
        System.out.println("service.findById(dummyTask.getId()) = " + service.findById(dummyTask.getId()).get().getMessage());
        System.out.println("service.updateMessage(dummyTask.getId(), \"Updated Dummy Task Message\") = " + service.updateMessage(dummyTask.getId(), "Updated Dummy Task Message").getMessage());
        System.out.println("service.updateStatus(dummyTask.getId(), Status.IN_PROGRESS) = " + service.updateStatus(dummyTask.getId(), Status.IN_PROGRESS).getStatus());
        System.out.println("service.markCompletedById(dummyTask.getId()) = " + service.markCompletedById(dummyTask.getId()));
        System.out.println("service.findById(dummyTask.getId()) = " + service.findById(dummyTask.getId()).get().getStatus());
        System.out.println("service.deleteById(dummyTask.getId()) = " + service.deleteById(dummyTask.getId()));
    }
}
