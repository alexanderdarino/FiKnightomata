/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FiKnightomata.GUI;

import FiKnightomata.nfa.NFA;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Alexander Darino
 */
public class Transitions extends JPanel{

    protected final TableModel data = new TableModel();
    protected final JTable table = new JTable(data);

    public Transitions()
    {
        super(new BorderLayout());
        table.setVisible(true);
        add(table, BorderLayout.CENTER);
        setVisible(true);

    }

    public void addState(String state)
    {
        data.addState(state);
    }

    public Set<String> getStates()
    {
        return data.getStates();
    }



    public  void removeState(String state)
    {
        data.removeState(state);
    }

    public void addAlphaSym(String alphaSym)
    {
        data.addAlphaSym(alphaSym);
    }

    public void removeAlphaSym(String alphaSym)
    {
        data.removeAlphaSym(alphaSym);
    }

    public boolean addTransition(String src, String alphaSym, String dest)
    {
        return data.addTransition(src, alphaSym, dest);
    }

    public Set<String> getTransitions(String src, String alphaSym)
    {
        return data.getTransitions(src, alphaSym);
    }

    public void removeTransition(String src, String alphaSym, String dest)
    {
        data.removeTranstion(src, alphaSym, dest);
    }

    protected class TableModel extends AbstractTableModel
    {
        protected final NFA.Builder builder = NFA.Builder.create();

        public void addAcceptingState(String stateAccepting)
        {
            builder.addAcceptingState(stateAccepting);
        }


        public boolean addAlphaSym(String alphaSym)
        {
            return builder.addAlphaSym(alphaSym);
        }


        public boolean addState(String state)
        {
            if (!builder.addState(state)) return false;
            rowState.put(getStates().size(), state);
            fireTableRowsInserted(getStates().size() - 1, getStates().size() - 1);
            return true;
        }

        public TableModel()
        {
            colSym.put(0, "Is Accepting?");
            colSym.put(1, "States");
            colSym.put(2, "\u03F5"); // epsilon
        }


        public boolean addTransition(String source, String input, String dest)
        {
            return builder.addTransition(source, input, dest);
        }


        public Set<String> getAlphabet()
        {
            return builder.getAlphabet();
        }


        public Set<String> getStates()
        {
            return builder.getStates();
        }


        public boolean isAcceptingState(String state)
        {
            return builder.isAcceptingState(state);
        }


        public void removeAcceptingState(String stateAccepting)
        {
            builder.removeAcceptingState(stateAccepting);
        }


        public void removeAlphaSym(String alphaSym)
        {
            builder.removeAlphaSym(alphaSym);
        }


        public void removeState(String state)
        {
            builder.removeState(state);
        }


        public void removeTranstion(String source, String alphaSym, String dest)
        {
            builder.removeTranstion(source, alphaSym, dest);
        }


        public void setStartState(String stateStart)
        {
            builder.setStartState(stateStart);
        }

        protected final Map<Integer, String> rowState = new HashMap();
        protected final Map<Integer, String> colSym = new HashMap();
        protected Set<TableModelListener> listeners = new HashSet();

        public int getRowCount()
        {
            return 1 + getStates().size();
        }

        public int getColumnCount()
        {
            return 3 + getAlphabet().size();
        }

        public Object getValueAt(int rowIndex, int columnIndex)
        {
            if (rowIndex == 0)
                return colSym.get(columnIndex);
            if (columnIndex == 1)
                return rowState.get(rowIndex);
            

            return getTransitions(rowState.get(rowIndex), colSym.get(columnIndex));
        }



        
        public Set<String> getTransitions(String source, String input)
        {
            return builder.getTransitions(source, input);
        }

    }
}
