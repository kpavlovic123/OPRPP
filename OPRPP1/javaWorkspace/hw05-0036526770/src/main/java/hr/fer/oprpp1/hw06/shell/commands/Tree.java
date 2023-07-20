package hr.fer.oprpp1.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import hr.fer.oprpp1.hw06.shell.Environment;
import hr.fer.oprpp1.hw06.shell.ShellCommand;
import hr.fer.oprpp1.hw06.shell.ShellStatus;

public class Tree implements ShellCommand {

    class MyFileVisitor implements FileVisitor<Path>{
        int depth = 0;
        int amount = 2;
        Environment env;
        MyFileVisitor(Environment env){
            this.env = env;
        }

        void indent(String name){
            for(int i = 0;i<depth;i++){
                env.write(" ");
            }
            env.writeln(name);
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            depth -= amount;
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            depth += amount;
            indent(dir.getFileName().toString());
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            indent(file.getFileName().toString());
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            env.writeln("Error while traversing");
            return FileVisitResult.CONTINUE;
        }

    }

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = arguments.split(" ");
        try {
            Files.walkFileTree(Paths.get(args[0]), new MyFileVisitor(env));   
        } catch (Exception e) {
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
        return "tree";
    }
    
}
