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
public class OtraCasilla extends Casilla{
    
    
    private TipoCasilla tipoCasilla;
    
    
    public OtraCasilla(int numeroCasilla, int coste, TipoCasilla tipoCasilla) {
        super(numeroCasilla, coste, tipoCasilla);
        this.tipoCasilla = tipoCasilla;
    }
    
    
    protected TipoCasilla getTipo() {
        return tipoCasilla;
    }

    //protected TituloPropiedad getTitulo() {
    //    return null;
    //}
    
    protected boolean soyEdificable(){
        return false; //(getTipo() == TipoCasilla.CALLE)
    }
    
    
    @Override
    public String toString() {
        return super.toString() +
                ", tipoCasilla=" + tipoCasilla ;
    }
    
}
