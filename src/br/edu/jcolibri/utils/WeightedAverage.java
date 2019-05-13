/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.jcolibri.utils;

import jcolibri.method.retrieve.KNNretrieval.similarity.StandardGlobalSimilarityFunction;

/**
 *
 * @author Lori Machado Filho
 */
@Immutable
public class WeightedAverage extends StandardGlobalSimilarityFunction {

    @Override
    public double computeSimilarity(final double[] values, final double[] weights, final int ivalue) {
        double acum = 0.0;
        double weigthsAcum = 0.0;
        for (int i = 0; i < ivalue; ++i) {
            acum += values[i] * weights[i];
            weigthsAcum += weights[i];
        }
        return acum / weigthsAcum;
    }

   


    public static void main(String[] args) {

        double[] v = new double[3];
        v[0] = 5;
        v[1] = 10;
        v[2] = 15;
        double[] w = new double[3];
        w[0] = 1;
        w[1] = 2;
        w[2] = 3;
        int ivalue = 3;
        
        double acum = 0.0;
        double weigthsAcum = 0.0;
        for (int i = 0; i < ivalue; ++i) {
            acum += v[i] * w[i];
            weigthsAcum += w[i];
        }
        
        
        System.out.println(acum / weigthsAcum);
    }
}
