package com.learning.cli.commands.sub;

import com.learning.cli.service.TodoFactory;
import com.learning.cli.service.TodoService;
import com.learning.cli.service.model.Todo;
import picocli.CommandLine;

import java.util.Objects;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "modify",
        aliases = {"change", "alter"},
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "This is a Sub Command to 'todo' and modifies the task message",
        header = "Modify Todo SubCommand",
        optionListHeading = "%nOptions are:%n",
        footerHeading = "%nCopyright",
        footer = "%nDeveloped by Ranga Bhupal")
public class ModifyTodoCommand implements Callable<Integer> {

    @CommandLine.Option(
            names = {"--id"},
            description = "Provide the task id which needs to be updated",
            required = true
    )
    Long id;

    @CommandLine.Option(
            names = {"--message"},
            description = "Provide the message which needs to be updated with",
            required = true
    )
    String message;

    TodoService todoService = null;

    public ModifyTodoCommand() {
        this.todoService = TodoFactory.getService();
    }

    @Override
    public Integer call() throws Exception {

        Todo todo = todoService.updateMessage(id, message);
        if (Objects.nonNull(todo)) {
            System.out.println("Updated Successfully");
        } else {
            System.out.println("Task Not Found !!!");
        }

        return 0;
    }
}
