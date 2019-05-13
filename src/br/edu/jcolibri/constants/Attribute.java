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
public enum Attribute {

    TIPO("Tipo", 1d),
    HORADETEC("Hora detecção", 1d),
    IPORIGEM("IP Origem", 1d),
    PORTASORIGEM("Porta Origem", 1d),
    URLMALICIOSA("URL Maliciosa", 1d),
    HOSTNAMEORIGEM("Hostname Origem", 1d),
    IPCC("IP Atacante", 1d),
    PORTACC("Porta Atacante", 1d),
    HOSTNAMECC("Hostname Atacante", 1d),
    NOMEMALWAREFALHA("Nome Malware/Falha", 1d),
    SISTEMAOPERACIONAL("Sistema Op. Host", 1d),
    TENTATIVAS("Tentativas Login/Intrusão", 1d);

    public String name;
    public Double weight;

    Attribute(String name, Double weight) {
        this.name = name;
        this.weight = weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public static Double getWeight(Attribute attribute) {
        switch (attribute) {
            case TIPO:
                return TIPO.weight;
            case HORADETEC:
                return HORADETEC.weight;
            case IPORIGEM:
                return IPORIGEM.weight;
            case PORTASORIGEM:
                return PORTASORIGEM.weight;
            case URLMALICIOSA:
                return URLMALICIOSA.weight;
            case HOSTNAMEORIGEM:
                return HOSTNAMEORIGEM.weight;
            case IPCC:
                return IPCC.weight;
            case PORTACC:
                return PORTACC.weight;
            case HOSTNAMECC:
                return HOSTNAMECC.weight;
            case NOMEMALWAREFALHA:
                return NOMEMALWAREFALHA.weight;
            case SISTEMAOPERACIONAL:
                return SISTEMAOPERACIONAL.weight;
            case TENTATIVAS:
                return TENTATIVAS.weight;
            default:
                return 0d;
        }
    }
}
