import java.util.*;

public class Automaton {
    private Set<String> states;
    private Set<String> alphabet;
    private Map<String, Map<String, String>> transitions;
    private String initialState;
    private Set<String> finalStates;

    public Automaton(Set<String> states, Set<String> alphabet, Map<String, Map<String, String>> transitions,
                           String initialState, Set<String> finalStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.initialState = initialState;
        this.finalStates = finalStates;
    }

}
