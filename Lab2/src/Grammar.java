import java.util.*;

class Grammar {
    private Map<String, Set<String>> productionRules;

    public Grammar() {
        productionRules = new HashMap<>();
    }

    public void setProductionRules(Map<String, Set<String>> rules) {
        productionRules = rules;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Set<String>> entry : productionRules.entrySet()) {
            for (String rule : entry.getValue()) {
                sb.append(rule+"\n");
            }
        }
        return sb.toString();
    }
}