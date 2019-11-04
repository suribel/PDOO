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
public abstract class Casilla {
    private int numeroCasilla = 0;
    private int coste;
    
    
    //private TipoCasilla tipoCasilla;
    //private TituloPropiedad titulo; 

    public Casilla(int numeroCasilla,int coste, TipoCasilla tipoCasilla) {
        this.numeroCasilla = numeroCasilla;
        this.coste = coste;
        //this.tipoCasilla = tipoCasilla;
        //this.titulo=null;
        
        
        
    }
    
    //Son de TIPO CALLE
    public Casilla(int numeroCasilla, TituloPropiedad titulo) {
        this.numeroCasilla = numeroCasilla;
        this.coste = titulo.getPrecioCompra();
        //this.tipoCasilla = TipoCasilla.CALLE;
        //this.titulo=titulo;
        
    }
    

    int getNumeroCasilla() {
        return numeroCasilla;
    }

    int getCoste() {
        return coste;
    }

    protected abstract TipoCasilla getTipo();
    

    //protected TituloPropiedad getTitulo() {
    //    return titulo;
    //}
    
    
    
    public int setCoste(int coste){
        return coste;
    }
        
    protected abstract boolean soyEdificable();
        
    
        
    
    @Override
    public String toString() {
        return "Casilla{" + "numeroCasilla=" + numeroCasilla + ", coste=" + coste +  '}';
    }
    
    

    
    
    
    
    
    
    
    
    
    
}
