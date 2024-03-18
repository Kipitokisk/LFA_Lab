import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                currentToken.append(c);
            } else if (c == '.') {
                currentToken.append(c);
            } else if (Character.isLetter(c)) {
                if (currentToken.length() > 0) {
                    tokens.add(createToken(currentToken.toString()));
                    currentToken.setLength(0);
                }
                tokens.add(createToken(String.valueOf(c)));
            } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '=') {
                if (currentToken.length() > 0) {
                    tokens.add(createToken(currentToken.toString()));
                    currentToken.setLength(0);
                }
                tokens.add(createToken(String.valueOf(c)));
            } else if (c == ' ') {
                if (currentToken.length() > 0) {
                    tokens.add(createToken(currentToken.toString()));
                    currentToken.setLength(0);
                }
            }
        }

        if (currentToken.length() > 0) {
            tokens.add(createToken(currentToken.toString()));
        }

        return tokens;
    }

    private static Token createToken(String value) {
        if (value.matches("\\d+")) {
            return new Token(value, TokenType.INTEGER);
        } else if (value.matches("\\d+\\.\\d+")) {
            return new Token(value, TokenType.FLOAT);
        } else if (value.length() == 1 && Character.isLetter(value.charAt(0))) {
            return new Token(value, TokenType.CHARACTER);
        } else if (value.equals("+")) {
            return new Token(value, TokenType.ADD);
        } else if (value.equals("-")) {
            return new Token(value, TokenType.SUBTRACT);
        } else if (value.equals("*")) {
            return new Token(value, TokenType.MULTIPLY);
        } else if (value.equals("/")) {
            return new Token(value, TokenType.DIVIDE);
        } else if (value.equals("=")) {
            return new Token(value, TokenType.EQUAL);
        } else {
            return new Token(value, TokenType.UNKNOWN);
        }
    }

    public static void main(String[] args) {
        String s = "10 + x = y /                 20.4                     4";
        List<Token> results = tokenize(s);
        System.out.println("Tokens:");
        for (Token token : results) {
            System.out.println(token);
        }
    }
}