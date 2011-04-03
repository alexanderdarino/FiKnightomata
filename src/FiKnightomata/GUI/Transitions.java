/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FiKnightomata.GUI;

import FiKnightomata.NFA;
import FiKnightomata.NFA.Builder;
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

    /**

     */
    protected TableModel data;
    /**

     */
    protected final JTable table = new JTable(data);

    /**

     */
    public Transitions()
    {
        super(new BorderLayout());
        table.setVisible(true);
        add(table, BorderLayout.CENTER);
        setVisible(true);
    }

    /**

     @param builder
     */
    public Transitions(NFA.Builder builder)
    {
        this();
        data = new TableModel(builder);

    }
    /**

     @param state
     */
    public void addState(String state)
    {
        data.addState(state);
    }

    /**

     @return
     */
    public Set<String> getStates()
    {
        return data.getStates();
    }



    /**

     @param state
     */
    public  void removeState(String state)
    {
        data.removeState(state);
    }

    /**

     @param alphaSym
     */
    public void addAlphaSym(String alphaSym)
    {
        data.addAlphaSym(alphaSym);
    }

    /**

     @param alphaSym
     */
    public void removeAlphaSym(String alphaSym)
    {
        data.removeAlphaSym(alphaSym);
    }

    /**

     @param src
     @param alphaSym
     @param dest
     @return
     */
    public boolean addTransition(String src, String alphaSym, String dest)
    {
        return data.addTransition(src, alphaSym, dest);
    }

    /**

     @param src
     @param alphaSym
     @return
     */
    public Set<String> getTransitions(String src, String alphaSym)
    {
        return data.getTransitions(src, alphaSym);
    }

    /**

     @param src
     @param alphaSym
     @param dest
     */
    public void removeTransition(String src, String alphaSym, String dest)
    {
        data.removeTranstion(src, alphaSym, dest);
    }

    /**

     */
    protected class TableModel extends AbstractTableModel
    {
        /**

         */
        protected final NFA.Builder builder;// = NFA.Builder.create();

        /**

         @param builder
         */
        public TableModel(Builder builder)
        {
            this.builder = builder;

            colSym.put(0, "Is Accepting?");
            colSym.put(1, "States");
            colSym.put(2, "\u03F5"); // epsilon
        }

        /**

         @param stateAccepting
         */
        public void addAcceptingState(String stateAccepting)
        {
            builder.addAcceptingState(stateAccepting);
        }


        /**

         @param alphaSym
         @return
         */
        public boolean addAlphaSym(String alphaSym)
        {
            return builder.addAlphaSym(alphaSym);
        }


        /**

         @param state
         @return
         */
        public boolean addState(String state)
        {
            if (!builder.addState(state)) return false;
            rowState.put(getStates().size(), state);
            fireTableRowsInserted(getStates().size() - 1, getStates().size() - 1);
            return true;
        }


        /**

         @param source
         @param input
         @param dest
         @return
         */
        public boolean addTransition(String source, String input, String dest)
        {
            return builder.addTransition(source, input, dest);
        }


        /**

         @return
         */
        public Set<String> getAlphabet()
        {
            return builder.getAlphabet();
        }


        /**

         @return
         */
        public Set<String> getStates()
        {
            return builder.getStates();
        }


        /**

         @param state
         @return
         */
        public boolean isAcceptingState(String state)
        {
            return builder.isAcceptingState(state);
        }


        /**

         @param stateAccepting
         */
        public void removeAcceptingState(String stateAccepting)
        {
            builder.removeAcceptingState(stateAccepting);
        }


        /**

         @param alphaSym
         */
        public void removeAlphaSym(String alphaSym)
        {
            builder.removeAlphaSym(alphaSym);
        }


        /**

         @param state
         */
        public void removeState(String state)
        {
            builder.removeState(state);
        }


        /**

         @param source
         @param alphaSym
         @param dest
         */
        public void removeTranstion(String source, String alphaSym, String dest)
        {
            builder.removeTranstion(source, alphaSym, dest);
        }


        /**

         @param stateStart
         */
        public void setStartState(String stateStart)
        {
            builder.setStartState(stateStart);
        }

        /**

         */
        protected final Map<Integer, String> rowState = new HashMap();
        /**

         */
        protected final Map<Integer, String> colSym = new HashMap();
        /**

         */
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



        
        /**

         @param source
         @param input
         @return
         */
        public Set<String> getTransitions(String source, String input)
        {
            return builder.getTransitions(source, input);
        }

    }
}
