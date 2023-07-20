package hr.fer.oprpp1.hw06.shell.commands;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.oprpp1.hw06.shell.Environment;
import hr.fer.oprpp1.hw06.shell.ShellCommand;
import hr.fer.oprpp1.hw06.shell.ShellStatus;

public class Symbol implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = arguments.split(" ");
        if(args.length != 1 && args.length != 2){
            env.writeln("Invalid input");
            return ShellStatus.CONTINUE;
        }
        char c;
        switch(args[0]){
            case "PROMPT": {
               c = env.getPromptSymbol();
               if(args.length == 2){
                    env.setPromptSymbol(args[1].toCharArray()[0]);
               }
               break;
            }

            case "MULTILINE": {
                c = env.getMultilineSymbol();
                if(args.length == 2){
                    env.setMultilineSymbol(args[1].toCharArray()[0]);
                }
                break;
            }

            case "MORELINES":{
                c = env.getMorelinesSymbol();
                if(args.length == 2){
                    env.setMorelinesSymbol(args[1].toCharArray()[0]);
                }
                break;
            }
            default: {
                env.writeln("Invalid input");
                return ShellStatus.CONTINUE;
            }
        }
        if(args.length == 1){
            env.writeln("Symbol for "+args[0]+" is '"+c+"'");
        }
        else{
            env.writeln("Symbol for "+args[0]+" changed from '"+c+"' to '"+args[1]+"'");
        }
        return ShellStatus.CONTINUE;
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> desc = new LinkedList<>();
        desc.add("Use function \"symbol\" to view or edit shells configuration");
        desc.add("Argument PROMPT shows the character which symbolizes the start of user input.");
        desc.add("Argument MORELINES shows the character which indicates that the user wants to continue writing in next line.");
        desc.add("Argument MULTILINE shows the character which symbols the continuation of user input.");
        desc.add("Add another single character to previously mentioned arguments to replace it with the given new charater");
        return Collections.unmodifiableList(desc);
    }

    @Override
    public String getCommandName() {
        return "symbol";
    }
    
}
