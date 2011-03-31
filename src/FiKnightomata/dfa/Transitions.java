package FiKnightomata.dfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Transitions
{

    //protected Transitions(){}

    public static Transitions create()
    {
        return new Transitions(new HashSet(), new HashSet());
    }
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

    protected boolean set(String src, String alphaSym, String dst)
    {
        if (!(states.contains(src) && alphabet.contains(alphaSym) && states.contains(dst))) {
            return false;
        }
        transitions.get(src).put(alphaSym, dst);
        return true;
    }


    public String get(String src, String alphaSym)
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

    protected HashMap<String, HashMap<String, String>> transitions = new HashMap();
    protected HashSet<String> states = new HashSet();
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
