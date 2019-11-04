# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module ModeloQytetet
  
  class Calle < Casilla
    
    attr_accessor :titulo
    
    def initialize(numeroCasilla, titulo)
      super(numeroCasilla,nil,nil, titulo)
      @titulo=titulo
    end
    
    def asignarPropietario(jugador)
      @titulo.propietario=(jugador)
      return @titulo
    end
    
    
    def pagarAlquiler()
      costeAlquiler = @titulo.pagarAlquiler()
      return costeAlquiler
    end
    
    def soyEdificable()
      return true
    end
        
    
    def tengoPropietario()
      return @titulo.tengoPropietario()
    end
    
    
    def to_s
      super + " \n titulo= #{@titulo}"
    end
       
    
    
    
  end
end
