package hr.fer.oprpp1.hw04.db;

public class ComparisonOperators {
    public static final IComparisonOperator LESS = (s1, s2) -> s1.compareTo(s2) < 0;
    public static final IComparisonOperator LESS_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) < 0 || s1.compareTo(s2) == 0;
    public static final IComparisonOperator GREATER = (s1, s2) -> s1.compareTo(s2) > 0;
    public static final IComparisonOperator GREATER_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) < 0
            || s1.compareTo(s2) == 0;
    public static final IComparisonOperator EQUALS = (s1, s2) -> s1.compareTo(s2) == 0;
    public static final IComparisonOperator NOT_EQUALS = (s1, s2) -> s1.compareTo(s2) != 0;
    public static final IComparisonOperator LIKE = (s1, s2) -> {
        int i = s2.indexOf("*");
        if (i == -1)
            return s1.equals(s2);
        else if (i == 0)
            return s1.endsWith(s2);
        else if (s1.length() - 1 == i)
            return s1.startsWith(s2);
        else {
            var subs = s2.split("\\*", 2);
            return s1.startsWith(subs[0]) && s1.endsWith(subs[1]);
        }
    };
};