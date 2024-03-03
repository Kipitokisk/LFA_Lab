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

    public List<String> generateStrings(String startSymbol, int numStrings) {
        Set<String> Strings = new HashSet<>();
        while (Strings.size() < numStrings) {
            StringBuilder sb = new StringBuilder();
            generateString(startSymbol, sb);
            Strings.add(sb.toString());
        }
        return new ArrayList<>(Strings);
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

    public FiniteAutomaton toFiniteAutomaton() {
        Set<String> states = nonTerminals;
        Set<String> alphabet = terminals;
        Map<String, Map<String, String>> transitions = new HashMap<>();
        transitions.put("S", Collections.singletonMap("d", "A"));
        transitions.put("A", new HashMap<String, String>() {{
            put("a", "B");
            put("b", "A");
        }});
        transitions.put("B", new HashMap<String, String>() {{
            put("b", "C");
            put("d", "B");
        }});
        transitions.put("C", new HashMap<String, String>() {{
            put("c", "B");
            put("a", "A");
        }});
        FiniteAutomaton finiteAutomaton = new FiniteAutomaton(states, alphabet, "S", new HashSet<>(Arrays.asList("A", "B")), transitions);

        return finiteAutomaton;
    }


    public String classifyGrammar() {
        boolean isRegular = true;
        boolean isContextFree = true;
        boolean isContextSensitive = true;
        boolean isUnrestricted = true;

        for (String nonTerminal : productions.keySet()) {
            for (String production : productions.get(nonTerminal)) {
                // Check if production is regular
                if (production.length() > 2 || (production.length() == 2 && !Character.isLowerCase(production.charAt(1)))) {
                    isRegular = false;
                }

                // Check if production is context-free
                if (production.length() > 2) {
                    isContextFree = false;
                }

                // Check if production is context-sensitive
                if (!production.equals("ε") && production.length() < nonTerminal.length()) {
                    isContextSensitive = false;
                }
            }
        }

        if (isRegular) {
            return "Regular Grammar";
        } else if (isContextFree) {
            return "Context-Free Grammar";
        } else if (isContextSensitive) {
            return "Context-Sensitive Grammar";
        } else {
            return "Unrestricted Grammar";
        }
    }
}
