
package br.edu.jcolibri.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import ssu.log.LogReport;

/** Classe utilitária que mantém métodos estáticos para tratamento de numéricos. */
public class NumberUtils {
	
	/** Constante que predefine a máscara com duas casas após a vírgula. */
    @Deprecated
	public static final String MASK_VALOR0 = "#,###,###,##0";
	/** Constante que predefine a máscara com duas casas após a vírgula. */
    @Deprecated
	public static final String MASK_VALOR2 = "#,###,###,##0.00";
	/** Constante que predefine a máscara com duas casas após a vírgula. */
    @Deprecated
	public static final String MASK_VALOR3 = "#,###,###,##0.000";
	/** Constante que predefine a máscara com quatro casas após a vírgula. */
    @Deprecated
	public static final String MASK_VALOR4 = "#,###,###,##0.0000";
	/** Constante que predefine a máscara com duas casas após a vírgula. */
    @Deprecated
	public static final String MASK_EXP2 = "#########0.00";
	/** Constante que predefine a máscara com quatro casas após a vírgula. */
    @Deprecated
	public static final String MASK_EXP4 = "#########0.0000";
        
	/** Constante de formato sem máscara. */
        public static final DecimalFormat FORMAT_NO_MASK  = new DecimalFormat();
	/** Constante que predefine a máscara sem casas após a vírgula. */
        public static final DecimalFormat FORMAT_MASK_VALOR_0 = new DecimalFormat(MASK_VALOR0);
	/** Constante que predefine a máscara com uma casas após a vírgula. */
        public static final DecimalFormat FORMAT_MASK_VALOR_1 = new DecimalFormat("#,###,###,##0.0");
	/** Constante que predefine a máscara com duas casas após a vírgula. */
        public static final DecimalFormat FORMAT_MASK_VALOR_2 = new DecimalFormat(MASK_VALOR2);
	/** Constante que predefine a máscara com duas casas após a vírgula. */
        public static final DecimalFormat FORMAT_MASK_VALOR_3 = new DecimalFormat(MASK_VALOR3);
	/** Constante que predefine a máscara com quatro casas após a vírgula. */
        public static final DecimalFormat FORMAT_MASK_VALOR_4 = new DecimalFormat(MASK_VALOR4);
	/** Constante que predefine a máscara com cinco casas após a vírgula. */
        public static final DecimalFormat FORMAT_MASK_VALOR_5 = new DecimalFormat("#,###,###,##0.00000");
	/** Constante que predefine a máscara com seis casas após a vírgula. */
        public static final DecimalFormat FORMAT_MASK_VALOR_6 = new DecimalFormat("#,###,###,##0.000000");
	/** Constante que predefine a máscara com sete casas após a vírgula. */
        public static final DecimalFormat FORMAT_MASK_VALOR_7 = new DecimalFormat("#,###,###,##0.0000000");
	/** Constante que predefine a máscara com duas casas após a vírgula. */
        public static final DecimalFormat FORMAT_MASK_EXP_2 = new DecimalFormat(MASK_EXP2);
	/** Constante que predefine a máscara com quatro casas após a vírgula. */
        public static final DecimalFormat FORMAT_MASK_EXP_4 = new DecimalFormat(MASK_EXP4);
        

	/** Constante que define a vírgula como separador decimal. */	
	private static String separador = ",";
	/** Constante que define o ponto como separador decimal. */
	private static String agrupador = ".";
        
		
  	
/*#####################################
    MÉTODOS DE CONVERSÃO NUMÉRICA
 #####################################*/	
	
	/** Método que retorna se uma string é um numerico ou nao. */
	public static boolean isNumero(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (Exception ex) { 
			return false;
		}
	}
        
        public static boolean isInteiro(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception ex) { 
			return false;
		}
	}
	
	public static String retirarZerosADireita(String n) {
		int i = n.length()-1;
		while (n.charAt(i) == '0') { i--; }
		if (n.charAt(i) == ',')
			i--;
		return n.substring(0, i+1);		
	}
	
	/** Método estático que retorna um valor formatado conforme a mascara.
	 * @param mascara Contendo a máscara a ser utilizada e que define o formato.
	 * @param valor Contendo o valor, do tipo double, a ser transformado.
	 * @return String Contendo o valor formatado. */
	public static String getFormato(String mascara, double valor) {	
		return getFormato(mascara, new Double(valor));
	}
	
	/** Método estático que retorna um valor formatado conforme a mascara.
	 * @param mascara Contendo a máscara a ser utilizada e que define o formato.
	 * @param valor Contendo o valor, do tipo Double, a ser transformado.
	 * @return String Contendo o valor formatado. */
	public static String getFormato(String mascara, Double valor) {	
		try {
			DecimalFormat formatter = null;			
			if (mascara.equals("NC") || mascara.equals("")) {
				formatter = FORMAT_NO_MASK;	
			} else {
				formatter = new DecimalFormat(mascara);	
			}
			
			// Deu problema no laboratorio...
			//formatter.setMaximumFractionDigits(6);			
			
			String r = formatter.format(valor.doubleValue());
			if (r.charAt(0) == ',')
				r = "0" + r;
			return r;
		} catch(Exception ex) {
			return null;
		}
	}
	
	/** Método estático que retorna um valor formatado conforme a mascara.
	 * @param mascara Contendo a máscara a ser utilizada e que define o formato.
	 * @param valor Contendo o valor, do tipo Double, a ser transformado.
	 * @return String Contendo o valor formatado. */
	public static String getValorFormatado(String mascara, Double valor) {
		return NumberUtils.getFormato(mascara, valor);
	}
	
	/** Método estático que retorna um valor formatado conforme a mascara.
	 * @param mascara Contendo a máscara a ser utilizada e que define o formato.
	 * @param valor Contendo o valor, do tipo double, a ser transformado.
	 * @return String Contendo o valor formatado. */
	public static String getValorFormatado(String mascara, double valor) {
		return NumberUtils.getFormato(mascara, new Double(valor));
	}
	
    /**
     * Método estático que retorna um Double, dado uma String como parâmetro.
     */
    public static Double stringToDouble(String s, boolean retornarNullQdoEmBranco) throws ParseException {
        if (s.isEmpty()) {
            if (retornarNullQdoEmBranco) {
                return null;
            } else {
                return new Double(0);
            }
        }
        return new Double(NumberFormat.getInstance().parse(s).doubleValue());
    }
	
	/** Método estático que retorna um Double, dado uma String como parâmetro.*/
	public static Double stringToDouble(String s) throws ParseException {	
		return stringToDouble(s, true);
	}		
	
	/** Método estático que retorna uma string dado um valor double.
	 * @param d Valor do tipo double.
	 * @return String Contendo uma string referente ao parâmetro d. */
	public static String numericToString(double d) {		
		return numericToString(new Double(d));
	}
		
	/** Método estático que retorna uma string dado um valor Double.
	 * @param d Valor do tipo Double.
	 * @return String Contendo uma string referente ao parâmetro d. */
	public static String numericToString(Double d) {		
		return getFormato("", d);
	}
			
	/** Método estático que retorna uma string dado um valor double.
	 * @param d Valor do tipo int.
	 * @return String Contendo uma string referente ao parâmetro d. */
	public static String numericToString(int d) {		
		return numericToString(new Integer(d));
	}
		
	/** Método estático que retorna uma string dado um valor Double.
	 * @param d Valor do tipo Integer.
	 * @return String Contendo uma string referente ao parâmetro d. */
	public static String numericToString(Integer d) {		
		return getFormato("", new Double(d.doubleValue()));
	}
	
	/** Método estático que testa dois double para corrigir o problema da precisão. */
	public static boolean iguais(double x, double y) {
		 double z = x - y;
		 if (z < 0)
			 z *= -1;
		 if (z < 0.000001) // aqui vc coloca a precisão
			 return true;
		 return false;
	}
	
    /* retorna o valor truncado no numero de decimais especificadas */
    public static double trunc(double value, int decimais) {
        double p = Math.pow(10, decimais);
        return Math.floor(value * p) / p; 
    }        
       
    /* Arredonda um valor com o numero de decimais especificado */
    public static double round(double value, int decimais) {
        double p = Math.pow(10, decimais);
        return Math.round(value * p) / p;
    }    
    
/*############################
 *      VINDO DO JPOSTO
 *############################*/	
	
	
	/** Método estático que retorna um valor formatado conforme a mascara.
	 * @param mascara Contendo a máscara a ser utilizada e que define o formato.
	 * @param valor Contendo o valor, do tipo double, a ser transformado.
	 * @return String Contendo o valor formatado. */
	public static String numberToString(double valor, String mascara) {	
		return numberToString(new Double(valor), mascara);
	}
	
	/** Método estático que retorna um valor formatado conforme a mascara.
	 * @param mascara Contendo a máscara a ser utilizada e que define o formato.
	 * @param valor Contendo o valor, do tipo Double, a ser transformado.
	 * @return String Contendo o valor formatado. */
	public static String numberToString(Double valor, String mascara) {	
		try {
			if (valor == null)
				return "";
			
			DecimalFormat formatter = null;			
			if (mascara.isEmpty()) 
				formatter = FORMAT_NO_MASK;	
			 else 
				formatter = new DecimalFormat(mascara);
			
			String r = formatter.format(valor.doubleValue());
			if (r.charAt(0) == ',')
				r = "0" + r;
			return r;
		} catch(Exception ex) {
			return null;
		}
	}
/**
     * Método estático que retorna um valor formatado conforme a mascara.
     * <p/>
     * @param formato Contendo o formato desejado, ex: NumberUtils.FORMAT_MASK_VALOR_0.
     * @param valor Contendo o valor, do tipo Double, a ser transformado.
     * @return String Contendo o valor formatado.
     */
    public static String numberToString(double valor, DecimalFormat formato) {
        return numberToString(Double.valueOf(valor), formato);
    }

    /**
     * Método estático que retorna um valor formatado conforme a mascara.
     * <p/>
     * @param formato Contendo o formato desejado, ex: NumberUtils.FORMAT_MASK_VALOR_0.
     * @param valor Contendo o valor, do tipo Double, a ser transformado.
     * @return String Contendo o valor formatado.
     */
    public static String numberToString(Double valor, DecimalFormat formato) {
        try {
            String r = formato.format(valor.doubleValue());
            if (r.charAt(0) == ',') {
                r = "0" + r;
            }
            return r;
        } catch (Exception ex) {
            LogReport.insertLog("Erro ao formatar número: " + valor + ".", ex, false);
            return null;
        }
    }
	
	/** Método estático que retorna uma string dado um valor double.
	 * @param d Valor do tipo double.
	 * @return String Contendo uma string referente ao parâmetro d. */
	public static String numberToString(double d) {		
		return numberToString(new Double(d), FORMAT_NO_MASK);
	}
		
	/** Método estático que retorna uma string dado um valor Double.
	 * @param d Valor do tipo Double.
	 * @return String Contendo uma string referente ao parâmetro d. */
	public static String numberToString(Double d) {		
		return numberToString(d, FORMAT_NO_MASK);
	}
			
	/** Método estático que retorna uma string dado um valor double.
	 * @param d Valor do tipo int.
	 * @return String Contendo uma string referente ao parâmetro d. */
	public static String numberToString(int d) {		
		return numberToString(new Double(d), FORMAT_NO_MASK);
	}
	
        /**
     Remove os zeros sem valor numérico. Mantém a vírgula (,) se a mesma
     * existir; Se o zero tem valor, é mantido. Ex: 13,000 == 13,0 Ex2: 16000 ==
     * 16000
     */
    public static String removerZerosSemValor(String numeroFormatado) {
        numeroFormatado.replaceAll("\\.", ",");
        if (!numeroFormatado.contains(",")) {
            return numeroFormatado;
        }
        StringBuilder buffer = new StringBuilder(numeroFormatado);
        while (buffer.length() > 2 && buffer.charAt(buffer.length() - 1) == '0' && buffer.charAt(buffer.length() - 2) != ',') {
            buffer.deleteCharAt(buffer.length() - 1);
        }
        return buffer.toString();
    }
        
	/** Método para testes. */
	public static void main(String args[]) throws Exception { 		
		//double v1 = 0.25;	  
		//DecimalFormat formatter = new DecimalFormat();	
		//formatter.setMaximumFractionDigits(6);
		//String r = formatter.format(v1);
		//System.out.println(r);
                Double d = new Double(1037.61);
                int s = 100;
                double c = NumberUtils.round((d.doubleValue()*s),0);
                Integer dd = new Integer((int)(d.doubleValue()*s));                
                System.out.println(dd);
	}	
}
