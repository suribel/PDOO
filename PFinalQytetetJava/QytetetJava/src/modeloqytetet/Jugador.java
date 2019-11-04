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
public class Jugador implements Comparable<Jugador> {
    
    private boolean encarcelado = false;
    private String nombre;
    int saldo = 7500;
    
    private Sorpresa cartaLibertad;
    private Casilla casillaActual;
    private ArrayList<TituloPropiedad> propiedades;
    
    public Jugador(String nombre){
        this.nombre = nombre;
        this.cartaLibertad = null;
        this.casillaActual = null;
        this.propiedades = new ArrayList(); 
        
        
    }
    
    public Jugador(String nombre, Sorpresa cartaLibertad, Casilla casillaActual,ArrayList<TituloPropiedad> propiedades) {
        this.nombre = nombre;
        this.cartaLibertad = cartaLibertad;
        this.casillaActual = casillaActual;
        this.propiedades = propiedades;
        
        
    }
    
 
    boolean cancelarHipoteca(TituloPropiedad titulo){
        boolean cancelar = false;
        int costecancelar = titulo.calcularCosteCancelar();
        
        if (tengoSaldo(costecancelar)){
            
            modificarSaldo(-costecancelar);
            titulo.cancelarHipoteca();
            cancelar = true;
        }        
        
        return cancelar;
    }
     
    boolean comprarTituloPropiedad(){        
        boolean comprado = false;
        
        int costeCompra = this.casillaActual.getCoste();
        if (costeCompra < this.saldo){
            TituloPropiedad titulo = ((Calle)this.casillaActual).asignarPropietario(this);//jugactual;
            this.propiedades.add(titulo);
            modificarSaldo(-costeCompra);
            
            comprado = true;
            
        }
        return comprado;
    }
     
    int cuantasCasasHotelesTengo(){
         
        int num = 0;
        for (TituloPropiedad prop: this.propiedades){
            num += prop.getNumCasas() + prop.getNumHoteles();
        }
        return num;
    }
      
    boolean deboPagarAlquiler(){    
        TituloPropiedad titulo = ((Calle)this.casillaActual).getTitulo();
        
        boolean esDeMiPropiedad = esDeMiPropiedad(titulo);
        boolean tienePropietario = false;
        boolean encarcelado = true;
        boolean estaHipotecada = true;
        
        
        
        if (!esDeMiPropiedad){
            tienePropietario =  titulo.tengoPropietario();
            
            if(tienePropietario){
                encarcelado = titulo.propietarioEncarcelado();
            
                estaHipotecada = titulo.getHipotecada();
            }        
        }
                    
        return !esDeMiPropiedad && tienePropietario && !encarcelado && !estaHipotecada ;
    }
     
    Sorpresa devolverCartaLibertad(){
        Sorpresa cartaLibertad = this.cartaLibertad;
        this.cartaLibertad = null;
        return cartaLibertad;
    }
     
    boolean edificarCasa(TituloPropiedad titulo){
        return puedoEdificarCasa(titulo);
    }
     
    boolean edificarHotel(TituloPropiedad titulo){
        return puedoEdificarHotel(titulo);
    }
     
     
    private void eliminarDeMisPropiedades(TituloPropiedad titulo){
         this.propiedades.remove(titulo);
         titulo.setPropietario(null);
    }
     
    private boolean esDeMiPropiedad(TituloPropiedad titulo){
        return this.propiedades.contains(titulo);
    }
     
    boolean estoyEnCalleLibre(){
         throw new UnsupportedOperationException("Sin implementar");
    }
     

    boolean getEncarcelado() {
        return encarcelado;
    }

    String getNombre() {
        return nombre;
    }

    public int getSaldo() {
        return saldo;
    }

    Sorpresa getCartaLibertad() {
        return cartaLibertad;
    }

    Casilla getCasillaActual() {
        return casillaActual;
    }

    ArrayList<TituloPropiedad> getPropiedades() {
        return propiedades;
    }
     
    
    void hipotecarPropiedad(TituloPropiedad titulo){
        int costeHipoteca = titulo.hipotecar();
        modificarSaldo(costeHipoteca);
        
    }
    
    void irACarcel(Casilla casilla){
        setCasillaActual(casilla);
        setEncarcelado(true);
    }
    
    int modificarSaldo(int cantidad){
        return this.saldo += cantidad;   
    }
    
    int obtenerCapital(){
        int capital = this.saldo;
        int valorProp = 0;
        
        for (TituloPropiedad prop: this.propiedades){
            valorProp = (prop.getNumCasas() + prop.getNumHoteles() + prop.getPrecioCompra()) * prop.getPrecioEdificar();
            capital += valorProp ;
            
            if (prop.getHipotecada()){
                capital -= prop.getHipotecaBase();
            }
        }
        return capital;
    }
     
    ArrayList<TituloPropiedad> obtenerPropiedades(boolean hipotecada){
        
        ArrayList<TituloPropiedad> nuevo = new ArrayList(); 
        
        for (TituloPropiedad prop: this.propiedades){
            if (prop.getHipotecada() == hipotecada){
                nuevo.add(prop);
            }
        }        
        
        return nuevo;    
    }
    
    
    void pagarAlquiler(){        
        int costeAlquiler = ((Calle)this.casillaActual).pagarAlquiler();
        modificarSaldo(-costeAlquiler);
        
    }
    
    protected void pagarImpuesto(){        
        this.saldo -= this.casillaActual.getCoste();
        
    }
    
    void pagarLibertad(int cantidad){
        boolean tengoSaldo = tengoSaldo(cantidad);
        
        if (tengoSaldo){
            setEncarcelado(false);
            modificarSaldo(-cantidad);            
        }
    }
    
    void setCartaLibertad(Sorpresa carta){
        this.cartaLibertad = carta;
    }
    
    void setCasillaActual(Casilla casilla){
        this.casillaActual = casilla;
    }
    
    void setEncarcelado(boolean encarcelado){
        this.encarcelado = encarcelado;
    }
    
    boolean tengoCartaLibertad(){
        return (this.cartaLibertad != null); 
    }
    
    protected boolean tengoSaldo(int cantidad){
       return (this.saldo > cantidad); //superior o >= ?
    }
    
    
    void venderPropiedad(Casilla casilla){
                
        TituloPropiedad titulo = ((Calle)casilla).getTitulo();
        eliminarDeMisPropiedades(titulo);
        int precioVenta = titulo.calcularPrecioVenta();
        modificarSaldo(precioVenta);
        
    }

    @Override
    public String toString() {
        return "Jugador{" + "encarcelado=" + encarcelado + ", nombre=" + nombre + ", saldo=" + saldo + ", CAPITAL=" + this.obtenerCapital() + ", cartaLibertad=" + cartaLibertad + ", casillaActual=" + casillaActual + ", propiedades=" + propiedades + '}';
    }
    
    
    @Override
    public int compareTo(Jugador otroJugador) {
        int otroCapital = ((Jugador) otroJugador).obtenerCapital();
        return (otroCapital - obtenerCapital());
        //descendente o ascendente cambiando la posicion del menos
        
    }
    
    
    protected Jugador(Jugador otroJugador){
        if(this != otroJugador){
            
            this.nombre = otroJugador.getNombre();
            this.cartaLibertad = otroJugador.getCartaLibertad();
            this.casillaActual = otroJugador.getCasillaActual();
            this.propiedades =otroJugador.getPropiedades();
            this.encarcelado = otroJugador.getEncarcelado();
            this.saldo = otroJugador.getSaldo();
        }
    }
    
    protected Especulador convertirme(int fianza){
        return new Especulador(this, fianza);
    }
    
    protected boolean deboIrACarcel(){
        return !tengoCartaLibertad();
    }
    
    protected boolean puedoEdificarCasa(TituloPropiedad titulo){
        boolean edificada = false;
        
        int numCasas = titulo.getNumCasas();
        if (numCasas < 4){
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
        if (numHoteles < 4 && numCasas >= 4){
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
    


    
}
