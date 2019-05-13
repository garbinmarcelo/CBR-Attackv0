/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.jcolibri.gui;

import br.edu.jcolibri.constants.Attribute;
import br.edu.jcolibri.constants.Operator;
import br.edu.jcolibri.constants.SuccessFail;
import br.edu.jcolibri.utils.components.JAttributeListener;
import br.edu.jcolibri.utils.components.JSimilaritySetupListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.ArrayList;
import javax.swing.JPanel;
import ssu.date.DateListener;

/**
 *
 * @author merito
 */
public class JSimilaritySetup extends javax.swing.JDialog {

    private Frame parent;
    private DateListener dateListener;
    /**
     * Creates new form JConfig
     */
    public JSimilaritySetup(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.parent = parent;
        initComponents();
        initGrid1();
        initGrid2();
        setEnabled(jPanel_SuccessFail, false);
        setEnabled(jPanel_Date, false);
        loadSuccessFailCombos();
        dateListener = new DateListener(null, jFormattedTextField_Date, jButton_Date, true, DateListener.DATE_FORMAT);
    }
    
      private void initGrid1() {
        ArrayList<Attribute> attributes = new ArrayList();
        attributes.add(Attribute.TIPO);
        attributes.add(Attribute.HORADETEC);
        attributes.add(Attribute.IPORIGEM);
        attributes.add(Attribute.PORTASORIGEM);
        attributes.add(Attribute.URLMALICIOSA);
        for (Attribute attribute : attributes) {
            JSimilaritySetupListener jConfigListener = new JSimilaritySetupListener(parent, attribute);
            jConfigListener.setWeight(Attribute.getWeight(attribute));
            jConfigListener.setVisible(true);
            jPanel_Grid1.add(jConfigListener);
        }
        JPanel jPanelBorder = new JPanel(new java.awt.BorderLayout());
        jPanelBorder.setBackground(new Color(255,255,255));
        jPanelBorder.setPreferredSize(new Dimension(1000, 1000));
        jPanel_Grid1.add(jPanelBorder);
    }
      
      private void initGrid2() {
        ArrayList<Attribute> attributes = new ArrayList();
        attributes.add(Attribute.HOSTNAMEORIGEM);
        attributes.add(Attribute.HOSTNAMECC);
        attributes.add(Attribute.PORTACC);
        attributes.add(Attribute.NOMEMALWAREFALHA);
        attributes.add(Attribute.SISTEMAOPERACIONAL);
        attributes.add(Attribute.IPCC);
        for (Attribute attribute : attributes) {
            JSimilaritySetupListener jConfigListener = new JSimilaritySetupListener(parent, attribute);
            jConfigListener.setWeight(Attribute.getWeight(attribute));
            jConfigListener.setVisible(true);
            jPanel_Grid2.add(jConfigListener);
        }
        JPanel jPanelBorder = new JPanel(new java.awt.BorderLayout());
        jPanelBorder.setBackground(new Color(255,255,255));
        jPanelBorder.setPreferredSize(new Dimension(1000, 1000));
        jPanel_Grid2.add(jPanelBorder);
    }
   
    private void setEnabled(JPanel jPanel, boolean enabled) {
        for (Component component : jPanel.getComponents()) {
            if (component instanceof JPanel) {
                setEnabled((JPanel) component, enabled);
            }
            component.setEnabled(enabled);
        }
    }
    
      private void loadSuccessFailCombos(){
        jComboBox_SuccessFail.removeAllItems();
        for (SuccessFail value : SuccessFail.values()) {
            jComboBox_SuccessFail.addItem(value.getName());
        }
        
        jComboBox_Operators.removeAllItems();
        for (Operator value : Operator.values()) {
            jComboBox_Operators.addItem(value.getOperator());
        }
    }
    
    private void save() {
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField_Similarity = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField_K = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jCheckBox_SuccessFail = new javax.swing.JCheckBox();
        jPanel_SuccessFail = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox_SuccessFail = new javax.swing.JComboBox<>();
        jComboBox_Operators = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jCheckBox_Date = new javax.swing.JCheckBox();
        jPanel_Date = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jFormattedTextField_Date = new javax.swing.JFormattedTextField();
        jButton_Date = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel_Grid1 = new javax.swing.JPanel();
        jPanel_Grid2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton_Save = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(700, 550));
        setPreferredSize(new java.awt.Dimension(700, 700));

        jPanel2.setBackground(new java.awt.Color(98, 126, 146));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Similarity setup");
        jLabel15.setMinimumSize(new java.awt.Dimension(130, 16));
        jLabel15.setName(""); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(130, 16));
        jPanel2.add(jLabel15);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel39.setBackground(new java.awt.Color(254, 254, 254));
        jPanel39.setMaximumSize(new java.awt.Dimension(32767, 70));
        jPanel39.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel6.setPreferredSize(new java.awt.Dimension(40, 16));
        jPanel39.add(jLabel6);

        jLabel11.setText("Minimum similarity:");
        jLabel11.setMinimumSize(new java.awt.Dimension(170, 16));
        jLabel11.setName(""); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(200, 16));
        jPanel39.add(jLabel11);

        jTextField_Similarity.setText("30");
        jTextField_Similarity.setPreferredSize(new java.awt.Dimension(50, 22));
        jPanel39.add(jTextField_Similarity);

        jLabel12.setText("%");
        jPanel39.add(jLabel12);

        jPanel4.add(jPanel39);

        jPanel40.setBackground(new java.awt.Color(254, 254, 254));
        jPanel40.setMaximumSize(new java.awt.Dimension(32767, 70));
        jPanel40.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel7.setPreferredSize(new java.awt.Dimension(40, 16));
        jPanel40.add(jLabel7);

        jLabel14.setText("Number of retrieved cases:");
        jLabel14.setPreferredSize(new java.awt.Dimension(200, 16));
        jPanel40.add(jLabel14);

        jTextField_K.setText("20");
        jTextField_K.setPreferredSize(new java.awt.Dimension(50, 22));
        jPanel40.add(jTextField_K);

        jPanel4.add(jPanel40);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel4.setPreferredSize(new java.awt.Dimension(5, 16));
        jPanel5.add(jLabel4);

        jCheckBox_SuccessFail.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox_SuccessFail.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox_SuccessFailStateChanged(evt);
            }
        });
        jPanel5.add(jCheckBox_SuccessFail);

        jPanel_SuccessFail.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("<html>Prioritize test cases acording to execution logs: </html>");
        jLabel2.setPreferredSize(new java.awt.Dimension(200, 32));
        jPanel_SuccessFail.add(jLabel2);

        jComboBox_SuccessFail.setPreferredSize(new java.awt.Dimension(100, 22));
        jPanel_SuccessFail.add(jComboBox_SuccessFail);

        jComboBox_Operators.setPreferredSize(new java.awt.Dimension(60, 22));
        jPanel_SuccessFail.add(jComboBox_Operators);

        jTextField1.setMinimumSize(new java.awt.Dimension(40, 22));
        jTextField1.setPreferredSize(new java.awt.Dimension(40, 22));
        jPanel_SuccessFail.add(jTextField1);

        jPanel5.add(jPanel_SuccessFail);

        jPanel4.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel5.setPreferredSize(new java.awt.Dimension(5, 16));
        jPanel6.add(jLabel5);

        jCheckBox_Date.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox_Date.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox_DateStateChanged(evt);
            }
        });
        jPanel6.add(jCheckBox_Date);

        jPanel_Date.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("<html>Prioritize test cases executed before this date:</html>");
        jLabel3.setPreferredSize(new java.awt.Dimension(200, 32));
        jPanel_Date.add(jLabel3);

        jFormattedTextField_Date.setMinimumSize(new java.awt.Dimension(120, 22));
        jFormattedTextField_Date.setPreferredSize(new java.awt.Dimension(100, 22));
        jPanel_Date.add(jFormattedTextField_Date);

        jButton_Date.setText("...");
        jPanel_Date.add(jButton_Date);

        jPanel6.add(jPanel_Date);

        jPanel4.add(jPanel6);

        jPanel1.setBackground(new java.awt.Color(254, 254, 254));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Weight setup"));
        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        jPanel_Grid1.setBackground(new java.awt.Color(254, 254, 254));
        jPanel_Grid1.setLayout(new javax.swing.BoxLayout(jPanel_Grid1, javax.swing.BoxLayout.PAGE_AXIS));
        jPanel1.add(jPanel_Grid1);

        jPanel_Grid2.setBackground(new java.awt.Color(254, 254, 254));
        jPanel_Grid2.setLayout(new javax.swing.BoxLayout(jPanel_Grid2, javax.swing.BoxLayout.PAGE_AXIS));
        jPanel1.add(jPanel_Grid2);

        jPanel4.add(jPanel1);

        getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(98, 126, 146));

        jButton_Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/jcolibri/img/save.png"))); // NOI18N
        jButton_Save.setText("Save");
        jButton_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SaveActionPerformed(evt);
            }
        });
        jPanel3.add(jButton_Save);

        getContentPane().add(jPanel3, java.awt.BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SaveActionPerformed
        save();
    }//GEN-LAST:event_jButton_SaveActionPerformed

    private void jCheckBox_SuccessFailStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox_SuccessFailStateChanged
        setEnabled(jPanel_SuccessFail, jCheckBox_SuccessFail.isSelected());
    }//GEN-LAST:event_jCheckBox_SuccessFailStateChanged

    private void jCheckBox_DateStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox_DateStateChanged
        setEnabled(jPanel_Date, jCheckBox_Date.isSelected());
    }//GEN-LAST:event_jCheckBox_DateStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JSimilaritySetup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JSimilaritySetup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JSimilaritySetup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JSimilaritySetup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JSimilaritySetup dialog = new JSimilaritySetup(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Date;
    private javax.swing.JButton jButton_Save;
    private javax.swing.JCheckBox jCheckBox_Date;
    private javax.swing.JCheckBox jCheckBox_SuccessFail;
    private javax.swing.JComboBox<String> jComboBox_Operators;
    private javax.swing.JComboBox<String> jComboBox_SuccessFail;
    private javax.swing.JFormattedTextField jFormattedTextField_Date;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel_Date;
    private javax.swing.JPanel jPanel_Grid1;
    private javax.swing.JPanel jPanel_Grid2;
    private javax.swing.JPanel jPanel_SuccessFail;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField_K;
    private javax.swing.JTextField jTextField_Similarity;
    // End of variables declaration//GEN-END:variables
}
