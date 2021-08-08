package com.learning.cli.commands.sub;

import com.learning.cli.service.TodoFactory;
import com.learning.cli.service.TodoService;
import picocli.CommandLine;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "delete",
        aliases = {"remove", "minus"},
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "This is a Sub Command to 'todo' and deletes the task",
        header = "Delete Todo SubCommand",
        optionListHeading = "%nOptions are:%n",
        footerHeading = "%nCopyright",
        footer = "%nDeveloped by Ranga Bhupal")
public class DeleteTodoCommand implements Callable<Integer> {

    @CommandLine.Option(
            names = {"--id"},
            description = "Provide the task ids which needs to be deleted",
            required = true
    )
    List<Long> id;

    TodoService todoService = null;

    public DeleteTodoCommand() {
        this.todoService = TodoFactory.getService();
    }

    @Override
    public Integer call() throws Exception {

        if (Objects.nonNull(id) && id.size() > 0) {
            id.stream().forEach(_id -> todoService.deleteById(_id));
        }

        System.out.println("Request has been submitted!!!");

        return 0;
    }
}
