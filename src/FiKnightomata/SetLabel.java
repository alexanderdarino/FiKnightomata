package finiteautomaton;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class SetLabel {

    public SetLabel(String label)
    {
        add(label);
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
        if (this.labels != other.labels && (this.labels == null || !this.labels.equals(other.labels))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 23 * hash + (this.labels != null ? this.labels.hashCode() : 0);
        return hash;
    }
    public SetLabel(Set<String> labels)
    {
        this.labels.addAll(labels);
    }

    protected TreeSet<String> labels = new TreeSet();

    public void add(String label)
    {
        labels.add(label);
    }

    public void remove (String label)
    {
        labels.remove(label);
    }

    public boolean contains (String label)
    {
        return labels.contains(label);
    }

    @Override
    public String toString()
    {
        StringBuilder toString = new StringBuilder("{");
        ArrayList<String> labels_list = new ArrayList(labels);
        for (int i = 0; i < labels.size() - 1; i++)
        {
            toString.append(labels_list.get(i)).append(", ");
        }
        toString.append(labels_list.get(labels_list.size() - 1)).append("}");
        return toString.toString();
    }



}
