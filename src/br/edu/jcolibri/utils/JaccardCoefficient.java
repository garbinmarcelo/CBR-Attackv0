/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.jcolibri.utils;

import java.util.ArrayList;
import java.util.Arrays;
import jcolibri.exception.NoApplicableSimilarityFunctionException;
import jcolibri.method.retrieve.KNNretrieval.similarity.LocalSimilarityFunction;

/**
 *
 * @author Lori Machado Filho
 */
@Immutable
public class JaccardCoefficient implements LocalSimilarityFunction {

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

        ArrayList<String> list1 = getListString(string1);
        ArrayList<String> list2 = getListString(string2);
        ArrayList<String> unionList = new ArrayList();

        //do union
        String unionString = "";
        for (String string : list1) {
            if (!unionList.contains(string)) {
                unionList.add(string);
            }
        }
        for (String string : list2) {
            if (!unionList.contains(string)) {
                unionList.add(string);
            }
        }
        for (String string : unionList) {
            unionString = string + " ";
        }
        if (unionString.endsWith(" ")) {
            unionString = unionString.substring(0, unionString.length() - 1);
        }

        double intersection = 0d;
        double union = unionString.split(" ").length + 0d;

        //do intersection
        for (String st1 : list1) {
            for (String st2 : list2) {
                if (st1.equals(st2)) {
                    intersection++;
                }
            }
        }
        double value = intersection / union;

        return value;
    }

    private static ArrayList<String> getListString(String value) {
        if (value == null) {
            return new ArrayList();
        }
        String split = "";
        String[] splitWithSpace = value.split(" ");
        for (String string : splitWithSpace) {
            String[] splitWithUpperCase = string.split("(?=\\p{Upper})");
            for (String upper : splitWithUpperCase) {
                split += upper + " ";
            }
        }
        if (split.endsWith(" ")) {
            split = split.substring(0, split.length() - 1);
        }

        ArrayList<String> list = new ArrayList();
        list.addAll(Arrays.asList(split.split(" ")));
        return list;
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

        String a = "a b c";
        String b = "teste a ffff b";

        ArrayList<String> list1 = getListString(a);
        ArrayList<String> list2 = getListString(b);
        ArrayList<String> unionList = new ArrayList();

        //do union
        String unionString = "";
        for (String string : list1) {
            if (!unionList.contains(string)) {
                unionList.add(string);
            }
        }
        for (String string : list2) {
            if (!unionList.contains(string)) {
                unionList.add(string);
            }
        }
        for (String string : unionList) {
            unionString += string + " ";
        }
        if (unionString.endsWith(" ")) {
            unionString = unionString.substring(0, unionString.length() - 1);
        }

        double intersection = 0d;
        double union = unionString.split(" ").length + 0d;

        //do intersection
        for (String st1 : list1) {
            for (String st2 : list2) {
                if (st1.equals(st2)) {
                    intersection++;
                }
            }
        }
        double value = intersection / union;

        System.out.println(value);
    }
}
