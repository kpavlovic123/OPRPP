package hr.fer.oprpp1.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.oprpp1.hw06.shell.Environment;
import hr.fer.oprpp1.hw06.shell.ShellCommand;
import hr.fer.oprpp1.hw06.shell.ShellStatus;

public class Charsets implements ShellCommand{

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        var map = Charset.availableCharsets();
        map.forEach((k,v)->{
            env.writeln(k);
        });
        return ShellStatus.CONTINUE;
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> desc = new LinkedList<>();
        desc.add("Show all available Charsets on this PC.");
        return Collections.unmodifiableList(desc);
    }

    @Override
    public String getCommandName() {
        // TODO Auto-generated method stub
        return "charsets";
    }
    
}
