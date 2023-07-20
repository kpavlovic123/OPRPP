package hr.fer.oprpp1.custom.nodes;

import java.util.ArrayList;

public class Node {
    ArrayList<Node> col;
    public void addChildNode(Node child){
        if(col == null){
            col = new ArrayList<>();
        }
        col.add(child);
    }

    public int numberOfChildren(){
        if(col == null)
            return 0;
        return col.size();
    }

    public Node getChild(int index){
        if(index < 0 || index >= col.size())
            throw new IndexOutOfBoundsException();
        return col.get(index);
    }
} 