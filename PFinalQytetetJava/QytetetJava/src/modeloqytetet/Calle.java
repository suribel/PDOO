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
public class Calle extends Casilla {
    
    
    private TituloPropiedad titulo;

    public Calle(int numeroCasilla,TituloPropiedad titulo) {
        super(numeroCasilla, titulo);
        this.titulo=titulo;
    }
    
    
    TituloPropiedad asignarPropietario(Jugador jugador){
        this.titulo.setPropietario(jugador);
        return this.titulo;
    }
        
    
    protected TipoCasilla getTipo() {
        return TipoCasilla.CALLE;
    }

    protected TituloPropiedad getTitulo() {
        return titulo;
    }
    
    int pagarAlquiler(){
        int costeAlquiler= this.titulo.pagarAlquiler();
        return costeAlquiler;
    }
    
    protected boolean soyEdificable(){
        return true; //(getTipo() == TipoCasilla.CALLE)
    }
    
    
    private void setTitulo(TituloPropiedad titulo) {
        this.titulo = titulo;
    }
    
    
    
    boolean tengoPropietario(){
        return this.titulo.tengoPropietario();
    }
    
    
    @Override
    public String toString() {
        return super.toString() + 
                ", tipoCasilla=" + TipoCasilla.CALLE + ", titulo=" + titulo ;
    }
    
    
    
    
    
    
    
    
    
    
    
}
