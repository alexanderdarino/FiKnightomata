package finiteautomaton;

import java.util.ArrayList;
import finiteautomaton.FiniteAutomaton.*;

/**
 * Maintains the list of {@link finiteautomaton.Traversal}s
 * undergone by the automaton executing a given input and specifies whether the
 * terminating state is also an accepting state of the automaton.
 * @author Alexander Darino
 */
public abstract class Execution {

    protected Execution (boolean accepted)
    {
        this.accepted = accepted;
    }

    /**
     * Sequence of traversals undergone by the automaton
     */
    protected ArrayList<Traversal> traversals = null;
    /**
     * Specifies whether the terminal state is an accepting state
     */
    protected final boolean accepted;

    /**
     * Returns <code>true</code> if the terminal state is an accepting state for the automaton
     * @return <code>true</code> if the terminal state is an accepting state for the automaton
     */
    public boolean isAccepted() {
        return accepted;
    }

    /**
     * Returns the sequence of state traversals undergone by the automaton
     * @return the sequence of state traversals undergone by the automaton
     */
    public ArrayList<? extends Traversal> getTraversals() {
        return traversals;
    }


    /**
     * Returns a comma-separated list of state traversals, followed by
     * "ACCEPTED" if the automaton accepted the input or "REJECTED" if the automaton
     * rejected in the input.
     * @return comma-separated list of state traversals and acceptance status
     */
    @Override
    public abstract String toString();

//    /**
//     * Returns a comma-separated list of state traversals, followed by
//     * "ACCEPTED" if the automaton accepted the input or "REJECTED" if the automaton
//     * rejected in the input.
//     * @return comma-separated list of state traversals and acceptance status
//     */
//    @Override
//    public String toString() {
//        StringBuilder rval = new StringBuilder();
//
//        for (Traversal i : traversals) {
//            rval.append(i).append(", ");
//        }
//        rval.append(accepted ? "ACCEPTED" : "REJECTED");
//
//        return rval.toString();
//    }



    /**
     * A class for building an {@link finiteautomaton.Execution} object.
     */
    public static abstract class Builder {

        /**
         * Constructs a builder object.
         */
        protected Builder() {
        }

        /**
         * Builds the constructed Execution object.
         * @param accepted specifies whether the terminal state is an accepting state
         * @return the constructed Execution object
         */
        protected abstract Execution build(boolean accepted);
    }
}
