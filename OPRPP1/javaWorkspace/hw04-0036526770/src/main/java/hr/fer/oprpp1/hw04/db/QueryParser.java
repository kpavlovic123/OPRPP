package hr.fer.oprpp1.hw04.db;

import java.util.LinkedList;
import java.util.List;

public class QueryParser {
    private boolean validInput;
    private LinkedList<String> tokens;

    public QueryParser(String query) {
        this.validInput = true;
        this.tokens = new LinkedList<>();
        tokenizeAndParse(query);
    }

    public boolean check(){
        return validInput;
    };

    private String addToken(String query, String token) {
        tokens.add(token);
        return query.substring(token.length());
    }

    private void tokenizeAndParse(String query) {

        query = query.replaceAll("\\s", "");
        while (true) {
            if (query.startsWith("jmbag")) {
                query = addToken(query, "jmbag");
            } else if (query.startsWith("lastName")) {
                query = addToken(query, "lastName");
            } else if (query.startsWith("firstName")) {
                query = addToken(query, "firstName");
            } else {
                validInput = false;
                return;
            }
            if (query.startsWith("<"))
                query = addToken(query, "<");
            else if (query.startsWith(">"))
                query = addToken(query, ">");
            else if (query.startsWith(">="))
                query = addToken(query, ">=");
            else if (query.startsWith("<="))
                query = addToken(query, "<=");
            else if (query.startsWith("="))
                query = addToken(query, "=");
            else if (query.startsWith("!="))
                query = addToken(query, "!=");
            else if (query.startsWith("LIKE"))
                query = addToken(query, "LIKE");
            else {
                validInput = false;
                return;
            }
            if (query.startsWith("\"")) {
                int index = query.indexOf("\"", 1);
                if (index != -1) {
                    String token = query.substring(1,index);
                    tokens.add(token);
                    query = query.substring(index+1);
                } else {
                    validInput = false;
                    return;
                }
            } else {
                validInput = false;
                return;
            }
            if (query.length() == 0)
                return;
            else if (query.length()>3 && query.substring(0, 3).equalsIgnoreCase("and")) {
                query = query.substring("and".length());
            }
            else {
                validInput = false;
                return;
            }
        }
    }

    public boolean isDirectQuery() {
        return tokens.size() == 3 && tokens.getFirst() == "jmbag" && tokens.get(1) == "=";
    };

    String getQueriedJMBAG() {
        if (isDirectQuery()) {
            return tokens.get(2);
        }
        throw new IllegalStateException("Query not direct.");
    };

    List<ConditionalExpression> getQuery() {
        LinkedList<ConditionalExpression> list = new LinkedList<>();
        for (int i = 0; i < tokens.size(); i += 3) {
            IFieldValueGetter f;
            switch (tokens.get(i)) {
                case "jmbag":
                    f = FieldValueGetters.JMBAG;
                    break;
                case "lastName":
                    f = FieldValueGetters.LAST_NAME;
                    break;
                case "firstName":
                    f = FieldValueGetters.FIRST_NAME;
                    break;
                default:
                    throw new IllegalStateException();
            }
            String l = tokens.get(i+2);
            IComparisonOperator c;
            switch (tokens.get(i+1)) {
                case "<":
                    c = ComparisonOperators.LESS;
                break;
                case ">":
                    c = ComparisonOperators.GREATER;
                break;
                case "<=":
                    c = ComparisonOperators.LESS_OR_EQUALS;
                break;
                case ">=":
                    c = ComparisonOperators.GREATER_OR_EQUALS;
                break;
                case "=":
                    c = ComparisonOperators.EQUALS;
                break;
                case "!=":
                    c = ComparisonOperators.NOT_EQUALS;
                break;
                case "LIKE":
                    c = ComparisonOperators.LIKE;
                break;
                default:
                    throw new IllegalStateException();
            }
            ConditionalExpression cond = new ConditionalExpression(f, l, c);
            list.add(cond);
        }
        return list;
    };

}
