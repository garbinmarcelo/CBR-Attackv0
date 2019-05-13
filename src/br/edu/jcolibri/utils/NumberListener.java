

package br.edu.jcolibri.utils;

import java.awt.Window;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import ssu.log.LogReport;

public class NumberListener {

    public static void registerInteger(final JTextField jTextField) {
        jTextField.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        jTextField.setDocument(new PlainDocument() {
            public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                if (str == null || str.length() == 0) {
                    return;
                }

                try {
                    if (str.length() > 1) {
                        NumberUtils.stringToDouble(str);
                    } else {
                        if (str.equals("-") || str.equals("+")) {
                            if (offset > 0) {
                                return;
                            }
                        }
                        if (!(str.equals("-") || str.equals("+") 
                                || str.equals("0") || str.equals("1") || str.equals("2") || str.equals("3") || str.equals("4")
                                || str.equals("5") || str.equals("6") || str.equals("7") || str.equals("8") || str.equals("9"))) {
                            return;
                        }
                    }
                    super.insertString(offset, str, attr);
                } catch (Exception ex) {
                    // Não mostra o erro, não precisa!
                }
            }
        });

        jTextField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                jTextField.selectAll();
            }

            public void focusLost(FocusEvent evt) {
                if (jTextField.getText().equals("")) {
                    jTextField.setText("0");
                }
                try {
                    new Integer(NumberUtils.stringToDouble(jTextField.getText()).intValue());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    LogReport.insertLog("Erro ao formatar: valor " + jTextField.getText() + " inválido.", null, true);
                    jTextField.setText("");
                    jTextField.requestFocus();
                }
            }
        });
    }
    
    /**
     * '-' e ',' não são contadas como caractere no tamanho da String do
     * TextField
     * <p>
     * EXEMPLO - NumberListener.registerInteger(jTextField_Cod, 10, SwingConstants.LEFT);
     *
     * @param jTextField
     * @param quantidadeCaracteres
     * @param alinhamento
     */
    public static void registerInteger(final JTextField jTextField, final Integer quantidadeCaracteres, Integer alinhamento) {
        registerInteger(jTextField, quantidadeCaracteres, alinhamento, null, null, null);
    }

    /**
     * <P>
     * Caractere '{@code -}' e '{@code ,}' não entram na contagem de caracteres
     * da String do JTextField TextField</P>
     *
     * {@code NumberListener.registerInteger(jTextField_Cod, 10,
     * SwingConstants.LEFT, 0);}
     *
     * @since 01/06/2015
     * @param jTextField
     * @param quantidadeCaracteres Tamanho máximo(<I>length</I>) de caracteres para o
     * JTextField. Caso {@code null}, padrão: Sem limitação de caracteres.
     * @param alinhamento Definição do alinhamento do texto em relação ao
     * JTextField. Caso {@code null}, padrão similar a:
     * {@code SwingConstants.LEFT}
     * @param valorDefault Utilizado para deixa um valor padrão no campo quando
     * tudo for apagado
     */
    public static void registerInteger(final JTextField jTextField, final Integer quantidadeCaracteres, final Integer alinhamento, final Integer valorDefault) {
        registerInteger(jTextField, quantidadeCaracteres, alinhamento, valorDefault, null, null);
    }
    
    /**
     * <P>
     * Caractere '{@code -}' e '{@code ,}' não entram na contagem de caracteres
     * da String do JTextField TextField</P>
     *
     * {@code NumberListener.registerInteger(jTextField_Cod, 10,
     * SwingConstants.LEFT, 0);}
     *
     * @since 01/06/2015
     * @param jTextField
     * @param quantidadeCaracteres Tamanho máximo(<I>length</I>) de caracteres para o
     * JTextField. Caso {@code null}, padrão: Sem limitação de caracteres.
     * @param alinhamento Definição do alinhamento do texto em relação ao
     * JTextField. Caso {@code null}, padrão similar a:
     * {@code SwingConstants.LEFT}
     * @param valorDefault Utilizado para deixa um valor padrão no campo quando
     * tudo for apagado
     * @param valorMinimo Valor Inteiro que será utilizado para verificar se o valor digitado está na faixa permitida
     * @param valorMaximo Valor Inteiro que será utilizado para verificar se o valor digitado está na faixa permitida
     */
    public static void registerInteger(final JTextField jTextField, final Integer quantidadeCaracteres, final Integer alinhamento, final Integer valorDefault, final Integer valorMinimo, final Integer valorMaximo) {
        if (alinhamento != null) {
            jTextField.setHorizontalAlignment(alinhamento);
        }

        jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField.selectAll();
            }
        });
        
        final boolean validaMinimoMaximo = (valorMinimo != null && valorMaximo != null && valorMinimo <= valorMaximo);
        jTextField.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                if (str == null || str.length() == 0) {
                    return;
                }

                try {
                    if (str.length() > 1) {
                        NumberUtils.stringToDouble(str);
                    } else {
                        if (str.equals("-") || str.equals("+")) {
                            if (offset > 0) {
                                return;
                            }
                        }
                        if (!(str.equals("-") || str.equals("+")
                                || str.equals("0") || str.equals("1") || str.equals("2") || str.equals("3") || str.equals("4")
                                || str.equals("5") || str.equals("6") || str.equals("7") || str.equals("8") || str.equals("9"))) {
                            return;
                        }
                    }
                    
                    if (quantidadeCaracteres != null) {
                        String conteudoString = str;
                        conteudoString = conteudoString.replace("-", "");
                        conteudoString = conteudoString.replace(".", "");
                        int ilen = (getLength() + conteudoString.length());
                        if (ilen > quantidadeCaracteres) {
                            return;
                        }
                    }

                    if (validaMinimoMaximo
                            && (!jTextField.getText().trim().isEmpty() || !str.isEmpty())) {

                        Integer temp = Integer.parseInt(jTextField.getText().trim() + str);
                        if (valorMinimo > temp
                                || valorMaximo < temp) {
                            return;
                        }
                    }
                    
                    super.insertString(offset, str, attr);
                } catch (Exception ex) {
                    // Não mostra o erro, não precisa!
                }
            }
        });
        
        jTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                jTextField.selectAll();
            }

            @Override
            public void focusLost(FocusEvent evt) {
                if (!jTextField.getText().equals("")) {
                    try {
                        new Integer(NumberUtils.stringToDouble(jTextField.getText()).intValue());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        LogReport.insertLog("Erro ao formatar: valor " + jTextField.getText() + " inválido.", null, true);
                        jTextField.setText("");
                        jTextField.requestFocus();
                    }
                    
                    if (validaMinimoMaximo
                            && !jTextField.getText().trim().isEmpty()) {

                        Integer temp = Integer.parseInt(jTextField.getText().trim());
                        if (valorMinimo > temp) {
                            jTextField.setText(String.valueOf(valorMinimo));
                        }
                        if (valorMaximo < temp) {
                            jTextField.setText(String.valueOf(valorMaximo));
                        }
                    }

                } else {
                    if (valorDefault != null) {
                        jTextField.setText(String.valueOf(valorDefault));
                    }
                }
            }
        });
    }

    /**
     * Método estático que registra um JTextField como um Double.
     * <p/>
     * @param jTextField Contendo o JTextField.
     * @param formato Se null, nao formata.
     */
    public static void registerDouble(final JTextField jTextField, final DecimalFormat formato) {
        registerDouble(jTextField, formato, 0d);
    }
    
    public static void registerDouble(final JTextField jTextField, final DecimalFormat formato, final Double valorDefault) {
        jTextField.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        jTextField.addFocusListener(new java.awt.event.FocusAdapter() {

            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField.selectAll();
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (jTextField.getText().isEmpty()) {
                    if (valorDefault != null) {
                        jTextField.setText(NumberUtils.numberToString(valorDefault));
                    }
                }
                try {
                    if (formato != null) {
                        Double vlr = NumberUtils.stringToDouble(jTextField.getText());
                        jTextField.setText(formatar(vlr, formato));
                    }
                } catch (Exception ex) {
                    jTextField.setText("");
                    LogReport.insertLog("Erro ao formatar valor: ", ex, true);
                    jTextField.requestFocus();
                }
            }
        });
    }

        /**
     * Método estático que registra um JTextField como um Double.
     * <p/>
     * @param jTextField Contendo o JTextField.
     * @param formato Se null, nao formata.
     */
    public static void registerDouble(final Window parent, final JTextField jTextField, final DecimalFormat formato) {
        jTextField.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField.selectAll();
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                String text = jTextField.getText();
                if (text.isEmpty()) {
                    jTextField.setText("0");
                }
                try {
                    if (formato != null) {
                        Double vlr = NumberUtils.stringToDouble(text);
                        jTextField.setText(formatar(vlr, formato));
                    }
                } catch (ParseException ex) {
                    jTextField.setText("");
                    LogReport.insertLog("Aviso ao formatar valor: A entrada '" + text + "' não representa um valor válido.", null, true);
                    jTextField.requestFocus();
                } catch (Exception ex) {
                    jTextField.setText("");
                    LogReport.insertLog(":TipoErro ao formatar valor: ", ex, true);
                    jTextField.requestFocus();
                }
            }
        });
    }
    
    /**
     * Método estático que registra um JTextField como um Double.
     * <p/>
     * @param jTextField Contendo o JTextField.
     * @param formato Se null, nao formata.
     */
    public static void registerDouble(final JTextField jTextField, final String formato) {
        registerDouble(jTextField, new DecimalFormat(formato));
    }

	
	/** Método estático que retorna um valor formatado conforme a mascara.
	 * @param formato Contendo a máscara a ser utilizada e que define o formato.
	 * @param valor Contendo o valor, do tipo Double, a ser transformado.
	 * @return String Contendo o valor formatado. */
	private static String formatar(Double valor, String formato) {	
		try {
			String.valueOf(valor);
			java.text.DecimalFormat formatter = new java.text.DecimalFormat(formato);			
			return formatter.format(valor.doubleValue());
		} catch(Exception ex) {
			return null;
		}
	}
	/** Método estático que retorna um valor formatado conforme a mascara.
	 * @param formato Contendo a máscara a ser utilizada e que define o formato.
	 * @param valor Contendo o valor, do tipo Double, a ser transformado.
	 * @return String Contendo o valor formatado. */
	private static String formatar(Double valor, DecimalFormat formatter) {	
		try {
			return formatter.format(valor.doubleValue());
		} catch(Exception ex) {
			return null;
		}
	}

    public static void registerIntegerAlinEsquerda(final JTextField jTextField) {
        jTextField.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);

        jTextField.setDocument(new PlainDocument() {
            public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                if (str == null || str.length() == 0)
                    return;

                try {
                    if (str.length() > 1) {
                        NumberUtils.stringToDouble(str);
                    } else {
                        if (str.equals("-") || str.equals("+")) {
                            if (offset > 0) {
                                return;
                            }
                        }
                        if (!(str.equals("-") || str.equals("+") ||
                                str.equals("0") || str.equals("1") || str.equals("2") || str.equals("3") || str.equals("4") ||
                                str.equals("5") || str.equals("6") || str.equals("7") || str.equals("8") || str.equals("9")))
                            return;
                    }
                    super.insertString(offset, str, attr);
                } catch (Exception ex) {
                    // Não mostra o erro, não precisa!
                }
            }
        });

        jTextField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                jTextField.selectAll();
            }
            public void focusLost(FocusEvent evt) {
                if (jTextField.getText().equals(""))
                    jTextField.setText("0");
                try {
                    new Integer(NumberUtils.stringToDouble(jTextField.getText()).intValue());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    LogReport.insertLog("Erro ao formatar: valor " + jTextField.getText() + " inválido.", null, true);
                    jTextField.setText("");
                    jTextField.requestFocus();
                }
            }
        });
    }
}
