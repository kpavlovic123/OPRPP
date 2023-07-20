package hr.fer.oprpp1.custom.nodes;

import hr.fer.oprpp1.custom.elems.Element;

public class EchoNode extends Node {
    Element elements[];

    public EchoNode(Element elements[]) {
        this.elements = elements;
    }

    public Element[] getElements() {
        return elements;
    }

    
    
}
