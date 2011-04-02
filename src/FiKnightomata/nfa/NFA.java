package FiKnightomata.nfa;

//import finiteautomaton.SetLabel;
import FiKnightomata.*;
import FiKnightomata.dfa.DFA;
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
     * are initialized to null, and the start state and accepting states
     * are left undefined.
     * @param states the add of states (labeled by <code>String</code>s).
     * @param alphabet add of alphabet symbols (labeled by <code>String</code>s).
     */
    protected NFA(Set<String> states, Set<String> alphabet)
    {
        transitions = Transitions.create(states, alphabet);
    }



    @Override
    public FiKnightomata.Execution execute(String[] input) {
        throw new UnsupportedOperationException();
    }

    public Set<String> getStates()
    {
        return transitions.getStates();
    }

    public Set<String> getAlphabet()
    {
        return transitions.getAlphabet();
    }

    public Transitions getTransitions(){
        return transitions;
    }

    
    public DFA toDFA()
    {
        return toDFA(toEpisilonFreeNFA());
    }
    
    protected DFA toDFA(NFA epsilonFreeNFA)
    {
        DFA.Builder dfaBuilder = DFA.Builder.create(epsilonFreeNFA.getAlphabet());
        LinkedList bfs = new LinkedList();
        HashSet<String> visited = new HashSet<String>();
        
        {

            //String stateStart = DFA.Builder.setToState(epsilonFreeNFA.getStartState());
            //dfaBuilder.addState(stateStart);
            String stateStartNew = DFA.Builder.setToState(epsilonFreeNFA.getStartState());
            bfs.add(stateStartNew);
            visited.add(stateStartNew);

            dfaBuilder.addState(stateStartNew);

            if(epsilonFreeNFA.isAccepting(epsilonFreeNFA.getStartState()))
                dfaBuilder.addAcceptingState(stateStartNew);

            
            HashSet stateStartSet = new HashSet();
            stateStartSet.add(epsilonFreeNFA.getStartState());
            initializeTransitionedStatesTransitions(stateStartSet, getTransitions(), dfaBuilder);

            //HashSet<String> stateStartSet = new HashSet();
            //stateStartSet.add(epsilonFreeNFA.getStartState());

            //initializeTransitionedStatesTransitions(epsilonFreeNFA.getTransitions(),dfaBuilder, stateStartSet);
        }

        

        while(!bfs.isEmpty()){
            String state = (String) bfs.poll();
            //String newState = dfaBuilder.setToState(closure(state));
            // newState = new SetLabel(closure(state));
            // dfaBuilder.addState(dfaBuilder.setToState(state));
            

            for(String alphaSym : epsilonFreeNFA.getAlphabet()){
                //Set<String> transitions = epsilonFreeNFA.getTransitions().get(state, alphaSym);
                Set<String> transitions = getTransitions().get(state, alphaSym);
                transitions.remove(null);

                if (transitions.size() > 0){
                    //dfaBuilder.addState(dfaBuilder.setToState(state));
                    String transitionedStates = DFA.Builder.setToState(transitions);
                    dfaBuilder.addState(transitionedStates);

                    //dfaBuilder.setTransition(dfaBuilder.setToState(state), alphaSym, transitionedStates);
                    dfaBuilder.setTransition(state, alphaSym, transitionedStates);

                    //initializeTransitionedStatesTransitions(epsilonFreeNFA.transitions, dfaBuilder, transitions);
                    initializeTransitionedStatesTransitions(transitions, getTransitions(), dfaBuilder);

                    if(epsilonFreeNFA.isAccepting(transitions))
                        dfaBuilder.addAcceptingState(transitionedStates);


                    if (!visited.contains(transitionedStates))
                        bfs.add(transitionedStates);
//                    for (String child : transitions){
//                        if (!visited.contains(child)){
//                            bfs.add(child);
//                            visited.add(child);
//                        }
//                    }
                }
            }
        }
        dfaBuilder.setStartState(dfaBuilder.setToState(closure(stateStart)));
        return dfaBuilder.build();
    }

    protected void initializeTransitionedStatesTransitions (Set<String> statesSource, Transitions transitionsOriginal, DFA.Builder builder){
        
        for (String alphaSym : transitionsOriginal.getAlphabet()) {
            HashSet<String> alphaTransitions = new HashSet<String>();

            for (String state : statesSource){
                alphaTransitions.addAll(transitions.get(state,alphaSym));
            }

            alphaTransitions.remove(null);

            if (alphaTransitions.size() > 0)
            {
                builder.addState(DFA.Builder.setToState(alphaTransitions));
                builder.setTransition(DFA.Builder.setToState(statesSource), alphaSym, DFA.Builder.setToState(alphaTransitions));
            }
            else
            {
                assert false;
                builder.setTransition(DFA.Builder.setToState(statesSource), alphaSym, null);
            }
        }
    }
    
    protected NFA toEpisilonFreeNFA() {
        Set set = new HashSet(transitions.getAlphabet());
        set.remove("");
        NFA r_val = new NFA(transitions.getStates(), set);
        r_val.stateStart = stateStart;
        r_val.statesAccepting.addAll(statesAccepting);

        for (String state : transitions.getStates())
        {
            if (isAccepting(closure(state))) r_val.statesAccepting.add(state);
        }

        collapse (stateStart, r_val.getAcceptingStates(), new HashSet(), r_val.getTransitions());

        

        r_val.getTransitions().removeAlphaSym("");

        return r_val;
    }

    protected void collapse(String state, Set<String> statesAccepting, Set<String> visited, Transitions transitions){
        if (visited.contains(state))
            return;

        visited.add(state);

        // Retain original non-epsilon transitions
        for (String alphaSym : transitions.getAlphabet())
        {
            for (String stateTransition : this.transitions.get(state,alphaSym))
                transitions.add(state, alphaSym, stateTransition);
        }

        Set<String> closureSet = closure(state);
        //closureSet.removeTransitionsToState(state);

        if (!closureSet.isEmpty())
        {
            if (containsAccepting(closureSet))
                statesAccepting.add(state);
            for (String closureState : closureSet)
            {
                for (String alphaSym : transitions.getAlphabet())
                {
                    HashSet<String> transitionSet = closure(this.transitions.get(closureState,alphaSym));
                    transitionSet.remove(null);


                    //transitions.get(state).get(alphaSym).addAll(transitionSet);

                    for (String transitionState : transitionSet){
                        transitions.add(state, alphaSym, transitionState);
                    }
                }
            }
        }

        for (String alphaSym : transitions.getAlphabet())
        {
            for (String stateTransition : transitions.get(state, alphaSym))
            {
                collapse(stateTransition, statesAccepting, visited, transitions);
            }
        }
    }

    protected boolean containsAccepting(Set<String> candidates){

        for(String i : candidates){
            if(statesAccepting.contains(i))
                return true;
        }

        return false;
    }

    protected Set<String> closure(String state)
    {
        return closure(state, new HashSet(), new HashSet());
    }

    protected HashSet<String> closure(Set<String> set)
    {
        HashSet<String> r_val = new HashSet();
        for (String state : set)
        {
            r_val.addAll(closure(state));
        }
        return r_val;
    }
    protected Set<String> closure (String state, Set<String> visited, Set<String> closureSet)
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

    protected boolean isAccepting(Set<String> states){
        for(String state : states)
            if(statesAccepting.contains(state))
                return true;

        return false;
    }

    private void clean()
    {
        transitions.clean();
    }

    protected void removeAcceptingState(String state)
    {
        statesAccepting.remove(state);
    }

    public static class Builder
    {
        NFA nfa = new NFA (new HashSet(), new HashSet());

        protected Builder(){}

        public static Builder create(){
            return new Builder();
        }
        public static Builder create(Set<String> alphabet)
        {
            if (alphabet.contains("") || alphabet == null)
                return null;
            return new Builder(alphabet);
        }

        protected Builder(Set<String> alphabet){
            for (String alphaSym : alphabet)
                addAlphaSym(alphaSym);
        }

        public boolean addState(String state)
        {
            return nfa.getTransitions().addState(state);
        }

        public boolean addAlphaSym(String alphaSym)
        {
            return nfa.getTransitions().addAlphaSym(alphaSym);
        }

        public void setStartState(String stateStart)
        {
            nfa.setStartState(stateStart);
        }
        public void addAcceptingState(String stateAccepting)
        {
            nfa.addAcceptingState(stateAccepting);
        }
        public boolean addTransition(String source, String input, String dest)
        {
            if (!nfa.getStates().contains(source) || !nfa.getStates().contains(dest) || !nfa.getAlphabet().contains(input)) return false;
            //nfa.transitions.get(source).put(input, dest);
            nfa.getTransitions().add(source, input, dest);
            return true;
        }

        public boolean isAcceptingState (String state)
        {
            return nfa.isAccepting(state);
        }

        public Set<String> getTransitions(String source, String input)
        {
            return nfa.getTransitions().get(source, input);
        }

        public void removeAcceptingState(String stateAccepting)
        {
            nfa.removeAcceptingState(stateAccepting);
        }

        public void removeState(String state)
        {
            nfa.getTransitions().removeState(state);
        }

        public void removeAlphaSym(String alphaSym)
        {
            nfa.getTransitions().removeAlphaSym(alphaSym);
        }
        public void removeTranstion(String source, String alphaSym, String dest)
        {
            nfa.getTransitions().removeTransition(source, alphaSym, dest);
        }

        public NFA build(){
            nfa.clean();

            return nfa;
        }

        public Set<String> getStates()
        {
            return nfa.getStates();
        }

        public Set<String> getAlphabet()
        {
            return nfa.getAlphabet();
        }
    }
}
