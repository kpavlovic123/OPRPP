package hr.fer.oprpp1.custom.nodes;

public class TextNode extends Node {
    String text;

    public TextNode(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }
    
}
