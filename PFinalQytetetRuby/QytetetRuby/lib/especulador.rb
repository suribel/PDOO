# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.



module ModeloQytetet
  
  
  
  
  class Especulador < Jugador
    
    def initialize (jugador, fianza)
      super(jugador.nombre, jugador.cartaLibertad, jugador.casillaActual, jugador.propiedades, jugador.encarcelado, jugador.saldo )
      @fianza = fianza
    end

    
    def pagarImpueto(cantidad)
      @saldo -= (casillaActual.coste)/2
    end
    
    def convertirme(fianza)
      return self
    end
    
    def deboIrACarcel()
      return super && !pagarFianza()
    end
    
    def pagarFianza()
        pagar = false
        
        if (tengoSaldo(@fianza))
          pagar = true
          puts "Fianza para salir carcel pagada"
        end
        
         
        
        return pagar
    end
    
    def puedoEdificarCasa(titulo)
        edificada = false
        
        numCasas = titulo.numCasas()
        if (numCasas < 8)
            costeEdificarCasa = titulo.precioEdificar()
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
        if (numHoteles < 8 && numCasas >= 4)
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
    
    #protected :pagarImpueto, :convertirme, :puedoEdificarCasa, :puedoEdificarHotel
    private :pagarFianza
    
    
    def to_s
      super + "\n fianza=#{@fianza}" 
    end
    
       
  end
end
