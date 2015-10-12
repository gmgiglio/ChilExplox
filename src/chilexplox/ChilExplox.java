/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

/**
 *
 * @author carlossalame
 */
public class ChilExplox {

    Usuario usr = new Usuario("Tito", "12345678");
    Empresa empresa = Empresa.getInstance();
    Cliente cliente = new Cliente("Pedro Pablo","Vicuña Mackenna 1122", 94556262);
    
    public void casoDeUso1(){
        usr.crearPedido(empresa.sucursales.get(0), empresa.sucursales.get(1)); //crear pedido sin datos de cliente
        
        
        
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        
        
    }
    
}
