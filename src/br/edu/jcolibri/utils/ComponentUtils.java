/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.jcolibri.utils;

import java.awt.event.KeyEvent;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;

/**
 *
 * @author Lori Machado Filho
 */
public class ComponentUtils {
     public void addComponents(JTextField jTextField, JComboBox jComboBox, JList jList) {
        if(jComboBox != null && jComboBox.isVisible()){
            if (jComboBox.getSelectedItem() != null) {
                addComponents((String) jComboBox.getSelectedItem(), jList);
                jComboBox.setSelectedItem(null);
                jComboBox.requestFocus();
            }
        } else {
            if(!jTextField.getText().isEmpty()){
                addComponents(jTextField.getText(), jList);
                jTextField.setText(null);
                jTextField.requestFocus();
            }
        }
    }

    private void addComponents(String text, JList jList) {
        DefaultListModel listModel = (DefaultListModel) jList.getModel();
        if(!listModel.contains(text)){
            listModel.addElement(text);
        }
        jList.setModel(listModel);
    }
    
    public void changeUseComponents(JTextField jTextField, JComboBox jComboBox){
        if(jTextField.isVisible()){
                jTextField.setVisible(false);
            jComboBox.setVisible(true);
        } else if(jComboBox.isVisible()){
            jTextField.setVisible(true);
            jComboBox.setVisible(false);
        }
    }
    
    public void removeItem(JList jList, java.awt.event.KeyEvent evt){
        if(evt.getKeyChar() == KeyEvent.VK_DELETE && jList.getSelectedIndex() >= 0){
            ((DefaultListModel)jList.getModel()).removeElementAt(jList.getSelectedIndex());
        }
    }
}
