import java.util.*;

public class FiniteAutomaton {
    private Set<String> states;
    private Set<String> alphabet;
    private String startState;
    private Set<String> finalStates;
    private Map<String, Map<String, String>> transitions;

    public FiniteAutomaton(Set<String> states, Set<String> alphabet, String startState, Set<String> finalStates, Map<String, Map<String, String>> transitions) {
        this.states = states;
        this.alphabet = alphabet;
        this.startState = startState;
        this.finalStates = finalStates;
        this.transitions = transitions;
    }

    public boolean accepts(String word) {
        String currentState = startState;
        for (char symbol : word.toCharArray()) {
            String symbolStr = String.valueOf(symbol);
            if (!alphabet.contains(symbolStr)) {
                return false;
            }
            if (transitions.containsKey(currentState) && transitions.get(currentState).containsKey(symbolStr)) {
                currentState = transitions.get(currentState).get(symbolStr);
            } else {
                return false;
            }
        }
        return finalStates.contains(currentState);
    }
}
