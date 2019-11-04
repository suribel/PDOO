#encoding: UTF-8

require_relative "casilla"

module ModeloQytetet
  class Tablero
    
    attr_reader :casillas, :carcel
    
    
    def initialize
      inicializar()
    end
    
    def inicializar()
      
      @casillas = Array.new
      
      
      @casillas << Casilla.new(0,1000,TipoCasilla::SALIDA)
        
      @casillas << Casilla.new(1,5,TipoCasilla::SORPRESA)
      @casillas << Casilla.new(2,5,TipoCasilla::SORPRESA)
      @casillas << Casilla.new(3,0,TipoCasilla::SORPRESA)
        
      @casillas << Casilla.new(4,0,TipoCasilla::CARCEL)
      
      @carcel = @casillas[4]
        
      @casillas << Casilla.new(5,0,TipoCasilla::JUEZ)
        
      @casillas << Casilla.new(6,0,TipoCasilla::PARKING)
        
      @casillas << Casilla.new(7,0,TipoCasilla::IMPUESTO)
        
        
        
      @casillas << Calle.new(8,TituloPropiedad.new("primera", 50, 50, 100 , 200,0.2))
      @casillas << Calle.new(9,TituloPropiedad.new("segunda", 50, 50, 100 , 200, 0.2))
      @casillas << Calle.new(10,TituloPropiedad.new("tercera", 50, 50, 100 , 200, 0.2))
      @casillas << Calle.new(11,TituloPropiedad.new("cuarta", 50, 50, 100 , 200, 0.2))
      @casillas << Calle.new(12,TituloPropiedad.new("quinta", 50, 50, 100 , 200, 0.2))
      @casillas << Calle.new(13,TituloPropiedad.new("sexta", 50, 50, 100 , 200,  0.2))
      @casillas << Calle.new(14,TituloPropiedad.new("setima", 50, 50, 100 , 200,  0.2))
      @casillas << Calle.new(15,TituloPropiedad.new("octava", 50, 50, 100 , 200,  0.2))
      @casillas << Calle.new(16,TituloPropiedad.new("novena", 50, 50, 100 , 200,  0.2))
      @casillas << Calle.new(17,TituloPropiedad.new("decima", 50, 50, 100 , 200,  0.2))
      @casillas << Calle.new(18,TituloPropiedad.new("decimoprimera", 50, 50, 100 , 200,  0.2))
      @casillas << Calle.new(19,TituloPropiedad.new("decimosegunda", 50, 50, 100 , 200,  0.2))
        
        
      
    end
    
    
    
    
    def esCasillaCarcel(numeroCasilla)
        return numeroCasilla == 4 
    end
    
    
    def obtenerCasillaFinal(casilla, desplazamiento)
      numCas = casilla.numeroCasilla
      numCas = (numCas + desplazamiento) % @casillas.length
      return @casillas[numCas]
    end
            
    def obtenerCasillaNumero(numeroCasilla)
      return @casillas[numeroCasilla]
    end
    
    
    private :inicializar
    
    
    def to_s
      "casillas=#{@casillas} \n Valor: #{@valor} \n carcel= #{@carcel}"  
      
    end
    
  end
end
