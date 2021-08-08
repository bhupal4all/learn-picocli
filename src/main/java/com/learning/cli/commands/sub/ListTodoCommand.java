package com.learning.cli.commands.sub;

import com.learning.cli.service.TodoFactory;
import com.learning.cli.service.TodoService;
import com.learning.cli.service.model.Status;
import com.learning.cli.service.model.Todo;
import org.jetbrains.annotations.NotNull;
import picocli.CommandLine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "list",
        aliases = {"ls", "show"},
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "This a Sub Command and lists all the tasks as per the parameters",
        header = "List Todo SubCommand",
        optionListHeading = "%nOptions are:%n",
        footerHeading = "%nCopyright",
        footer = "%nDeveloped by Ranga Bhupal")
public class ListTodoCommand implements Callable<Integer> {

    @CommandLine.Option(
            names = {"-f", "--format"},
            description = "Formatting the Todo and the default value is ${DEFAULT-VALUE} %nAll Formats are ${COMPLETION-CANDIDATES}",
            defaultValue = "DEFAULT",
            required = false
    )
    ListFormat format;

    @CommandLine.Option(
            names = {"-s", "--status"},
            description = "Lists the todo by Status %nAvailable Statuses are ${COMPLETION-CANDIDATES}",
            required = false
    )
    Status status;

    @CommandLine.Option(
            names = {"--short", "--compact"},
            description = "Lists the todo in SHORT format",
            required = false
    )
    boolean compact;

    @CommandLine.Option(
            names = {"--completed", "--done"},
            description = "Lists the todo which are either completed or not completed",
            negatable = true,
            required = false
    )
    Boolean completed = null;

    @CommandLine.Option(
            names = {"--id"},
            description = "Shows the todos for the given ID",
            required = false,
            split = ","
    )
    Long[] id;

    TodoService todoService = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

    public ListTodoCommand() {
        this.todoService = TodoFactory.getService();
    }

    @Override
    public Integer call() throws Exception {
        if (compact) {
            format = ListFormat.SHORT;
        }

        List<Todo> todoList = new ArrayList<>();

        if (Objects.nonNull(id)) {
            todoList = todoService.findByIds(Arrays.asList(id));
        } else if (Objects.isNull(completed)) {
            if (Objects.isNull(status)) {
                todoList = this.todoService.findAll();
            } else {
                todoList = this.todoService.findByStatus(status);
            }
        } else {
            List<Status> statuses = new ArrayList<>();

            if (completed) {
                statuses.add(Status.COMPLETED);
            } else {
                statuses.add(Status.CREATED);
                statuses.add(Status.IN_PROGRESS);
            }

            todoList = this.todoService.findByStatus(statuses);
        }

        if (Objects.nonNull(todoList) && todoList.size() > 0) {
            todoList.stream().forEach(todo -> printTodo(format, todo));
        } else {
            System.out.println("No Tasks to display !!!");
        }

        return 0;
    }

    private void printTodo(ListFormat format, Todo todo) {
        if (format == ListFormat.SHORT) {
            System.out.println(String.format("%4d %3s %10s %s",
                    todo.getId(),
                    getStatus(todo),
                    this.dateFormat.format(todo.getCreatedOn()),
                    todo.getMessage()
            ));
        } else {
            System.out.println("ID      = " + todo.getId());
            System.out.println("Message = " + todo.getMessage());
            System.out.println("Status  = " + todo.getStatus());
            System.out.println("Created = " + todo.getCreatedOn());
        }
    }

    @NotNull
    private String getStatus(Todo todo) {
        if (todo.getStatus() == Status.COMPLETED) return "[x]";
        if (todo.getStatus() == Status.IN_PROGRESS) return "[/]";
        return "[ ]";
    }

}
