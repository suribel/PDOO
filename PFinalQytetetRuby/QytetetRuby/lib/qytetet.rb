#encoding: UTF-8



require_relative "sorpresa"
require_relative "tablero"
require_relative "dado"
require_relative "jugador"
require_relative "estado_juego"

require "singleton"

module ModeloQytetet
  class Qytetet
    
    include Singleton
    
    attr_reader :mazo, :tablero, :dado, :jugadorActual, :jugadores
    attr_accessor :cartaActual, :estadoJuego
    
    @@MAX_JUGADORES = 4;
    @@NUM_SORPRESAS = 10;
    @@NUM_CASILLAS = 20;
    @@PRECIO_LIBERTAD = 200;
    @@SALDO_SALIDA = 1000;
    
    def initialize
      @mazo = Array.new
      
      @dado = Dado.instance
      
      @jugadores = Array.new
      @cartaActual = nil
      @jugadorActual = nil
      @estadoJuego = nil
    
    end
    
    
        
    def inicializarCartasSorpresa
      
      
      @mazo << Sorpresa.new("Coviertete", 3500, TipoSorpresa::CONVERTIRME)
      @mazo << Sorpresa.new("Covierte te", 4000, TipoSorpresa::CONVERTIRME)
      
      @mazo << Sorpresa.new("Te hemos pillado con chanclas y calcetines, lo sentimos, ¡debes ir a la carcel!", 4, TipoSorpresa::IRACASILLA)
      @mazo << Sorpresa.new("Un fan anónimo ha pagado tu fianza. Sales de la cárcel", 0, TipoSorpresa::SALIRCARCEL)
      @mazo << Sorpresa.new("venga vete", 12, TipoSorpresa::IRACASILLA)
      @mazo << Sorpresa.new("tira pa ya", 18, TipoSorpresa::IRACASILLA)
      
      @mazo << Sorpresa.new("siiiii", 200, TipoSorpresa::PAGARCOBRAR)
      @mazo << Sorpresa.new("nooooo", -150, TipoSorpresa::PAGARCOBRAR)
      
      @mazo << Sorpresa.new("recibe por vivir 50", 50, TipoSorpresa::PORCASAHOTEL)
      @mazo << Sorpresa.new("paga por vivir 50", -50, TipoSorpresa::PORCASAHOTEL)
        
        
      @mazo << Sorpresa.new("recibe 100", 100, TipoSorpresa::PORJUGADOR)
      @mazo << Sorpresa.new("paga 100", -100, TipoSorpresa::PORJUGADOR)
        
      @mazo << Sorpresa.new("Sal de ahi muchacho", 0, TipoSorpresa::SALIRCARCEL)
      
      
        
        
    end
    
    def inicializarTablero
        @tablero = Tablero.new        
    end  
    
    
    
    def actuarSiEnCasillaEdificable()
      deboPagar = @jugadorActual.deboPagarAlquiler() 
      if (deboPagar)
        @jugadorActual.pagarAlquiler()
        
        if (@jugadorActual.saldo <= 0)
            @estadoJuego=(EstadoJuego::ALGUNJUGADORENBANCARROTA)
        end
      end
      
      casilla = obtenerCasillaJugadorActual()
      tengoPropietario = casilla.tengoPropietario()
      
      if (@estadoJuego != EstadoJuego::ALGUNJUGADORENBANCARROTA)
          if (tengoPropietario)
            @estadoJuego=(EstadoJuego::JA_PUEDEGESTIONAR)
          else
            @estadoJuego=(EstadoJuego::JA_PUEDECOMPRAROGESTIONAR)
          end
      end
      
    end
    
    def actuarSiEnCasillaNoEdificable()
      @estadoJuego=(EstadoJuego::JA_PUEDEGESTIONAR)
      casillaActual = @jugadorActual.casillaActual
       
      if(casillaActual.tipoCasilla == TipoCasilla::IMPUESTO)
          @jugadorActual.pagarImpuesto()
           
      elsif(casillaActual.tipoCasilla == TipoCasilla::JUEZ)
         encarcelarJugador()
          
      elsif(casillaActual.tipoCasilla == TipoCasilla::SORPRESA)
          @cartaActual = @mazo.delete_at(0)
          print "Carta Sorpresa:  ", @cartaActual
          puts
          @estadoJuego = (EstadoJuego::JA_CONSORPRESA)
      end
    end
    
    def aplicarSorpresa()
      @estadoJuego=(EstadoJuego::JA_PUEDEGESTIONAR)
        
        if (@cartaActual.tipo == TipoSorpresa::SALIRCARCEL)
            @jugadorActual.cartaLibertad=(@cartaActual)
        else
            @mazo << (@cartaActual)
        end
        
        if (@cartaActual.tipo == TipoSorpresa::PAGARCOBRAR)            
            
            @jugadorActual.modificarSaldo(@cartaActual.valor)
            
            if (@jugadorActual.saldo <= 0)
                @estadoJuego=(EstadoJuego::ALGUNJUGADORENBANCARROTA)                               
            end            
            
        elsif (@cartaActual.tipo == TipoSorpresa::IRACASILLA)
            valor = @cartaActual.valor
            casillaCarcel = @tablero.esCasillaCarcel(valor)
            
            if (casillaCarcel)
                encarcelarJugador()
            else
                mover(valor)               
            end         
            
        elsif (@cartaActual.tipo == TipoSorpresa::PORCASAHOTEL)
            cantidad = @cartaActual.valor
            numeroTotal = @jugadorActual.cuantasCasasHotelesTengo()
            @jugadorActual.modificarSaldo(cantidad*numeroTotal)
            
            if (@jugadorActual.saldo <= 0)
                @estadoJuego=(EstadoJuego::ALGUNJUGADORENBANCARROTA)
            end          
            
        elsif (@cartaActual.tipo == TipoSorpresa::PORJUGADOR)
            
            @jugadores.each do |jugador|
                if (jugador != @jugadorActual) 
                    jugador.modificarSaldo(@cartaActual.valor)
                end
                
                if (jugador.saldo <= 0)
                    @estadoJuego=(EstadoJuego::ALGUNJUGADORENBANCARROTA)
                end
                
                @jugadorActual.modificarSaldo(-@cartaActual.valor)
                
                
                if (@jugadorActual.saldo <= 0)
                    @estadoJuego=(EstadoJuego::ALGUNJUGADORENBANCARROTA)
                end               
            end
            
        
        
        elsif (@cartaActual.tipo == TipoSorpresa::CONVERTIRME)
            especulador = @jugadorActual.convertirme(@cartaActual.valor)
            
            i = @jugadores.index(@jugadorActual)
            
            @jugadores[i]=especulador
            #@jugadores.insert(i, especulador)
            
            @jugadorActual = especulador
            
        end
        
        
        
    end
    
    def cancelarHipoteca(numeroCasilla)
      #numCasilla corresponde a una casilla edificable 
      #que es propiedad del jugadorActual y que está hipotecada        
      
      casilla = @tablero.obtenerCasillaNumero(numeroCasilla)
      titulo = casilla.titulo
                        
      @estadoJuego=(EstadoJuego::JA_PUEDEGESTIONAR)
        
      cancelar = @jugadorActual.cancelarHipoteca(titulo)
        
      return cancelar
    end
    
    def comprarTituloPropiedad()
      comprado = @jugadorActual.comprarTituloPropiedad()
        if (comprado)
            @estadoJuego=(EstadoJuego::JA_PUEDEGESTIONAR)
        end
        
        return comprado
    end
    
    def edificarCasa(numeroCasilla)
      edificada = false
      casilla = @tablero.obtenerCasillaNumero(numeroCasilla)
      titulo = casilla.titulo
      edificada = @jugadorActual.edificarCasa(titulo)
        
      if (edificada)
        @estadoJuego=(EstadoJuego::JA_PUEDEGESTIONAR)
      end
        
      return edificada
    end
    
    def edificarHotel(numeroCasilla)
      #LO MISMO QUE CASA
      
      edificada = false
      casilla = @tablero.obtenerCasillaNumero(numeroCasilla)
      titulo = casilla.titulo
      edificada = @jugadorActual.edificarHotel(titulo)
        
      if (edificada)
          @estadoJuego=(EstadoJuego::JA_PUEDEGESTIONAR)
      end
        
      return edificada
    end
    
    def encarcelarJugador()
      if (!@jugadorActual.deboIrACarcel()) 
        casillaCarcel = @tablero.carcel
        @jugadorActual.irACarcel(casillaCarcel)
        @estadoJuego=(EstadoJuego::JA_ENCARCELADO)
        puts "ENCARCELADO"
      else
        carta = @jugadorActual.devolverCartaLibertad()
        @mazo << (carta)
        @estadoJuego=(EstadoJuego::JA_PUEDEGESTIONAR)
        puts "ESCAPAS DE LA CARCEL"
      end
    end
       
   
    def hipotecarPropiedad(numeroCasilla)
      casilla = @tablero.obtenerCasillaNumero(numeroCasilla)
      titulo = casilla.titulo
      @jugadorActual.hipotecarPropiedad(titulo)
      @estadoJuego=(EstadoJuego::JA_PUEDEGESTIONAR)
    end
        
  
    def inicializarJuego(nombres)
        
        inicializarJugadores(nombres)
        inicializarTablero()
        inicializarCartasSorpresa()
        salidaJugadores()
    end
    
    def inicializarJugadores(nombres)
      
      
      nombres.each do |nom|    #tb for .. in..
        @jugadores << Jugador.new(nom)
      end
    end
    
    
    
    
    
    def intentarSalirCarcel(metodo)
              
      if (metodo == MetodoSalirCarcel::TIRANDODADO)
        resultado = tirarDado()
            
        if(resultado >= 5)
          @jugadorActual.encarcelado=(false)
        end
            
      elsif (metodo == MetodoSalirCarcel::PAGANDOLIBERTAD)
          @jugadorActual.pagarLibertad(@@PRECIO_LIBERTAD)
      end
      
      libre = @jugadorActual.encarcelado
        
      if (libre)
        @estadoJuego=(EstadoJuego::JA_ENCARCELADO)
      else
        @estadoJuego=(EstadoJuego::JA_PREPARADO)
      end
        
      return !libre
    end
    
    def jugar()
      desplazar = tirarDado()
      casilla = @jugadorActual.casillaActual
      casillaDestino = @tablero.obtenerCasillaFinal(casilla, desplazar)
        
      mover(casillaDestino.numeroCasilla)
    end
    
    def mover(numCasillaDestino)
      casillaInicial = @jugadorActual.casillaActual
      casillaFinal = @tablero.obtenerCasillaNumero(numCasillaDestino)
      @jugadorActual.casillaActual=(casillaFinal)
      
      puts casillaFinal
      
      if (numCasillaDestino < casillaInicial.numeroCasilla)
        @jugadorActual.modificarSaldo(@@SALDO_SALIDA)
      end
        
      if (casillaFinal.soyEdificable())
        actuarSiEnCasillaEdificable()
      else
        actuarSiEnCasillaNoEdificable()
      end
      
    end
    
    def obtenerCasillaJugadorActual()
      return @jugadorActual.casillaActual
    end
    
    def obtenerCasillasTablero()
        return @tablero.casillas
    end
    
    def obtenerPropiedadesJugador()
      
      propiedades = @jugadorActual.propiedades
      numeros = Array.new
      
      i = 0        
      while ( i < @@NUM_CASILLAS) 
        propiedades.each do |prop|    
          
          
          if(@tablero.casillas[i].instance_of? Calle)
            if (@tablero.casillas[i].titulo.equal?(prop))
              numeros << (i)
            end
          end
          
          
          
        end   
        i += 1
      end
        
      return numeros;
        
    end
    
    def obtenerPropiedadesJugadorSegunEstadoHipoteca(estadoHipoteca)
      
      propiedades = @jugadorActual.obtenerPropiedades(estadoHipoteca)
      numeros = Array.new        
      
      i = 0        
      while ( i < @@NUM_CASILLAS) 
        propiedades.each do |prop|                    
          if(@tablero.casillas[i].instance_of? Calle)
            if (@tablero.casillas[i].titulo.equal?(prop))
              numeros << (i)
            end
          end
        end   
        i += 1
      end
        
      return numeros;
        
    end
    
    
    #def jugadorActualEnCalleLibre()
    #  return (@jugadorActual.casillaActual.soyEdificable() && @jugadorActual.casillaActual().tengoPropietario())
    #end
    
    #def jugadorActualEncarcelado()
    #  return @jugadorActual.encarcelado 
    #end
    
    
    def obtenerRanking()
      @jugadores=@jugadores.sort
      #prueba
        @jugadores.each do |jug|
          puts (jug.nombre)
          
        end
    end
    
    def obtenerSaldoJugadorActual()
      return @jugadorActual.saldo
    end
    
    def salidaJugadores()
      
      @jugadores.each do |jug|
        jug.casillaActual = @tablero.obtenerCasillaNumero(0);
      end
      
            
      @jugadorActual = @jugadores[rand(@jugadores.count)]
      
      print "Jugador Salida Actual: ", @jugadorActual.nombre
      puts
      @estadoJuego = EstadoJuego::JA_PREPARADO
        
    end
    
    
    def siguienteJugador()
      
      encontrado = false
      siguiente = 0
      size = @jugadores.length()
      i = 0
      while (i < size && !encontrado)
        
        if (@jugadorActual.equal?(@jugadores[i]))
          encontrado= true
          siguiente = i + 1
        end
        
        i += 1
        
      end
        
      @jugadorActual = @jugadores[(siguiente % size)]
        
      if (@jugadorActual.encarcelado)
        @estadoJuego = EstadoJuego::JA_ENCARCELADOCONOPCIONDELIBERTAD
      else
        @estadoJuego = EstadoJuego::JA_PREPARADO
      end
        
      
    end
    
    def tirarDado()
      return @dado.tirar()
    end
    
    def getValorDado()
      return @dado.valor
    end
    
    def venderPropiedad(numeroCasilla)
      vender = false
      casilla = @tablero.obtenerCasillaNumero(numeroCasilla)
        
      @jugadorActual.venderPropiedad(casilla)
    end

    private :inicializarJugadores, :inicializarTablero, :inicializarCartasSorpresa, :encarcelarJugador, :salidaJugadores
    
    
    
    
    def to_s
      "mazo=#{@mazo} \n cartaActual= #{@cartaActual} \n jugadorActual= #{@jugadorActual} \n jugadores= #{@jugadores} \n tablero= #{@tablero} \n dado= #{@dado}"  
    end 
    
  end
end

