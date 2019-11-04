# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

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

require_relative "controlador_qytetet"


module Vistatextualqytetet
  class VistaTextualQytetet
    
    include Controladorqytetet
    
    @@controlador = ControladorQytetet.instance
    
    def initialize
      
    end
    
    
    #private static final Scanner in = new Scanner (System.in);
    
    def obtenerNombreJugadores()
        nombres = Array.new
        
        num = 0
        while (num<3)
          puts "Ingresa numero de jugadores mayor que dos: "
        
          num=gets.chomp.to_i
        end
        
        i = 0 
        
        while i < num  
          puts "Ingresa nombre: "
            
          nombre = gets
          nombres << nombre  
          
          i += 1  
        end       
        
        return nombres 
    end
    
    def elegirCasilla(opcionMenu)
       operaciones = @@controlador.obtenerCasillasValidas(opcionMenu)
       
       num =-1
       
       if (!operaciones.empty?())
         
        arrayString = Array.new
           
        i=0
        while (i < operaciones.count())
           
          arrayString.insert(i,(operaciones[i]).to_s)
          i+=1
        end  
           
        puts " "
        puts "OPCIONES: "
        
        i=0   
        while (i < arrayString.count())
           
          print "Casilla numero: " , arrayString[i]
          puts
          i+=1
        end
                
        #Comprobar valor correcto
        var = false
        while(!var)          
            puts
            puts "Ingresa el numero de Casilla: "
            num = gets         
            num = num.to_i
            operaciones.each do |n|                          
                if (num == n)
                    var = true
                    break
                end
            end
        end     
        
           
       end
        
       return num
    end
    
    def leerValorCorrecto(valoresCorrectos)       
        return nil
    end
    
    def elegirOperacion()
        
        
        operaciones = @@controlador.obtenerOperacionesJuegoValidas()
        
        arrayString = Array.new
        
        i=0
        while (i < operaciones.count())
           
          arrayString.insert(i, (operaciones[i]).to_s)
          i+=1
        end  
           
        puts " "
        puts "OPCIONES: "
        
        i=0
        while (i < arrayString.count())
           
          print arrayString[i], "-" ,OpcionMenu[operaciones[i]]
          puts
          i+=1
        end
        
        #Comprobar valor correcto
        num = 0
        var = false
        while(!var)          
            puts
            puts "Ingresa valor: "
            num = gets         
            num = num.to_i
            operaciones.each do |n|                          
                if (num == n)
                    var = true
                    break
                end
            end
        end
           
        
                
        return num
    end
    
    
    
    def VistaTextualQytetet.main
        puts "hola"
        ui = VistaTextualQytetet.new
        @@controlador.nombreJugadores = (ui.obtenerNombreJugadores())
        operacionElegida = nil
        casillaElegida = 0
        necesitaElegirCasilla = nil
        while (1 == 1) 
            operacionElegida = ui.elegirOperacion()
            necesitaElegirCasilla = @@controlador.necesitaElegirCasilla(operacionElegida)
            if (necesitaElegirCasilla)
                casillaElegida = ui.elegirCasilla(operacionElegida)
            end
            if (!necesitaElegirCasilla || casillaElegida >= 0)
                puts (@@controlador.realizarOperacion(operacionElegida,casillaElegida))
            end
        end
    end
    
    
    
    
    
    
    
    
    
  end
  
  #VistaTextualQytetet.main
  
end
