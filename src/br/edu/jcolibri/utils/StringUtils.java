/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.jcolibri.utils;

/**
 *
 * @author Lori Machado Filho
 */
public class StringUtils {

    public static String separateByUpper(String text) {
        String newText = "";

        if(text == null){
            text = "";
        }
        
        String[] splitWithSpace = text.split(" ");
        for (String string : splitWithSpace) {
            String[] splitWithUpperCase = string.split("(?=\\p{Upper})");
            for (String upper : splitWithUpperCase) {
                newText += upper + " ";
            }
        }
        if(!newText.isEmpty()){
            newText = newText.substring(0, newText.length() - 1);
        }
        
        return newText;
    }
}
