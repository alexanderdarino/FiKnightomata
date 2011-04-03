package FiKnightomata;

import FiKnightomata.*;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

/**
 * Represents a Deterministic Finite Automaton (DFA).
 * @author Alexander Darino
 * @see finiteautomaton.FiniteAutomaton

 */
public class DFA extends FiniteAutomaton
{

    /**
     * The automaton's transitions function
     */
    //protected HashMap<String, HashMap<String, String>> transitions;// = new HashMap();
    protected Transitions transitions;

    /**
     * Creates a DFA using the specified states and alphabet. All transitions
     * are initialized to null, and the start FiniteAutomaton and accepting states
     * are left undefined.
     * @param states the set of states (labeled by <code>String</code>s).
     * @param alphabet set of alphabet symbols (labeled by  <code>String</code>s).
     */
    protected DFA(HashSet<String> states, HashSet<String> alphabet)
    {
        transitions = Transitions.create(states, alphabet);

        if (transitions == null) {
            throw new IllegalStateException();
        }
    }

    @Override
    public Execution execute(String[] input)
    {

        String stateCurrent = stateStart, stateNext = stateStart;
        Execution.Builder builder = new Execution.Builder();

        for (String i : input) {
            stateNext = transitions.get(stateCurrent, i);
            builder.addTraversal(stateCurrent, i, stateNext);
            stateCurrent = stateNext;
        }

        return builder.build(isAccepting(stateNext));
    }

    /**

     @param sym
     @return
     */
    public Execution execute(String sym)
    {
        Execution.Builder builder = new Execution.Builder();
        String stateNext = transitions.get(stateStart, sym);
        builder.addTraversal(stateStart, sym, stateNext);
        return builder.build(isAccepting(stateNext));
    }

    /**

     @param sym
     @param execution
     @return
     */
    public Execution execute(String sym, Execution execution)
    {
        if (execution == null) {
            return null;
        }

        Execution.Builder builder = new Execution.Builder(execution);
        String stateNext = transitions.get(builder.getLastState(), sym);
        builder.addTraversal(builder.getLastState(), sym, stateNext);
        return builder.build(isAccepting(stateNext));
    }

//    private static Transitions transitionsAsDFA(HashMap<String, HashMap<String, HashSet<String>>> transitions)
//    {
////        HashMap<String, HashMap<String, String>> r_val = new HashMap();
////        Set<String> nullErrorTransitionStates = (Set<String>) ((HashMap<String, HashMap<String, HashSet<String>>>)transitions.clone()).keySet();
////        nullErrorTransitionStates.remove(null);
////        for (String FiniteAutomaton: nullErrorTransitionStates)
////        {
////            r_val.put(FiniteAutomaton, new HashMap());
////            HashMap<String, HashSet<String>> nullErrorStateTransitionFunction = (HashMap<String, HashSet<String>>) (transitions.get(FiniteAutomaton)).clone();
////            nullErrorStateTransitionFunction.remove(null);
////            for (String input : nullErrorStateTransitionFunction.keySet())
////            {
////                HashSet<String> stateTransitions = nullErrorStateTransitionFunction.get(input);
////                if (stateTransitions == null || stateTransitions.size() != 1) return null;
////
////                r_val.get(FiniteAutomaton).put(input, (String) stateTransitions.toArray()[0]);
////            }
////        }
////
////        return r_val;
//
//        Transitions r_val = new Transitions();
//        for (String FiniteAutomaton : transitions.keySet())
//    }
    /**

     @return
     */
    public Set<String> getStates()
    {
        return transitions.getStates();
    }

    Transitions getTransitions()
    {
        return transitions;
    }

    /**

     @return
     */
    public Set<String> getAlphabet()
    {
        return transitions.getAlphabet();
    }

    private void clean()
    {
        Set<String> visited = new HashSet();
        Stack<String> dfsStack = new Stack();
        Set<String> keep = new HashSet();
        Stack<String> path = new Stack();

        dfsStack.push(stateStart);

        Set<String> alphabet = transitions.getAlphabet();

        // Mark all states that are on a simple path to an accepting FiniteAutomaton
        while (!dfsStack.isEmpty())
        {

            String state = dfsStack.pop();
            path.push(state);
            visited.add(state);

            if (isAccepting(state))
            {
                //String[] ancestors = new String[dfsStack.size()];
                //ancestors = dfsStack.toArray(ancestors);
                for (String i : path)
                {
                    keep.add(i);
                }
            }

            // Add unvisited neighbors
            boolean backTracking = true;
            for (String alphaSym : alphabet)
            {
                String neighbor = transitions.get(state, alphaSym);

                if (neighbor == null) continue;

                if (!visited.contains(neighbor))
                {
                    dfsStack.push(neighbor);
                    backTracking = false;
                }
            }
            if (backTracking)
                path.pop();
        }

        // Mark all states that are on cycles leading to an accepting FiniteAutomaton
        
        visited = new HashSet();
        dfsStack = new Stack();
        path = new Stack();
        dfsStack.add(stateStart);
        while (!dfsStack.isEmpty())
        {
            String state = dfsStack.pop();
            path.add(state);
            visited.add(state);

            // Add unvisited neighbors
            boolean backTracking = true;
            for (String alphaSym : alphabet)
            {
                String neighbor = transitions.get(state, alphaSym);

                if (neighbor == null) continue;

                if (!visited.contains(neighbor))
                {
                    dfsStack.push(neighbor);
                    backTracking = false;
                }

                if (path.contains(neighbor) && keep.contains(neighbor))
                {
                    for (String i : path)
                    {
                        keep.add(i);
                    }
                }
            }
            if (backTracking)
                path.pop();
        }

        for (String state : getStates())
        {
            if (!keep.contains(state))
                getTransitions().removeState(state);
        }

    }

    /**

     */
    public static class Builder
    {

        /**

         */
        protected DFA dfa = new DFA(new HashSet(), new HashSet());
        //protected Set<SetLabel> setLabels = new HashSet();

        /**

         */
        protected Builder()
        {
        }

        /**

         @return
         */
        public static Builder create()
        {
            return new Builder();
        }

        /**

         @param alphabet
         @return
         */
        public static Builder create(Set<String> alphabet)
        {
            if (alphabet.contains("") || alphabet == null) {
                return null;
            }
            return new Builder(alphabet);
        }

        /**

         @param alphabet
         */
        protected Builder(Set<String> alphabet)
        {
            for (String alphaSym : alphabet) {
                addAlphaSym(alphaSym);
            }
        }

//        void addSetLabel(SetLabel setLabel)
//        {
//            setLabels.add(setLabel);
//        }

        /**

         @param state 
         @return
         */
        public boolean addState(String state)
        {
            return dfa.getTransitions().addState(state);
        }

        /**

         @param state
         @return
         */
        public static String setToState(String state)
        {
            return ("{" + state + "}");
        }

        /**

         @param states
         @return
         */
        public static String setToState(Set<String> states)
        {

            String[] statesArray = new String[1];
            statesArray = states.toArray(statesArray);
            if (statesArray.length == 1) {
                return setToState(statesArray[0]);
            }

            StringBuilder stateNew = new StringBuilder("{");
            for (int i = 0; i < states.size() - 1; i++) {
                if (statesArray[i] == null) {
                    continue;
                }
                stateNew.append(statesArray[i]).append(", ");
            }
            stateNew.append(statesArray[statesArray.length - 1]).append("}");
            return stateNew.toString();
        }

        /**

         @param alphaSym
         @return
         */
        public boolean addAlphaSym(String alphaSym)
        {
            return dfa.getTransitions().addAlphaSym(alphaSym);
        }

        /**

         @param stateStart
         */
        public void setStartState(String stateStart)
        {
            dfa.setStartState(stateStart);
        }

        /**

         @param stateAccepting
         */
        public void addAcceptingState(String stateAccepting)
        {
            dfa.addAcceptingState(stateAccepting);
        }

        /**

         @param source
         @param input
         @param dest
         @return
         */
        public boolean setTransition(String source, String input, String dest)
        {
            if (!dfa.getStates().contains(source) || !dfa.getStates().contains(dest) || !dfa.getAlphabet().contains(input)) {
                return false;
            }
            //dfa.transitions.get(source).put(input, dest);
            dfa.getTransitions().set(source, input, dest);
            return true;
        }

        /**

         @return
         */
        public DFA build()
        {
            dfa.clean();
            return dfa;
        }

        /**

         @param state
         @param alphaSym
         @return
         */
        public String getTransition(String state, String alphaSym)
        {
            return dfa.getTransitions().get(state, alphaSym);
        }
    }

    /**

     */
    public static class Execution extends FiKnightomata.FiniteAutomaton.Execution
    {
        /**

         */
        protected ArrayList<Traversal> traversals = new ArrayList();

    //    protected Execution (Execution execution, Traversal traversal, boolean accepted)
    //    {
    //        super(accepted);
    //        traversals = execution.getTraversals();
    //        traversals.add(traversal);
    //
    //    }

        /**

         @param transitions
         @param accepted
         */
        protected Execution(ArrayList transitions, boolean accepted)
        {
            super(accepted);
            this.traversals = transitions;
        }

        /**

         */
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
         * Returns a comma-separated list of FiniteAutomaton traversals, followed by
         * "ACCEPTED" if the automaton accepted the input or "REJECTED" if the automaton
         * rejected in the input.
         * @return comma-separated list of FiniteAutomaton traversals and acceptance status
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

        /**

         */
        protected static class Builder extends FiKnightomata.FiniteAutomaton.Execution.Builder
        {

            /**

             */
            protected ArrayList<Traversal> traversals = new ArrayList();

            /**
             * Constructs a builder object.
             */
            public Builder(){}

            /**

             @param execution
             */
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

        /**

         */
        public static class Traversal extends FiKnightomata.FiniteAutomaton.Execution.Traversal
        {
            /**

             */
            protected String stateEnd;

            @Override
            public String getInput() {
                return (String) input;
            }

            @Override
            public String getStateStart() {
                return stateStart;
            }

            /**

             @return
             */
            public String getStateEnd()
            {
                return stateEnd;
            }

            /**

             @param stateStart
             @param input
             @param stateEnd
             */
            protected Traversal(String stateStart, String input, String stateEnd)
            {
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

    /**
     
     */
    public static class Transitions
    {

        //protected Transitions(){}

        /**

         @return
         */
        public static Transitions create()
        {
            return new Transitions(new HashSet(), new HashSet());
        }
        /**

         @param states
         @param alphabet
         @return
         */
        public static Transitions create(Set<String> states, Set<String> alphabet)
        {
            if (alphabet.contains("")) return null;
                return new Transitions(states, alphabet);
        }

        private Transitions(Set<String> states, Set<String> alphabet)
        {
            addAlphaSym("");
            addState(null);
            for (String state : states)
                addState(state);
            for (String alphaSym : alphabet)
                addAlphaSym(alphaSym);
        }

        /**

         @param state
         @return
         */
        protected boolean addState(String state)
        {
            if (states.contains(state)) {
                return false;
            }
            HashMap<String, String> map = new HashMap();
            for (String alphaSym : alphabet) {
                map.put(alphaSym, null);
            }
            map.put("", state);
            transitions.put(state, map);
            states.add(state);
            return true;
        }

        /**

         @param alphaSym
         @return
         */
        protected boolean addAlphaSym(String alphaSym)
        {
            if (alphaSym.compareTo("") == 0) {
                return false;
            }
            if (alphabet.contains(alphaSym)) {
                return false;
            }
            for (String state : states) {
                transitions.get(state).put(alphaSym, null);
            }
            alphabet.add(alphaSym);
            return true;
        }

        /**

         @param src
         @param alphaSym
         @param dst
         @return
         */
        protected boolean set(String src, String alphaSym, String dst)
        {
            if (!(states.contains(src) && alphabet.contains(alphaSym) && states.contains(dst))) {
                return false;
            }
            transitions.get(src).put(alphaSym, dst);
            return true;
        }


        /**

         @param src
         @param alphaSym
         @return
         */
        public String get(String src, String alphaSym)
        {
            if (!(states.contains(src) && alphabet.contains(alphaSym))) {
                return null;
            }
            return transitions.get(src).get(alphaSym);
        }

        /**

         @return
         */
        public Set<String> getStates()
        {
            return (Set<String>) states.clone();
        }

        /**

         @return
         */
        public Set<String> getAlphabet()
        {
            return (Set<String>) alphabet.clone();
        }

        /**

         */
        protected HashMap<String, HashMap<String, String>> transitions = new HashMap();
        /**

         */
        protected HashSet<String> states = new HashSet();
        /**

         */
        protected HashSet<String> alphabet = new HashSet();


        // Returns states that transitioned to it
        Set<String> removeState(String removalState)
        {
            Set<String> r_val = new HashSet();
            for (String state : states)
            {
                for (String alphaSym : alphabet)
                {
                    String transition = transitions.get(state).get(alphaSym);
                    if ((transition == null && removalState == null) || (removalState != null && transition != null && transition.compareTo(removalState) == 0))
                    {
                        transitions.get(state).put(alphaSym, null);
                        r_val.add(state);
                    }
                }
            }
            states.remove(removalState);
            return r_val;
        }
    }


    /**

     */
    public void print()
    {
        for (String state : transitions.getStates()) {
            System.out.println("STATE:    " + state);
            for (String alphaSym : transitions.getAlphabet()) {
                System.out.println(alphaSym + ":\t" + transitions.get(state, alphaSym));
            }
            System.out.println();
        }
    }


}
