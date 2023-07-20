package hr.fer.oprpp1.custom.elems;

public class ElementVariable extends Element {
    public ElementVariable(String name){
        this.name = name;
    }

    String name;

    @Override
    public String asText() {
        return name;
    }
}
