import java.util.*;

public class Grammar {
    private Set<String> nonTerminals;
    private Set<String> terminals;
    private Map<String, List<String>> productions;

    public Grammar(Set<String> nonTerminals, Set<String> terminals, Map<String, List<String>> productions) {
        this.nonTerminals = nonTerminals;
        this.terminals = terminals;
        this.productions = productions;
    }

    public Set<String> getNonTerminals() {
        return nonTerminals;
    }

    public Set<String> getTerminals() {
        return terminals;
    }

    public Map<String, List<String>> getProductions() {
        return productions;
    }

    public List<String> generateStrings(String startSymbol, int numStrings) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < numStrings; i++) {
            StringBuilder sb = new StringBuilder();
            generateString(startSymbol, sb);
            strings.add(sb.toString());
        }
        return strings;
    }

    private void generateString(String symbol, StringBuilder sb) {
        if (!productions.containsKey(symbol)) {
            sb.append(symbol);
            return;
        }
        List<String> choices = productions.get(symbol);
        String choice = choices.get(new Random().nextInt(choices.size()));
        for (char c : choice.toCharArray()) {
            if (Character.isUpperCase(c)) {
                generateString(String.valueOf(c), sb);
            } else {
                sb.append(c);
            }
        }
    }
}
