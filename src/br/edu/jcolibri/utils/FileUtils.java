/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.jcolibri.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import ssu.log.LogReport;

/**
 *
 * @author Lori Machado Filho
 */
public class FileUtils {
    public static void loadCBRFile(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("src/br/edu/jcolibri/testcase/CasesBaseNull.txt")));
            String line = "";
            Integer id = 0;            
            while((line = reader.readLine()) != null){
                if(line.length() > 0 && line.contains(";")){
                    id = new Integer(line.split(";")[0]);
                }
            }
            br.edu.jcolibri.constants.CBR.MAX_ID = id;
        } catch (Exception e) {
            LogReport.insertLog("Error reading case base file at 'loadCBRFile()", e, true);
        }
    }
}
