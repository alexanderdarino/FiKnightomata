package FiKnightomata;

import java.util.*;
//import java.util.HashMap;
//import java.util.HashSet;

/**
 * Represents an abstract finite automaton (FA). Every finite automaton
 * consists of
 * <ul>
 * <li>a set of states</li>
 * <li>an set of input symbols called the alphabet (of
 * type <code>AlphabetTypecodett>)</li>
 * <li>a transition function, which is defined differently for deterministic FA (DFA)
 * and nondeterministic FA (NFA)</li>
 * <li>a start state</li>
 * <li>a set of accepting states</li>
 * </ul>
 * A finite automaton can process a given input string using the {@link FiniteAutomaton.execute()}
 * method to yield the {@link finiteautomaton.Execution} of the automaton on the input.
 * Each Execution maintains the list of {@link finiteautomaton.Transition}s
 * undergone by the automaton on the given input and specifies whether the terminal
 * state is also an accepting state for the automaton.
 * @author Alexander Darino
 * @see finiteautomaton.Execution
 * @see finiteautomaton.Transition
 */
public abstract class FiniteAutomaton {

    /**
     * Initializes a Finite Automaton.
     */
    protected FiniteAutomaton (){}

    /**
     * The set of states (labeled by <code>String</code>s).
     */
    //protected HashSet<String> states = new HashSet();
    /**
     * The set of alphabet symbols (labeled by <code>String</code>s).
     */
    //protected HashSet<String> alphabet = new HashSet();

//    /**
//     * The automaton's transition function. Maps a state onto the
//     * state's transition function, which is defined differently for DFA and
//     * NFA.
//     */
//    protected HashMap<String, HashMap<String, ?>> transitions;

    /**
     * The automaton's start state.
     */
    protected String stateStart;
    /**
     * The set of the automaton's accepting states.
     */
    protected Set<String> statesAccepting = new HashSet();

    /**
     * Returns the execution of the automaton on a given input.
     * @param input the input string to process
     * @return the automaton's execution
     */
    public abstract Execution execute(String[] input);

    public abstract Set<String> getAlphabet();

    public abstract Set<String> getStates();

    public String getStartState()
    {
        return stateStart;
    }

    protected Set<String> getAcceptingStates()
    {
        return statesAccepting;
    }

    public Set<String> getAcceptingStatesView()
    {
        return (Set<String>) Collections.unmodifiableSet(statesAccepting);
    }

    /**
     * Returns <code>true</code> if the state identified by stateLabel is an
     * accepting state for this automaton
     * @param stateLabel the state's label
     * @return true if the specified state is an accepting state
     */
    public boolean isAccepting(String stateLabel)
    {
        return statesAccepting.contains(stateLabel);
    }

    protected void setStartState(String stateStart)
    {
        this.stateStart = stateStart;
    }

    protected void addAcceptingState (String acceptingState)
    {
        statesAccepting.add(acceptingState);
    }
}