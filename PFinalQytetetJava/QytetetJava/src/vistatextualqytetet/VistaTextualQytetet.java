/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistatextualqytetet;

import controladorqytetet.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author antonio-w10
 */
public class VistaTextualQytetet {
    
    private static final Scanner in = new Scanner (System.in);
    
    public ArrayList<String> obtenerNombreJugadores(){
        ArrayList<String> nombres = new ArrayList<>();
        
        int num = 0;
        while (num<3){
            System.out.println("Ingresa numero de jugadores mayor que dos: ");
            num = Integer.parseInt(in.nextLine()); //Se hace esto para no tener problemas con la lectura de enteros ya que se usa el mismo scaner        
        }
        
        for(int i=0; i < num; i++){
            System.out.println("Ingresa nombre: ");
            String nombre = in.nextLine();
            nombres.add(nombre);
        }
        
        return nombres;
    }
    
    public int elegirCasilla(int opcionMenu){
       ArrayList<Integer> operaciones = controlador.obtenerCasillasValidas(opcionMenu);
       
       int num =-1;
       if (!operaciones.isEmpty()){
           
           ArrayList<String> ArrayString = new ArrayList<>();
        
           
                
           for (int i=0; i < operaciones.size(); i++) {
                ArrayString.add(i, Integer.toString(operaciones.get(i)));
           }  
           
           System.out.println();
           System.out.println("OPCIONES: "); 
           for (int i=0; i < ArrayString.size(); i++) {
               
               System.out.println("Casilla numero: " + ArrayString.get(i));
           }
           
            //Comprobar valor correcto
            boolean var = false;
            while(!var){
                            
                System.out.println("Ingresa el numero de Casilla: ");
                num = Integer.parseInt(in.nextLine());
            
                for (Integer n: operaciones){                
                    if (num == n){
                        var = true;
                        break;
                    }
                } 
            }
           
        
           //String stringresult = leerValorCorrecto(ArrayString);
           //result = Integer.parseInt(stringresult);
       }
        
        return num;
    }
    public String leerValorCorrecto(ArrayList<String> valoresCorrectos){
        
        
        return null;
    }
    public int elegirOperacion(){
        
        
        ArrayList<Integer> operaciones = controlador.obtenerOperacionesJuegoValidas();
        
        ArrayList<String> ArrayString = new ArrayList<>();
        
        //muestra al usuario cada valor
                
        for (int i=0; i < operaciones.size(); i++) {
            ArrayString.add(i, Integer.toString(operaciones.get(i)));
        }    
        
        System.out.println(); 
        System.out.println("OPCIONES: "); 
        for (int i=0; i < ArrayString.size(); i++) {
           System.out.println(ArrayString.get(i) + "-" + OpcionMenu.values()[operaciones.get(i)]);
        }
        
        //Comprobar valor correcto
        int num =0;
        boolean var = false;
        while(!var){
            
            System.out.println("Ingresa valor: ");
            num = Integer.parseInt(in.nextLine());
            
            for (Integer n: operaciones){                
               if (num == n){
                   var = true;
                   break;
               }
            } 
        }
        
        
           
        //String result = leerValorCorrecto(ArrayString);
        //int numEntero = Integer.parseInt(result);
                
        return num;
    }
    
    static ControladorQytetet controlador = ControladorQytetet.getInstance();
    
    public static void main(String args[]) {
        
        VistaTextualQytetet ui = new VistaTextualQytetet();
        controlador.setNombreJugadores(ui.obtenerNombreJugadores());
        int operacionElegida, casillaElegida = 0;
        boolean necesitaElegirCasilla;
        do {
            operacionElegida = ui.elegirOperacion();
            necesitaElegirCasilla = controlador.necesitaElegirCasilla(operacionElegida);
            if (necesitaElegirCasilla)
                casillaElegida = ui.elegirCasilla(operacionElegida);
            if (!necesitaElegirCasilla || casillaElegida >= 0)
                System.out.println(controlador.realizarOperacion(operacionElegida,casillaElegida));
        } while (1 == 1);
    }
        
}
