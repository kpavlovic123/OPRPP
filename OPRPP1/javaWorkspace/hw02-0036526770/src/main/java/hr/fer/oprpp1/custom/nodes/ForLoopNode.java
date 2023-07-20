package hr.fer.oprpp1.custom.nodes;

import hr.fer.oprpp1.custom.elems.Element;
import hr.fer.oprpp1.custom.elems.ElementVariable;

public class ForLoopNode extends Node {
    ElementVariable variable;
    Element startExpression;
    Element endExpression;
    Element stepExpression;

    public ForLoopNode(ElementVariable variable,Element startExpression, Element endExpression){
        this.variable = variable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
    }

    public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
            Element stepExpression) {
        this.variable = variable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.stepExpression = stepExpression;
    }

    public ElementVariable getVariable() {
        return variable;
    }

    public Element getStartExpression() {
        return startExpression;
    }

    public Element getEndExpression() {
        return endExpression;
    }

    public Element getStepExpression() {
        return stepExpression;
    }

    
        
}
