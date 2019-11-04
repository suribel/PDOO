/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
/**
 *
 * @author antonio-w10
 */
public class Qytetet {
    
    
    
    
    
    static public final int MAX_JUGADORES = 4;
    static final int NUM_SORPRESAS = 10;
    static public final int NUM_CASILLAS = 20;
    static final int PRECIO_LIBERTAD = 200;
    static final int SALDO_SALIDA = 1000;
        
    
    private ArrayList<Sorpresa> mazo; 
    Sorpresa cartaActual;
    Jugador jugadorActual;
    private ArrayList<Jugador> jugadores;
    private Tablero tablero;
    Dado dado;
    
    EstadoJuego estadoJuego;
    
    
    
    private static final Qytetet instance = new Qytetet();
    
    private Qytetet(){
        
        mazo = new ArrayList<>(); 
        
        dado = Dado.getInstance();
        
        jugadores = new ArrayList<>();
        cartaActual = null;
        jugadorActual = null;
        estadoJuego= null;
        
        
        
    }
    
    public static Qytetet getInstance() {
        return instance;
    }
    
    
    
    
    ArrayList<Sorpresa> getMazo(){
        return mazo;
    }

    public Tablero getTablero() {
        return tablero;
    }
    
    Dado getDado(){
        return dado;
    }
    
    public EstadoJuego getEstadoJuego(){
        return this.estadoJuego;
    }
    
    public int getValorDado(){
        return this.dado.getValor();
    }
    private void inicializarCartasSorpresa(){
        
        mazo.add(new Sorpresa ("Te hemos pillado con chanclas y calcetines, lo sentimos, ¡debes ir a la carcel!", tablero.getCarcel().getNumeroCasilla(), TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa ("venga vete", 12, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa ("tira pa ya", 18, TipoSorpresa.IRACASILLA));
        
        mazo.add(new Sorpresa ("siiiii", 200, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa ("nooooo", -150, TipoSorpresa.PAGARCOBRAR));
        
        mazo.add(new Sorpresa ("recibe por vivir 50", 50, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa ("paga por vivir 50", -50, TipoSorpresa.PORCASAHOTEL));
        
        
        mazo.add(new Sorpresa ("recibe 100", 100, TipoSorpresa.PORJUGADOR));
        mazo.add(new Sorpresa ("paga 100", -100, TipoSorpresa.PORJUGADOR));
        
        mazo.add(new Sorpresa ("Sal de ahi muchacho", 0, TipoSorpresa.SALIRCARCEL));
       
        mazo.add(new Sorpresa ("Sal de ahi muchacho", 3500, TipoSorpresa.CONVERTIRME));
        mazo.add(new Sorpresa ("Sal de ahi muchacho", 4000, TipoSorpresa.CONVERTIRME));
    }
    
    private void inicializarTablero(){
        this.tablero = new Tablero();
        
    }   
    
    
    
    void actuarSiEnCasillaEdificable(){
      boolean deboPagar = this.jugadorActual.deboPagarAlquiler();
      if (deboPagar){
        this.jugadorActual.pagarAlquiler();
        
        if (this.jugadorActual.getSaldo() <= 0){
            setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
        }
      }
      
      Casilla casilla = obtenerCasillaJugadorActual();
      boolean tengoPropietario = ((Calle)casilla).tengoPropietario();
      
      if (this.estadoJuego != EstadoJuego.ALGUNJUGADORENBANCARROTA){
          if (tengoPropietario){
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
          }else{
            setEstadoJuego(EstadoJuego.JA_PUEDECOMPRAROGESTIONAR);
          }
      }
      
    }
    
    void actuarSiEnCasillaNoEdificable(){
       setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
       Casilla casillaActual = this.jugadorActual.getCasillaActual();
       
       if(casillaActual.getTipo() == TipoCasilla.IMPUESTO){
           this.jugadorActual.pagarImpuesto();
           
           if (this.jugadorActual.getSaldo() <= 0){
               setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
           }
           
       }else if(casillaActual.getTipo()==TipoCasilla.JUEZ){
           encarcelarJugador();
           
       }else if(casillaActual.getTipo()==TipoCasilla.SORPRESA){
           this.cartaActual = this.mazo.remove(0);
           setEstadoJuego(EstadoJuego.JA_CONSORPRESA);
           
       }
    }
    
    public void aplicarSorpresa(){
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
        if (this.cartaActual.getTipo() == TipoSorpresa.SALIRCARCEL){
            this.jugadorActual.setCartaLibertad(this.cartaActual);
        }else{
            this.mazo.add(this.cartaActual);
        }
        
        
        
        if (this.cartaActual.getTipo() == TipoSorpresa.PAGARCOBRAR){
            
            
            this.jugadorActual.modificarSaldo(this.cartaActual.getValor());
            
            if (this.jugadorActual.getSaldo() <= 0){
                setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);                                
            }            
            
        }else if (this.cartaActual.getTipo() == TipoSorpresa.IRACASILLA){
            int valor = this.cartaActual.getValor();
            boolean casillaCarcel = this.tablero.esCasillaCarcel(valor);
            
            if (casillaCarcel){
                encarcelarJugador();
            }else{
                mover(valor);                
            }            
            
        }else if (this.cartaActual.getTipo() == TipoSorpresa.PORCASAHOTEL){
            int cantidad = this.cartaActual.getValor();
            int numeroTotal = this.jugadorActual.cuantasCasasHotelesTengo();
            this.jugadorActual.modificarSaldo(cantidad*numeroTotal);
            
            if (this.jugadorActual.getSaldo() <= 0){
                setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
            }            
            
        }else if (this.cartaActual.getTipo() == TipoSorpresa.PORJUGADOR){
            
            for (Jugador jugador: this.jugadores){
                if (jugador != this.jugadorActual) {
                    jugador.modificarSaldo(this.cartaActual.getValor());
                }
                
                if (jugador.saldo<=0){
                    setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
                }
                
                this.jugadorActual.modificarSaldo(-this.cartaActual.getValor());
                
                
                if (this.jugadorActual.getSaldo() <= 0){
                    setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
                }
                
                
            }
        }else if (this.cartaActual.getTipo() == TipoSorpresa.CONVERTIRME){
            Especulador especulador = this.jugadorActual.convertirme(this.cartaActual.getValor());
            
            int i = this.jugadores.indexOf(this.jugadorActual);
            
            this.jugadores.set(i, especulador);
            
            this.jugadorActual = especulador; //this.jugadorActual = this.jugadores.get(i);
            
           
            

        }
        
    }
    
    public boolean cancelarHipoteca(int numeroCasilla){
        //numCasilla corresponde a una casilla edificable 
        //que es propiedad del jugadorActual y que está hipotecada        
        
        Casilla casilla = this.tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = ((Calle)casilla).getTitulo();
                        
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
        boolean cancelar = this.jugadorActual.cancelarHipoteca(titulo);
        
        return cancelar;
    }
    
    public boolean comprarTituloPropiedad(){
        boolean comprado = this.jugadorActual.comprarTituloPropiedad();
        if (comprado){
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        }
        
        return comprado;
    }
    
    public boolean edificarCasa(int numeroCasilla){
        boolean edificada = false;
        Casilla casilla = this.tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = ((Calle)casilla).getTitulo();
        edificada = this.jugadorActual.edificarCasa(titulo);
        
        if (edificada){
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        }
        
        return edificada;        
    }
    
    public boolean edificarHotel(int numeroCasilla){
        // 
        //LO MISMO QUE CASA
        //
        boolean edificada = false;
        Casilla casilla = this.tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = ((Calle)casilla).getTitulo();
        edificada = this.jugadorActual.edificarHotel(titulo);
        
        if (edificada){
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        }
        
        return edificada;
    }
    
    private void encarcelarJugador(){
        if (jugadorActual.deboIrACarcel()) { //cambiado por tengocartalibertad()
            Casilla casillaCarcel = tablero.getCarcel();
            jugadorActual.irACarcel(casillaCarcel);
            setEstadoJuego(EstadoJuego.JA_ENCARCELADO);
            System.out.println("ENCARCELADO"); 
        }
        else {
            Sorpresa carta = jugadorActual.devolverCartaLibertad();
            mazo.add(carta);    
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
            System.out.println("ESCAPAS DE LA CARCEL"); 
        }
    }
    
    public Sorpresa getCartaActual(){
        return cartaActual;
    }
    
    public Jugador getJugadorActual(){
        return jugadorActual;
    }
    public ArrayList<Jugador> getJugadores(){
        //Jugador [2..MAX_JUGADORES]
        return jugadores;
    }
    
    
    public void hipotecarPropiedad(int numeroCasilla){
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = ((Calle)casilla).getTitulo();
        this.jugadorActual.hipotecarPropiedad(titulo);
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
    }
    
    
    
    
  
    public void inicializarJuego(ArrayList<String> nombres){
        //NO se comprueba aqui nombres [2..MAX_JUGADORES]
        inicializarJugadores(nombres);
        inicializarTablero();
        inicializarCartasSorpresa();
        salidaJugadores();
    }
    private void inicializarJugadores(ArrayList<String> nombres){
        //NO se comprueba aqui nombres [2..MAX_JUGADORES]
        
        
        for (String nom: nombres){
            jugadores.add(new Jugador(nom));
        }
    }
    
    
    
    
    
    public boolean intentarSalirCarcel(MetodoSalirCarcel metodo){
        boolean libre;
        
        if (metodo == MetodoSalirCarcel.TIRANDODADO){
            int resultado = tirarDado();
            
            if(resultado >= 5){
                this.jugadorActual.setEncarcelado(false);
            }
            
        }else if (metodo == MetodoSalirCarcel.PAGANDOLIBERTAD){
            this.jugadorActual.pagarLibertad(PRECIO_LIBERTAD);
        }
        
        libre = this.jugadorActual.getEncarcelado();
        
        if (libre){
            setEstadoJuego(EstadoJuego.JA_ENCARCELADO);
        }else{
            setEstadoJuego(EstadoJuego.JA_PREPARADO);
        }
        
        return !libre;
    }
    
    public void jugar(){
        int desplazar = this.tirarDado();
        Casilla casilla = this.jugadorActual.getCasillaActual();
        Casilla casillaDestino = this.tablero.obtenerCasillaFinal(casilla, desplazar);
        
        mover(casillaDestino.getNumeroCasilla());
    }
    
    void mover(int numCasillaDestino){
        Casilla casillaInicial = this.jugadorActual.getCasillaActual();
        Casilla casillaFinal = this.tablero.obtenerCasillaNumero(numCasillaDestino);
        this.jugadorActual.setCasillaActual(casillaFinal);
        System.out.println(casillaFinal);
        if (numCasillaDestino < casillaInicial.getNumeroCasilla()){
            this.jugadorActual.modificarSaldo(SALDO_SALIDA);
        }
        
        if (casillaFinal.soyEdificable()){
            actuarSiEnCasillaEdificable();
        }else{
            actuarSiEnCasillaNoEdificable();
        }
    
    }
    
    public Casilla obtenerCasillaJugadorActual(){
        return this.jugadorActual.getCasillaActual();
    }
    
    public ArrayList<Casilla> obtenerCasillasTablero(){
        return this.tablero.getCasillas();
    }
    
    public ArrayList<Integer> obtenerPropiedadesJugador(){
        ArrayList<TituloPropiedad> propiedades = this.jugadorActual.getPropiedades();
        
        ArrayList<Integer> numeros = new ArrayList();
        
        for (int i = 0; i < NUM_CASILLAS; i++){
        
            for (TituloPropiedad prop: propiedades){
                
                if( this.tablero.getCasillas().get(i) instanceof Calle) {
                    
                    if (((Calle)this.tablero.getCasillas().get(i)).getTitulo().equals(prop)){
                        numeros.add(i);
                    }
                }            
                
            }            
        }
        
        return numeros;
    }
    
    public ArrayList<Integer> obtenerPropiedadesJugadorSegunEstadoHipoteca(boolean estadoHipoteca){
        ArrayList<TituloPropiedad> propiedades = this.jugadorActual.obtenerPropiedades(estadoHipoteca);
        ArrayList<Integer> numeros = new ArrayList();
        
        for (int i = 0; i < NUM_CASILLAS; i++){
        
            for (TituloPropiedad prop: propiedades){
                
                if( this.tablero.getCasillas().get(i) instanceof Calle) {
                    
                    if (((Calle)this.tablero.getCasillas().get(i)).getTitulo().equals(prop)){
                        numeros.add(i);
                    }
                }
            
                
            }
            
        }
        
        return numeros;
    }
    
    
    public void obtenerRanking(){
        Collections.sort(this.jugadores);
        //prueba
        for (Jugador jug: this.jugadores){
           System.out.println(jug.getNombre());
        }
    }
    
    public int obtenerSaldoJugadorActual(){
        return this.jugadorActual.getSaldo();
    }
    
    private void salidaJugadores(){
        for (Jugador jug: this.jugadores){
            jug.setCasillaActual(this.tablero.obtenerCasillaNumero(0));
        }
        
        int numero = (int) (Math.random() * this.jugadores.size());
        
        System.out.println("Jugador Salida Actual: " + numero);
        
        this.jugadorActual = this.jugadores.get(numero);
        this.estadoJuego = EstadoJuego.JA_PREPARADO;
        
    }
    
    private void setCartaActual(Sorpresa cartaActual){
        this.cartaActual = cartaActual;
    }
    
    //Preguntar cambio DCQ
    //setEstadoJuego(estadoJuego : EstadoJuego = ALGUNJUGADORENBANCARROTA) :
    public void setEstadoJuego(EstadoJuego estado){
        this.estadoJuego = estado;
    }
    
    public void siguienteJugador(){
        
        boolean encontrado = false;
        int siguiente = 0;
        int size = this.jugadores.size();
        
        for (int i = 0; i < size && !encontrado; i++ ){
            if (this.jugadorActual.equals(this.jugadores.get(i))){
                encontrado= true;
                siguiente = i + 1;
            }
        }
        
        this.jugadorActual = this.jugadores.get(siguiente % size);
        
        if (this.jugadorActual.getEncarcelado()){
            this.estadoJuego = EstadoJuego.JA_ENCARCELADOCONOPCIONDELIBERTAD;
        }else{
            this.estadoJuego = EstadoJuego.JA_PREPARADO;
        }

        
    }
    
    int tirarDado(){
        return this.dado.tirar();
    }
    
    public void venderPropiedad(int numeroCasilla){
             
        boolean vender = false;
        Casilla casilla = this.tablero.obtenerCasillaNumero(numeroCasilla);
        
        jugadorActual.venderPropiedad(casilla);
                
        
    }
  
    
    
    @Override
    public String toString() {
        return "Qytetet{" + "mazo=" + mazo + ", cartaActual=" + cartaActual + ", jugadorActual=" + jugadorActual + ", jugadores=" + jugadores + ", tablero=" + tablero + ", dado=" + dado + '}';
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
