package FiKnightomata.nfa;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Transitions
{
    public static Transitions create()
    {
        return new Transitions(new HashSet(), new HashSet());
    }
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

    protected class StateTransition
    {
        protected HashMap<String, HashSet<String>> transitions = new HashMap();
        public StateTransition(){}

        protected void addAlphaSym(String alphaSym)
        {
            assert !transitions.containsKey(alphaSym);
            HashSet set = new HashSet();
            set.add(null);
            transitions.put(alphaSym, set);
        }
        protected void addTransition (String alphaSym, String transitionState)
        {
            assert alphabet.contains(alphaSym);
            transitions.get(alphaSym).add(transitionState);
        }
        protected HashSet<String> get (String alphaSym)
        {
            return transitions.get(alphaSym);
        }

        protected void removeTransition(String alphaSym, String state)
        {
            transitions.get(alphaSym).remove(state);
        }
        protected void removeAlphaSym (String alphaSym){

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

    protected boolean add(String src, String alphaSym, String dst)
    {
        if (!(states.contains(src) && alphabet.contains(alphaSym) && states.contains(dst))) {
            return false;
        }
        transitions.get(src).addTransition(alphaSym, dst);
        return true;
    }

    public void removeAlphaSym (String alphaSym){
        alphabet.remove(alphaSym);
        for (String state : states)
            transitions.get(state).removeAlphaSym(alphaSym);


    }

    public Set<String> get(String src, String alphaSym)
    {
        if (!(states.contains(src) && alphabet.contains(alphaSym))) {
            return null;
        }
        return transitions.get(src).get(alphaSym);
    }

    public Set<String> getStates()
    {
        return (Set<String>) states.clone();
    }

    public Set<String> getAlphabet()
    {
        return (Set<String>) alphabet.clone();
    }

    protected HashMap<String, StateTransition> transitions = new HashMap();
    protected HashSet<String> states = new HashSet();
    protected HashSet<String> alphabet = new HashSet();
}
