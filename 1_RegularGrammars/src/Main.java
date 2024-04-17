import java.util.*;

public class Main {
    public static void main (String[] args) {
        Set<String> nonTerminals = new HashSet<>(Arrays.asList("S", "A", "B", "C"));
        Set<String> terminals = new HashSet<>(Arrays.asList("a", "b", "c", "d"));
        Map<String, List<String>> productions = new HashMap<>();
        productions.put("S", Collections.singletonList("dA"));
        productions.put("A", Arrays.asList("aB", "b"));
        productions.put("B", Arrays.asList("bC", "d"));
        productions.put("C", Arrays.asList("cB", "aA"));
        Grammar grammar = new Grammar(nonTerminals, terminals, productions);

        List<String> strings = grammar.generateStrings("S", 5);
        for (String str : strings) {
            System.out.println(str);
        }

        FiniteAutomaton finiteAutomaton = grammar.toFiniteAutomaton();

        String checkWord = "dabaabaabcbaabab";
        System.out.println(finiteAutomaton.accepts(checkWord));

        System.out.println(grammar.classifyGrammar());
    }
}
