package hr.fer.oprpp1.hw04.db;

import java.util.List;

public class QueryFilter implements IFilter {
    List<ConditionalExpression> l;
    public QueryFilter(List<ConditionalExpression> l){
        this.l = l;
    };
    public boolean accepts (StudentRecord e){
        for(var c : l){
            if(!c.getComparisonOperator().satisfied(c.getFieldGetter().get(e), c.getStringLiteral()))
                return false;
        }
        return true;
    };
}
