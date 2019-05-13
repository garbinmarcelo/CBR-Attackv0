/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.jcolibri.constants;

/**
 *
 * @author Lori Machado Filho
 */
public enum SuccessFail {

    SUCCESS("Success"),
    FAIL("Fail");
    
    private String name;
    SuccessFail(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
}
