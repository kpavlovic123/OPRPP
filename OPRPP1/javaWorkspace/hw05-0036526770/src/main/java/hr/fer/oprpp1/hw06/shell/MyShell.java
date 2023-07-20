package hr.fer.oprpp1.hw06.shell;

import hr.fer.oprpp1.hw06.shell.commands.*;
import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class MyShell {

    static Environment buildEnvironment(){
        class MyEnvironment implements Environment{
            SortedMap<String,ShellCommand> map;

            Character prompt = '>',more = '\\',multi='|';
            String command = "";

            Scanner scanner;

            MyEnvironment(){
                map = new TreeMap<String,ShellCommand>();
                map.put("exit", new Exit());
                map.put("symbol", new Symbol());
                map.put("charsets",new Charsets());
                map.put("cat",new Cat());
                map.put("ls",new Ls());
                map.put("tree",new Tree());
                map.put("copy",new Copy());
                map.put("mkdir",new Mkdir());
                map.put("hexdump", new Hexdump());
                map.put("help",new Help());
                map = Collections.unmodifiableSortedMap(this.map);
                scanner = new Scanner(System.in);
            }

            @Override
            public String readLine() throws ShellIOException {
                command = "";
                String input = "";
                System.out.printf(prompt+" ");
                while(true){
                    input = scanner.nextLine().trim();
                    if(input.endsWith(more.toString())){
                        String append = input.substring(0,input.length()-1).trim();
                        command += append + " ";
                        System.out.printf(multi + " ");
                    } 
                    else{
                        command += input.trim()+" ";
                        break;
                    }
                }
                return command;
            }

            @Override
            public void write(String text) throws ShellIOException {
                try {
                    System.out.print(text);
                } catch (Exception e) {
                    throw new ShellIOException();
                }
            }

            @Override
            public void writeln(String text) throws ShellIOException {
                try {
                    System.out.println(text);
                } catch (Exception e) {
                    throw new ShellIOException();
                }
                
            }

            @Override
            public SortedMap<String, ShellCommand> commands() {
                return map;
            }

            @Override
            public Character getMultilineSymbol() {
                return multi;
            }

            @Override
            public void setMultilineSymbol(Character symbol) {
                multi = symbol;
            }

            @Override
            public Character getPromptSymbol() {
                return prompt;
            }

            @Override
            public void setPromptSymbol(Character symbol) {
                prompt = symbol;
            }

            @Override
            public Character getMorelinesSymbol() {
                return more;
            }

            @Override
            public void setMorelinesSymbol(Character symbol) {
                more = symbol;
            }

        }
        return new MyEnvironment();
    };

    public static void main(String[] args) {
        
        Environment e = buildEnvironment();
        ShellStatus status = ShellStatus.CONTINUE;
        SortedMap<String,ShellCommand> commands = e.commands();
        System.out.println("Welcome to MyShell v1.0");
        do{
            String l = e.readLine();
            String commandName = l.substring(0,l.indexOf(" "));
            String arguments = l.substring(l.indexOf(" ")+1);
            arguments = arguments.replaceAll("\"", "");
            ShellCommand command = commands.get(commandName);
            if(command == null){
                e.writeln("Invalid input.");
                continue;
            }
            status = command.executeCommand(e, arguments);
        }while(status != ShellStatus.TERMINATE);
    }
}
