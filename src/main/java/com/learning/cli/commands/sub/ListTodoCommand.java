package com.learning.cli.commands.sub;

import picocli.CommandLine;

import java.util.Arrays;
import java.util.Date;
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

    @Override
    public Integer call() throws Exception {
        System.out.println("[list] Listing Tasks ");
        return 0;
    }
}
