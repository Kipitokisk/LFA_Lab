import java.util.*;

public class Grammar {
    private Set<String> nonTerminals;
    private Set<Character> terminals;
    private Map<String, Set<String>> productionRules;

    public Grammar() {
        nonTerminals = new HashSet<>();
        terminals = new HashSet<>();
        productionRules = new HashMap<>();
    }

    public Set<String> getNonTerminals() {
        return nonTerminals;
    }

    public void setNonTerminals(Set<String> nonTerminals) {
        this.nonTerminals = nonTerminals;
    }

    public Set<Character> getTerminals() {
        return terminals;
    }

    public void setTerminals(Set<Character> terminals) {
        this.terminals = terminals;
    }

    public Map<String, Set<String>> getProductionRules() {
        return productionRules;
    }

    public void setProductionRules(Map<String, Set<String>> productionRules) {
        this.productionRules = productionRules;
    }

    public String classifyGrammar() {
        boolean isRegular = true;
        boolean isContextFree = true;
        boolean isContextSensitive = true;
        boolean isUnrestricted = true;

        for (String nonTerminal : productionRules.keySet()) {
            for (String production : productionRules.get(nonTerminal)) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Non-terminals: ").append(nonTerminals.isEmpty() ? "{}" : nonTerminals).append("\n");
        sb.append("Terminals: ").append(terminals.isEmpty() ? "{}" : terminals).append("\n");
        sb.append("Productions: \n");
        for (Map.Entry<String, Set<String>> entry : productionRules.entrySet()) {
            String nonTerminal = entry.getKey();
            sb.append(nonTerminal).append(" : ");
            Set<String> productions = entry.getValue();
            if (!productions.isEmpty()) {
                Iterator<String> iterator = productions.iterator();
                sb.append(iterator.next());
                while (iterator.hasNext()) {
                    sb.append(" | ").append(iterator.next());
                }
            } else {
                sb.append("{}");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
