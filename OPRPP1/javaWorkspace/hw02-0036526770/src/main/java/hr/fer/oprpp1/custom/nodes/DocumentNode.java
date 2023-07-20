package hr.fer.oprpp1.custom.nodes;

import hr.fer.oprpp1.custom.elems.Element;

public class DocumentNode extends Node {
    public String toString(){
        return getValues(this, "");
    }

    private String getValues(Node n,String v){
        if(n instanceof EchoNode){
            Element elements[] = ((EchoNode) n).getElements();
            v+="{$=";
            for(int i = 0;i<elements.length;i++){
                v += " " + elements[i].asText();
            }
            v+="$}";
        }
        else if(n instanceof ForLoopNode){
            ForLoopNode f = (ForLoopNode) n;
            v+="{$"+" "+f.getVariable().asText()+" "+f.getStartExpression().asText()+" "+f.getEndExpression().asText()+
            " "+(f.getStepExpression()!=null?f.getStepExpression().asText()+" ":"")+"$}";
        }
        else if(n instanceof TextNode){
            v+=((TextNode)n).getText();
        }
        for(int i = 0;i<n.numberOfChildren();i++){
            v = getValues(n.getChild(i), v);      
        }
        return v;
    }
}
