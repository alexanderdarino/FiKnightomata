package FiKnightomata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**

 @author Alexander Darino
 */
class SetLabel
{

    /**

     */
    public SetLabel()
    {

    }

    /**

     @param state
     */
    public SetLabel(String state)
    {
        add(state);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SetLabel other = (SetLabel) obj;
        if (this.states != other.states && (this.states == null || !this.states.equals(other.states))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 23 * hash + (this.states != null ? this.states.hashCode() : 0);
        return hash;
    }

    /**

     @param states
     */
    public SetLabel(Set<String> states)
    {
        this.states.addAll(states);
    }

    /**

     */
    protected Set<String> states = new HashSet();

    /**

     @param state
     */
    public void add(String state)
    {
        states.add(state);
    }

    /**

     @param states
     */
    public void addAll(Set<String> states)
    {
        this.states.addAll(states);
    }

    /**

     @param state
     */
    public void remove(String state)
    {
        states.remove(state);
    }

    /**

     @param state
     @return
     */
    public boolean contains(String state)
    {
        return states.contains(state);
    }

    @Override
    public String toString()
    {
        StringBuilder toString = new StringBuilder("{");
        ArrayList<String> labels_list = new ArrayList(states);
        Collections.sort(labels_list);


        for (int i = 0; i < labels_list.size() - 1; i++)
        {
            toString.append(labels_list.get(i)).append(", ");
        }
        toString.append(labels_list.get(labels_list.size() - 1)).append("}");
        return toString.toString();
    }

    /**

     @return
     */
    public Set<String> getStates()
    {
        return Collections.unmodifiableSet(states);
    }

    /**

     @return
     */
    public int size()
    {
        return states.size();
    }

}
