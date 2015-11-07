/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import java.io.*;

/**
 *
 * @author carlossalame
 */
public class ChilExplox {

    Usuario usr = new Usuario("Tito", "12345678");
    Cliente cliente = new Cliente("Pedro Pablo","Vicuña Mackenna 1122", "94556262");
    
    
    public void casoDeUso1(){
        //Pedido p = usr.crearPed(Empresa.getInstance().getSucursal("San Joaquin"), Empresa.getInstance().getCliente("Valparaiso")); //crear pedido sin datos de cliente
        //usr.agregarEnc(p, 450, 315, "Placeres 2413");//agregar encomiendas sin prioridad
        //usr.agregarEnc(p, 1500, 2300, "Altamirano 1321");
        //usr.agregarEnc(p, 201, 160, 3 , "O'higgins 4045");//agregar encomienda con prioridad
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
 
        //Empresa.getInstance().agregarSucursal("San Joaquin", "Santiago", "Vicuña Mackenna 4860");
        //Empresa.getInstance().agregarSucursal("Valparaiso", "Valparaiso", "España 2130");
        
        
        
        Empresa.serializar("data/empresa.ser");
        
        //Empresa.deserializar("data/empresa.ser");
        
        //System.out.println(Empresa.getInstance().sucursalesEnCiudad("Santiago").get(0).getNombre());
        
        
                
              
        
    }
    
}
