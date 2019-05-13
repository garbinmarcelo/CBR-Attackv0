/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.jcolibri.utils;

import jcolibri.method.retrieve.KNNretrieval.similarity.LocalSimilarityFunction;


/**
 *
 * @author Lori Machado Filho
 */
public class MultipleValuesTextValues implements LocalSimilarityFunction {

    /**
     * Applies the similarity funciton.
     *
     * @param s String
     * @param t String
     * @return the result of apply the similarity function.
     */
    public double compute(Object s, Object t) throws jcolibri.exception.NoApplicableSimilarityFunctionException {
        if ((s == null) || (t == null)) {
            return 0;
        }
        if (!(s instanceof java.lang.String)) {
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), s.getClass());
        }
        if (!(t instanceof java.lang.String)) {
            throw new jcolibri.exception.NoApplicableSimilarityFunctionException(this.getClass(), t.getClass());
        }
        
        String[] vetS = ((String)s).split(" ");
        String[] vetT = ((String)t).split(" ");
        
        double similarity = 0;
        double proportion = 1d/vetS.length;
        
        for (String stringS : vetS) {
            for (String stringT : vetT) {
                if(stringS.equals(stringT)){
                    similarity += proportion;
                    break;
                }
            }
        }

        return similarity;
    }

    /**
     * Applicable to String
     */
    public boolean isApplicable(Object o1, Object o2) {
        if ((o1 == null) && (o2 == null)) {
            return true;
        } else if (o1 == null) {
            return o2 instanceof String;
        } else if (o2 == null) {
            return o1 instanceof String;
        } else {
            return (o1 instanceof String) && (o2 instanceof String);
        }
    }
}
