package hr.fer.oprpp1.custom.scripting.lexer;

import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp1.custom.elems.Element;
import hr.fer.oprpp1.custom.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.elems.ElementFunction;
import hr.fer.oprpp1.custom.elems.ElementOperator;
import hr.fer.oprpp1.custom.elems.ElementString;
import hr.fer.oprpp1.custom.elems.ElementVariable;
import hr.fer.oprpp1.custom.nodes.EchoNode;
import hr.fer.oprpp1.custom.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.nodes.Node;
import hr.fer.oprpp1.custom.nodes.TextNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class Lexer {

    String body;
    LexerState state = LexerState.UNDEFINED;
    List<Token> tokens;
    int index = 0;

    public Lexer(String body) {
        this.body = body;
        tokens = new ArrayList<>();
    }

    public Token getToken() {
        if (tokens.size() == 0)
            return null;
        else
            return tokens.get(tokens.size() - 1);
    };

    public Token nextToken() {
        if (state == LexerState.UNDEFINED) {
            if (index == body.length()) {
                state = LexerState.EOF;
                return new Token(NodeType.EOF, null);
            } else if (!body.startsWith("{$", index)) {
                state = LexerState.TEXT;
                return nextToken();
            } else if (body.substring(index).toLowerCase().replaceAll("\\s", "").startsWith("{$for")) {
                state = LexerState.FOR;
                return nextToken();
            } else if (body.substring(index).toLowerCase().replaceAll("\\s", "").startsWith("{$=")) {
                state = LexerState.ECHO;
                return nextToken();
            } else if (body.substring(index).toLowerCase().replaceAll("\\s", "").startsWith("{$end$}")) {
                state = LexerState.END;
                return nextToken();
            } else
                throw new SmartScriptParserException();
        }
        
        else if (state == LexerState.EOF) {
            return new Token(NodeType.EOF, null);
        }

        else if (state == LexerState.TEXT) {
            String text;
            int newIndex;
            if ((newIndex = body.indexOf("{$", index)) != -1 && body.charAt(newIndex - 1) != '\\') {
                text = body.substring(index, newIndex);
                index = newIndex;
                state = LexerState.UNDEFINED;
            } else {
                text = body.substring(index);
                state = LexerState.EOF;
            }
            Token token = new Token(NodeType.TEXT, new TextNode(text));
            tokens.add(token);
            return token;

        } else if (state == LexerState.FOR) {
            index = body.toLowerCase().indexOf("for", index) + 3;
            String elements[] = new String[4];
            int i = 0;
            while (!(body.startsWith("$}", index))) {
                char c = body.charAt(index);
                if (c == ' ') {
                    if (elements[i]==null) {
                        index++;
                        continue;
                    } else {
                        i++;
                        index++;
                        continue;
                    }
                }
                else if(c == '\"'){
                    if(elements[i]!=null){
                        i++;
                    }
                    elements[i] = body.substring(index,body.indexOf(c, index+1)+1);
                        index = body.indexOf(c,index+1)+1;
                        i++;
                    continue;
                }
                String newElement = "";
                if(elements[i]!=null)
                    newElement = elements[i];
                newElement += c;
                if (newElement.matches("[A-Za-z][\\w]*") || newElement.matches("[-]?\\d+(\\.\\d+)?")) {
                    elements[i] = newElement;
                    index++;
                }
                else{
                    i++;
                }
            }
            index+=2;
            List<Element> forElements = new ArrayList<>();
            for(i = 0;i<elements.length;i++){
                if(elements[i]==null)
                    break;
                if(elements[i].matches("[A-Za-z][\\w]*")){
                    forElements.add(new ElementVariable(elements[i]));
                }
                else if(elements[i].startsWith("\"")){
                    forElements.add(new ElementString(elements[i]));
                }
                else if(elements[i].matches("[-]?\\d+(\\.\\d+)?")){
                    Element e;
                    if(elements[i].contains(".")){
                        e = new ElementConstantDouble(Double.valueOf(elements[i]));
                    }
                    else
                        e = new ElementConstantInteger(Integer.valueOf(elements[i]));
                    forElements.add(e);
                }
                else{
                    throw new SmartScriptParserException();
                }
            }
            Node n;
            if (i==3){
                n = new ForLoopNode((ElementVariable)forElements.get(0), forElements.get(1), forElements.get(2));
            }
            else {
                n = new ForLoopNode((ElementVariable)forElements.get(0), forElements.get(1), forElements.get(2),forElements.get(3));
            }
            state = LexerState.UNDEFINED;
            return new Token(NodeType.FOR, n);
        }
        else if (state == LexerState.END){
            index = body.indexOf("$}", index)+2;
            state = LexerState.UNDEFINED;
            return new Token(NodeType.END,null);
        }

        else if(state == LexerState.ECHO){
            index = body.toLowerCase().indexOf("=", index) + 1;
            List<String> elements = new ArrayList<>();
            int i = 0;
            while (!body.startsWith("$}", index)) {
                char c = body.charAt(index);
                if (c == ' ') {
                    if (i==elements.size()) {
                        index++;
                        continue;
                    } else {
                        i++;
                        index++;
                        continue;
                    }
                }
                else if(c == '\"'){
                    if(i<elements.size()){
                        i++;
                    }
                    elements.add(body.substring(index,body.indexOf(c, index+1)+1));
                        index = body.indexOf(c,index+1)+1;
                        i++;
                    continue;
                }
                String newElement = "";
                if(elements.size()==i)
                    newElement += c;
                else    
                    newElement = elements.get(i)+c;
                if (newElement.matches("[A-Za-z][\\w]*") || newElement.matches("[-]?\\d+(\\.\\d+)?") ||
                    newElement.matches("@[A-Za-z][\\w]*") || newElement.matches("[+\\-*/^]") || newElement.matches("@")) {
                    if(i == elements.size())
                        elements.add(newElement);
                    elements.remove(i);
                    elements.add(newElement);
                    index++;
                }
                else{
                    i++;
                }
            }
            index += 2;
            List<Element> forElements = new ArrayList<>();
            for(i = 0;i<elements.size();i++){
                if(elements.size()==i)
                    break;
                else if(elements.get(i).matches("[A-Za-z][\\w]*")){
                    forElements.add(new ElementVariable(elements.get(i)));
                }
                else if(elements.get(i).startsWith("\"")){
                    forElements.add(new ElementString(elements.get(i)));
                }
                else if(elements.get(i).matches("[-]?\\d+(\\.\\d+)?")){
                    Element e;
                    if(elements.get(i).contains(".")){
                        e = new ElementConstantDouble(Double.valueOf(elements.get(i)));
                    }
                    else
                        e = new ElementConstantInteger(Integer.valueOf(elements.get(i)));
                    forElements.add(e);
                }
                else if(elements.get(i).matches("[+\\-*/^]")){
                    forElements.add(new ElementOperator(elements.get(i)));
                }
                else if(elements.get(i).matches("@[A-Za-z][\\w]*")){
                    forElements.add(new ElementFunction(elements.get(i)));
                }
                else{
                    throw new SmartScriptParserException();
                }
            }
            state = LexerState.UNDEFINED;
            return new Token(NodeType.ECHO,new EchoNode(forElements.toArray(new Element[forElements.size()])));
        }

        return null;
    }
}
