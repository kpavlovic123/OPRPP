package hr.fer.oprpp1.hw06.shell.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import hr.fer.oprpp1.hw06.shell.Environment;
import hr.fer.oprpp1.hw06.shell.ShellCommand;
import hr.fer.oprpp1.hw06.shell.ShellStatus;

public class Ls implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments){
        String[] args = arguments.split(" ");
        Path path = Paths.get(args[0]);
        if (!Files.isDirectory(path)) {
            env.writeln(path.toAbsolutePath().toString());
            return ShellStatus.CONTINUE;
        }
        String att = "----";
        File directory = path.toFile();
        File[] children = directory.listFiles();
        for (var c : children) {
            char[] a = att.toCharArray();
            if (c.isDirectory()) {
                a[0] = 'd';
            }
            if (c.canRead()) {
                a[1] = 'r';
            }
            if (c.canWrite()) {
                a[2] = 'w';
            }
            if (c.canExecute()) {
                a[3] = 'x';
            }
            long size = 0;
            BasicFileAttributes ba;
            String date = "";
            try {
                size = Files.size(c.toPath());
                var fa = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                ba = fa.readAttributes();
                FileTime ft = ba.creationTime();
                date = sdf.format(new Date(ft.toMillis())); 
            } catch (Exception e) {
            }
            String result = String.format("%s %10d %s %s",String.valueOf(a),size,date,c.toString());
            env.writeln(result);
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
        return "ls";
    }

}
