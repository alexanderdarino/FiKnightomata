package finiteautomaton.dfa;

import finiteautomaton.*;
import java.util.ArrayList;

public class Execution extends finiteautomaton.Execution
{
    protected ArrayList<Traversal> traversals = new ArrayList();

//    protected Execution (Execution execution, Traversal traversal, boolean accepted)
//    {
//        super(accepted);
//        traversals = execution.getTraversals();
//        traversals.add(traversal);
//
//    }

    protected Execution(ArrayList transitions, boolean accepted) {
        super(accepted);
        this.traversals = transitions;
    }

    public void printLastTraversal()
    {
        System.out.println(traversals.get(traversals.size() - 1));
        System.out.println(accepted ? "ACCEPTED" : "REJECTED");
    }

    public ArrayList<Traversal> getTraversals()
    {
        return traversals;
    }

//    @Override
//    public String toString() {
//        StringBuilder r_val = new StringBuilder();
//        for (Traversal i : traversals)
//        {
//            r_val.append(i).append(", ");
//        }
//        r_val.delete(r_val.length() - 2, r_val.length() - 1);
//        return r_val.toString();
//    }

    /**
     * Returns a comma-separated list of state traversals, followed by
     * "ACCEPTED" if the automaton accepted the input or "REJECTED" if the automaton
     * rejected in the input.
     * @return comma-separated list of state traversals and acceptance status
     */
    @Override
    public String toString() {
        StringBuilder rval = new StringBuilder();

        for (Traversal i : traversals) {
            rval.append(i).append("\n");
        }
        rval.append(accepted ? "ACCEPTED" : "REJECTED");

        return rval.toString();
    }

    public static class Builder extends finiteautomaton.Execution.Builder
    {

        protected ArrayList<Traversal> traversals = new ArrayList();

        /**
         * Constructs a builder object.
         */
        public Builder(){}

        public Builder(Execution execution)
        {
            traversals.addAll(execution.getTraversals());
        }

        Builder addTraversal (String src, String alphaSym, String dst)
        {
            traversals.add(new Traversal(src, alphaSym, dst));
            return this;
        }

        String getLastState()
        {
            assert !traversals.isEmpty();
            return traversals.get(traversals.size() - 1).getStateEnd();
        }

        protected Execution build(boolean accepted) {
            return new Execution (traversals, accepted);
        }
    }

    public static class Traversal extends finiteautomaton.Traversal
    {
        protected String stateEnd;

        @Override
        public String getInput() {
            return (String) input;
        }

        @Override
        public String getStateStart() {
            return stateStart;
        }

        public String getStateEnd() {
            return stateEnd;
        }

        protected Traversal (String stateStart, String input, String stateEnd) {
            this.stateStart = stateStart;
            this.input = input;
            this.stateEnd = stateEnd;
        }

        @Override
        public String toString() {
            StringBuilder rval = new StringBuilder("(" + stateStart + ", " + input + "): " + stateEnd);
            return rval.toString();
        }
    }
}