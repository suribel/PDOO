/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

/**
 *
 * @author antonio-w10
 */
public class Especulador extends Jugador{
    
    private int fianza;

    public Especulador(Jugador jugador, int fianza) {
        super(jugador);
        this.fianza = fianza;
    }
    
    protected void pagarImpueto(int cantidad){
        this.saldo -= (getCasillaActual().getCoste())/2;
    }
    
    protected Especulador convertirme(int fianza){
        return this;
    }
    
    protected boolean deboIrACarcel(){
        return super.deboIrACarcel() && !this.pagarFianza();
    }
    
    private boolean pagarFianza(){
        boolean pagar = false;
        
        if (tengoSaldo(fianza)){
              pagar = true;
              System.out.println("Fianza para salir carcel pagada"); 
        }
        
        
        return pagar;
    }
    
    protected boolean puedoEdificarCasa(TituloPropiedad titulo){
        boolean edificada = false;
        
        int numCasas = titulo.getNumCasas();
        if (numCasas < 8){
            int costeEdificarCasa = titulo.getPrecioEdificar();
            boolean tengoSaldo = tengoSaldo(costeEdificarCasa);
            
            if (tengoSaldo){
                titulo.edificarCasa();
                modificarSaldo(-costeEdificarCasa); 
                edificada = true;
            }            
        }
        
        return edificada;
    }
    
    protected boolean puedoEdificarHotel(TituloPropiedad titulo){
        // 
        //LO MISMO QUE CASA
        //
        boolean edificada = false;
        
        int numHoteles = titulo.getNumHoteles();
        int numCasas = titulo.getNumCasas();
        if (numHoteles < 8 && numCasas >= 4){
            int costeEdificarHotel = titulo.getPrecioEdificar();
            boolean tengoSaldo = tengoSaldo(costeEdificarHotel);
            
            if (tengoSaldo){
                titulo.edificarHotel();
                modificarSaldo(-costeEdificarHotel); 
                edificada = true;
            }            
        }
        
        return edificada;  
    }
    
    
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    
}
