package hr.fer.oprpp1.custom.elems;

public class ElementOperator extends Element{
    String symbol;
    public ElementOperator(String symbol){
        this.symbol = symbol;
    }
    @Override
    public String asText() {
        return symbol;
    }
}
