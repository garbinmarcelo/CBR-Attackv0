// 
// Decompiled by Procyon v0.5.30
// 



import br.edu.jcolibri.testcase.*;
import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;

public class Environment implements CaseComponent
{
    Integer id;
    String modulo;
    String versao;
    String so;
    
    @Override
    public String toString() {
        return "(" + this.id + "," + this.modulo + "," + this.versao + "," + this.so + ")";
    }
    
    public String getModulo() {
        return this.modulo;
    }
    
    public void setModulo(final String modulo) {
        this.modulo = modulo;
    }
    
    public String getVersao() {
        return this.versao;
    }
    
    public void setVersao(final String versao) {
        this.versao = versao;
    }
    
    public String getSo() {
        return this.so;
    }
    
    public void setSo(final String so) {
        this.so = so;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
  
    
    @Override
    public Attribute getIdAttribute() {
        return new Attribute("id", this.getClass());
    }
}
