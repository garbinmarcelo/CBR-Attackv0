/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.jcolibri.utils.components;

import br.edu.jcolibri.constants.Attribute;
import br.edu.jcolibri.utils.NumberListener;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

/**
 *
 * @author Lori Machado Filho
 */
public class JSimilaritySetupListener extends javax.swing.JPanel {
    
    private Attribute attribute;
    private Frame parent;
    /**
     * Creates new form JAttributeListener
     */
    public JSimilaritySetupListener() {
        initComponents();
    }
    
    public JSimilaritySetupListener(Frame parent, Attribute attribute) {
        initComponents();
        this.jLabel.setText(attribute.name);
        this.attribute = attribute;
        this.parent = parent;        
        NumberListener.registerDouble(jTextField, "0.00");
    }
   
    public void setJTextFieldEditable(boolean editable) {
        jTextField.setEditable(editable);
    }
    
    public void setJTextFieldEnabled(boolean enabled) {
        jTextField.setEnabled(enabled);
    }
    
    public void setAllEnabled(boolean enabled) {        
        setAllEnabled(jPanel_Main, enabled);
    }
    
    private void setAllEnabled(Component component, boolean enabled) {        
        if (!(component instanceof JTabbedPane) && 
                !(component instanceof JTable)) {
            component.setEnabled(enabled);
        }
        
        if (component instanceof Container) {
            Container container = (Container) component;

            for (int i = 0; i < container.getComponentCount(); i++) {
                Component c = container.getComponent(i);
                if(!(c instanceof JLabel) || (c instanceof JLabel && component.getMouseListeners()!=null)){
                    setAllEnabled(c, enabled);
                }
            }
        }    
    }
 
 
  public Attribute getAttribute(){
      return this.attribute;
  }
  
  private void up(){
      Double weight = Double.valueOf(jTextField.getText().replace(",", "."));
      DecimalFormat dc = new DecimalFormat("0.00");
      if(weight < 1d){
          weight += 0.1d;
          jTextField.setText(dc.format(weight));
      }
  }
  
  private void down(){
      Double weight = Double.valueOf(jTextField.getText().replace(",", "."));
      DecimalFormat dc = new DecimalFormat("0.00");
      if(weight > 0d){
          weight -= 0.1d;
          jTextField.setText(dc.format(weight));
      }
  }
  
  public Double getWeight(){
      return Double.valueOf(jTextField.getText());
  }
  
  public void setWeight(Double weight){
      if(weight == null){
          return;
      }
      jTextField.setText(weight.toString());
  }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_Main = new javax.swing.JPanel();
        jLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTextField = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jButton_Up = new javax.swing.JButton();
        jButton_Down = new javax.swing.JButton();

        setBackground(new java.awt.Color(254, 254, 254));
        setMinimumSize(new java.awt.Dimension(265, 59));
        setPreferredSize(new java.awt.Dimension(265, 59));
        setLayout(new java.awt.BorderLayout());

        jPanel_Main.setBackground(new java.awt.Color(254, 254, 254));
        jPanel_Main.setPreferredSize(new java.awt.Dimension(400, 115));
        jPanel_Main.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel.setBackground(new java.awt.Color(255, 255, 255));
        jLabel.setText("Label:");
        jLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel.setMaximumSize(new java.awt.Dimension(160, 16));
        jLabel.setMinimumSize(new java.awt.Dimension(160, 16));
        jLabel.setPreferredSize(new java.awt.Dimension(160, 16));
        jPanel_Main.add(jLabel);

        jPanel2.setBackground(new java.awt.Color(254, 254, 254));

        jPanel3.setBackground(new java.awt.Color(254, 254, 254));

        jTextField.setPreferredSize(new java.awt.Dimension(50, 29));
        jPanel3.add(jTextField);

        jPanel2.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(254, 254, 254));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.PAGE_AXIS));

        jButton_Up.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/jcolibri/img/up.png"))); // NOI18N
        jButton_Up.setPreferredSize(new java.awt.Dimension(18, 18));
        jButton_Up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_UpActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_Up);

        jButton_Down.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/edu/jcolibri/img/down.png"))); // NOI18N
        jButton_Down.setPreferredSize(new java.awt.Dimension(18, 18));
        jButton_Down.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_DownActionPerformed(evt);
            }
        });
        jPanel4.add(jButton_Down);

        jPanel2.add(jPanel4);

        jPanel_Main.add(jPanel2);

        add(jPanel_Main, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_UpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_UpActionPerformed
        up();
    }//GEN-LAST:event_jButton_UpActionPerformed

    private void jButton_DownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_DownActionPerformed
        down();
    }//GEN-LAST:event_jButton_DownActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Down;
    private javax.swing.JButton jButton_Up;
    private javax.swing.JLabel jLabel;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel_Main;
    private javax.swing.JTextField jTextField;
    // End of variables declaration//GEN-END:variables
}