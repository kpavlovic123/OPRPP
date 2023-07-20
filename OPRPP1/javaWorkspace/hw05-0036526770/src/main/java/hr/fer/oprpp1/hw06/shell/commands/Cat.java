package hr.fer.oprpp1.hw06.shell.commands;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.oprpp1.hw06.shell.Environment;
import hr.fer.oprpp1.hw06.shell.ShellCommand;
import hr.fer.oprpp1.hw06.shell.ShellStatus;

public class Cat implements ShellCommand{

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = arguments.split(" ");
        env.writeln(args[0]);
        Charset set;
        if(args.length == 1){
            set = Charset.defaultCharset();
        }
        else
            set = Charset.forName(args[1]);
        try (InputStream in = new FileInputStream(args[0])){
            byte[] bytes = in.readAllBytes();
            String content = new String(bytes,set);
            env.writeln(content);
        } catch (Exception e) {

        }
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "cat";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> desc = new LinkedList<>();
        desc.add("Use cat to show contents of a file.");
        return Collections.unmodifiableList(desc);
    }
    
}
