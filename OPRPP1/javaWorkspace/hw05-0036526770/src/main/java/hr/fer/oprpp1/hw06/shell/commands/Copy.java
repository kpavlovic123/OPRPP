package hr.fer.oprpp1.hw06.shell.commands;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import hr.fer.oprpp1.hw06.shell.Environment;
import hr.fer.oprpp1.hw06.shell.ShellCommand;
import hr.fer.oprpp1.hw06.shell.ShellStatus;

public class Copy implements ShellCommand{

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = arguments.split(" ");
        try {
            Path f1 = Paths.get(args[0]);
            Path f2 = Paths.get(args[1]);
            if(!Files.isReadable(f1)){
                env.writeln("File not readable");
                return ShellStatus.CONTINUE;
            }
            InputStream in = new FileInputStream(args[0]);
            byte[] bytes = new byte[2048];
            if(Files.isDirectory(f2)){
                Path p = Paths.get(f2.toString(), f1.getFileName().toString());
                Files.createFile(p);
                OutputStream out = new FileOutputStream(p.toString());
                int n;
                while((n=in.read(bytes))!=-1){
                    out.write(bytes, 0, n);
                }
                out.close();
            }
            else{
                OutputStream out = new FileOutputStream(f2.toString());
                int n;
                while((n=in.read(bytes))!=-1){
                    out.write(bytes, 0, n);
                }
                out.close();
            }
            in.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return ShellStatus.CONTINUE;
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> desc = new LinkedList<>();
        desc.add("Description");
        return Collections.unmodifiableList(desc);
    }

    @Override
    public String getCommandName() {
        return "copy";
    }
    
}
