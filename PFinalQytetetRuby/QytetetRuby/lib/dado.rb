#encoding: UTF-8

require "singleton"



module ModeloQytetet
  class Dado
    
    include Singleton
    
    attr_reader :valor
    
    def initialize()
      @valor = nil
    end
    
    def tirar
      @valor = rand(6) + 1
      print "Valor dado: ", @valor
      puts
      return @valor
    end
    
    def to_s
      "Dado:#{@valor}"
      
    end
    
    
    def to_s
      "valordado=#{@valor}"        
    end
    
    
  end
end
