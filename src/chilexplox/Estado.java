/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

/**
 *
 * @author gianfrancogiglio
 */
public enum Estado {
    En_origen("En origen"),En_transito("En transito"),En_destino("En destino"),Entregado("Entregado"),Equivocado("Equivocado");
    
    private final String text;

    /**
     * @param text
     */
    private Estado(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
    
}
