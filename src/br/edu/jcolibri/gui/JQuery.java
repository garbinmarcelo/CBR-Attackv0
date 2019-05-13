/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.jcolibri.gui;

import br.edu.jcolibri.constants.Attribute;
import br.edu.jcolibri.testcase.Description;
import br.edu.jcolibri.utils.Session;
import br.edu.jcolibri.utils.components.JAttributeListener;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JPanel;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRQuery;
import ssu.date.DateUtils;
import ssu.log.LogReport;

/**
 *
 * @author Lori Machado Filho
 */
public class JQuery extends javax.swing.JPanel {

    private JPrincipal jPrincipal;
    private ArrayList<JAttributeListener> jAttributeListenerList = new ArrayList();
    
    /**
     * Creates new form JSearchCase
     *
     * @param jPrincipal
     */
    public JQuery(JPrincipal jPrincipal) {
        initComponents();
        init(jPrincipal);
    }

    private void init(JPrincipal jPrincipal) {

        try {
            initGrid1();      
            this.jPrincipal = jPrincipal;
            
        } catch (Exception e) {
            LogReport.insertLog("Error at search init.", e, true);
        }
    }

    private void initGrid1() {
        ArrayList<Attribute> attributes = new ArrayList();
        attributes.add(Attribute.TIPO);
        attributes.add(Attribute.HORADETEC);
        attributes.add(Attribute.IPORIGEM);
        attributes.add(Attribute.PORTASORIGEM);
        attributes.add(Attribute.URLMALICIOSA);
        attributes.add(Attribute.NOMEMALWAREFALHA);
        attributes.add(Attribute.HOSTNAMEORIGEM);
        attributes.add(Attribute.PORTACC);
        attributes.add(Attribute.HOSTNAMECC);
        attributes.add(Attribute.SISTEMAOPERACIONAL);
        attributes.add(Attribute.IPCC);
        for (Attribute attribute : attributes) {
            JAttributeListener jAttributeListener = new JAttributeListener(jPrincipal, attribute, null);
            jAttributeListener.setJTextFieldEnabled(attribute == Attribute.TIPO);
            jAttributeListener.setVisible(true);
            jAttributeListenerList.add(jAttributeListener);
            jPanel_Grid1.add(jAttributeListener);
        }
        JPanel jPanelBorder = new JPanel(new java.awt.BorderLayout());
        jPanelBorder.setBackground(new Color(255,255,255));
        jPanelBorder.setPreferredSize(new Dimension(1000, 1000));
        jPanel_Grid1.add(jPanelBorder);
    }
    
    private void cleanQuery() {
        init(jPrincipal);
    }

    private void search() {
        jPrincipal.query(this);
    }
 
    private void setQuery(JAttributeListener jAttributeListener, Object objQuery, String param) {

        if (objQuery instanceof Description) {
            Description queryDesc = (Description) objQuery;
            if (null != jAttributeListener.getAttribute()) switch (jAttributeListener.getAttribute()) {
                case HORADETEC:
                    queryDesc.setHoraDetec(param);
                    break;
                case IPORIGEM:
                    queryDesc.setIpOrigem(param);
                    break;
                case PORTASORIGEM:
                    queryDesc.setPortasOrigem(param);
                    break;
                case URLMALICIOSA:
                    queryDesc.setUrlMaliciosa(param);
                    break;
                case IPCC:
                    queryDesc.setIpCC(param);
                    break;
                case HOSTNAMEORIGEM:
                    queryDesc.setHostnameOrigem(param);
                    break;
                case HOSTNAMECC:
                    queryDesc.setHostnameCC(param);
                    break;
                case PORTACC:
                    queryDesc.setPortaCC(param);
                    break;
                case NOMEMALWAREFALHA:
                    queryDesc.setNomeMalwareFalha(param);
                    break;
                case SISTEMAOPERACIONAL:
                    queryDesc.setSistemasOperacional(param);
                    break;
                default:
                    break;
            }
        }
    }
    
    public CBRQuery getCBRQuery() {

        Description queryDesc = new Description();

        for (JAttributeListener jAttributeListener : jAttributeListenerList) {
            String param = "";
            for (String string : jAttributeListener.getList()) {
                param += string.replace(" ", "") + " ";
            }
            if(!jAttributeListener.getList().isEmpty()){
                param = param.substring(0, param.length() -1);
            }
            setQuery(jAttributeListener, queryDesc, param);
        }

        final CBRQuery query = new CBRQuery();
        query.setDescription(queryDesc);
        return query;
    }

    private String getTodayExecutionLogs(){
        Date today = new Timestamp(new Date().getTime());
        Double totalFail = 0d;
        Integer totalCases = Session.getCBR().getCaseBase().getCases().size();
        for (CBRCase aCase : Session.getCBR().getCaseBase().getCases()) {
            String item = ((Description) aCase.getDescription()).getHostnameCC();
            if(item == null || item.isEmpty() || item.toLowerCase().equals("null")){
                continue;
            }
            
            String[] split = item.split(" ");
            for (String string : split) {
                String[] split2 = string.split("\\|");
                if (Integer.valueOf(split2[2]) == 0) {
                    totalFail++;
                }
            }
        }
        Double average = totalFail / totalCases;

        String executionLog = DateUtils.dateToString(today, "dd/MM/yyyy") + "|"
                + DateUtils.dateToString(today, "HH:mm") + "|"
                + average;
        
        return executionLog;
    }

    public ArrayList<JAttributeListener> getjAttributeListenerList() {
        return jAttributeListenerList;
    }

    public void setjAttributeListenerList(ArrayList<JAttributeListener> jAttributeListenerList) {
        this.jAttributeListenerList = jAttributeListenerList;
    }
        
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel_Grid1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton_Search = new javax.swing.JButton();
        jButton_ClearFilter = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(300, 150));
        setPreferredSize(new java.awt.Dimension(800, 600));
        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(800, 600));

        jPanel5.setMinimumSize(new java.awt.Dimension(750, 550));
        jPanel5.setPreferredSize(new java.awt.Dimension(750, 550));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(98, 126, 146));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Insert the testing characteristics");
        jPanel2.add(jLabel1);

        jPanel5.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jScrollPane2.setAutoscrolls(true);

        jPanel_Grid1.setBackground(new java.awt.Color(254, 254, 254));
        jPanel_Grid1.setMinimumSize(new java.awt.Dimension(400, 472));
        jPanel_Grid1.setPreferredSize(new java.awt.Dimension(400, 1400));
        jPanel_Grid1.setLayout(new javax.swing.BoxLayout(jPanel_Grid1, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane2.setViewportView(jPanel_Grid1);

        jPanel5.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(98, 126, 146));

        jButton_Search.setText("Query");
        jButton_Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SearchActionPerformed(evt);
            }
        });
        jPanel3.add(jButton_Search);

        jButton_ClearFilter.setText("Clean Query");
        jButton_ClearFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ClearFilterActionPerformed(evt);
            }
        });
        jPanel3.add(jButton_ClearFilter);

        jPanel5.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jScrollPane1.setViewportView(jPanel5);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SearchActionPerformed
        search();
    }//GEN-LAST:event_jButton_SearchActionPerformed

    private void jButton_ClearFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ClearFilterActionPerformed
        cleanQuery();
    }//GEN-LAST:event_jButton_ClearFilterActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_ClearFilter;
    private javax.swing.JButton jButton_Search;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel_Grid1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
