/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.jcolibri.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcolibri.exception.NoApplicableSimilarityFunctionException;
import jcolibri.method.retrieve.KNNretrieval.similarity.LocalSimilarityFunction;
import ssu.date.DateUtils;

/**
 *
 * @author Lori Machado Filho
 */
/**
 * The longest common subsequence (LCS) problem consists in finding the longest
 * subsequence common to two (or more) sequences. It differs from problems of
 * finding common substrings: unlike substrings, subsequences are not required
 * to occupy consecutive positions within the original sequences.
 *
 * It is used by the diff utility, by Git for reconciling multiple changes, etc.
 *
 * The LCS distance between Strings X (length n) and Y (length m) is n + m - 2
 * |LCS(X, Y)| min = 0 max = n + m
 *
 * LCS distance is equivalent to Levenshtein distance, when only insertion and
 * deletion is allowed (no substitution), or when the cost of the substitution
 * is the double of the cost of an insertion or deletion.
 *
 * ! This class currently implements the dynamic programming approach, which has
 * a space requirement O(m * n)!
 *
 * @author Thibault Debatty
 */
@Immutable
public class ExecutionLogsSimilarity implements LocalSimilarityFunction {

    double _interval;

    public ExecutionLogsSimilarity() {
        this._interval = 0.0;
    }

    public ExecutionLogsSimilarity(final double interval) {
        this._interval = interval;
    }

    @Override
    public double compute(final Object obj1, final Object obj2) throws NoApplicableSimilarityFunctionException {
        if (obj1 == null || obj2 == null) {
            return 0.0;
        }
        if (!(obj1 instanceof String)) {
            throw new NoApplicableSimilarityFunctionException(this.getClass(), obj1.getClass());
        }
        if (!(obj2 instanceof String)) {
            throw new NoApplicableSimilarityFunctionException(this.getClass(), obj2.getClass());
        }
        final String string1 = (String) obj1;
        final String string2 = (String) obj2;
        if (string2.length() == 0) {
            return 0.0;
        }
        if (string1.length() == 0) {
            return 0.0;
        }

        if (string1.equals("null") || string2.equals("null")) {
            return 0;
        }

        Double dayResult = 0d;
        Double passFail = 0d;
        Date dt1 = null;
        Date dt2 = null;
        Date dtr = null;
        Date dtrAux = null;
        Integer fail1 = 0;

        String[] stringSplit1 = string1.split(" "); //All cases
        String[] stringSplit2 = string2.split("\\|"); //Today case
        try {

            //Get most recent date
            dtrAux = DateUtils.stringToDate(stringSplit1[0].split("\\|")[0], "dd/MM/yyyy");
            for (String string : stringSplit1) {
                String[] stringSplit11 = string.split("\\|");
                dtr = DateUtils.stringToDate(stringSplit11[0], "dd/MM/yyyy");
                if (dtr.before(dtrAux)) {
                    dtr = dtrAux;
                }
                if (stringSplit11[2].equals("0")) {
                    fail1++;
                }
            }

            // Calculating Day Weight
            dt1 = dtr;
            dt2 = DateUtils.stringToDate(stringSplit2[0], "dd/MM/yyyy");
        } catch (Exception ex) {
            Logger.getLogger(ExecutionLogsSimilarity.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

        Integer days = 0;
        if (dt1.before(dt2)) {
            days = DateUtils.getDiferencaEmDias(dt1, dt2);
        } else {
            days = DateUtils.getDiferencaEmDias(dt2, dt1);
        }

        dayResult = days / 365d;
        dayResult = 1 - dayResult;
        System.out.println("Day Result: " + dayResult);

        // Calculating Pass/Fail Weight        
        Double fail2 = Double.valueOf(stringSplit2[2]);
        if(fail1 == 0 && fail2 == 0){
            passFail = 0d;
        } else {
            passFail = fail1 / fail2;
            if (passFail > 1d) {
                passFail = 1d;
            }
        }

        System.out.println("Fail Result: " + passFail);

        Double both = (dayResult + passFail) / 2;
        System.out.println("Both: " + both);

        return both;
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

    public static void main(String[] args) {

        ExecutionLogsSimilarity lcs = new ExecutionLogsSimilarity();
        String a = "HUSSSSSsdsdsd";
        String b = "HUsdaaa";

        double greaterLenght = a.length() > b.length() ? a.length() : b.length();
        double proportion = 1 / greaterLenght;
//        System.out.println(Math.abs((lcs.distance(a, b) * proportion) - 1));
    }
}
