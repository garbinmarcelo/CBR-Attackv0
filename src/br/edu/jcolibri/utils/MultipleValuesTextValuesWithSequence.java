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
public class MultipleValuesTextValuesWithSequence implements LocalSimilarityFunction {

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
        double sequence = proportion*1d/vetS.length;
        
        //<editor-fold defaultstate="collapsed" desc=" Example) ">
        /**
         * E.g.: length     = 3
         *       proportion = 0,3333
         *       sequence   = 0,1111
         * 
         *    Right sequence1: 1 -> 2 -> 3
         *    Result = 0,3333 + 0,3333 + 0,3333                         = 1
         * 
         *    Wrong sequence2: 3 -> 2 -> 1
         *    Result = 0,3333 + 0,3333 + 0,3333 - 0,111 - 0,111         = 0,666
         * 
         *    Wrong sequence3: 1 -> 3 -> 2
         *    Result = 0,3333 + 0,3333 + 0,3333 - 0,111 - 0,111         = 0,777
         * 
         *    Wrong sequence4: 3 -> 2
         *    Result = 0,3333 + 0,3333 - 0,111 - 0,111 - 0,111          = 0,333
         */
        //</editor-fold>
        
        for (String stringS : vetS) {
            for (String stringT : vetT) {
                if(stringS.equals(stringT)){
                    similarity += proportion;
                    break;
                } else {
                    similarity -= sequence;
                }
            }
        }

        if(similarity < 0){
            similarity = 0;
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
