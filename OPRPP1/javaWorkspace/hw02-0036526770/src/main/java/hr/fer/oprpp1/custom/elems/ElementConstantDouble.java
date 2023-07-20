package hr.fer.oprpp1.custom.elems;

public class ElementConstantDouble extends Element {
    double value;

    public ElementConstantDouble(double value){
        this.value = value;
    }
    @Override
    public String asText() {
        return String.valueOf(value);
    }
}
