import java.util.*;

class Main {
    public static void main(String[] args) {
        // Define the finite automaton
        FiniteAutomaton fa = new FiniteAutomaton();
        fa.addState("q0");
        fa.addState("q1");
        fa.addState("q2");
        fa.addState("q3");
        fa.setStartState("q0");
        fa.addFinalState("q3");
        fa.addTransition("q0", 'a', "q1");
        fa.addTransition("q1", 'b', "q1");
        fa.addTransition("q1", 'a', "q2");
        fa.addTransition("q0", 'a', "q0");
        fa.addTransition("q2", 'c', "q3");
        fa.addTransition("q3", 'c', "q3");

        // Convert FA to regular grammar
        Grammar regularGrammar = fa.convertToRegularGrammar();
        System.out.println("Regular Grammar:");
        System.out.println(regularGrammar);

        // Determine if FA is deterministic or non-deterministic
        String determinism = fa.isDeterministic() ? "Deterministic" : "Non-deterministic";
        System.out.println("Finite Automaton is " + determinism);

        // Convert NDFA to DFA
        FiniteAutomaton dfa = fa.convertToDFA();
        System.out.println("NDFA -> DFA:");
        System.out.println(dfa);
    }
}
