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
public enum Operator {

    GREATER(">"),
    GREATER_EQUALS(">="),
    LOWER("<"),
    LOWER_EQUALS("<="),
    EQUALS("=="),
    DIFFERENT("!=");

    private String operator;
    
    Operator(String operator){
        this.operator = operator;
    }
    
    public String getOperator(){
        return this.operator;
    }
    
    public void setOperator(String operator) {
        this.operator = operator;
    }
}
