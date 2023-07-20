package hr.fer.oprpp1.hw04.db;

public class ConditionalExpression {
    private IFieldValueGetter f;
    private String l;
    private IComparisonOperator c;

    public ConditionalExpression(IFieldValueGetter f, String l, IComparisonOperator c) {
        this.f = f;
        this.l = l;
        this.c = c;
    }

    public IFieldValueGetter getFieldGetter() {
        return f;
    }

    public IComparisonOperator getComparisonOperator() {
        return c;
    }

    public String getStringLiteral() {
        return l;
    }
}
