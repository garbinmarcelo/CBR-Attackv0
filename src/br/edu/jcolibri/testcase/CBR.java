/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.jcolibri.testcase;

import br.edu.jcolibri.utils.ExecutionLogsSimilarity;
import br.edu.jcolibri.utils.JaccardCoefficient;
import br.edu.jcolibri.utils.LongestCommonSubsequence;
import br.edu.jcolibri.utils.WeightedAverage;
import br.edu.jcolibri.utils.components.JAttributeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import jcolibri.casebase.LinealCaseBase;
import jcolibri.cbraplications.StandardCBRApplication;
import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.cbrcore.Connector;
import jcolibri.connector.PlainTextConnector;
import jcolibri.exception.ExecutionException;
import jcolibri.method.retrieve.KNNretrieval.KNNClassificationConfig;
import jcolibri.method.retrieve.KNNretrieval.KNNretrievalMethod;
import jcolibri.method.retrieve.KNNretrieval.similarity.global.Average;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.util.FileIO;

/**
 *
 * @author Lori Machado Filho
 */
public class CBR implements StandardCBRApplication {

    private Connector _connector;
    private CBRCaseBase _caseBase;
    private Description description;
    private Collection<RetrievalResult> eval;
    private Collection<CBRCase> selectedCases;
    private Collection<CBRCase> combinedCases;
    private Integer k = 0;
    private Double similarity = 0d;
    private ArrayList<JAttributeListener> jAttributeListenerList = new ArrayList();

    @Override
    public void configure() throws ExecutionException {
        try {
            (this._connector = new PlainTextConnector()).initFromXMLfile(FileIO.findFile("br/edu/jcolibri/testcase/plaintextconfig.xml"));
            this._caseBase = new LinealCaseBase();
        } catch (Exception e) {
            throw new ExecutionException(e);
        }
    }

    @Override
    public CBRCaseBase preCycle() throws ExecutionException {
        this._caseBase.init(this._connector);
        for (final CBRCase c : this._caseBase.getCases()) {
            System.out.println(c);
        }
        return this._caseBase;
    }
    
    private HashMap<br.edu.jcolibri.constants.Attribute, ArrayList<String>> getAttributeMap(){
        HashMap<br.edu.jcolibri.constants.Attribute, ArrayList<String>> attributeMap = new HashMap();
        for (JAttributeListener jAttributeListener : jAttributeListenerList) {
            attributeMap.put(jAttributeListener.getAttribute(), jAttributeListener.getList());
        }
        for (br.edu.jcolibri.constants.Attribute value : br.edu.jcolibri.constants.Attribute.values()) {
            if(!attributeMap.containsKey(value)){
                attributeMap.put(value, new ArrayList(0));
            }
        }
        return attributeMap;
    }

    @Override
    public void cycle(final CBRQuery query) throws ExecutionException {
        
        final KNNClassificationConfig simConfig = new KNNClassificationConfig();
        simConfig.setDescriptionSimFunction(new WeightedAverage()); //distancia euclidiana ponderada entre os atributos que tem valor nos casos 'atual' e 'passado'
        
        HashMap<br.edu.jcolibri.constants.Attribute, ArrayList<String>> attributeMap = getAttributeMap();
        
        
        if(!attributeMap.get(br.edu.jcolibri.constants.Attribute.HORADETEC).isEmpty()){
            Attribute useCasesRelationship = new Attribute("useCases", Description.class);
            simConfig.addMapping(useCasesRelationship, new JaccardCoefficient());
            simConfig.setWeight(useCasesRelationship, br.edu.jcolibri.constants.Attribute.HORADETEC.weight);
        }
        
        if(!attributeMap.get(br.edu.jcolibri.constants.Attribute.HORADETEC).isEmpty()){
            Attribute environment = new Attribute("systemEnvironment", Description.class);
            simConfig.addMapping(environment, new JaccardCoefficient());
            simConfig.setWeight(environment, br.edu.jcolibri.constants.Attribute.IPORIGEM.weight);
        }

        if (!attributeMap.get(br.edu.jcolibri.constants.Attribute.PORTASORIGEM).isEmpty()) {
            Attribute actorsInvolved = new Attribute("actors", Description.class);
            simConfig.addMapping(actorsInvolved, new JaccardCoefficient());
            simConfig.setWeight(actorsInvolved, br.edu.jcolibri.constants.Attribute.PORTASORIGEM.weight);
        }

        if (!attributeMap.get(br.edu.jcolibri.constants.Attribute.URLMALICIOSA).isEmpty()) {
            Attribute systemModulesInvolved = new Attribute("systemModules", Description.class);
            simConfig.addMapping(systemModulesInvolved, new JaccardCoefficient());
            simConfig.setWeight(systemModulesInvolved, br.edu.jcolibri.constants.Attribute.URLMALICIOSA.weight);
        }

        if (!attributeMap.get(br.edu.jcolibri.constants.Attribute.IPCC).isEmpty()) {
            Attribute executionLogs = new Attribute("executionLogs", Description.class);
            simConfig.addMapping(executionLogs, new JaccardCoefficient());
            simConfig.setWeight(executionLogs, br.edu.jcolibri.constants.Attribute.IPCC.weight);
        }

        if (!attributeMap.get(br.edu.jcolibri.constants.Attribute.HOSTNAMEORIGEM).isEmpty()) {
            Attribute inputs = new Attribute("inputs", Description.class);
            simConfig.addMapping(inputs, new JaccardCoefficient());
            simConfig.setWeight(inputs, br.edu.jcolibri.constants.Attribute.HOSTNAMEORIGEM.weight);
        }

        if (!attributeMap.get(br.edu.jcolibri.constants.Attribute.HOSTNAMECC).isEmpty()) {
            Attribute outputs = new Attribute("outputs", Description.class);
            simConfig.addMapping(outputs, new JaccardCoefficient());
            simConfig.setWeight(outputs, br.edu.jcolibri.constants.Attribute.HOSTNAMECC.weight);
        }

        if (!attributeMap.get(br.edu.jcolibri.constants.Attribute.PORTACC).isEmpty()) {
            Attribute executionPlan = new Attribute("executionPlan", Description.class);
            simConfig.addMapping(executionPlan, new LongestCommonSubsequence());
            simConfig.setWeight(executionPlan, br.edu.jcolibri.constants.Attribute.PORTACC.weight);
        }

        if (!attributeMap.get(br.edu.jcolibri.constants.Attribute.NOMEMALWAREFALHA).isEmpty()) {
            Attribute preConditions = new Attribute("preConditions", Description.class);
            simConfig.addMapping(preConditions, new JaccardCoefficient());
            simConfig.setWeight(preConditions, br.edu.jcolibri.constants.Attribute.NOMEMALWAREFALHA.weight);
        }

        if (!attributeMap.get(br.edu.jcolibri.constants.Attribute.SISTEMAOPERACIONAL).isEmpty()) {
            Attribute posConditions = new Attribute("posConditions", Description.class);
            simConfig.addMapping(posConditions, new JaccardCoefficient());
            simConfig.setWeight(posConditions, br.edu.jcolibri.constants.Attribute.SISTEMAOPERACIONAL.weight);
        }
        
        simConfig.setK(k);
        
        eval = KNNretrievalMethod.evaluateSimilarity(this._caseBase.getCases(), query, simConfig);

    }

    @Override
    public void postCycle() throws ExecutionException {
        this._caseBase.close();
    }

    public Connector getConnector() {
        return _connector;
    }

    public void setConnector(Connector _connector) {
        this._connector = _connector;
    }

    public CBRCaseBase getCaseBase() {
        return _caseBase;
    }

    public void setCaseBase(CBRCaseBase _caseBase) {
        this._caseBase = _caseBase;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Collection<CBRCase> getCombinedCases() {
        return combinedCases;
    }

    public void setCombinedCases(Collection<CBRCase> combinedCases) {
        this.combinedCases = combinedCases;
    }

    public Collection<RetrievalResult> getEval() {
        return eval;
    }

    public void setEval(Collection<RetrievalResult> eval) {
        this.eval = eval;
    }

    public ArrayList<String> getUseCasesRelationship() {
        ArrayList<String> list = new ArrayList();
        for (CBRCase aCase : this.getCaseBase().getCases()) {
            String item = ((Description) aCase.getDescription()).getPortasOrigem();
            if (item == null) {
                continue;
            }
            String[] splitWithSpace = item.split(" ");
            for (String string : splitWithSpace) {
                String[] splitWithUpperCase = string.split("(?=\\p{Upper})");
                String itemFinal = "";
                for (String upper : splitWithUpperCase) {
                    itemFinal += upper + " ";
                }
                itemFinal = itemFinal.substring(0, itemFinal.length() - 1);
                if (!list.contains(itemFinal)) {
                    list.add(itemFinal);
                }
            }
        }
        return list;
    }

    public ArrayList<String> getEnvironment() {
        ArrayList<String> list = new ArrayList();
        for (CBRCase aCase : this.getCaseBase().getCases()) {
            String item = ((Description) aCase.getDescription()).getUrlMaliciosa();
            if (item == null) {
                continue;
            }
            String[] splitWithSpace = item.split(" ");
            for (String string : splitWithSpace) {
                String[] splitWithUpperCase = string.split("(?=\\p{Upper})");
                String itemFinal = "";
                for (String upper : splitWithUpperCase) {
                    itemFinal += upper + " ";
                }
                itemFinal = itemFinal.substring(0, itemFinal.length() - 1);
                if (!list.contains(itemFinal)) {
                    list.add(itemFinal);
                }
            }
        }
        return list;
    }

    public ArrayList<String> getActorsInvolved() {
        ArrayList<String> list = new ArrayList();
        for (CBRCase aCase : this.getCaseBase().getCases()) {
            String item = ((Description) aCase.getDescription()).getHostnameOrigem();
            if (item == null) {
                continue;
            }
            String[] splitWithSpace = item.split(" ");
            for (String string : splitWithSpace) {
                String[] splitWithUpperCase = string.split("(?=\\p{Upper})");
                String itemFinal = "";
                for (String upper : splitWithUpperCase) {
                    itemFinal += upper + " ";
                }
                itemFinal = itemFinal.substring(0, itemFinal.length() - 1);
                if (!list.contains(itemFinal)) {
                    list.add(itemFinal);
                }
            }
        }
        return list;
    }

    public ArrayList<String> getSystemModulesInvolved() {
        ArrayList<String> list = new ArrayList();
        for (CBRCase aCase : this.getCaseBase().getCases()) {
            String item = ((Description) aCase.getDescription()).getIpCC();
            if (item == null) {
                continue;
            }
            String[] splitWithSpace = item.split(" ");
            for (String string : splitWithSpace) {
                String[] splitWithUpperCase = string.split("(?=\\p{Upper})");
                String itemFinal = "";
                for (String upper : splitWithUpperCase) {
                    itemFinal += upper + " ";
                }
                itemFinal = itemFinal.substring(0, itemFinal.length() - 1);
                if (!list.contains(itemFinal)) {
                    list.add(itemFinal);
                }
            }
        }
        return list;
    }

    public ArrayList<String> getPreConditions() {
        ArrayList<String> list = new ArrayList();
        for (CBRCase aCase : this.getCaseBase().getCases()) {
            String item = ((Description) aCase.getDescription()).getNomeMalwareFalha();
            if (item == null) {
                continue;
            }
            String[] splitWithSpace = item.split(" ");
            for (String string : splitWithSpace) {
                String[] splitWithUpperCase = string.split("(?=\\p{Upper})");
                String itemFinal = "";
                for (String upper : splitWithUpperCase) {
                    itemFinal += upper + " ";
                }
                itemFinal = itemFinal.substring(0, itemFinal.length() - 1);
                if (!list.contains(itemFinal)) {
                    list.add(itemFinal);
                }
            }
        }
        return list;
    }

    public ArrayList<String> getInputs() {
        ArrayList<String> list = new ArrayList();
        for (CBRCase aCase : this.getCaseBase().getCases()) {
            String item = ((Description) aCase.getDescription()).getSistemaOperacional();
            if (item == null) {
                continue;
            }
            String[] splitWithSpace = item.split(" ");
            for (String string : splitWithSpace) {
                String[] splitWithUpperCase = string.split("(?=\\p{Upper})");
                String itemFinal = "";
                for (String upper : splitWithUpperCase) {
                    itemFinal += upper + " ";
                }
                itemFinal = itemFinal.substring(0, itemFinal.length() - 1);
                if (!list.contains(itemFinal)) {
                    list.add(itemFinal);
                }
            }
        }
        return list;
    }

    public ArrayList<String> getExecutionPlan() {
        ArrayList<String> list = new ArrayList();
        for (CBRCase aCase : this.getCaseBase().getCases()) {
            String item = ((Description) aCase.getDescription()).getTentativas();
            if (item == null) {
                continue;
            }
            String[] splitWithSpace = item.split(" ");
            for (String string : splitWithSpace) {
                String[] splitWithUpperCase = string.split("(?=\\p{Upper})");
                String itemFinal = "";
                for (String upper : splitWithUpperCase) {
                    itemFinal += upper + " ";
                }
                itemFinal = itemFinal.substring(0, itemFinal.length() - 1);
                if (!list.contains(itemFinal)) {
                    list.add(itemFinal);
                }
            }
        }
        return list;
    }

//    public ArrayList<String> getExpectedOutputs() {
//        ArrayList<String> list = new ArrayList();
//        for (CBRCase aCase : this.getCaseBase().getCases()) {
//            String item = ((Description) aCase.getDescription()).getOutputs();
//            if (item == null) {
//                continue;
//            }
//            String[] splitWithSpace = item.split(" ");
//            for (String string : splitWithSpace) {
//                String[] splitWithUpperCase = string.split("(?=\\p{Upper})");
//                String itemFinal = "";
//                for (String upper : splitWithUpperCase) {
//                    itemFinal += upper + " ";
//                }
//                itemFinal = itemFinal.substring(0, itemFinal.length() - 1);
//                if (!list.contains(itemFinal)) {
//                    list.add(itemFinal);
//                }
//            }
//        }
//        return list;
//    }

//    public ArrayList<String> getPosConditions() {
//        ArrayList<String> list = new ArrayList();
//        for (CBRCase aCase : this.getCaseBase().getCases()) {
//            String item = ((Description) aCase.getDescription()).getPosConditions();
//            if (item == null) {
//                continue;
//            }
//            String[] splitWithSpace = item.split(" ");
//            for (String string : splitWithSpace) {
//                String[] splitWithUpperCase = string.split("(?=\\p{Upper})");
//                String itemFinal = "";
//                for (String upper : splitWithUpperCase) {
//                    itemFinal += upper + " ";
//                }
//                itemFinal = itemFinal.substring(0, itemFinal.length() - 1);
//                if (!list.contains(itemFinal)) {
//                    list.add(itemFinal);
//                }
//            }
//        }
//        return list;
//    }

    public ArrayList<String> getExecutionLogs() {
        ArrayList<String> list = new ArrayList();
        for (CBRCase aCase : this.getCaseBase().getCases()) {
            String item = ((Description) aCase.getDescription()).getHostnameCC();
            if (item == null) {
                continue;
            }
            String[] splitWithSpace = item.split(" ");
            for (String string : splitWithSpace) {
                String[] splitWithUpperCase = string.split("(?=\\p{Upper})");
                String itemFinal = "";
                for (String upper : splitWithUpperCase) {
                    itemFinal += upper + " ";
                }
                itemFinal = itemFinal.substring(0, itemFinal.length() - 1);
                if (!list.contains(itemFinal)) {
                    list.add(itemFinal);
                }
            }
        }
        return list;
    }
    
    public ArrayList<String> getValues(br.edu.jcolibri.constants.Attribute attribute) {
        switch (attribute) {
            case PORTASORIGEM:
                return getActorsInvolved();
            case IPORIGEM:
                return getEnvironment();
            case IPCC:
                return getExecutionLogs();
            case HOSTNAMEORIGEM:
                return getInputs();
            case URLMALICIOSA:
                return getSystemModulesInvolved();
            case HORADETEC:
                return getUseCasesRelationship();
            case PORTACC:
                return getExecutionPlan();
            case NOMEMALWAREFALHA:
                return getPreConditions();
            default:
                return null;
        }
    }

    public Integer getK() {
        return k;
    }

    public void setK(Integer k) {
        this.k = k;
    }

    public ArrayList<JAttributeListener> getjAttributeListenerList() {
        return jAttributeListenerList;
    }

    public void setjAttributeListenerList(ArrayList<JAttributeListener> jAttributeListenerList) {
        this.jAttributeListenerList = jAttributeListenerList;
    }
    
    public Double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }
    
    
}
