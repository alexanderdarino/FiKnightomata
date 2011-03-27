package finiteautomaton.dfa;

import finiteautomaton.*;
import java.lang.StringBuilder;
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
     * are initialized to null, and the start state and accepting states
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

    public Execution execute(String sym)
    {
        Execution.Builder builder = new Execution.Builder();
        String stateNext = transitions.get(stateStart, sym);
        builder.addTraversal(stateStart, sym, stateNext);
        return builder.build(isAccepting(stateNext));
    }

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
////        for (String state: nullErrorTransitionStates)
////        {
////            r_val.put(state, new HashMap());
////            HashMap<String, HashSet<String>> nullErrorStateTransitionFunction = (HashMap<String, HashSet<String>>) (transitions.get(state)).clone();
////            nullErrorStateTransitionFunction.remove(null);
////            for (String input : nullErrorStateTransitionFunction.keySet())
////            {
////                HashSet<String> stateTransitions = nullErrorStateTransitionFunction.get(input);
////                if (stateTransitions == null || stateTransitions.size() != 1) return null;
////
////                r_val.get(state).put(input, (String) stateTransitions.toArray()[0]);
////            }
////        }
////
////        return r_val;
//
//        Transitions r_val = new Transitions();
//        for (String state : transitions.keySet())
//    }
    public Set<String> getStates()
    {
        return transitions.getStates();
    }

    Transitions getTransitions()
    {
        return transitions;
    }

    public Set<String> getAlphabet()
    {
        return transitions.getAlphabet();
    }

    private void clean()
    {

        //Remove all-null non-accepting transition states
        //for (String state : transitions.getStates())


//        {
//            Queue<String> states = new LinkedList();
//            states.addAll(transitions.getStates());
//            states.remove(null);
//            while (!states.isEmpty())
//            {
//                String state = states.poll();
//                if (isAccepting(state))
//                {
//                    continue;
//                }
//                boolean remove = true;
//                for (String alphaSym : transitions.getAlphabet())
//                {
//                    if (transitions.get(state, alphaSym) != null)
//                    {
//                        remove = false;
//                        break;
//                    }
//                }
//                if (remove)
//                {
//                    states.addAll(transitions.removeState(state));
//                }
//            }
//        }

        //remove unreachable states
        Set<String> visited = new HashSet();
        Stack<String> dfsStack = new Stack();

        dfsStack.push(stateStart);
        visited.add(stateStart);

        for (String alphaSym : transitions.getAlphabet()) 
        {
            String neighbor = transitions.get(dfsStack.peek(), alphaSym);

            if (neighbor == null) continue;

            dfsStack.push(neighbor);
            visited.add(neighbor);
        }
        
        Set<String> alphabet = transitions.getAlphabet();


        {
            boolean deadCycle = true;
            while (!dfsStack.isEmpty())
            {
                String state = dfsStack.pop();

                //boolean deadCycle = true;

                for (String alphaSym : alphabet)
                {
                    String neighbor = transitions.get(state, alphaSym);

                    if (neighbor == null) continue;

                    if (isAccepting(neighbor))
                        deadCycle = false;

                    if (visited.contains(neighbor))
                    {
        //                    String deleteState;
        //                    if(dfsStack.isEmpty()) continue;
        //                    deleteState = dfsStack.peek();
        //                    while(!deleteState.equals(neighbor))
        //                    {
//                            transitions.removeState(deleteState);
//                            deleteState = dfsStack.pop();
//                            if(dfsStack.isEmpty()) break;

                        if (deadCycle)
                            transitions.set(state, alphaSym, null);
                        else
                            deadCycle = true;
                        continue;
                    }

                    dfsStack.push(neighbor);
                    visited.add(neighbor);


                }

                //if(deadCycle)
                //    transitions.removeState(state);
            }
    }


    }

    public static class Builder
    {

        protected DFA dfa = new DFA(new HashSet(), new HashSet());
        protected Set<SetLabel> setLabels = new HashSet();

        protected Builder()
        {
        }

        public static Builder create()
        {
            return new Builder();
        }

        public static Builder create(Set<String> alphabet)
        {
            if (alphabet.contains("") || alphabet == null) {
                return null;
            }
            return new Builder(alphabet);
        }

        protected Builder(Set<String> alphabet)
        {
            for (String alphaSym : alphabet) {
                addAlphaSym(alphaSym);
            }
        }

        void addSetLabel(SetLabel setLabel)
        {
            setLabels.add(setLabel);
        }

        public boolean addState(String state)
        {
            return dfa.getTransitions().addState(state);
        }

        public static String setToState(String state)
        {
            return ("{" + state + "}");
        }

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

        public boolean addAlphaSym(String alphaSym)
        {
            return dfa.getTransitions().addAlphaSym(alphaSym);
        }

        public void setStartState(String stateStart)
        {
            dfa.setStartState(stateStart);
        }

        public void addAcceptingState(String stateAccepting)
        {
            dfa.addAcceptingState(stateAccepting);
        }

        public boolean setTransition(String source, String input, String dest)
        {
            if (!dfa.getStates().contains(source) || !dfa.getStates().contains(dest) || !dfa.getAlphabet().contains(input)) {
                return false;
            }
            //dfa.transitions.get(source).put(input, dest);
            dfa.getTransitions().set(source, input, dest);
            return true;
        }

        public DFA build()
        {
            dfa.clean();
            return dfa;
        }
    }

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
