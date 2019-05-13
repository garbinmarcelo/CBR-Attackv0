/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.jcolibri.gui;

import br.edu.jcolibri.testcase.Description;
import br.edu.jcolibri.utils.Session;
import br.edu.jcolibri.utils.StringUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import jcolibri.cbrcore.CBRCase;
import jcolibri.method.retrieve.RetrievalResult;
import ssu.log.LogReport;
import ssu.table.MyTableModel;
import ssu.table.MyTableSorter;

/**
 *
 * @author Lori Machado Filho
 */
public class JViewCaseBase extends javax.swing.JPanel {
    
    private JPrincipal jPrincipal;
    private JQuery jSearchCase;
    /**
     * Creates new form JViewCaseBase
     * @param jPrincipal
     */
    public JViewCaseBase(JPrincipal jPrincipal) {        
        init(jPrincipal, null);
        
    }
    public JViewCaseBase(JPrincipal jPrincipal, JQuery jSearchCase) {
        init(jPrincipal, jSearchCase);
    }
    
    private void init(JPrincipal jPrincipal, JQuery jSearchCase) {
        initComponents();
        try {         
            setJprincipal(jPrincipal);
            setJSearchCase(jSearchCase);

            refreshCasesTableDescription();
        } catch (Exception ex){
            LogReport.insertLog("Error at JViewCaseBase init.", ex, true);
        }
    }
        
    public void setJprincipal(JPrincipal jPrincipal){
        this.jPrincipal = jPrincipal;
    }
    
    public void setJSearchCase(JQuery jSearchCase){
        this.jSearchCase = jSearchCase;
    }
    
    private void initCasesTableDescription() throws Exception {
        // Set Default Table Model
        MyTableModel tm = new MyTableModel();

        
        // Set Columns 
        CBRCase aCase = Session.getCBR().getCaseBase().getCases().iterator().next();
        for (Field declaredField : aCase.getDescription().getClass().getDeclaredFields()) {
            tm.addColumn(declaredField.getName());
        }
        if(jSearchCase != null){
            tm.addColumn("Similarity");
        }
        tm.addColumn("Object");

        MyTableSorter ts = new MyTableSorter(tm);
        jTable_Description.setModel(ts);
        ts.setTableHeader(jTable_Description.getTableHeader());

        // Set Columns Width        
        for (Field declaredField : aCase.getDescription().getClass().getDeclaredFields()) {
            jTable_Description.getColumn(declaredField.getName()).setPreferredWidth(60);
            
        }
        if(jSearchCase != null){
            jTable_Description.getColumn("Similarity").setPreferredWidth(60);
        }

        // Hide Object
        jTable_Description.removeColumn(jTable_Description.getColumn("Object"));
        
    }

    private void refreshCasesTableDescription() {
        
        try {
            
            initCasesTableDescription();
            jTable_Description.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            MyTableSorter ts = (MyTableSorter) jTable_Description.getModel();
            ts.reset();

            if(jSearchCase == null) {
                for (CBRCase aCase : Session.getCBR().getCaseBase().getCases()) {                
                    Object[] row = new Object[aCase.getDescription().getClass().getDeclaredFields().length+1];
                    int aux = 0;
                    for (Field declaredField : aCase.getDescription().getClass().getDeclaredFields()) {
                        declaredField.setAccessible(true);                   
                        row[aux++] = StringUtils.separateByUpper((String)declaredField.get((Description)aCase.getDescription()));
                    }
                    row[aux++] = aCase;
                    ts.addRow(row);
                }
            } else {
                Session.getCBR().setK(Session.getK());
                Session.getCBR().cycle(jSearchCase.getCBRQuery());
                Session.getCBR().postCycle();
                updateRetrievalResultWithSimilarity(Session.getCBR().getEval());
                for (RetrievalResult aCase : Session.getCBR().getEval()) {
                    Object[] row = new Object[aCase.get_case().getDescription().getClass().getDeclaredFields().length+1 + (jSearchCase != null ? 1 : 0)];
                    int aux = 0;
                    for (Field declaredField : aCase.get_case().getDescription().getClass().getDeclaredFields()) {
                        declaredField.setAccessible(true);                   
                        row[aux++] = StringUtils.separateByUpper((String)declaredField.get((Description)aCase.get_case().getDescription()));
                    }
                    if(jSearchCase != null){
                        row[aux++] = aCase.getEval()*100;
                    }
                    row[aux++] = aCase;
                    ts.addRow(row);
                }
            }
        } catch (Exception e) {
            LogReport.insertLog("Error refreshing table.", e, false);
        }
    }
    
    private synchronized void updateRetrievalResultWithSimilarity(Collection<RetrievalResult> cases) {
        ArrayList<RetrievalResult> remove = new ArrayList();
        for (RetrievalResult aCase : cases) {
            if((aCase.getEval() *100d) < Session.getMinimunSimilarity()){
                remove.add(aCase);
            }
        }
        for (RetrievalResult aCase : remove) {
            cases.remove(aCase);            
        }
    }
       
    private void back(){
        jPrincipal.query();
    }
    
    public void refresh(){
        try {
            Session.updateCBR();
        } catch (Exception ex){
            LogReport.insertLog("Error configuring CBR base", ex, true);
        }
        refreshCasesTableDescription();
    }
    
    private void selectCase(java.awt.event.MouseEvent evt){
        if(evt.getClickCount() == 2){
            Object obj = jTable_Description.getModel().getValueAt(jTable_Description.getSelectedRow(), jTable_Description.getModel().getColumnCount()- 1) ;
            if(obj instanceof RetrievalResult){
                jPrincipal.learnCase(null, ((RetrievalResult) obj).get_case());   
            } else if( obj instanceof CBRCase){
                jPrincipal.learnCase(null, (CBRCase) obj);   
                
            }
        }
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
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane_Description = new javax.swing.JScrollPane();
        jTable_Description = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton_Refresh = new javax.swing.JButton();
        jButton_Refresh1 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(98, 126, 146));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cases");
        jPanel2.add(jLabel1);

        add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));

        jTable_Description.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable_Description.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_DescriptionMouseClicked(evt);
            }
        });
        jScrollPane_Description.setViewportView(jTable_Description);

        jPanel1.add(jScrollPane_Description);

        add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(98, 126, 146));

        jButton_Refresh.setText("Refresh");
        jButton_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RefreshActionPerformed(evt);
            }
        });
        jPanel3.add(jButton_Refresh);

        jButton_Refresh1.setText("Back");
        jButton_Refresh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Refresh1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton_Refresh1);

        add(jPanel3, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RefreshActionPerformed
        refresh();
    }//GEN-LAST:event_jButton_RefreshActionPerformed

    private void jButton_Refresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Refresh1ActionPerformed
        back();
    }//GEN-LAST:event_jButton_Refresh1ActionPerformed

    private void jTable_DescriptionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_DescriptionMouseClicked
        selectCase(evt);
    }//GEN-LAST:event_jTable_DescriptionMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Refresh;
    private javax.swing.JButton jButton_Refresh1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane_Description;
    private javax.swing.JTable jTable_Description;
    // End of variables declaration//GEN-END:variables
}
