package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.nodes.Node;

public class Token {
    NodeType type;
    Node node; 
    public Token(NodeType type,Node node){
        this.type = type;
        this.node = node;
    }
    public NodeType getType() {
        return type;
    }
    public Node getNode() {
        return node;
    }
    
}
