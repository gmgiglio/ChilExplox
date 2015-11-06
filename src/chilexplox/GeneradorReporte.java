/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gianfrancogiglio
 */
public class GeneradorReporte {
    
    private static Usuario autoRobot = new Usuario("AutoGererador reporte","1234");
    
    public static Mensaje generarReporte(){
        String fecha = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS").format(new Date());
        String nombreReporte = "Reporte ganancias " + Empresa.getNombre() + fecha;
        Mensaje reporte = new Mensaje(nombreReporte , "", autoRobot);
        
        reporte.agregarParrafo("Numero de pedidos: " + Empresa.getNroPedidos());
        reporte.agregarParrafo("Ganancias Totales: $" + gananciasTotales());
        
        //ganancias sucursales
        String parrGanSuc = "Ganancias sucursales:" + System.lineSeparator();
        for(Sucursal s : Empresa.getSucursales()){
            parrGanSuc += "          " + s.getNombre() + ": $" + gananciasSucursal(s) + System.lineSeparator();
        }
        reporte.agregarParrafo(parrGanSuc);
        
        return reporte;
    }
    
    private static double gananciasTotales(){
        double total=0;
        for(Sucursal s : Empresa.getSucursales()){
<<<<<<< HEAD
            LinkedList<Pedido> pedidosTotales = s.getPedidosEnDest();
            pedidosTotales.addAll(s.getPedidosPend());
            pedidosTotales.addAll(s.getPedidosEntregados());
            pedidosTotales.addAll(s.getPedidosEquivocados());
            for (Pedido p : pedidosTotales){
                System.out.println(p.getIdPedido() + " " + p.getCostoEnvio());
                total += p.getCostoEnvio();
            }
=======
            total += gananciasSucursal(s);
>>>>>>> origin/master
        }
        return total;
    }
    
    private static double gananciasSucursal(Sucursal s){
        double total = 0;
        List<Pedido> pedidosTotales = s.getPedidosEnDest();
        pedidosTotales.addAll(s.getPedidosPend());
        pedidosTotales.addAll(s.getPedidosEntregados());
        pedidosTotales.addAll(s.getPedidosEquivocados());
        for (Pedido p : pedidosTotales){
            total += p.getCostoEnvio();
        }
        
        return total;
    }
    
    
    
}
