package hr.fer.oprpp1.hw06.shell.commands;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import hr.fer.oprpp1.hw06.shell.Environment;
import hr.fer.oprpp1.hw06.shell.ShellCommand;
import hr.fer.oprpp1.hw06.shell.ShellStatus;

public class Help implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = arguments.split(" ");
        var commands = env.commands();
        if (args[0].equals("")) {
            commands.forEach((k,v)->{
                env.writeln(v.getCommandName());
            });
        } else {
            if (!commands.containsKey(args[0])) {
                env.writeln("No such command.");
                return ShellStatus.CONTINUE;
            }
            List<String> desc = commands.get(args[0]).getCommandDescription();
            desc.forEach((v)->{
                env.writeln(v);
            });
        }
        return null;
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> desc = new LinkedList<>();
        desc.add("Description");
        return Collections.unmodifiableList(desc);
    }

    @Override
    public String getCommandName() {
        return "help";
    }

}
