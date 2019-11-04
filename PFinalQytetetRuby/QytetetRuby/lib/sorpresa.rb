#encoding: UTF-8

require_relative "tipo_sorpresa"

module ModeloQytetet
  class Sorpresa
    attr_reader :texto, :tipo, :valor
    def initialize(texto, valor, tipo)
      @texto = texto
      @valor = valor
      @tipo = tipo
    
    end
    
    def to_s
      "Texto:#{@texto} Valor: #{@valor} Tipo: #{@tipo}"
    end 
  end
end

