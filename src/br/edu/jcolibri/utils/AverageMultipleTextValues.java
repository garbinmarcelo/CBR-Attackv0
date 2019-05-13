/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.jcolibri.utils;

import java.util.ArrayList;
import java.util.StringTokenizer;
import jcolibri.exception.NoApplicableSimilarityFunctionException;
import jcolibri.method.retrieve.KNNretrieval.similarity.LocalSimilarityFunction;

/**
 *
 * @author Lori Machado Filho
 */
public class AverageMultipleTextValues implements LocalSimilarityFunction
{
    double _interval;
    
    public AverageMultipleTextValues() {
        this._interval = 0.0;
    }
    
    public AverageMultipleTextValues(final double interval) {
        this._interval = interval;
    }
    
    private double extractStringAverage(final String s) {
        final ArrayList<Double> list = new ArrayList<Double>();
        final StringTokenizer st = new StringTokenizer(s);
        while (st.hasMoreTokens()) {
            final String num = st.nextToken();
            double dnum = 0.0;
            try {
                dnum = Double.parseDouble(num);
            }
            catch (Exception ex) {}
            if (dnum == 0.0) {
                try {
                    dnum = Integer.parseInt(num);
                }
                catch (Exception ex2) {}
            }
            list.add(new Double(dnum));
        }
        final double total = list.size();
        if (total == 0.0) {
            return 0.0;
        }
        double acum = 0.0;
        for (final Double Dnum : list) {
            acum += Dnum;
        }
        return acum / total;
    }
    
    @Override
    public double compute(final Object caseObject, final Object queryObject) throws NoApplicableSimilarityFunctionException {
        if (caseObject == null || queryObject == null) {
            return 0.0;
        }
        if (!(caseObject instanceof String)) {
            throw new NoApplicableSimilarityFunctionException(this.getClass(), caseObject.getClass());
        }
        if (!(queryObject instanceof String)) {
            throw new NoApplicableSimilarityFunctionException(this.getClass(), queryObject.getClass());
        }
        final String caseS = (String)caseObject;
        final String queryS = (String)queryObject;
        if (queryS.length() == 0) {
            return 0.0;
        }
        if (caseS.length() == 0) {
            return 0.0;
        }
        final double qV = this.extractStringAverage(queryS);
        final double cV = this.extractStringAverage(caseS);
        return 1.0 - Math.abs(qV - cV) / this._interval;
    }
    
    @Override
    public boolean isApplicable(final Object o1, final Object o2) {
        if (o1 == null && o2 == null) {
            return true;
        }
        if (o1 == null) {
            return o2 instanceof String;
        }
        if (o2 == null) {
            return o1 instanceof String;
        }
        return o1 instanceof String && o2 instanceof String;
    }
}
