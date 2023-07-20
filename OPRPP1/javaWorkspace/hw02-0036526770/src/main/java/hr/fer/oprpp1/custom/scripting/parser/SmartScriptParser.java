package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.nodes.DocumentNode;
import hr.fer.oprpp1.custom.nodes.Node;
import hr.fer.oprpp1.custom.scripting.lexer.Lexer;
import hr.fer.oprpp1.custom.scripting.lexer.NodeType;
import hr.fer.oprpp1.custom.scripting.lexer.Token;

public class SmartScriptParser {
    ObjectStack stack;
    String body;
    Lexer lexer;
    DocumentNode doc;

    public SmartScriptParser(String body) {
        this.body = body;
        stack = new ObjectStack();
        doc = new DocumentNode();
        stack.push(doc);
        lexer = new Lexer(body);
        Token t;
        while((t = lexer.nextToken()).getType() != NodeType.EOF){
            Node n = (Node) stack.peek();
            if(t.getType() == NodeType.END){
                stack.pop();
                continue;
            }
            n.addChildNode(t.getNode());
            if(t.getType()==NodeType.FOR || t.getType()==NodeType.ECHO);{
                stack.push(t.getNode());
            }
        }
    }

    public DocumentNode getDocumentNode(){
        return this.doc;
    }

    public static void main(String[] args) {
        String docBody = "This is sample text. {$ FOR i 1 10 1 $} This is {$= i $}-th time this message is generated. {$END$} {$FOR i 0 10 2 $} sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $} {$END$}";
        SmartScriptParser parser = null;
        try {
            parser = new SmartScriptParser(docBody);
        } catch (SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.exit(-1);
        } /* catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("If this line ever executes, you have failed this class!");
            System.exit(-1);
        } */
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        System.out.println(originalDocumentBody); // should write something like original
        // content of docBody
    }


}
