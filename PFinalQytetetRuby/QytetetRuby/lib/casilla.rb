#encoding: UTF-8

module ModeloQytetet
  class Casilla
    
    attr_reader :numeroCasilla, :coste, :tipoCasilla
    
    
    def initialize(numeroCasilla, coste, tipoCasilla, titulo=nil)
      
      if titulo.nil?
        @numeroCasilla = numeroCasilla
        @coste = coste
        @tipoCasilla = tipoCasilla
        
      else
        @numeroCasilla = numeroCasilla
        @coste = titulo.precioCompra
        @tipoCasilla = TipoCasilla::CALLE
        
      
      
      end
            
    end   
    
    def soyEdificable()
      return (@tipoCasilla == TipoCasilla::CALLE)
    end
    
    
   
    
    def to_s
      if (@tipoCasilla = TipoCasilla::SORPRESA)
        "numeroCasilla=#{@numeroCasilla}  coste= #{@coste}  tipoCasilla= #{@tipoCasilla}"
      end
        
      "numeroCasilla=#{@numeroCasilla}  coste= #{@coste}  tipoCasilla= #{@tipoCasilla}"        
    
      
      end
    
  end
end
