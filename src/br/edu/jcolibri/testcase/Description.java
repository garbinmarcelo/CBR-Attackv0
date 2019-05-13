package br.edu.jcolibri.testcase;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;

/**
 *
 * @author Lori Machado Filho
 */
public class Description implements CaseComponent {

    private String id;
    private String Tipo;
    private String HoraDetec;
    private String IpOrigem;
    private String PortasOrigem;
    private String UrlMaliciosa;
    private String HostnameOrigem;
    private String IpCC;
    private String PortaCC;
    private String HostnameCC;
    private String NomeMalwareFalha;
    private String SistemaOperacional;
    private String Tentativas;
    private Integer passo1;
    private Integer passo2;
    private Integer passo3;
    private Integer passo4;
    private Integer passo5;
    private Integer passo6;
    private Integer passo7;
    private Integer passo8;
    private Integer passo9;
    private Integer passo10;
    private Integer passo11;
    private Integer passo12;
    private Integer passo13;
    private Integer passo14;

    @Override
    public String toString() {
        return "(" + this.id + "," + this.Tipo + "," + this.HoraDetec + "," + this.IpOrigem + ","
                + this.PortasOrigem + "," + this.UrlMaliciosa + "," + this.HostnameOrigem + ","
                + this.IpCC + "," + this.HostnameCC + ","
                + this.NomeMalwareFalha + "," + this.SistemaOperacional + this.Tentativas + "," + ")";
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public String getHoraDetec() {
        return HoraDetec;
    }

    public void setHoraDetec(String HoraDetec) {
        this.HoraDetec = HoraDetec;
    }

    public String getIpOrigem() {
        return IpOrigem;
    }

    public void setIpOrigem(String IpOrigem) {
        this.IpOrigem = IpOrigem;
    }

    public String getPortasOrigem() {
        return PortasOrigem;
    }

    public void setPortasOrigem(String useCasesRelationship) {
        this.PortasOrigem = useCasesRelationship;
    }

    public String getUrlMaliciosa() {
        return UrlMaliciosa;
    }

    public void setUrlMaliciosa(String environment) {
        this.UrlMaliciosa = environment;
    }

    public String getHostnameOrigem() {
        return HostnameOrigem;
    }

    public void setHostnameOrigem(String actorsInvolved) {
        this.HostnameOrigem = actorsInvolved;
    }

    public String getIpCC() {
        return IpCC;
    }

    public void setIpCC(String systemModulesInvolved) {
        this.IpCC = systemModulesInvolved;
    }

    public String getHostnameCC() {
        return HostnameCC;
    }

    public void setHostnameCC(String HostnameCC) {
        this.HostnameCC = HostnameCC;
    }

    public String getTentativas() {
        return Tentativas;
    }

    public void setTentativas(String Tentativas) {
        this.Tentativas = Tentativas;
    }

    public String getNomeMalwareFalha() {
        return NomeMalwareFalha;
    }

    public void setNomeMalwareFalha(String NomeMalwareFalha) {
        this.NomeMalwareFalha = NomeMalwareFalha;
    }

    public String getSistemaOperacional() {
        return SistemaOperacional;
    }

    public void setSistemaOperacional(String SistemaOperacional) {
        this.SistemaOperacional = SistemaOperacional;
    }

    public String getPortaCC() {
        return PortaCC;
    }

    public void setPortaCC(String PortaCC) {
        this.PortaCC = PortaCC;
    }

    public Integer getPasso1() {
        return passo1;
    }

    public void setPasso1(Integer passo1) {
        this.passo1 = passo1;
    }

    public Integer getPasso2() {
        return passo2;
    }

    public void setPasso2(Integer passo2) {
        this.passo2 = passo2;
    }

    public Integer getPasso3() {
        return passo3;
    }

    public void setPasso3(Integer passo3) {
        this.passo3 = passo3;
    }

    public Integer getPasso4() {
        return passo4;
    }

    public void setPasso4(Integer passo4) {
        this.passo4 = passo4;
    }

    public Integer getPasso5() {
        return passo5;
    }

    public void setPasso5(Integer passo5) {
        this.passo5 = passo5;
    }

    public Integer getPasso6() {
        return passo6;
    }

    public void setPasso6(Integer passo6) {
        this.passo6 = passo6;
    }

    public Integer getPasso7() {
        return passo7;
    }

    public void setPasso7(Integer passo7) {
        this.passo7 = passo7;
    }

    public Integer getPasso8() {
        return passo8;
    }

    public void setPasso8(Integer passo8) {
        this.passo8 = passo8;
    }

    public Integer getPasso9() {
        return passo9;
    }

    public void setPasso9(Integer passo9) {
        this.passo9 = passo9;
    }

    public Integer getPasso10() {
        return passo10;
    }

    public void setPasso10(Integer passo10) {
        this.passo10 = passo10;
    }

    public Integer getPasso11() {
        return passo11;
    }

    public void setPasso11(Integer passo11) {
        this.passo11 = passo11;
    }

    public Integer getPasso12() {
        return passo12;
    }

    public void setPasso12(Integer passo12) {
        this.passo12 = passo12;
    }

    public Integer getPasso13() {
        return passo13;
    }

    public void setPasso13(Integer passo13) {
        this.passo13 = passo13;
    }

    public Integer getPasso14() {
        return passo14;
    }

    public void setPasso14(Integer passo14) {
        this.passo14 = passo14;
    }
    
    

    @Override
    public Attribute getIdAttribute() {
        return new Attribute("id", this.getClass());
    }
}
