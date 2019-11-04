/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author antonio-w10
 */
public class PruebaQytetet {
    
    private static final Scanner in = new Scanner (System.in);
    
    
    static Qytetet juego = Qytetet.getInstance();
    
    
    static ArrayList<Sorpresa> mazo;
    
    static ArrayList<String> nombres;
    
            
    /**
     * @param args the command line arguments
     */
    public static void maiyt(String[] args) {
        
        
        nombres = PruebaQytetet.getNombreJugadores();
        
        juego.inicializarJuego(nombres);
        
        juego.mover(6);
        
        //juego.aplicarSorpresa();
        juego.comprarTituloPropiedad();
        //juego.edificarHotel(6);
        //juego.hipotecarPropiedad(6);
        //juego.venderPropiedad(6);
        //juego.cancelarHipoteca(6);
        //juego.siguienteJugador();
        
        //juego.mover(9);
        
        juego.obtenerRanking();
        
        
        System.out.println(juego.jugadorActual.toString());
        
        //juego.jugadorActual.toString();
        
        //juego.jugar();
        
        //juego.jugar();
       
        
        //System.out.print(juego.toString());
          
        //mazo = juego.getMazo();
        
        //System.out.print(mazo.toString());
        //System.out.print(juego.getTablero().toString());
        
        
        
        //System.out.println(getM1().toString());
        //System.out.println(getM2().toString());
        //System.out.println(getM3(TipoSorpresa.PAGARCOBRAR).toString());
        
        //System.out.println("Jugadores: ");
        //for (String s: nombres) {
        //   System.out.println(s);
        //}    
        
        //System.out.print(juego.getJugadores().toString());
        
        
        
        
        
        
        
        
    }
    
    static ArrayList<String> getNombreJugadores(){
        ArrayList<String> nombres = new ArrayList<>();
        
        System.out.println("Ingresa numero de jugadores: ");
        int num = Integer.parseInt(in.nextLine()); //Se hace esto para no tener problemas con la lectura de enteros ya que se usa el mismo scaner        
        
        for(int i=0; i < num; i++){
            System.out.println("Ingresa nombre: ");
            String nombre = in.nextLine();
            nombres.add(nombre);
        }
        
        return nombres;
        
        
        
    }
    
    
    private static ArrayList<Sorpresa> getM1(){
               
        ArrayList<Sorpresa> mazoSorpresa = new ArrayList<>();
        
        for (Sorpresa s: mazo) {
           if (s.getValor() > 0){                
                mazoSorpresa.add(s);
            }
        }       
        
        return mazoSorpresa;       
        
        
    }
    
    
    private static ArrayList<Sorpresa> getM2(){
        
        ArrayList<Sorpresa> mazoSorpresa = new ArrayList<>();
        
        for (Sorpresa s: mazo) {
           if (s.getTipo() == TipoSorpresa.IRACASILLA){                
                mazoSorpresa.add(s);
            }
        }       
        
        return mazoSorpresa;       
               
        
        
    }
    
    
    private static ArrayList<Sorpresa> getM3(TipoSorpresa tipo){
        
        ArrayList<Sorpresa> mazoSorpresa = new ArrayList<>();
        
        for (Sorpresa s: mazo) {
           if (s.getTipo() == tipo){                
                mazoSorpresa.add(s);
            }
        }       
        
        return mazoSorpresa;      
        
        
    }
    
}
