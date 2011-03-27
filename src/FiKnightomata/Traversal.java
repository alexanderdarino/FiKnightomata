package finiteautomaton;

/**
 * Represents the transitions made by an automaton from a state on a given input.
 * @author Alexander Darino
 * @param <AlphabetType> the input type
 */
public abstract class Traversal
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