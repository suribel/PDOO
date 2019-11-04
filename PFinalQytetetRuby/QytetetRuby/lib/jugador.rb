#encoding: UTF-8



module ModeloQytetet
  class Jugador 
       
    attr_accessor :encarcelado, :cartaLibertad, :casillaActual
    attr_reader :nombre, :saldo, :propiedades    
    
    
    def initialize(nombre, cartaLibertad=nil, casillaActual=nil, propiedades=Array.new, encarcelado = false, saldo = 7500 ) 
        @nombre = nombre
        @cartaLibertad = cartaLibertad
        @casillaActual = casillaActual
        @propiedades = propiedades
        @encarcelado = encarcelado
        @saldo = saldo    
    end
    
    
    def cancelarHipoteca(titulo)
      cancelar = false
      costecancelar = titulo.calcularCosteCancelar()
        
      if (tengoSaldo(costecancelar))
            
          modificarSaldo(-costecancelar)
          titulo.cancelarHipoteca()
          cancelar = true
      end        
        
      return cancelar
    end
     
    def comprarTituloPropiedad()
      comprado = false
        
      costeCompra = @casillaActual.coste
      if (costeCompra < @saldo)
          titulo = @casillaActual.asignarPropietario(self) #jugadorActual          
          @propiedades << (titulo)
          modificarSaldo(-costeCompra)
            
          comprado = true
            
      end
      return comprado
    end
     
    def cuantasCasasHotelesTengo()
      num = 0
      @propiedades.each do |prop|      
          num += prop.numCasas + prop.numHoteles
      end
      return num
    end
     
    def deboPagarAlquiler()
      titulo = @casillaActual.titulo
        
      esDeMiPropiedad = esDeMiPropiedad(titulo)
      tienePropietario = false
      encarcelado = true
      estaHipotecada = true
        
        
        
      if (!esDeMiPropiedad)
          tienePropietario =  titulo.tengoPropietario()
          
          if(tienePropietario)
              encarcelado = titulo.propietarioEncarcelado()
          
              estaHipotecada = titulo.hipotecada
          end        
      end
                    
      return !esDeMiPropiedad && tienePropietario && !encarcelado && !estaHipotecada 
    end
     
    def devolverCartaLibertad()
      cartaLibertad = @cartaLibertad
      @cartaLibertad = nil
      return cartaLibertad
    end
     
    def edificarCasa(titulo)
      return puedoEdificarCasa(titulo)
    end
     
    def edificarHotel(titulo)
      return puedoEdificarHotel(titulo)
    end
     
     
    def eliminarDeMisPropiedades(titulo)
      @propiedades.delete(titulo)
      titulo.propietario=(nil)
    end
     
    def esDeMiPropiedad(titulo)
      return @propiedades.include?(titulo)
    end
     
    def estoyEnCalleLibre()
      raise NotImplementedError
    end
     
    
    def hipotecarPropiedad(titulo)
      costeHipoteca = titulo.hipotecar()
      modificarSaldo(costeHipoteca)
    end
    
    def irACarcel(casilla)
      @casillaActual=(casilla)
      @encarcelado=(true)
    end
    
    def modificarSaldo(cantidad)
      return @saldo += cantidad  
    end
    
    def obtenerCapital()
      capital = @saldo
      valorProp = 0
        
      @propiedades.each do |prop|
        valorProp = (prop.numCasas + prop.numHoteles + prop.precioCompra) * prop.precioEdificar
        capital += valorProp
            
        if (prop.hipotecada)
          capital -= prop.hipotecaBase
        end
      end
        return capital
    end
     
    def obtenerPropiedades(hipotecada)
      
      nuevo =Array.new
        
      @propiedades.each do |prop|
        if (prop.hipotecada() == hipotecada)
          nuevo << (prop)
        end
      end       
        
      return nuevo
    end    
    
    def pagarAlquiler()
      costeAlquiler = @casillaActual.pagarAlquiler()
      modificarSaldo(-costeAlquiler)
    end
    
    def pagarImpuesto()        
      @saldo -= @casillaActual.coste
    end
    
    def pagarLibertad(cantidad)
      tengoSaldo = tengoSaldo(cantidad)
        
        if (tengoSaldo)
            @encarcelado=(false)
            modificarSaldo(-cantidad);           
        end
    end
    
    
    
    def tengoCartaLibertad()
      return (@cartaLibertad != nil)
    end
    
    def tengoSaldo(cantidad)
      return (@saldo > cantidad) #superior o >= ?
    end
    
    
    def venderPropiedad(casilla)
      titulo = casilla.titulo
      eliminarDeMisPropiedades(titulo)
      precioVenta = titulo.calcularPrecioVenta()
      modificarSaldo(precioVenta)
        
    end
    
    private :eliminarDeMisPropiedades, :esDeMiPropiedad
    
      
    def <=>(otroJugador)
      otroJugador.obtenerCapital <=> obtenerCapital
                  
      otroCapital= otroJugador.obtenerCapital
      miCapital=obtenerCapital      
      if (otroCapital>miCapital)
        return 1 
      end
      if (otroCapital<miCapital)
        return -1 
      end      
      return 0
    end
    
    
    
    def stringpropiedades
      hey = ""
      @propiedades.each do |prop|      
        hey = " " + prop.to_s
      end     
      return hey
    end
      
      
    def to_s
      "nombre= #{@nombre} encarcelado=#{@encarcelado} \n saldo= #{@saldo} \n CAPITAL= #{obtenerCapital()} \n cartaLibertad= #{@cartaLibertad} \n casillaActual= #{@casillaActual} \n propiedades= #{stringpropiedades}"  
    end
    
    
    
    
    def convertirme(fianza)
      return Especulador.new(self, fianza)
    end
    
    def deboIrACarcel()
        return !tengoCartaLibertad()
    end
    
    def puedoEdificarCasa(titulo)
        edificada = false
        
        numCasas = titulo.numCasas
        if (numCasas < 4)
            costeEdificarCasa = titulo.precioEdificar
            tengoSaldo = tengoSaldo(costeEdificarCasa)
            
            if (tengoSaldo)
                titulo.edificarCasa()
                modificarSaldo(-costeEdificarCasa)
                edificada = true
            end            
        end
        
        return edificada
    end
    
    def puedoEdificarHotel(titulo)
        
        edificada = false
        
        numHoteles = titulo.numHoteles
        numCasas = titulo.numCasas
        if (numHoteles < 4 && numCasas >= 4)
            costeEdificarHotel = titulo.precioEdificar
            tengoSaldo = tengoSaldo(costeEdificarHotel)
            
            if (tengoSaldo)
                titulo.edificarHotel()
                modificarSaldo(-costeEdificarHotel)
                edificada = true
            end         
        end
        
        return edificada
    end
    
    
    #protected :convertirme, :deboIrACarcel, :puedoEdificarCasa, :puedoEdificarHotel, :tengoSaldo, :pagarImpuesto
    
    
    
    
  end
end
