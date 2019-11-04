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
public class TituloPropiedad {
    
    private String nombre;
    private boolean hipotecada;
    private int precioCompra, alquilerBase, hipotecaBase, precioEdificar, numHoteles, numCasas;
    private float factorRevalorizacion;
    
    Jugador propietario;
    
    public TituloPropiedad(String nombre, int precioCompra, int alquilerBase, int hipotecaBase , int precioEdificar, float factorRevalorizacion){
        this.nombre=nombre;
        this.precioCompra=precioCompra;
        this.alquilerBase=alquilerBase;
        this.hipotecaBase=hipotecaBase;
        this.precioEdificar=precioEdificar;
        this.factorRevalorizacion=factorRevalorizacion;
        this.hipotecada=false;
        this.numHoteles=0;
        this.numCasas=0;       
        
    }
    
    String getNombre(){
        return nombre;
    }
    
    boolean getHipotecada(){
        return hipotecada;
    }
    
    int getPrecioCompra(){
        return precioCompra;
    }
    
    int getAlquilerBase(){
        return alquilerBase;
    }
    
    int getHipotecaBase(){
        return hipotecaBase;
    }
    
    int getPrecioEdificar(){
        return precioEdificar;
    }
    
    int getNumHoteles(){
        return numHoteles;
    }
    
    int getNumCasas(){
        return numCasas;
    }
    
    float getFactorRevalorizacion(){
        return factorRevalorizacion;
    }
    
    void setHipotecada(boolean hipotecada){
        this.hipotecada=hipotecada;
    }   
    
    int calcularCosteCancelar(){
       int cantidadRecibida = calcularCosteHipotecar();
       cantidadRecibida += cantidadRecibida*0.1;
       return cantidadRecibida;
    }
    
    int calcularCosteHipotecar(){
        return (int) (this.hipotecaBase + this.numCasas * 0.5 * this.hipotecaBase + this.numHoteles * this.hipotecaBase);
    }
    
    ///
        ///
        ///Preguntar si es asi
        ///
        /// 
    int calcularImporteAlquiler(){
        int coste = (int) (this.alquilerBase + this.numCasas*0.5 + this.numHoteles*2);
        return coste;
    }
    
    
    ///
        ///
        ///Preguntar si es asi
        ///
        ///
    int calcularPrecioVenta(){
        //int coste = (int) ((this.precioCompra + (this.numCasas)*this.precioEdificar + this.numHoteles*(this.precioEdificar*4) + this.precioEdificar ) * this.factorRevalorizacion);
        int coste = (int) (this.precioCompra + (this.numCasas + this.numHoteles) * this.precioEdificar * this.factorRevalorizacion);
        return coste;
    }
    
    void cancelarHipoteca(){
        this.hipotecada = false;
    }
    ///
        ///
        ///Preguntar si es asi
        ///
        /// 
    void cobrarAlquiler(int coste) {
        this.propietario.modificarSaldo(coste);
    }
    
    void edificarCasa() {
        this.numCasas += 1;
    
    }
    
    void edificarHotel() {
        this.numHoteles += 1;
    
    }
    
    Jugador getPropietario(){
        return propietario;
        
    }
    
    int hipotecar(){
        setHipotecada(true);
        int costeHipoteca = calcularCosteHipotecar();
        
        return costeHipoteca;
    }
    
    int pagarAlquiler(){
        int costeAlquiler = calcularImporteAlquiler();
        this.propietario.modificarSaldo(costeAlquiler);
        
        return costeAlquiler;
    }
    
    boolean propietarioEncarcelado(){
        return this.propietario.getEncarcelado();
    }
    
    
    void setPropietario(Jugador propietario){
        this.propietario = propietario;
    }
    
    
    boolean tengoPropietario(){
        return this.propietario != null;
    }

    @Override
    public String toString() {
        return "TituloPropiedad{" + "nombre=" + nombre + ", hipotecada=" + hipotecada + ", precioCompra=" + precioCompra + ", alquilerBase=" + alquilerBase + ", hipotecaBase=" + hipotecaBase + ", precioEdificar=" + precioEdificar + ", numHoteles=" + numHoteles + ", numCasas=" + numCasas + ", factorRevalorizacion=" + factorRevalorizacion +  '}'; //", propietario=" + propietario +
    }
    
    
    
    
    
    
    
    
    
    
    
}
