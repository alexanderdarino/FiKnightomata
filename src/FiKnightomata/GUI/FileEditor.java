/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FileEditor.java
 *
 * Created on Mar 31, 2011, 7:37:41 PM
 */

package FiKnightomata.GUI;

import FiKnightomata.NFA;

/**
 *
 * @author Alexander Darino
 */
public class FileEditor extends javax.swing.JFrame {


    private NFA.Builder nfaBuilder = NFA.Builder.create();
    /** Creates new form FileEditor */
    public FileEditor() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        transitionsTable = new FiKnightomata.GUI.Transitions();
        controlPanel = new javax.swing.JPanel();
        stateAlphaEditorPanel = new javax.swing.JPanel();
        stateEditorPanel = new javax.swing.JPanel();
        stateNameLabel = new javax.swing.JLabel();
        stateNameField = new javax.swing.JTextField();
        addRemoveStatePanel = new javax.swing.JPanel();
        addStateButton = new javax.swing.JButton();
        removeStateButton = new javax.swing.JButton();
        startStatePanel = new javax.swing.JPanel();
        startingStateLabel = new javax.swing.JLabel();
        startStateList = new javax.swing.JComboBox();
        alphaSymEditorPanel = new javax.swing.JPanel();
        alphaSymLabel = new javax.swing.JLabel();
        alphaSymField = new javax.swing.JTextField();
        addRemoveStatePanel1 = new javax.swing.JPanel();
        addAlphaSymButton = new javax.swing.JButton();
        removeAlphaSymButton = new javax.swing.JButton();
        transitionsPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(transitionsTable, java.awt.BorderLayout.CENTER);

        controlPanel.setLayout(new java.awt.BorderLayout());

        stateAlphaEditorPanel.setLayout(new java.awt.GridLayout(3, 1));

        stateEditorPanel.setLayout(new java.awt.BorderLayout());

        stateNameLabel.setText("State Name:");
        stateEditorPanel.add(stateNameLabel, java.awt.BorderLayout.WEST);

        stateNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateNameFieldActionPerformed(evt);
            }
        });
        stateEditorPanel.add(stateNameField, java.awt.BorderLayout.CENTER);

        addRemoveStatePanel.setLayout(new java.awt.GridLayout(1, 2));

        addStateButton.setText("Add");
        addStateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStateButtonActionPerformed(evt);
            }
        });
        addRemoveStatePanel.add(addStateButton);

        removeStateButton.setText("Remove");
        removeStateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeStateButtonActionPerformed(evt);
            }
        });
        addRemoveStatePanel.add(removeStateButton);

        stateEditorPanel.add(addRemoveStatePanel, java.awt.BorderLayout.EAST);

        stateAlphaEditorPanel.add(stateEditorPanel);

        startStatePanel.setLayout(new java.awt.BorderLayout());

        startingStateLabel.setText("Start State:");
        startStatePanel.add(startingStateLabel, java.awt.BorderLayout.WEST);

        startStateList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startStateListActionPerformed(evt);
            }
        });
        startStatePanel.add(startStateList, java.awt.BorderLayout.CENTER);

        stateAlphaEditorPanel.add(startStatePanel);

        alphaSymEditorPanel.setLayout(new java.awt.BorderLayout());

        alphaSymLabel.setText("Alphabet Symbol:");
        alphaSymEditorPanel.add(alphaSymLabel, java.awt.BorderLayout.WEST);

        alphaSymField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alphaSymFieldActionPerformed(evt);
            }
        });
        alphaSymEditorPanel.add(alphaSymField, java.awt.BorderLayout.CENTER);

        addRemoveStatePanel1.setLayout(new java.awt.GridLayout(1, 2));

        addAlphaSymButton.setText("Add");
        addAlphaSymButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAlphaSymButtonActionPerformed(evt);
            }
        });
        addRemoveStatePanel1.add(addAlphaSymButton);

        removeAlphaSymButton.setText("Remove");
        removeAlphaSymButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAlphaSymButtonActionPerformed(evt);
            }
        });
        addRemoveStatePanel1.add(removeAlphaSymButton);

        alphaSymEditorPanel.add(addRemoveStatePanel1, java.awt.BorderLayout.EAST);

        stateAlphaEditorPanel.add(alphaSymEditorPanel);

        controlPanel.add(stateAlphaEditorPanel, java.awt.BorderLayout.NORTH);

        transitionsPanel.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Available States");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        transitionsPanel.add(jLabel1, gridBagConstraints);

        jLabel2.setText("Transition States");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        transitionsPanel.add(jLabel2, gridBagConstraints);

        jScrollPane1.setViewportView(jList1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        transitionsPanel.add(jScrollPane1, gridBagConstraints);

        jButton2.setText("<-");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        transitionsPanel.add(jButton2, gridBagConstraints);

        jScrollPane2.setViewportView(jList2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        transitionsPanel.add(jScrollPane2, gridBagConstraints);

        jButton1.setText("->");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        transitionsPanel.add(jButton1, gridBagConstraints);

        controlPanel.add(transitionsPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(controlPanel, java.awt.BorderLayout.EAST);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stateNameFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_stateNameFieldActionPerformed
    {//GEN-HEADEREND:event_stateNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stateNameFieldActionPerformed

    private void removeStateButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_removeStateButtonActionPerformed
    {//GEN-HEADEREND:event_removeStateButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_removeStateButtonActionPerformed

    private void addStateButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addStateButtonActionPerformed
    {//GEN-HEADEREND:event_addStateButtonActionPerformed
        transitionsTable.addState(stateNameField.getText());
    }//GEN-LAST:event_addStateButtonActionPerformed

    private void alphaSymFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_alphaSymFieldActionPerformed
    {//GEN-HEADEREND:event_alphaSymFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_alphaSymFieldActionPerformed

    private void addAlphaSymButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addAlphaSymButtonActionPerformed
    {//GEN-HEADEREND:event_addAlphaSymButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addAlphaSymButtonActionPerformed

    private void removeAlphaSymButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_removeAlphaSymButtonActionPerformed
    {//GEN-HEADEREND:event_removeAlphaSymButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_removeAlphaSymButtonActionPerformed

    private void startStateListActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_startStateListActionPerformed
    {//GEN-HEADEREND:event_startStateListActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_startStateListActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FileEditor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAlphaSymButton;
    private javax.swing.JPanel addRemoveStatePanel;
    private javax.swing.JPanel addRemoveStatePanel1;
    private javax.swing.JButton addStateButton;
    private javax.swing.JPanel alphaSymEditorPanel;
    private javax.swing.JTextField alphaSymField;
    private javax.swing.JLabel alphaSymLabel;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton removeAlphaSymButton;
    private javax.swing.JButton removeStateButton;
    private javax.swing.JComboBox startStateList;
    private javax.swing.JPanel startStatePanel;
    private javax.swing.JLabel startingStateLabel;
    private javax.swing.JPanel stateAlphaEditorPanel;
    private javax.swing.JPanel stateEditorPanel;
    private javax.swing.JTextField stateNameField;
    private javax.swing.JLabel stateNameLabel;
    private javax.swing.JPanel transitionsPanel;
    private FiKnightomata.GUI.Transitions transitionsTable;
    // End of variables declaration//GEN-END:variables

}
