package hr.fer.oprpp1.custom.elems;

public class ElementFunction extends Element{
    String name;
    public ElementFunction(String name){
        this.name = name;
    }
    @Override
    public String asText() {
        return name;
    }
}
