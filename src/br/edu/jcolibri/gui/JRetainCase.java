/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.jcolibri.gui;

import br.edu.jcolibri.constants.Attribute;
import br.edu.jcolibri.testcase.Description;
import br.edu.jcolibri.utils.FileUtils;
import br.edu.jcolibri.utils.Session;
import br.edu.jcolibri.utils.components.JAttributeListener;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import jcolibri.cbrcore.CBRCase;
import jcolibri.method.retrieve.RetrievalResult;
import ssu.date.DateUtils;
import ssu.log.LogReport;

/**
 *
 * @author Lori Machado Filho
 */
public class JRetainCase extends javax.swing.JPanel {

    private JPrincipal jPrincipal;
    private JQuery jQuery;
    private CBRCase cbrCase;
    private HashMap<String, JAttributeListener> jAttributeListenerMap = new HashMap();
    private ArrayList<CBRCase> cbrCases = new ArrayList();
    private ArrayList<RetrievalResult> retrievalResults = new ArrayList();
    private Integer index = 0;
    
    /**
     * Creates new form JLearnCase
     */
    public JRetainCase(JPrincipal jPrincipal) {
        init(jPrincipal);
    }

    private void init(JPrincipal jPrincipal) {
        try {
            initComponents();
            this.jPrincipal = jPrincipal;
            initGridDescription();
        } catch (Exception e) {
            LogReport.insertLog("Error at learn case init.", e, true);
        }
    }
    
    private void initGridDescription() {
        ArrayList<Attribute> attributes = new ArrayList();
        attributes.add(Attribute.HORADETEC);
        attributes.add(Attribute.IPORIGEM);
        attributes.add(Attribute.PORTASORIGEM);
        attributes.add(Attribute.URLMALICIOSA);
        attributes.add(Attribute.NOMEMALWAREFALHA);
        attributes.add(Attribute.HOSTNAMEORIGEM);
        attributes.add(Attribute.PORTACC);
        attributes.add(Attribute.HOSTNAMECC);
        attributes.add(Attribute.SISTEMAOPERACIONAL);
        attributes.add(Attribute.TENTATIVAS);
        attributes.add(Attribute.IPCC);
        for (Attribute attribute : attributes) {
            JAttributeListener jAttributeListener = new JAttributeListener(jPrincipal, attribute, null);
            jAttributeListener.setVisible(true);
            jAttributeListenerMap.put(attribute.name, jAttributeListener);
            jPanel_GridDescription.add(jAttributeListener);
        }
        JPanel jPanelBorder = new JPanel(new java.awt.BorderLayout());
        jPanelBorder.setBackground(new Color(255,255,255));
        jPanelBorder.setPreferredSize(new Dimension(1000, 1000));
        jPanel_GridDescription.add(jPanelBorder);
    }
    
    public void loadCase(Collection<RetrievalResult> retrievalResults) {
        this.cbrCases.clear();
        this.retrievalResults.clear();
        this.retrievalResults.addAll(retrievalResults);
        Collections.sort(this.retrievalResults, (RetrievalResult o1, RetrievalResult o2) -> - new Double(o1.getEval()).compareTo(o2.getEval()));
        this.retrievalResults.forEach((retrievalResult) -> {            
            this.cbrCases.add(retrievalResult.get_case());
        });        
        this.index = 0;
        loadCase(cbrCases.get(0), true);
    }
    
    private void creatArrowButtons(boolean isQuery) {
        //Remove all components to avoid duplicated components
        jPanel_Bottom.removeAll();
        
        if (isQuery) {
            //Create left buttons
            javax.swing.JButton jButton_Left = new javax.swing.JButton();
            jButton_Left.setText("< Previous test case");
            jButton_Left.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if(index > 0){
                        index--;
                        updateInfo(true);
                        loadCase(cbrCases.get(index), true);
                    }
                }
            });
            jPanel_Bottom.add(jButton_Left);
        }
        
        //Create retain Button
        javax.swing.JButton jButton_Insert = new javax.swing.JButton();
        jButton_Insert.setText("Retain new test case");
        jButton_Insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                 learnCase();
            }
        });
        jPanel_Bottom.add(jButton_Insert);
        
        
        if (isQuery) {
            //Create right buttons
            javax.swing.JButton jButton_Right = new javax.swing.JButton();
            jButton_Right.setText("Next test case >");
            jButton_Right.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if (index < (cbrCases.size() - 1)) {
                        index++;
                        updateInfo(true);
                        loadCase(cbrCases.get(index), true);
                    }
                }
            });
            jPanel_Bottom.add(jButton_Right);
        }
    }
    
    public void loadCase(CBRCase cbrCase) {
        loadCase(cbrCase, false);
    }
    
    private void updateInfo(boolean isQuery){
        
        if(isQuery){
            DecimalFormat dc = new DecimalFormat("0.00");
            String color = "<font color = ";
            if (retrievalResults.get(index).getEval() < 0.30) {
                color += " 'rgb(223,16,44)'";
            } else if (retrievalResults.get(index).getEval() < 0.70) {
                color += "'yellow'";
            } else {
                color += " rgb(102,255,153')";
            }
            color += ">";
            jLabel_Similarity.setText("<html>"
                    + "Case " + (index + 1) + " of " + cbrCases.size()
                    + " - Similarity: " + color + dc.format(retrievalResults.get(index).getEval() * 100d) + "</font>"
                    + " %</html>");
            jLabel_Info.setText(retrievalResults.size() + " test cases retrieved");
            jLabel_TestCase.setText("Test case " + ((Description)cbrCases.get(index).getDescription()).getId() + ": " 
                    +  ((Description)cbrCases.get(index).getDescription()).getTipo());
        } else {
            jLabel_Similarity.setText("");
            jLabel_Info.setText("");
            jLabel_TestCase.setText("Test case " + ((Description)cbrCase.getDescription()).getId() + ": " 
                    +  ((Description)cbrCase.getDescription()).getTipo());
        }
    }
    
    public void loadCase(CBRCase cbrCase, boolean isQuery) {
        this.cbrCase = cbrCase;
        
        creatArrowButtons(isQuery);
      
        updateInfo(isQuery);
            
        jTextField_Name.setText(((Description)cbrCase.getDescription()).getTipo());
        jTextArea_Description.setText(((Description)cbrCase.getDescription()).getHoraDetec());
        jTextArea_Scenario.setText(((Description)cbrCase.getDescription()).getIpOrigem());
        jAttributeListenerMap.get(Attribute.HORADETEC.name).addList(getListFromVetString(((Description)cbrCase.getDescription()).getHoraDetec()));
        jAttributeListenerMap.get(Attribute.IPORIGEM.name).addList(getListFromVetString(((Description)cbrCase.getDescription()).getIpOrigem()));
        jAttributeListenerMap.get(Attribute.PORTASORIGEM.name).addList(getListFromVetString(((Description)cbrCase.getDescription()).getPortasOrigem()));
        jAttributeListenerMap.get(Attribute.URLMALICIOSA.name).addList(getListFromVetString(((Description)cbrCase.getDescription()).getUrlMaliciosa()));
        jAttributeListenerMap.get(Attribute.IPORIGEM.name).addList(getListFromVetString(((Description)cbrCase.getDescription()).getIpOrigem()));
        jAttributeListenerMap.get(Attribute.IPCC.name).addList(getListFromVetString(((Description)cbrCase.getDescription()).getIpCC()));
        jAttributeListenerMap.get(Attribute.NOMEMALWAREFALHA.name).addList(getListFromVetString(((Description)cbrCase.getDescription()).getNomeMalwareFalha()));
        jAttributeListenerMap.get(Attribute.HOSTNAMEORIGEM.name).addList(getListFromVetString(((Description)cbrCase.getDescription()).getHostnameOrigem()));
        jAttributeListenerMap.get(Attribute.PORTACC.name).addList(getListFromVetString(((Description)cbrCase.getDescription()).getPortaCC()));
        jAttributeListenerMap.get(Attribute.HOSTNAMECC.name).addList(getListFromVetString(((Description)cbrCase.getDescription()).getHostnameCC()));
        jAttributeListenerMap.get(Attribute.SISTEMAOPERACIONAL.name).addList(getListFromVetString(((Description)cbrCase.getDescription()).getSistemasOperacional()));
        jAttributeListenerMap.get(Attribute.TENTATIVAS.name).addList(getListFromVetString(((Description)cbrCase.getDescription()).getTentativas()));
    }
    
    private ArrayList<String> getListFromVetString(String text){
        if(text == null){
            return new ArrayList<String>();
        }
        String[] vet = text.split(" ");
        ArrayList<String> list = new ArrayList();
        for (String string : vet) {
            String aux = "";
            for (String upper : string.split("(?=\\p{Upper})")) {
                aux += upper + " ";
            }
            aux = aux.substring(0, aux.length() - 1);
            list.add(aux);
        }
        return list;
    }
    
    private String getStringFromArray(ArrayList<String> list){
        if(list == null){
            return null;
        }
        String string = "";
        for (String iterator : list) {
            string += iterator.replace(" ", "") + " ";
        }
        if(string.length() > 0){
            string = string.substring(0, string.length() - 1);
        }
        return string;
    }

    private void learnCase() {
        CBRCase newcase = Session.getCBR().getCaseBase().getCases().iterator().next();
        
        Description description = new Description();
        FileUtils.loadCBRFile();
        description.setId(Integer.toString(br.edu.jcolibri.constants.CBR.MAX_ID + 1));
        description.setTipo(jTextField_Name.getText());
        description.setHoraDetec(jTextArea_Description.getText());
        description.setIpOrigem(jTextArea_Scenario.getText());
        description.setHoraDetec(getStringFromArray(getArrayFromJList(jAttributeListenerMap.get(Attribute.HORADETEC.name).getJList())));
        description.setIpOrigem(getStringFromArray(getArrayFromJList(jAttributeListenerMap.get(Attribute.IPORIGEM.name).getJList())));
        description.setPortasOrigem(getStringFromArray(getArrayFromJList(jAttributeListenerMap.get(Attribute.PORTASORIGEM.name).getJList())));
        description.setUrlMaliciosa(getStringFromArray(getArrayFromJList(jAttributeListenerMap.get(Attribute.URLMALICIOSA.name).getJList())));
        description.setIpCC(getStringFromArray(getArrayFromJList(jAttributeListenerMap.get(Attribute.IPCC.name).getJList())));
        
        description.setTentativas(getStringFromArray(getArrayFromJList(jAttributeListenerMap.get(Attribute.PORTACC.name).getJList())));
        description.setHostnameCC(getStringFromArray(getArrayFromJList(jAttributeListenerMap.get(Attribute.HOSTNAMECC.name).getJList())));
        description.setSistemasOperacional(getStringFromArray(getArrayFromJList(jAttributeListenerMap.get(Attribute.SISTEMAOPERACIONAL.name).getJList())));
        description.setTentativas(getStringFromArray(getArrayFromJList(jAttributeListenerMap.get(Attribute.TENTATIVAS.name).getJList())));
        
        newcase.setDescription(description);

        ArrayList<CBRCase> casestoLearnt = new ArrayList<CBRCase>();
        casestoLearnt.add(newcase);
        Session.getCBR().getCaseBase().learnCases(casestoLearnt);
        LogReport.insertLog("Case Retained!", null, true);
    }
    
    public ArrayList<String> getArrayFromJList(JList jList) {
        ListModel model = jList.getModel();
        ArrayList<String> list = new ArrayList();
        for (int i = 0; i < model.getSize(); i++) {
            list.add((String) model.getElementAt(i));
        }
        return list;
    }
    
    private void useCase(){
        
        boolean pass = LogReport.confirm("Did the test case Pass?");
        
        CBRCase newcase = Session.getCBR().getCaseBase().getCases().iterator().next();
        
        Description description = new Description();
        FileUtils.loadCBRFile();
        description.setId(Integer.toString(br.edu.jcolibri.constants.CBR.MAX_ID + 1));
        description.setTipo(jTextField_Name.getText());
        description.setHoraDetec(jTextArea_Description.getText());
        description.setIpOrigem(jTextArea_Scenario.getText());
        description.setHoraDetec(getStringFromArray(getArrayFromJList(jAttributeListenerMap.get(Attribute.HORADETEC.name).getJList())));
        description.setIpOrigem(getStringFromArray(getArrayFromJList(jAttributeListenerMap.get(Attribute.IPORIGEM.name).getJList())));
        description.setPortasOrigem(getStringFromArray(getArrayFromJList(jAttributeListenerMap.get(Attribute.PORTASORIGEM.name).getJList())));
        description.setUrlMaliciosa(getStringFromArray(getArrayFromJList(jAttributeListenerMap.get(Attribute.URLMALICIOSA.name).getJList())));
        
        //Add Log
        Timestamp dt = new Timestamp(new Date().getTime());
        String log = ((Description) this.cbrCase.getDescription()).getHostnameCC();
        log = log.replace("null", "");
        log += " " + DateUtils.dateToString(dt, "dd/MM/yyyy") + "|" + DateUtils.dateToString(dt, "HH:m") + "|" + (pass ? 1 :0);
        description.setHostnameCC(log);
        
        description.setPortaCC(getStringFromArray(getArrayFromJList(jAttributeListenerMap.get(Attribute.PORTACC.name).getJList())));
        description.setHostnameCC(getStringFromArray(getArrayFromJList(jAttributeListenerMap.get(Attribute.HOSTNAMECC.name).getJList())));
        description.setSistemasOperacional(getStringFromArray(getArrayFromJList(jAttributeListenerMap.get(Attribute.SISTEMAOPERACIONAL.name).getJList())));
        description.setTentativas(getStringFromArray(getArrayFromJList(jAttributeListenerMap.get(Attribute.TENTATIVAS.name).getJList())));
        newcase.setDescription(description);

        //Learn
        ArrayList<CBRCase> casestoLearnt = new ArrayList<CBRCase>();
        casestoLearnt.add(newcase);
        Session.getCBR().getCaseBase().learnCases(casestoLearnt);
        
        //Forget - Does not Work! Lib did not implement the method
        ArrayList<CBRCase> casestoForget = new ArrayList<CBRCase>();
        casestoForget.add(this.cbrCase);
        Session.getCBR().getCaseBase().forgetCases(casestoForget);
        
        //Forget
        Session.getCBR().getCaseBase().getCases().removeAll(casestoForget);
        
        
        LogReport.insertLog("Case used successful", null, true);
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
        jPanel8 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel_Info = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel_TestCase = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel_Similarity = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel_Name = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jTextField_Name = new javax.swing.JTextField();
        jPanel_Description = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea_Description = new javax.swing.JTextArea();
        jPanel_Scenario = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea_Scenario = new javax.swing.JTextArea();
        jPanel_GridDescription = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel_Bottom = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(1000, 600));
        setLayout(new java.awt.BorderLayout());

        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(98, 126, 146));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jPanel3.setBackground(new java.awt.Color(98, 126, 146));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel_Info.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel_Info.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Info.setText("Your Query Retrieved X Cases");
        jPanel3.add(jLabel_Info);

        jPanel2.add(jPanel3);

        jPanel15.setBackground(new java.awt.Color(98, 126, 146));

        jLabel_TestCase.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel_TestCase.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_TestCase.setText("Test Case");
        jPanel15.add(jLabel_TestCase);

        jPanel2.add(jPanel15);

        jPanel16.setBackground(new java.awt.Color(98, 126, 146));
        jPanel16.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel_Similarity.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel_Similarity.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Similarity.setText("Case X of Y - Similarity:%");
        jPanel16.add(jLabel_Similarity);

        jPanel2.add(jPanel16);

        jPanel8.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jScrollPane4.setPreferredSize(new java.awt.Dimension(218, 402));

        jPanel1.setPreferredSize(new java.awt.Dimension(216, 1630));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel9.setPreferredSize(new java.awt.Dimension(216, 141));
        jPanel9.setLayout(new java.awt.GridLayout(1, 0));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N
        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel_Name.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Name.setLayout(new javax.swing.BoxLayout(jPanel_Name, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel4.setBackground(new java.awt.Color(254, 254, 254));
        jPanel4.setMinimumSize(new java.awt.Dimension(170, 26));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Name:");
        jLabel1.setPreferredSize(new java.awt.Dimension(160, 16));
        jPanel4.add(jLabel1);

        jPanel_Name.add(jPanel4);

        jPanel5.setMaximumSize(new java.awt.Dimension(4000, 22));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        jTextField_Name.setPreferredSize(new java.awt.Dimension(400, 22));
        jPanel5.add(jTextField_Name);

        jPanel_Name.add(jPanel5);

        jPanel10.add(jPanel_Name);

        jPanel_Description.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Description.setLayout(new javax.swing.BoxLayout(jPanel_Description, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel6.setBackground(new java.awt.Color(254, 254, 254));
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Description:");
        jLabel3.setPreferredSize(new java.awt.Dimension(160, 16));
        jPanel6.add(jLabel3);

        jPanel_Description.add(jPanel6);

        jPanel12.setMinimumSize(new java.awt.Dimension(164, 64));
        jPanel12.setPreferredSize(new java.awt.Dimension(164, 64));
        jPanel12.setLayout(new javax.swing.BoxLayout(jPanel12, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane2.setMinimumSize(new java.awt.Dimension(164, 60));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(164, 60));

        jTextArea_Description.setColumns(20);
        jTextArea_Description.setRows(5);
        jTextArea_Description.setMinimumSize(new java.awt.Dimension(164, 50));
        jTextArea_Description.setPreferredSize(new java.awt.Dimension(164, 50));
        jScrollPane2.setViewportView(jTextArea_Description);

        jPanel12.add(jScrollPane2);

        jPanel_Description.add(jPanel12);

        jPanel10.add(jPanel_Description);

        jPanel_Scenario.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Scenario.setLayout(new javax.swing.BoxLayout(jPanel_Scenario, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel13.setBackground(new java.awt.Color(254, 254, 254));
        jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel4.setText("Scenario:");
        jLabel4.setPreferredSize(new java.awt.Dimension(160, 16));
        jPanel13.add(jLabel4);

        jPanel_Scenario.add(jPanel13);

        jPanel14.setLayout(new javax.swing.BoxLayout(jPanel14, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane3.setMinimumSize(new java.awt.Dimension(164, 60));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(164, 60));

        jTextArea_Scenario.setColumns(20);
        jTextArea_Scenario.setRows(5);
        jTextArea_Scenario.setMinimumSize(new java.awt.Dimension(164, 50));
        jTextArea_Scenario.setPreferredSize(new java.awt.Dimension(164, 50));
        jScrollPane3.setViewportView(jTextArea_Scenario);

        jPanel14.add(jScrollPane3);

        jPanel_Scenario.add(jPanel14);

        jPanel10.add(jPanel_Scenario);

        jPanel_GridDescription.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_GridDescription.setLayout(new javax.swing.BoxLayout(jPanel_GridDescription, javax.swing.BoxLayout.PAGE_AXIS));
        jPanel10.add(jPanel_GridDescription);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout());
        jPanel10.add(jPanel7);

        jPanel9.add(jPanel10);

        jPanel1.add(jPanel9);

        jScrollPane4.setViewportView(jPanel1);

        jPanel8.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jPanel_Bottom.setBackground(new java.awt.Color(98, 126, 146));
        jPanel8.add(jPanel_Bottom, java.awt.BorderLayout.PAGE_END);

        jScrollPane1.setViewportView(jPanel8);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel_Info;
    private javax.swing.JLabel jLabel_Similarity;
    private javax.swing.JLabel jLabel_TestCase;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel_Bottom;
    private javax.swing.JPanel jPanel_Description;
    private javax.swing.JPanel jPanel_GridDescription;
    private javax.swing.JPanel jPanel_Name;
    private javax.swing.JPanel jPanel_Scenario;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea_Description;
    private javax.swing.JTextArea jTextArea_Scenario;
    private javax.swing.JTextField jTextField_Name;
    // End of variables declaration//GEN-END:variables
}
