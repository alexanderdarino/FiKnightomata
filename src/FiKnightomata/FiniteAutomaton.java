package FiKnightomata;

import java.util.*;

/**
 * Represents an abstract finite automaton (FA). Every finite automaton
 * consists of
 * <ul>
 * <li>a set of states</li>
 * <li>a set of input symbols called the alphabet</li>
 * <li>a transition function (defined and implemented differently for deterministic FA (DFA)
 * and nondeterministic FA (NFA))</li>
 * <li>a start FiniteAutomaton</li>
 * <li>a set of accepting states</li>
 * </ul>
 * A finite automaton can process a given input string using the {@link FiniteAutomaton.execute()}
 * method, in either an online or an offline fashion, to yield the {@link finiteautomaton.Execution} of the automaton on the input.
 * Each Execution maintains the list of {@link finiteautomaton.Transition}s
 * undergone by the automaton on the given input and specifies whether the terminal
 * FiniteAutomaton is also an accepting FiniteAutomaton for the automaton.
 * @author Alexander Darino
 * @see FiKnightomata.Execution
 * @see finiteautomaton.Transition
 */
public abstract class FiniteAutomaton {

    /**
     * Initializes a Finite Automaton.
     */
    protected FiniteAutomaton (){}

    /**
     * The automaton's start FiniteAutomaton.
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

    /**
        Returns the set of alphabet symbols processed by the automaton.
     @return the set of alphabet symbols processed by the automaton.
     */
    public abstract Set<String> getAlphabet();

    /**
        Returns the set of states comprising the automaton.
     @return the set of states comprising the automaton.
     */
    public abstract Set<String> getStates();

    /**
        Returns the start FiniteAutomaton of the automaton.
     @return the start FiniteAutomaton of the automaton.
     */
    public String getStartState()
    {
        return stateStart;
    }

    /**
    Returns a view of the accepting states of the automaton.
     @return a view of the accepting states of the automaton
     */
    public Set<String> getAcceptingStates()
    {
        return (Set<String>) Collections.unmodifiableSet(statesAccepting);
    }


    /**
     * Returns true if the specified FiniteAutomaton is accepting for this automaton.
     @param state
     @return true if the specified FiniteAutomaton is accepting for this automaton
     */
    public boolean isAccepting(String state)
    {
        return statesAccepting.contains(state);
    }

    /**
    Specifies the starting FiniteAutomaton for this automaton.
     @param stateStart the starting FiniteAutomaton for this automaton
     */
    protected void setStartState(String stateStart)
    {
        this.stateStart = stateStart;
    }

    /**
     Adds the specified state as an accepting state for this automaton.
     @param state state to add as an accepting state for this automaton
     */
    protected void addAcceptingState(String state)
    {
        statesAccepting.add(state);
    }


    /**
     * Maintains the list of {@link finiteautomaton.Traversal}s
     * undergone by the automaton executing a given input and specifies whether the
     * terminating FiniteAutomaton is also an accepting FiniteAutomaton of the automaton.
     * @author Alexander Darino
     */
    public static abstract class Execution {

        /**
        Constructs a new Execution object whose terminal FiniteAutomaton is accepting.
         @param accepted true if the terminal FiniteAutomaton is accepting; false otherwise
         */
        protected Execution(boolean accepted)
        {
            this.accepted = accepted;
        }

        /**
         * Sequence of traversals undergone by the automaton.
         */
        protected ArrayList<Traversal> traversals = null;


        /**
         * true if the terminal FiniteAutomaton is accepting.
         */
        protected final boolean accepted;

        /**
         * Returns true if the terminal FiniteAutomaton is an accepting FiniteAutomaton for the automaton
         * @return true if the terminal FiniteAutomaton is an accepting FiniteAutomaton for the automaton
         */
        public boolean isAccepted() {
            return accepted;
        }

        /**
         * Returns the sequence of FiniteAutomaton traversals undergone by the automaton
         * @return the sequence of FiniteAutomaton traversals undergone by the automaton
         */
        public ArrayList<? extends Traversal> getTraversals() {
            return traversals;
        }

        @Override
        public abstract String toString();

        /**
         * A class for building an {@link FiKnightomata.FiniteAutomaton.Execution} object.
         */
        public static abstract class Builder {

            /**
             * Constructs a builder object.
             */
            protected Builder() {
            }

            /**
             * Builds the constructed Execution object.
             * @param accepted true if the terminal FiniteAutomaton is an accepting FiniteAutomaton for
                                the automaton; false otherwise
             * @return the constructed Execution object
             */
            protected abstract Execution build(boolean accepted);
        }

        /**
         * Represents the traversals made by an automaton from a state on a given input.
         * @author Alexander Darino
         */
        public static abstract class Traversal
        {
            /**
             * Represents the label of state being transitioned from
             */
            protected String stateStart;
            /**
             * Represents the input on which the transition is occurring
             */
            protected String input;

            /**
             * Returns the input on which the transition is occurring
             * @return the input on which the transition is occurring
             */
            public String getInput() {
                return input;
            }

            /**
             * Returns the label of state being transitioned from
             * @return the label of state being transitioned from
             */
            public String getStateStart() {
                return stateStart;
            }
        }
    }
}