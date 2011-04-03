package FiKnightomata;

//import finiteautomaton.SetLabel;
import FiKnightomata.*;
import FiKnightomata.DFA;
import java.util.*;

/**
 * Represents a Nondeterministic Finite Automaton (NFA).
 * @author Alexander Darino
 * @see finiteautomaton.FiniteAutomaton

 */
public class NFA extends FiniteAutomaton{

    /**
     * The automaton's transition function
     */
    protected Transitions transitions;// = new HashMap();

    /**
     * Creates a NFA using the specified states and alphabet. All transitions
     * are initialized to null, and the start stateStartNew and accepting states
     * are left undefined.
     * @param states the add of states (labeled by <code>String</code>s).
     * @param alphabet add of alphabet symbols (labeled by <code>String</code>s).
     */
    protected NFA(Set<String> states, Set<String> alphabet)
    {
        transitions = Transitions.create(states, alphabet);
    }



    @Override
    public FiKnightomata.FiniteAutomaton.Execution execute(String[] input) {
        throw new UnsupportedOperationException();
    }

    /**

     @return
     */
    public Set<String> getStates()
    {
        return transitions.getStates();
    }

    /**

     @return
     */
    public Set<String> getAlphabet()
    {
        return transitions.getAlphabet();
    }

    /**
     
     @return
     */
    public Transitions getTransitions()
    {
        return transitions;
    }

    
    /**

     @return
     */
    public DFA toDFA()
    {
        return toDFA(toEpisilonFreeNFA());
    }
    
    /**

     @param epsilonFreeNFA
     @return
     */
    protected DFA toDFA(NFA epsilonFreeNFA)
    {
        DFA.Builder dfaBuilder = DFA.Builder.create(epsilonFreeNFA.getAlphabet());
        Queue<SetLabel> bfs = new LinkedList();
        HashSet<String> visited = new HashSet<String>();
        
        
        {
            SetLabel stateStartNew = new SetLabel(epsilonFreeNFA.getStartState());

            bfs.add(stateStartNew);
            visited.add(stateStartNew.toString());

            dfaBuilder.addState(stateStartNew.toString());

            if(epsilonFreeNFA.isAccepting(epsilonFreeNFA.getStartState()))
                dfaBuilder.addAcceptingState(stateStartNew.toString());

            dfaBuilder.setStartState(stateStartNew.toString());
            
            initializeTransitionedStatesTransitions(stateStartNew, getTransitions(), dfaBuilder);

            //HashSet<String> stateStartSet = new HashSet();
            //stateStartSet.add(epsilonFreeNFA.getStartState());

            //initializeTransitionedStatesTransitions(epsilonFreeNFA.getTransitions(),dfaBuilder, stateStartSet);
        }

        

        while(!bfs.isEmpty()){
            //String stateStartNew = (String) bfs.poll();
            SetLabel state = bfs.poll();
            //String newState = dfaBuilder.setToState(closure(stateStartNew));
            // newState = new SetLabel(closure(stateStartNew));
            // dfaBuilder.addState(dfaBuilder.setToState(stateStartNew));
            

            for(String alphaSym : epsilonFreeNFA.getAlphabet()){
                SetLabel transitions = new SetLabel();

                for (String i : state.getStates())
                {
                    transitions.addAll(getTransitions().get(i, alphaSym));
                }

                //if (transitions.contains(null))
                    transitions.remove(null);

                if (transitions.size() > 1){

                    dfaBuilder.addState(transitions.toString());

                    //dfaBuilder.setTransition(dfaBuilder.setToState(stateStartNew), alphaSym, transitionedStates);
                    dfaBuilder.setTransition(state.toString(), alphaSym, transitions.toString());

                    //initializeTransitionedStatesTransitions(epsilonFreeNFA.transitions, dfaBuilder, transitions);
                    initializeTransitionedStatesTransitions(transitions, getTransitions(), dfaBuilder);

                    if(epsilonFreeNFA.isAccepting(transitions.getStates()))
                        dfaBuilder.addAcceptingState(transitions.toString());


                    if (!visited.contains(transitions.toString()))
                    {
                         bfs.add(transitions);
                         visited.add(transitions.toString());
                    }
                       
//                    for (String child : transitions){
//                        if (!visited.contains(child)){
//                            bfs.add(child);
//                            visited.add(child);
//                        }
//                    }
                }
            }
        }
        //dfaBuilder.setStartState(dfaBuilder.setToState(closure(stateStart)));
        return dfaBuilder.build();
    }

    /**
     
     @param stateSource
     @param transitionsOriginal
     @param builder
     */
    protected void initializeTransitionedStatesTransitions(SetLabel stateSource, Transitions transitionsOriginal, DFA.Builder builder)
    {
        
        for (String alphaSym : transitionsOriginal.getAlphabet()) {
            SetLabel alphaTransitions = new SetLabel();
            //HashSet<String> alphaTransitions = new HashSet<String>();

            for (String state : stateSource.getStates()){
                alphaTransitions.addAll(transitions.get(state,alphaSym));
            }

            alphaTransitions.remove(null);

            if (alphaTransitions.size() > 0)
            {
                builder.addState(alphaTransitions.toString());
                builder.setTransition(stateSource.toString(), alphaSym, alphaTransitions.toString());
            }
            else
            {
                assert false;
                builder.setTransition(stateSource.toString(), alphaSym, null);
            }
        }
    }

    /**
     
     @param states
     */
    protected void addAcceptingStates(Set<String> states)
    {
        for (String i : states)
        {
            addAcceptingState(i);
        }
    }

    /**
     
     @return
     */
    protected NFA toEpisilonFreeNFA()
    {
        Set set = new HashSet(transitions.getAlphabet());
        set.remove("");
        NFA r_val = new NFA(transitions.getStates(), set);
        r_val.setStartState(stateStart);
        r_val.statesAccepting.addAll(statesAccepting);

        for (String state : transitions.getStates())
        {
            if (isAccepting(closure(state))) r_val.statesAccepting.add(state);
        }

        collapse (r_val, stateStart, new HashSet());

        

        r_val.getTransitions().removeAlphaSym("");

        return r_val;
    }

    /**

     @param nfa
     @param state
     @param visited
     */
    protected void collapse(NFA nfa, String state, Set<String> visited)
    {
        if (visited.contains(state))
            return;

        visited.add(state);

        // Retain original non-epsilon transitions
        for (String alphaSym : nfa.getAlphabet())
        {
            for (String stateTransition : this.transitions.get(state,alphaSym))
                nfa.getTransitions().add(state, alphaSym, stateTransition);
        }

        Set<String> closureSet = closure(state);

        if (!closureSet.isEmpty())
        {
            if (containsAccepting(closureSet))
                nfa.addAcceptingState(state);
            for (String closureState : closureSet)
            {
                for (String alphaSym : nfa.getAlphabet())
                {
                    HashSet<String> transitionSet = closure(this.transitions.get(closureState,alphaSym));
                    transitionSet.remove(null);

                    for (String transitionState : transitionSet){
                        nfa.getTransitions().add(state, alphaSym, transitionState);
                    }
                }
            }
        }

        for (String alphaSym : nfa.getAlphabet())
        {
            for (String stateTransition : nfa.getTransitions().get(state, alphaSym))
            {
                collapse(nfa, stateTransition, visited);
            }
        }
    }

    /**
     
     @param candidates
     @return
     */
    protected boolean containsAccepting(Set<String> candidates)
    {

        for(String i : candidates){
            if(statesAccepting.contains(i))
                return true;
        }

        return false;
    }

    /**

     @param state
     @return
     */
    protected Set<String> closure(String state)
    {
        return closure(state, new HashSet(), new HashSet());
    }

    /**

     @param set
     @return
     */
    protected HashSet<String> closure(Set<String> set)
    {
        HashSet<String> r_val = new HashSet();
        for (String state : set)
        {
            r_val.addAll(closure(state));
        }
        return r_val;
    }
    /**

     @param state
     @param visited
     @param closureSet
     @return
     */
    protected Set<String> closure(String state, Set<String> visited, Set<String> closureSet)
    {
        if (visited.contains(state)) return closureSet;
        
        closureSet.add(state);
        visited.add(state);
        for (String transitionState: transitions.get(state, ""))
        {
            closure(transitionState, visited, closureSet);
        }
        
        return closureSet;
    }

    /**
     
     @param states
     @return
     */
    protected boolean isAccepting(Set<String> states)
    {
        for(String state : states)
            if(statesAccepting.contains(state))
                return true;

        return false;
    }

    private void clean()
    {
        transitions.clean();
    }

    /**

     @param state
     */
    protected void removeAcceptingState(String state)
    {
        statesAccepting.remove(state);
    }

    /**

     */
    public static class Builder
    {
        NFA nfa = new NFA (new HashSet(), new HashSet());

        /**
         
         */
        protected Builder()
        {}

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
            if (alphabet.contains("") || alphabet == null)
                return null;
            return new Builder(alphabet);
        }

        /**
         
         @param alphabet
         */
        protected Builder(Set<String> alphabet)
        {
            for (String alphaSym : alphabet)
                addAlphaSym(alphaSym);
        }

        /**

         @param state
         @return
         */
        public boolean addState(String state)
        {
            return nfa.getTransitions().addState(state);
        }

        /**

         @param alphaSym
         @return
         */
        public boolean addAlphaSym(String alphaSym)
        {
            return nfa.getTransitions().addAlphaSym(alphaSym);
        }

        /**

         @param stateStart
         */
        public void setStartState(String stateStart)
        {
            nfa.setStartState(stateStart);
        }
        /**

         @param stateAccepting
         */
        public void addAcceptingState(String stateAccepting)
        {
            nfa.addAcceptingState(stateAccepting);
        }
        /**

         @param source
         @param input
         @param dest
         @return
         */
        public boolean addTransition(String source, String input, String dest)
        {
            if (!nfa.getStates().contains(source) || !nfa.getStates().contains(dest) || !nfa.getAlphabet().contains(input)) return false;
            //nfa.transitions.get(source).put(input, dest);
            nfa.getTransitions().add(source, input, dest);
            return true;
        }

        /**

         @param state
         @return
         */
        public boolean isAcceptingState(String state)
        {
            return nfa.isAccepting(state);
        }

        /**

         @param source
         @param input
         @return
         */
        public Set<String> getTransitions(String source, String input)
        {
            return nfa.getTransitions().get(source, input);
        }

        /**

         @param stateAccepting
         */
        public void removeAcceptingState(String stateAccepting)
        {
            nfa.removeAcceptingState(stateAccepting);
        }

        /**

         @param state
         */
        public void removeState(String state)
        {
            nfa.getTransitions().removeState(state);
        }

        /**

         @param alphaSym
         */
        public void removeAlphaSym(String alphaSym)
        {
            nfa.getTransitions().removeAlphaSym(alphaSym);
        }
        /**

         @param source
         @param alphaSym
         @param dest
         */
        public void removeTranstion(String source, String alphaSym, String dest)
        {
            nfa.getTransitions().removeTransition(source, alphaSym, dest);
        }

        /**
         
         @return
         */
        public NFA build()
        {
            nfa.clean();

            return nfa;
        }

        /**

         @return
         */
        public Set<String> getStates()
        {
            return nfa.getStates();
        }

        /**

         @return
         */
        public Set<String> getAlphabet()
        {
            return nfa.getAlphabet();
        }
    }

    /**

     */
    public static interface Listener
    {
        /**

         */
        public static enum ChangeType
        {

            /**

             */
            INSERT,
            /**
            
             */
            UPDATE,
            /**

             */
            DELETE
        }
        /**

         @param state
         @param event
         */
        public void stateChanged(String state, ChangeType event);
        /**

         @param alphaSym
         @param event
         */
        public void alphabetChanged(String alphaSym, ChangeType event);
        /**

         @param state
         @param alphaSym
         @param event
         */
        public void transitionChanged(String state, String alphaSym, ChangeType event);
        /**

         @param state
         @param event
         */
        public void startStateChanged(String state, ChangeType event);
        /**

         @param state
         @param event
         */
        public void statesChanged(String state, ChangeType event);
    }

    /**

     */
    public static class Transitions
    {
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

        void clean()
        {
            for (String state : states)
            {
                transitions.get(state).clean();
            }
        }

        /**

         @param state
         */
        protected void removeState(String state)
        {
            states.remove(state);
            transitions.remove(state);
            for (String i : states)
                transitions.get(state).removeTransition(i, state);
        }

        /**

         @param source
         @param alphaSym
         @param dest
         */
        protected void removeTransition(String source, String alphaSym, String dest)
        {
            if (!(states.contains(source) && states.contains(dest) && alphabet.contains(alphaSym))) return;

            transitions.get(source).removeTransition(alphaSym, dest);
        }


        /**

         */
        protected class StateTransition
        {
            /**

             */
            protected HashMap<String, HashSet<String>> transitions = new HashMap();
            /**

             */
            public StateTransition()
            {}

            /**

             @param alphaSym
             */
            protected void addAlphaSym(String alphaSym)
            {
                assert !transitions.containsKey(alphaSym);
                HashSet set = new HashSet();
                set.add(null);
                transitions.put(alphaSym, set);
            }
            /**

             @param alphaSym
             @param transitionState
             */
            protected void addTransition(String alphaSym, String transitionState)
            {
                assert alphabet.contains(alphaSym);
                transitions.get(alphaSym).add(transitionState);
            }
            /**

             @param alphaSym
             @return
             */
            protected HashSet<String> get(String alphaSym)
            {
                return transitions.get(alphaSym);
            }

            /**

             @param alphaSym
             @param state
             */
            protected void removeTransition(String alphaSym, String state)
            {
                transitions.get(alphaSym).remove(state);
            }
            /**

             @param alphaSym
             */
            protected void removeAlphaSym(String alphaSym)
            {

                transitions.remove(alphaSym);
            }

            private void clean()
            {
                for (String alphaSym : alphabet)
                {
                    if (get(alphaSym).size() > 1)
                        removeTransition(alphaSym, null);
                }
            }
        }

        private Transitions(Set<String> states, Set<String> alphabet)
        {
            this.alphabet.add("");
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
            StateTransition stateTransition = new StateTransition();
            for (String alphaSym : alphabet) {
                stateTransition.addAlphaSym(alphaSym);
            }
            stateTransition.addTransition("", state);
            transitions.put(state, stateTransition);
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
                transitions.get(state).addAlphaSym(alphaSym);
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
        protected boolean add(String src, String alphaSym, String dst)
        {
            if (!(states.contains(src) && alphabet.contains(alphaSym) && states.contains(dst))) {
                return false;
            }
            transitions.get(src).addTransition(alphaSym, dst);
            return true;
        }

        /**

         @param alphaSym
         */
        protected void removeAlphaSym(String alphaSym)
        {
            alphabet.remove(alphaSym);
            for (String state : states)
                transitions.get(state).removeAlphaSym(alphaSym);


        }

        /**

         @param src
         @param alphaSym
         @return
         */
        public Set<String> get(String src, String alphaSym)
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
            //return (Set<String>) states.clone();
            return Collections.unmodifiableSet(states);
        }

        /**

         @return
         */
        public Set<String> getAlphabet()
        {
            //return (Set<String>) alphabet.clone();
            return Collections.unmodifiableSet(alphabet);
        }

        /**

         */
        protected HashMap<String, StateTransition> transitions = new HashMap();
        /**

         */
        protected HashSet<String> states = new HashSet();
        /**

         */
        protected HashSet<String> alphabet = new HashSet();
    }
}
