/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladorqytetet;

import modeloqytetet.EstadoJuego;
import modeloqytetet.Qytetet;
import modeloqytetet.MetodoSalirCarcel;

import java.util.ArrayList;
import modeloqytetet.Jugador;

/**
 *
 * @author antonio-w10
 */
public class ControladorQytetet {
    
    
    private ArrayList<String> nombreJugadores;
    
    private Qytetet modelo = Qytetet.getInstance();
    
    private ControladorQytetet() {
    }
    
    
    private static final ControladorQytetet instance = new ControladorQytetet();
    
    public static ControladorQytetet getInstance() {
        return instance;
    }
    
    
    
    public void setNombreJugadores( ArrayList<String> nombreJugadores){
        this.nombreJugadores = nombreJugadores;
    }
    
    public ArrayList<Integer> obtenerOperacionesJuegoValidas() {
        
        ArrayList<Integer> operaciones= new ArrayList<>();
                
        if (modelo.getJugadores().isEmpty()){
            operaciones.add(OpcionMenu.INICIARJUEGO.ordinal());
        }
        else if (modelo.getEstadoJuego() == EstadoJuego.JA_CONSORPRESA){
            operaciones.add(OpcionMenu.APLICARSORPRESA.ordinal());
            
            operaciones.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
            operaciones.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
            operaciones.add(OpcionMenu.MOSTRARTABLERO.ordinal());
            operaciones.add(OpcionMenu.TERMINARJUEGO.ordinal());
        }
        else if (modelo.getEstadoJuego() == EstadoJuego.ALGUNJUGADORENBANCARROTA){            
            operaciones.add(OpcionMenu.OBTENERRANKING.ordinal());
            
            operaciones.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
            operaciones.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
            operaciones.add(OpcionMenu.MOSTRARTABLERO.ordinal());
            operaciones.add(OpcionMenu.TERMINARJUEGO.ordinal());
        }
        else if (modelo.getEstadoJuego() == EstadoJuego.JA_PUEDECOMPRAROGESTIONAR){           
            operaciones.add(OpcionMenu.PASARTURNO.ordinal());
            operaciones.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
            operaciones.add(OpcionMenu.EDIFICARCASA.ordinal());
            operaciones.add(OpcionMenu.EDIFICARHOTEL.ordinal());
            operaciones.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal());
            operaciones.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
            operaciones.add(OpcionMenu.COMPRARTITULOPROPIEDAD.ordinal());
            
            operaciones.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
            operaciones.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
            operaciones.add(OpcionMenu.MOSTRARTABLERO.ordinal());
            operaciones.add(OpcionMenu.TERMINARJUEGO.ordinal());
            
        }
        else if (modelo.getEstadoJuego() == EstadoJuego.JA_PUEDEGESTIONAR){            
            operaciones.add(OpcionMenu.PASARTURNO.ordinal());
            operaciones.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
            operaciones.add(OpcionMenu.EDIFICARCASA.ordinal());
            operaciones.add(OpcionMenu.EDIFICARHOTEL.ordinal());
            operaciones.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal());
            operaciones.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
            
            operaciones.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
            operaciones.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
            operaciones.add(OpcionMenu.MOSTRARTABLERO.ordinal());
            operaciones.add(OpcionMenu.TERMINARJUEGO.ordinal());
        
        }
        else if (modelo.getEstadoJuego() == EstadoJuego.JA_PREPARADO){            
            operaciones.add(OpcionMenu.JUGAR.ordinal());
            
            operaciones.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
            operaciones.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
            operaciones.add(OpcionMenu.MOSTRARTABLERO.ordinal());
            operaciones.add(OpcionMenu.TERMINARJUEGO.ordinal());
        }
        else if (modelo.getEstadoJuego() == EstadoJuego.JA_ENCARCELADO){            
            operaciones.add(OpcionMenu.PASARTURNO.ordinal());
            
            operaciones.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
            operaciones.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
            operaciones.add(OpcionMenu.MOSTRARTABLERO.ordinal());
            operaciones.add(OpcionMenu.TERMINARJUEGO.ordinal());
        }
        else if (modelo.getEstadoJuego() == EstadoJuego.JA_ENCARCELADOCONOPCIONDELIBERTAD){            
            operaciones.add(OpcionMenu.INTENTARSALIRCARCELPAGANDOLIBERTAD.ordinal());
            operaciones.add(OpcionMenu.INTENTARSALIRCARCELTIRANDODADO.ordinal());
            
            operaciones.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
            operaciones.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
            operaciones.add(OpcionMenu.MOSTRARTABLERO.ordinal());
            operaciones.add(OpcionMenu.TERMINARJUEGO.ordinal());
        }
        
        
        
        
        
    
        return operaciones;
    }
    public boolean necesitaElegirCasilla(int opcionMenu) {
        
        boolean puede = false;
        if (opcionMenu == OpcionMenu.HIPOTECARPROPIEDAD.ordinal()
                || opcionMenu == OpcionMenu.CANCELARHIPOTECA.ordinal()
                || opcionMenu == OpcionMenu.EDIFICARCASA.ordinal()
                || opcionMenu == OpcionMenu.EDIFICARHOTEL.ordinal()
                || opcionMenu == OpcionMenu.VENDERPROPIEDAD.ordinal() ) {
            
            
            puede = true;
        }
        
        
        return puede;
    }
    
    public ArrayList<Integer> obtenerCasillasValidas(int opcionMenu) {
        
        ArrayList<Integer> operaciones = new ArrayList<>();
        
         if(opcionMenu == OpcionMenu.HIPOTECARPROPIEDAD.ordinal()){
            operaciones = modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(false);
        }
        
        else if(opcionMenu == OpcionMenu.CANCELARHIPOTECA.ordinal()){
            operaciones = modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(true);
        }
        else{
            operaciones = modelo.obtenerPropiedadesJugador();
        }
               
        return operaciones;
    }
    
    public String realizarOperacion(int opcionElegida, int casillaElegida) {
        
        String mensaje = "naaaada";
        
        if (opcionElegida == OpcionMenu.INICIARJUEGO.ordinal()) {
            
            mensaje = "Juego Iniciado";
            modelo.inicializarJuego(nombreJugadores);
            
        }
        
        if (opcionElegida == OpcionMenu.JUGAR.ordinal()) {
            
            mensaje = "JUGANDO";
            modelo.jugar();
            
        }
        
        if (opcionElegida == OpcionMenu.APLICARSORPRESA.ordinal()) {
            
            modelo.aplicarSorpresa();
            mensaje = "SORPRESA APLICADA";
        }
        
        if (opcionElegida == OpcionMenu.INTENTARSALIRCARCELPAGANDOLIBERTAD.ordinal()) {
            
            
            if(modelo.intentarSalirCarcel(MetodoSalirCarcel.PAGANDOLIBERTAD)){
                mensaje = "SALIR CARCEL CORRECTO";
            }
            else{
                mensaje = "SALIR CARCEL  FALLIDO";
            }
        }
        
        if (opcionElegida == OpcionMenu.INTENTARSALIRCARCELTIRANDODADO.ordinal()) {
            
            if(modelo.intentarSalirCarcel(MetodoSalirCarcel.TIRANDODADO)){
                mensaje = "SALIR CARCEL CORRECTO";
            }
            else{
                mensaje = "SALIR CARCEL  FALLIDO";
            }
            

        }
        
        if (opcionElegida == OpcionMenu.COMPRARTITULOPROPIEDAD.ordinal()) {
            
            if(modelo.comprarTituloPropiedad()){
                mensaje = "COMPRAR CORRECTO";
            }
            else{
                mensaje = "COMPRAR FALLIDO";
            }
            
            
        }
        
        if (opcionElegida == OpcionMenu.HIPOTECARPROPIEDAD.ordinal()) {
            
            modelo.hipotecarPropiedad(casillaElegida);
            mensaje = "PROPIEDAD HIPOTECADA";
        }
        
        if (opcionElegida == OpcionMenu.CANCELARHIPOTECA.ordinal()) {
            
            
            if(modelo.cancelarHipoteca(casillaElegida)){
                mensaje = "CANCELAR HIPOTECA CORRECTO";
            }
            else{
                mensaje = "CANCELAR HIPOTECA FALLIDO";
            }
        }
        
        if (opcionElegida == OpcionMenu.EDIFICARCASA.ordinal()) {
            
            if(modelo.edificarCasa(casillaElegida)){
                mensaje = "EDIFICAR CASA CORRECTO";
            }
            else{
                mensaje = "EDIFICAR CASA FALLIDO";
            }
        }
        
        if (opcionElegida == OpcionMenu.EDIFICARHOTEL.ordinal()) {
                        
            if(modelo.edificarHotel(casillaElegida)){
                mensaje = "EDIFICAR HOTEL CORRECTO";
            }
            else{
                mensaje = "EDIFICAR HOTEL FALLIDO";
            }
        }
        
        if (opcionElegida == OpcionMenu.VENDERPROPIEDAD.ordinal()) {
            
            modelo.venderPropiedad(casillaElegida);
            mensaje = "PROPIEDAD VENDIDA";
        }
        
        if (opcionElegida == OpcionMenu.PASARTURNO.ordinal()) {
            
            modelo.siguienteJugador();
            mensaje = "TURNO del SIGUIENTE JUGADOR";
        }
        
        if (opcionElegida == OpcionMenu.OBTENERRANKING.ordinal()) {
            mensaje = "realizar operacion";
            modelo.obtenerRanking();
            
        }
        
        if (opcionElegida == OpcionMenu.TERMINARJUEGO.ordinal()) {
            
            System.exit(0);
        }
        
        if (opcionElegida == OpcionMenu.MOSTRARJUGADORACTUAL.ordinal()) {
            mensaje = "realizar operacion";
            System.out.print(modelo.getJugadorActual().toString());
            
        }
        
        if (opcionElegida == OpcionMenu.MOSTRARJUGADORES.ordinal()) {
            mensaje = "realizar operacion";
            for (Jugador jug: modelo.getJugadores()){                
               System.out.println(jug.toString());
            }  
            
            
        }
        if (opcionElegida == OpcionMenu.MOSTRARTABLERO.ordinal()) {
            mensaje = "realizar operacion";
            System.out.print(modelo.getTablero().toString());
            
        }
        
        return mensaje;
    }

   
    
}
