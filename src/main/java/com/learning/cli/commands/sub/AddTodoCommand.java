package com.learning.cli.commands.sub;

import com.learning.cli.service.TodoFactory;
import com.learning.cli.service.TodoService;
import com.learning.cli.service.model.Todo;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "add",
        aliases = {"create", "plus"},
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "This is a Sub Command to 'todo' and adds the task to the list",
        header = "Add Todo SubCommand",
        optionListHeading = "%nOptions are:%n",
        footerHeading = "%nCopyright",
        footer = "%nDeveloped by Ranga Bhupal")
public class AddTodoCommand implements Callable<Integer> {

    @CommandLine.Option(
            names = {"-m", "--message"},
            required = true,
            description = "a Todo Note or a Message")
    String[] message;

    @CommandLine.Option(
            names = {"--create-date"},
            required = false,
            description = "Created date for the Todo[s]"
    )
    Date createdDate;

    TodoService todoService = null;

    public AddTodoCommand() {
        this.todoService = TodoFactory.getService();
    }

    @Override
    public Integer call() throws Exception {

        if (Objects.isNull(createdDate)) {
            Arrays.asList(message).stream()
                    .forEach(todoMessage -> {
                        Todo todo = this.todoService.createTodo(todoMessage);
                        System.out.println("New Task ID is " + todo.getId());
                    });
        } else {
            Arrays.asList(message).stream()
                    .forEach(todoMessage -> {
                        Todo todo = this.todoService.createTodo(todoMessage, createdDate);
                        System.out.println("New Task ID is " + todo.getId());
                    });
        }

        return 0;
    }
}
