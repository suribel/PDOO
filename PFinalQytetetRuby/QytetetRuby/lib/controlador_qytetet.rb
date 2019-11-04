# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

#require_relative "@modeloQytetet"
require_relative "qytetet"
require_relative "metodo_salir_carcel"
require_relative "estado_juego"
require_relative "opcion_menu"


require "singleton"


module Controladorqytetet
  class ControladorQytetet
    
    
    include Singleton
    include ModeloQytetet
    
    attr_accessor :nombreJugadores
    
    @@modelo = Qytetet.instance
    
    def initialize
      @nombreJugadores = Array.new
      
    end
    
    
    
   def obtenerOperacionesJuegoValidas() 
        
        operaciones = Array.new
                
        if (@@modelo.jugadores.empty?())
            operaciones << (OpcionMenu.index(:INICIARJUEGO))
        
        elsif (@@modelo.estadoJuego == EstadoJuego::JA_CONSORPRESA)
            operaciones << (OpcionMenu.index(:APLICARSORPRESA))            
       
            operaciones << (OpcionMenu.index(:MOSTRARJUGADORACTUAL))
            operaciones << (OpcionMenu.index(:MOSTRARJUGADORES))
            operaciones << (OpcionMenu.index(:MOSTRARTABLERO))
            operaciones << (OpcionMenu.index(:TERMINARJUEGO))
        
        elsif (@@modelo.estadoJuego == EstadoJuego::ALGUNJUGADORENBANCARROTA)         
            operaciones << (OpcionMenu.index(:OBTENERRANKING))
            
            operaciones << (OpcionMenu.index(:MOSTRARJUGADORACTUAL))
            operaciones << (OpcionMenu.index(:MOSTRARJUGADORES))
            operaciones << (OpcionMenu.index(:MOSTRARTABLERO))
            operaciones << (OpcionMenu.index(:TERMINARJUEGO))
        
        elsif (@@modelo.estadoJuego == EstadoJuego::JA_PUEDECOMPRAROGESTIONAR)
            operaciones << (OpcionMenu.index(:PASARTURNO))
            operaciones << (OpcionMenu.index(:VENDERPROPIEDAD))
            operaciones << (OpcionMenu.index(:EDIFICARCASA))
            operaciones << (OpcionMenu.index(:EDIFICARHOTEL))
            operaciones << (OpcionMenu.index(:HIPOTECARPROPIEDAD))
            operaciones << (OpcionMenu.index(:CANCELARHIPOTECA))
            operaciones << (OpcionMenu.index(:COMPRARTITULOPROPIEDAD))
       
            operaciones << (OpcionMenu.index(:MOSTRARJUGADORACTUAL))
            operaciones << (OpcionMenu.index(:MOSTRARJUGADORES))
            operaciones << (OpcionMenu.index(:MOSTRARTABLERO))
            operaciones << (OpcionMenu.index(:TERMINARJUEGO))
            
        
        elsif (@@modelo.estadoJuego == EstadoJuego::JA_PUEDEGESTIONAR)        
            operaciones << (OpcionMenu.index(:PASARTURNO))
            operaciones << (OpcionMenu.index(:VENDERPROPIEDAD))
            operaciones << (OpcionMenu.index(:EDIFICARCASA))
            operaciones << (OpcionMenu.index(:EDIFICARHOTEL))
            operaciones << (OpcionMenu.index(:HIPOTECARPROPIEDAD))
            operaciones << (OpcionMenu.index(:CANCELARHIPOTECA))
            
            operaciones << (OpcionMenu.index(:MOSTRARJUGADORACTUAL))
            operaciones << (OpcionMenu.index(:MOSTRARJUGADORES))
            operaciones << (OpcionMenu.index(:MOSTRARTABLERO))
            operaciones << (OpcionMenu.index(:TERMINARJUEGO))
        
        
        elsif (@@modelo.estadoJuego == EstadoJuego::JA_PREPARADO)          
            
            operaciones << (OpcionMenu.index(:JUGAR))
       
            operaciones << (OpcionMenu.index(:MOSTRARJUGADORACTUAL))
            operaciones << (OpcionMenu.index(:MOSTRARJUGADORES))
            operaciones << (OpcionMenu.index(:MOSTRARTABLERO))
            operaciones << (OpcionMenu.index(:TERMINARJUEGO))
        
        elsif (@@modelo.estadoJuego == EstadoJuego::JA_ENCARCELADO)     
            
            operaciones << (OpcionMenu.index(:PASARTURNO))
       
            operaciones << (OpcionMenu.index(:MOSTRARJUGADORACTUAL))
            operaciones << (OpcionMenu.index(:MOSTRARJUGADORES))
            operaciones << (OpcionMenu.index(:MOSTRARTABLERO))
            operaciones << (OpcionMenu.index(:TERMINARJUEGO))
        
        elsif (@@modelo.estadoJuego == EstadoJuego::JA_ENCARCELADOCONOPCIONDELIBERTAD)        
            
            operaciones << (OpcionMenu.index(:INTENTARSALIRCARCELPAGANDOLIBERTAD))
            operaciones << (OpcionMenu.index(:INTENTARSALIRCARCELTIRANDODADO))
       
       
            operaciones << (OpcionMenu.index(:MOSTRARJUGADORACTUAL))
            operaciones << (OpcionMenu.index(:MOSTRARJUGADORES))
            operaciones << (OpcionMenu.index(:MOSTRARTABLERO))
            operaciones << (OpcionMenu.index(:TERMINARJUEGO))
        end
        
        
        
        
        
    
        return operaciones
    end
    
    def necesitaElegirCasilla(opcionMenu) 
        
        puede = false
        if (opcionMenu == (OpcionMenu.index(:VENDERPROPIEDAD)) || opcionMenu == (OpcionMenu.index(:EDIFICARCASA)) || opcionMenu == (OpcionMenu.index(:EDIFICARHOTEL)) || opcionMenu == (OpcionMenu.index(:HIPOTECARPROPIEDAD)) || opcionMenu == (OpcionMenu.index(:CANCELARHIPOTECA)))
            
            puede = true
        end
        
        
        return puede
    end
    
    def obtenerCasillasValidas(opcionMenu)
        
        operaciones = Array.new
        
         if(opcionMenu == (OpcionMenu.index(:HIPOTECARPROPIEDAD)))
            operaciones = @@modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(false)
         
        
        elsif(opcionMenu == (OpcionMenu.index(:CANCELARHIPOTECA)))
            operaciones = @@modelo.obtenerPropiedadesJugadorSegunEstadoHipoteca(true)
        
        else
            operaciones = @@modelo.obtenerPropiedadesJugador()
        end
               
        return operaciones
    end
    
    def realizarOperacion(opcionElegida, casillaElegida) 
        
        mensaje = "naaaada"
        
        if (opcionElegida == (OpcionMenu.index(:INICIARJUEGO)))
            
            mensaje = "Juego Iniciado"
            @@modelo.inicializarJuego(@nombreJugadores)
            
        end
        
        if (opcionElegida == (OpcionMenu.index(:JUGAR)))
            
            mensaje = "JUGANDO"
            @@modelo.jugar()
            
        end
        
        if (opcionElegida == (OpcionMenu.index(:APLICARSORPRESA)))
            
            @@modelo.aplicarSorpresa()
            mensaje = "SORPRESA APLICADA"
        end
        
        if (opcionElegida == (OpcionMenu.index(:INTENTARSALIRCARCELPAGANDOLIBERTAD)))
            
            
            if(@@modelo.intentarSalirCarcel(MetodoSalirCarcel::PAGANDOLIBERTAD))
                mensaje = "SALIR CARCEL CORRECTO"
            
            else
                mensaje = "SALIR CARCEL  FALLIDO"
            end
        end
        
        if (opcionElegida == (OpcionMenu.index(:INTENTARSALIRCARCELTIRANDODADO)))
            
            if(@@modelo.intentarSalirCarcel(MetodoSalirCarcel::TIRANDODADO))
                mensaje = "SALIR CARCEL CORRECTO"
            
            else
                mensaje = "SALIR CARCEL  FALLIDO"
            end
            

        end
        
        if (opcionElegida == (OpcionMenu.index(:COMPRARTITULOPROPIEDAD)))
            
            if(@@modelo.comprarTituloPropiedad())
                mensaje = "COMPRAR CORRECTO"
            
            else
                mensaje = "COMPRAR FALLIDO"
            end
            
            
        end
        
        if (opcionElegida == (OpcionMenu.index(:HIPOTECARPROPIEDAD))) 
            
            @@modelo.hipotecarPropiedad(casillaElegida)
            mensaje = "PROPIEDAD HIPOTECADA"
        end
        
        if (opcionElegida == (OpcionMenu.index(:CANCELARHIPOTECA)))
            
            
            if(@@modelo.cancelarHipoteca(casillaElegida))
                mensaje = "CANCELAR HIPOTECA CORRECTO"
            
            else
                mensaje = "CANCELAR HIPOTECA FALLIDO"
            end
        end
        
        if (opcionElegida == (OpcionMenu.index(:EDIFICARCASA)))
            
            if(@@modelo.edificarCasa(casillaElegida))
                mensaje = "EDIFICAR CASA CORRECTO"
            
            else
                mensaje = "EDIFICAR CASA FALLIDO"
            end
        end
        
        if (opcionElegida == (OpcionMenu.index(:EDIFICARHOTEL)))
                        
            if(@@modelo.edificarHotel(casillaElegida))
                mensaje = "EDIFICAR HOTEL CORRECTO"
            
            else
                mensaje = "EDIFICAR HOTEL FALLIDO"
            end
        end
        
        if (opcionElegida == (OpcionMenu.index(:VENDERPROPIEDAD)))
            
            @@modelo.venderPropiedad(casillaElegida)
            mensaje = "PROPIEDAD VENDIDA"
        end
        
        if (opcionElegida == (OpcionMenu.index(:PASARTURNO)))
            
            @@modelo.siguienteJugador()
            mensaje = "TURNO del SIGUIENTE JUGADOR"
        end
        
        if (opcionElegida == (OpcionMenu.index(:OBTENERRANKING)))
            mensaje = "realizada operacion"
            @@modelo.obtenerRanking()
            
        end
        
        if (opcionElegida == (OpcionMenu.index(:TERMINARJUEGO)))
            
            exit
        end
        
        if (opcionElegida == (OpcionMenu.index(:MOSTRARJUGADORACTUAL)))
            mensaje = "realizada operacion"
            puts (@@modelo.jugadorActual.to_s)
            
        end
        
        if (opcionElegida == (OpcionMenu.index(:MOSTRARJUGADORES)))
            mensaje = "realizada operacion"
            @@modelo.jugadores.each do |jug|      
              puts (jug.to_s)
              puts
            end     
      
            
        end
        if (opcionElegida == (OpcionMenu.index(:MOSTRARTABLERO)))
            mensaje = "realizada operacion"
            puts (@@modelo.tablero.to_s)
            
        end
        
        return mensaje
    end
    
    
    
    
    
  end
end
