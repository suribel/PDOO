#encoding: UTF-8

module ModeloQytetet
  class TituloPropiedad
    
    attr_reader :nombre, :precioCompra, :alquilerBase, :hipotecaBase, :precioEdificar, :numHoteles, :numCasas, :factorRevalorizacion
    attr_accessor :hipotecada, :propietario
    
    
    def initialize(nombre, precioCompra, alquilerBase, hipotecaBase , precioEdificar, factorRevalorizacion)
      @nombre=nombre
      @precioCompra=precioCompra
      @alquilerBase=alquilerBase
      @hipotecaBase=hipotecaBase
      @precioEdificar=precioEdificar
      @factorRevalorizacion=factorRevalorizacion
      
      @hipotecada=false
      @propietario = nil
      @numHoteles=0
      @numCasas=0
    end
    
    
    
    def calcularCosteCancelar()
      cantidadRecibida = calcularCosteHipotecar()
      cantidadRecibida += cantidadRecibida*0.1
      return cantidadRecibida
    end
    
    def calcularCosteHipotecar()
      return (@hipotecaBase + @numCasas * 0.5 * @hipotecaBase + @numHoteles * @hipotecaBase)
    end
    
    def calcularImporteAlquiler()
      coste = (@alquilerBase + @numCasas*0.5 + @numHoteles*2)
      return coste
    end
    
    def calcularPrecioVenta()
      coste = (@precioCompra + (@numCasas + @numHoteles) * @precioEdificar * @factorRevalorizacion)
      return coste
    end
    
    def cancelarHipoteca()
      @hipotecada = false
    end
    
    def cobrarAlquiler(coste) 
      @propietario.modificarSaldo(coste) 
    end
    
    def edificarCasa() 
      @numCasas += 1
    
    end
    
    def edificarHotel() 
      @numHoteles += 1
    
    end
    
    
    def hipotecar()
      @hipotecada=(true)
      costeHipoteca = calcularCosteHipotecar()
        
      return costeHipoteca
    end
    
    def pagarAlquiler()
      costeAlquiler = calcularImporteAlquiler()
      @propietario.modificarSaldo(costeAlquiler)
        
      return costeAlquiler
    end
    
    def propietarioEncarcelado()
      return @propietario.encarcelado
    end
        
        
    
    def tengoPropietario()
        return @propietario != nil
    end
    
    
    
    def to_s
        "nombre=#{@nombre}  hipotecada= #{@hipotecada}  precioCompra= #{@precioCompra}  alquilerBase= #{@alquilerBase}  hipotecaBase= #{@hipotecaBase}  precioEdificar= #{@precioEdificar}  numHoteles= #{@numHoteles}  numCasas= #{@numCasas} factorRevalorizacion= #{@factorRevalorizacion}"  
    end
    
    
  end
end
