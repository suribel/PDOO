/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import java.util.ArrayList;

/**
 *
 * @author antonio-w10
 */
public class Tablero {
    
    private ArrayList<Casilla> casillas;
    private Casilla carcel;

    public Tablero() {
        inicializar();
    }

    ArrayList<Casilla> getCasillas() {
        return casillas;
    }

    Casilla getCarcel() {
        return carcel;
    }
    
    
    
    private void inicializar(){
        casillas = new ArrayList<>(); 
        
        casillas.add(new OtraCasilla(0,1000,TipoCasilla.SALIDA));
        
        casillas.add(new Calle(1,new TituloPropiedad("primera", 50, 25, 100 , 200, (float) 0.2)));
        casillas.add(new Calle(2,new TituloPropiedad("segunda", 50, 25, 100 , 200, (float) 0.2)));
        casillas.add(new Calle(3,new TituloPropiedad("tercera", 50, 25, 100 , 200, (float) 0.2)));
        
        casillas.add(new OtraCasilla(4,100,TipoCasilla.CARCEL));
        carcel = casillas.get(4);
        
        casillas.add(new OtraCasilla(5,100,TipoCasilla.SORPRESA));
        
        casillas.add(new Calle(6,new TituloPropiedad("cuarta", 150, 50, 100 , 200, (float) 0.2)));
        casillas.add(new Calle(7,new TituloPropiedad("quinta", 150, 50, 100 , 200, (float) 0.2)));
        
        casillas.add(new OtraCasilla(8,100,TipoCasilla.JUEZ));
        
        casillas.add(new Calle(9,new TituloPropiedad("sexta", 50, 50, 100 , 200, (float) 0.2)));        
        
        casillas.add(new OtraCasilla(10,100,TipoCasilla.SORPRESA));
        
        casillas.add(new Calle(11,new TituloPropiedad("setima", 250, 150, 100 , 200, (float) 0.2)));
        casillas.add(new Calle(12,new TituloPropiedad("octava", 200, 100, 100 , 200, (float) 0.2)));
        
        casillas.add(new OtraCasilla(13,100,TipoCasilla.PARKING));
        
        casillas.add(new Calle(14,new TituloPropiedad("novena", 50, 30, 100 , 200, (float) 0.2)));
        
        casillas.add(new OtraCasilla(15,100,TipoCasilla.SORPRESA));
        
        casillas.add(new Calle(16,new TituloPropiedad("decima", 50, 20, 100 , 200, (float) 0.2)));
        casillas.add(new Calle(17,new TituloPropiedad("decimoprimera", 50, 20, 100 , 200, (float) 0.2)));
        
        casillas.add(new OtraCasilla(18,100,TipoCasilla.IMPUESTO));
        
        casillas.add(new Calle(19,new TituloPropiedad("decimosegunda", 300, 200, 100 , 200, (float) 0.2)));
        
       
        
        
        
        
        
        
        
        
        
        
        
                        
    }
    
    
    
    boolean esCasillaCarcel(int numeroCasilla){
        return numeroCasilla == 4 ;
    }
    
    
    Casilla obtenerCasillaFinal(Casilla casilla, int desplazamiento){
        int numCas = casilla.getNumeroCasilla();
        numCas = (numCas + desplazamiento) % this.casillas.size();
        return this.casillas.get(numCas);
    }
            
    Casilla obtenerCasillaNumero(int numeroCasilla){
        return casillas.get(numeroCasilla);
    }
    
    
    
    
    

    @Override
    public String toString() {
        return "Tablero{" + "casillas=" + casillas + ", carcel=" + carcel + '}';
    }
    
    
    
    
    
}
