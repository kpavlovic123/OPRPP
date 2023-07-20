package hr.fer.oprpp1.hw02.prob1;

public class Lexer {
    private char[] data;
    private Token token;
    private int currentIndex;
    LexerState state;
    
    public Lexer(String text) {
        this.data = text.toCharArray();
        this.currentIndex = 0;
        this.state = LexerState.BASIC;
    };

    public Token nextToken() {
        if(token != null && token.getType().equals(TokenType.EOF))
            throw new LexerException("Lexer exception.");
        
        TokenType tt;
        
        if(currentIndex == data.length) {
            tt = TokenType.EOF;
            token = new Token(tt,null); 
            return token;
        }
        
        String s = new String();
        char c = data[currentIndex++];
        
        while(c == '\t' || c == '\r' || c == ' ' || c == '\n') {
            if(currentIndex == data.length) {
                tt = TokenType.EOF;
                token = new Token(tt,null);
                return token;
            }
            c = data[currentIndex++];
        }
        
        if(state == LexerState.EXTENDED) {
            s += c;
            if(Character.isDigit(c) || Character.isLetter(c) || c =='\\') {
                tt = TokenType.WORD;
                while((currentIndex != data.length && (Character.isDigit(data[currentIndex]) || Character.isLetter(data[currentIndex]) || data[currentIndex]=='\\' ))) {
                    c = data[currentIndex++];
                    s += c;
                }
                token = new Token(tt,s);
                return token;
            }
            else {
                tt = TokenType.SYMBOL;
                token = new Token(tt,c);
                return token;
            }
        }
        
        if (Character.isLetter(c) || c == '\\') {
            tt = TokenType.WORD;
            if(Character.isLetter(c)) {
                s+=c;
            }
            else {
                if(currentIndex == data.length || (!(Character.isDigit(data[currentIndex]) || data[currentIndex] == '\\')))
                    throw new LexerException("Illegal input");
                c = data[currentIndex++]; 
                s+=c;                    
            }
            while(currentIndex != data.length && (Character.isLetter(data[currentIndex])||data[currentIndex]=='\\')) {
                c = data[currentIndex++];
                if(Character.isLetter(c)) {
                    s+=c;
                }
                else {
                    if(currentIndex == data.length || (!(Character.isDigit(data[currentIndex]) || data[currentIndex] == '\\')))
                        throw new LexerException("Illegal input");
                    c = data[currentIndex++];
                    s+=c;                    
                }
            }
        }
        else if(Character.isDigit(c)) {
            tt = TokenType.NUMBER;
            s += c;
            while(currentIndex != data.length && Character.isDigit(data[currentIndex])){
                c = data[currentIndex++];
                s += c;
            }
        }
        else {
            System.out.println(c);
            tt = TokenType.SYMBOL;
            token = new Token(tt,c);
        }
        
        if(tt == TokenType.NUMBER) {
            if(s.length()>19)
                throw new LexerException("Digit number too long.");
            long l = Long.parseLong(s);
            token = new Token(tt,l);
        }
        else if(tt == TokenType.WORD){
            token = new Token(tt,s);
        }
        return token;
    };

    public Token getToken() {
        return token;
    };
    public void setState(LexerState state) {
        if(state == null)
            throw new NullPointerException("Null pointer exception.");
        this.state = state;
    };
    
    
    
}
