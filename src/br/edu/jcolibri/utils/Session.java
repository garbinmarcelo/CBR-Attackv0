/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.jcolibri.utils;

import br.edu.jcolibri.constants.Attribute;
import br.edu.jcolibri.testcase.CBR;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import jcolibri.exception.ExecutionException;

/**
 *
 * @author Lori Machado Filho
 */
public class Session {

    private static CBR cbr;
    private static Integer k = 10;
    private static Integer minimunSimilarity = 0;
    private static ArrayList<Attribute> attributes = new ArrayList();

    public static CBR getCBR() {
        if (cbr == null) {
            updateCBR();
        }
        return cbr;
    }

    public static void updateCBR() {
        try {
            cbr = new CBR();
            cbr.configure();
            cbr.preCycle();
        } catch (ExecutionException e) {
            JOptionPane.showMessageDialog(null, "Error loading CBR. Please, check it out.\n"
                    + e.getMessage());
        }
    }

    public static Integer getK() {
        return k;
    }

    public static void setK(Integer k) {
        Session.k = k;
    }

    public static ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public static void setAttributes(ArrayList<Attribute> attributes) {
        Session.attributes.clear();
        Session.attributes.addAll(attributes);
    }

    public static void addAttributeInList(Attribute attribute) {
        if (attributes.contains(attribute)) {
            attributes.remove(attribute);
        }
        attributes.add(attribute);
    }

    public static Integer getMinimunSimilarity() {
        return minimunSimilarity;
    }

    public static void setMinimunSimilarity(Integer minimunSimilarity) {
        Session.minimunSimilarity = minimunSimilarity;
    }

}
