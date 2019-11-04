/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloqytetet;



/**
 *
 * @author antonio-w10
 */
public class Dado {
    
    private static final Dado instance = new Dado();
    
    private int valor; 
    
    private Dado() {
    }
    
    public static Dado getInstance() {
        return instance;
    }
    
    int tirar(){
        this.valor = (int) (Math.random() * 6) + 1;
        System.out.println("dado= " + valor);
        
        return this.valor;
    }
    
    public int getValor(){
        return valor;
    }    

    
    
    @Override
    public String toString() {
        return "Dado{" + "valor=" + valor + '}';
    }
    
    
    
    
}


