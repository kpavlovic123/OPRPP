package hr.fer.oprpp1.custom.elems;

public class ElementConstantInteger extends Element {
    public ElementConstantInteger (int value){
        this.value = value;
    }
    int value;
    @Override
    public String asText() {
        return String.valueOf(value);
    }
}
