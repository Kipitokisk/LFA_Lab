import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                currentToken.append(c);
            } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '=') {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
                tokens.add(String.valueOf(c));
            } else if (c == ' ') {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
            }
        }

        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }

        return tokens;
    }

    public static void main(String[] args) {
        String s = "10 + x = y /                 20                     4";
        List<String> results = tokenize(s);
        System.out.print("Tokens:");
        System.out.println(results);
    }
}
