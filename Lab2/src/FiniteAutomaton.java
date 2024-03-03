import java.util.*;
import java.util.stream.Collectors;

class FiniteAutomaton {
    private Set<String> states;
    private Set<Character> alphabet;
    private Map<String, Map<Character, Set<String>>> transitions;
    private String startState;
    private Set<String> finalStates;

    public FiniteAutomaton() {
        states = new HashSet<>();
        alphabet = new HashSet<>();
        transitions = new HashMap<>();
        finalStates = new HashSet<>();
    }

    public void addState(String state) {
        states.add(state);
    }

    public void setStartState(String state) {
        startState = state;
    }

    public void addFinalState(String state) {
        finalStates.add(state);
    }

    public void addTransition(String fromState, char symbol, String toState) {
        alphabet.add(symbol);
        transitions.computeIfAbsent(fromState, k -> new HashMap<>())
                .computeIfAbsent(symbol, k -> new HashSet<>())
                .add(toState);
    }

    public Grammar convertToRegularGrammar() {
        Grammar grammar = new Grammar();

        // Add non-terminals based on states
        grammar.setNonTerminals(states);

        // Add terminals based on alphabet
        grammar.setTerminals(alphabet.stream().map(String::valueOf).collect(Collectors.toSet()));

        Map<String, Set<String>> productionRules = new HashMap<>();

        // Add transitions as production rules
        for (String state : states) {
            for (char symbol : alphabet) {
                if (transitions.containsKey(state) && transitions.get(state).containsKey(symbol)) {
                    for (String nextState : transitions.get(state).get(symbol)) {
                        String rule = String.format("%s -> %s%s", state, symbol, nextState);
                        productionRules.computeIfAbsent(state, k -> new HashSet<>()).add(rule);
                    }
                }
            }
        }

        grammar.setProductionRules(productionRules);
        return grammar;
    }



    public boolean isDeterministic() {
        for (String state : states) {
            if (transitions.containsKey(state)) {
                for (char symbol : alphabet) {
                    if (transitions.get(state).containsKey(symbol) && transitions.get(state).get(symbol).size() > 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public FiniteAutomaton convertToDFA() {
        FiniteAutomaton dfa = new FiniteAutomaton();
        dfa.setStartState(startState);
        Set<Set<String>> newStates = new HashSet<>();
        Set<String> initialState = new HashSet<>();
        initialState.add(startState);
        Map<Set<String>, String> stateMapping = new HashMap<>();
        newStates.add(initialState);
        stateMapping.put(initialState, startState);
        dfa.addState(startState);
        if (initialState.containsAll(finalStates)) {
            dfa.addFinalState(startState);
        }

        Queue<Set<String>> queue = new LinkedList<>();
        queue.add(initialState);
        while (!queue.isEmpty()) {
            Set<String> currentState = queue.poll();
            for (char symbol : alphabet) {
                Set<String> nextState = new HashSet<>();
                for (String state : currentState) {
                    if (transitions.containsKey(state) && transitions.get(state).containsKey(symbol)) {
                        nextState.addAll(transitions.get(state).get(symbol));
                    }
                }
                if (!newStates.contains(nextState)) {
                    newStates.add(nextState);
                    String stateName = nextState.toString().replaceAll("[\\[\\],\\s]", "");
                    stateMapping.put(nextState, stateName);
                    dfa.addState(stateName);
                    if (nextState.containsAll(finalStates)) {
                        dfa.addFinalState(stateName);
                    }
                    queue.add(nextState);
                }
                dfa.addTransition(stateMapping.get(currentState), symbol, stateMapping.get(nextState));
            }
        }
        return dfa;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("States: ").append(states.isEmpty() ? "{}" : states).append("\n");
        sb.append("Alphabet: ").append(alphabet).append("\n");
        sb.append("Transitions: \n");
        for (Map.Entry<String, Map<Character, Set<String>>> entry : transitions.entrySet()) {
            String state = entry.getKey();
            sb.append(state).append(":\n");
            Map<Character, Set<String>> transitionMap = entry.getValue();
            if (!transitionMap.isEmpty()) {
                for (Map.Entry<Character, Set<String>> trans : transitionMap.entrySet()) {
                    char symbol = trans.getKey();
                    Set<String> nextStates = trans.getValue();
                    sb.append("\t").append(symbol).append(" -> ").append(nextStates.isEmpty() ? "{}" : nextStates).append("\n");
                }
            } else {
                sb.append("\t{}").append("\n");
            }
        }
        sb.append("Start State: ").append(startState).append("\n");
        sb.append("Final States: ").append(finalStates.isEmpty() ? "{}" : finalStates).append("\n");
        return sb.toString();
    }

}
