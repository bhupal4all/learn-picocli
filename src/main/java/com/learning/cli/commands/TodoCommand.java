package com.learning.cli.commands;

import com.learning.cli.commands.sub.AddTodoCommand;
import com.learning.cli.commands.sub.DeleteTodoCommand;
import com.learning.cli.commands.sub.ListTodoCommand;
import com.learning.cli.commands.sub.ModifyTodoCommand;
import picocli.CommandLine;

import java.util.concurrent.Callable;


@CommandLine.Command(name = "todo",
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "This is a todo Tool which will help us to manage todo activities",
        header = "Todo CLI",
        optionListHeading = "%nOptions are:%n",
        footerHeading = "%nCopyright",
        footer = "%nDeveloped by Ranga Bhupal",
        subcommandsRepeatable = true,
        commandListHeading = "%nSubCommands are: %n",
        subcommands = {
                AddTodoCommand.class,
                ListTodoCommand.class,
                ModifyTodoCommand.class,
                DeleteTodoCommand.class
        }
)
public class TodoCommand implements Callable<Integer> {
    final Integer SUCCESS = 0;
    final Integer FAILURE = 1;

    public static void main(String[] args) {
        int exitStatus = new CommandLine(new TodoCommand())
                .setCaseInsensitiveEnumValuesAllowed(true)
                .execute(args);
        System.exit(exitStatus);
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("[todo] Welcome to Todo");
        return SUCCESS;
    }

}
