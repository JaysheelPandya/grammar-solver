// The GrammarSolver class reads an input file with grammar in
// Backus-Naur form and randomly generates elements of the grammar

import java.util.*;

public class GrammarSolver {

    // Stores the grammar
    private SortedMap<String, String[]> grammarStorage;

    // PRE: The given grammar must not be empty
    // (throws IllegalArgumentException otherwise).
    // There should not be duplicate non-terminal symbols
    // in the given grammar (throws IllegalArgumentException otherwise).
    // POST: Takes the given grammar and stores it conveniently,
    // separated by non-terminal symbols and their given rules
    public GrammarSolver(List<String> grammar) {
        if (grammar.isEmpty()) {
            throw new IllegalArgumentException();
        }
        grammarStorage = new TreeMap<>();
        for (String s : grammar)  {
            String[] parts = s.split("::=");
            if (grammarStorage.containsKey(parts[0])) {
                throw new IllegalArgumentException();
            }
            String[] rules = parts[1].split("[|]");
            grammarStorage.put(parts[0], rules);
        }
    }

    // Returns true if the stored grammar contains the
    // given non-terminal symbol
    public boolean grammarContains(String symbol) {
        return grammarStorage.containsKey(symbol);
    }

    // PRE: The stored grammar must contain the given symbol,
    // and the given times to generate must be greater than or equal to 0
    // (throws IllegalArgumentException otherwise).
    // POST: Creates and returns a randomly generated grammar the
    // given number of times
    public String[] generate(String symbol, int times) {
        if (!grammarContains(symbol) || times < 0) {
            throw new IllegalArgumentException();
        }
        String[] results = new String[times];
        for (int i = 0; i < times; i++) {
            results[i] = generateGrammar(symbol).trim();
        }
        return results;
    }

    // Creates and returns a randomly generated grammar
    // using the given symbol
    private String generateGrammar(String symbol) {
        String result = "";
        String[] rules = grammarStorage.get(symbol);
        Random rand = new Random();
        int chosenRule = rand.nextInt(rules.length);
        String[] parts = rules[chosenRule].trim().split("[ \t]+");
        for (String part : parts) {
            if (grammarStorage.containsKey(part)) {
                result += generateGrammar(part);
            }
            else {
                result += part + " ";
            }
        }
        return result;
    }

    // Returns all the non-terminal symbols in the stored grammar
    public String getSymbols() {
        return grammarStorage.keySet().toString();
    }
}
