#encoding: UTF-8

#Para poder usar el nombre de una clase definida en otro archivo
require_relative "sorpresa"
require_relative "tipo_sorpresa"
require_relative "qytetet"
require_relative "tablero"
require_relative "casilla"
require_relative "tipo_casilla"
require_relative "titulo_propiedad"
require_relative "metodo_salir_carcel"
require_relative "jugador"
require_relative "especulador"
require_relative "calle"
require_relative "dado"

require_relative "vista_textual_qytetet"
require_relative "controlador_qytetet"

module ModeloQytetet
  class PruebaQytetet
    
    include Vistatextualqytetet
    
      
    
    def PruebaQytetet.main     
      
      VistaTextualQytetet.main

    end
    
    
    
    
  end
  
  PruebaQytetet.main
  
end


    
    
    
    
            
        
        
        
    
    
