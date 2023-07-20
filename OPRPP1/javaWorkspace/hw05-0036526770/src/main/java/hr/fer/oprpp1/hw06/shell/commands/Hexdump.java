package hr.fer.oprpp1.hw06.shell.commands;

import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import hr.fer.oprpp1.hw06.shell.Environment;
import hr.fer.oprpp1.hw06.shell.ShellCommand;
import hr.fer.oprpp1.hw06.shell.ShellStatus;

public class Hexdump implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = arguments.split(" ");
        Path f = Paths.get(args[0]);
        try {
            FileReader in = new FileReader(f.toFile());
            char[] buff = new char[2048];
            int rows = 0;
            int n = 0;
            String text = "";
            while ((n = in.read(buff)) != -1) {
                String append = new String(buff, 0, n);
                text += append;
                int x = 0;
                while (text.length() >= 16 * (x + 1)) {
                    char[] c = text.substring((x) * 16, (x + 1) * 16).toCharArray();
                    String line = String.format("%08d: ", rows);
                    for (int i = 0; i < 16; i++) {
                        if (c[i] > 127 || c[i] < 0)
                            c[i] = '.';
                        if (i == 8)
                            line += "|";
                        line += String.format("%x ", (int)c[i]);
                    }
                    line += "| " + new String(c);
                    env.writeln(line);
                    rows++;
                    x++;
                }
                text = text.substring(x * 16);
            }
            if (text.length() > 0) {
                String line = String.format("%08d: ", rows);
                char[] c = text.toCharArray();
                for (int i = 0; i < 16; i++) {
                    if (i == 8)
                        line += "|";
                    if (i < text.length()) {
                        if (c[i] > 127 || c[i] < 0)
                            c[i] = '.';
                        line += String.format("%x ", (int)c[i]);
                    }
                    else  
                        line += "   ";
                }
                line += "| " + new String(c);
                env.writeln(line);
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
        return "hexdump";
    }

}
