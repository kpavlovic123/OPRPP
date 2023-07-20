package hr.fer.oprpp1.custom.elems;

public class ElementString extends Element {
    String value;
    public ElementString(String value){
        this.value = value;
    }
    @Override
    public String asText() {
        return value;
    }
}
