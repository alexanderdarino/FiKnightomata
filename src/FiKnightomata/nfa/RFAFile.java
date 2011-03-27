package finiteautomaton.nfa;

import finiteautomaton.dfa.DFA;
import finiteautomaton.nfa.Transitions;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Represents a Rivas Finite Automaton (RFA) File. Finite Automaton can be loaded from
 * an RFAFile using the {@link loadFiniteAutomaton()} method.
 * @author Alexander Darino
 */
public class RFAFile extends File {

    /**
     * Creates a new <code>RFAFile</code> instance by converting the given
     * pathname string into an abstract pathname.  If the given string is
     * the empty string, then the result is the empty abstract pathname.
     *
     * @param   pathname  A pathname string
     * @throws  NullPointerException
     *          If the <code>pathname</code> argument is <code>null</code>
     */
    public RFAFile(String filename) {
        super(filename);
    }

    /**
     * Loads the Finite Automaton's components into the specified fields. Returns
     * the label of the starting state if successful; else returns null.
     * @param states the add states
     * @param alphabet the add of input symbols
     * @param transitions the nondeterministic transition function
     * @param statesFinal the add of final states
     * @return the label of the starting state if successful; else returns <code>null</code>.
     */
    public DFA loadDFA()
    {
        return loadNFA().toDFA();
    }
    public NFA loadNFA()
    {
        Scanner fileIn = null;
        try {
            fileIn = new Scanner(this);
        } catch (FileNotFoundException ex) {
            return null;
        }
        assert fileIn != null;

        NFA.Builder builder = new NFA.Builder();

        //Transitions transitions = Transitions.create();


        int statesTotal = fileIn.nextInt();
        ArrayList<String> statesList = new ArrayList<String>(statesTotal);
        statesList.add(null);
        for (int i = 0; i < statesTotal; i++) {
            statesList.add(fileIn.next());
            builder.addState(statesList.get(statesList.size() - 1));
        }

        int symbolTotal = fileIn.nextInt();
        fileIn.nextLine(); // Consume EOL

        ArrayList<String> alphabetList = new ArrayList<String>(symbolTotal);
        alphabetList.add("");

        for (int i = 0; i < symbolTotal; i++) {
            alphabetList.add(fileIn.nextLine());
            builder.addAlphaSym(alphabetList.get(alphabetList.size() - 1));
        }

        for (String state : statesList.subList(1, statesList.size()))
        {
            String line = fileIn.nextLine();
            Scanner stateSetScanner = new Scanner(line);

            // Episilon Input
            {
                String set = stateSetScanner.next();
                Scanner stateScanner = new Scanner(set);
                stateScanner.useDelimiter(",");
                while (stateScanner.hasNext()) {
                    int index = stateScanner.nextInt();
                    builder.addTransition(state, "", set);
                    if (index == 0) continue;
                    String transitionState = getState(statesList, index);
                    builder.addTransition(state, "", transitionState);
                }
            }
            for (String symbol : alphabetList.subList(1, alphabetList.size())) {
                String set = stateSetScanner.next();
                Scanner stateScanner = new Scanner(set);
                stateScanner.useDelimiter(",");
                while (stateScanner.hasNext()) {
                    int index = stateScanner.nextInt();
                    String transitionState = getState(statesList, index);
                    if (transitionState == null) continue;
                    builder.addTransition(state, symbol, transitionState);
                }
            }
        }


        int startStateNum = fileIn.nextInt();
        builder.setStartState(getState(statesList, startStateNum));


        int finalStateTotal = fileIn.nextInt();

        for (int i = 0; i < finalStateTotal; i++) {
            builder.addAcceptingState(getState(statesList, fileIn.nextInt()));
        }

        return builder.build();

    }

    public static String getState(ArrayList<String> statesList, int index) {
        return statesList.get(index);
    }
}
