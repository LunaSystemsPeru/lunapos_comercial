/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_autocomplete;

import java.util.Date;

/**
 *
 * @author Usuario
 */
public class cla_estado_cliente {
    
    private Date fecha;
    private String documento;
    private String descripcion;
    private double pagado;
    private double venta;

    public cla_estado_cliente(Date fecha, String documento, String descripcion, double pagado, double venta) {
        this.fecha = fecha;
        this.documento = documento;
        this.descripcion = descripcion;
        this.pagado = pagado;
        this.venta = venta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPagado() {
        return pagado;
    }

    public void setPagado(double pagado) {
        this.pagado = pagado;
    }

    public double getVenta() {
        return venta;
    }

    public void setVenta(double venta) {
        this.venta = venta;
    }

    @Override
    public String toString() {
        return "cla_estado_cliente{" + "fecha=" + fecha + ", documento=" + documento + ", descripcion=" + descripcion + ", pagado=" + pagado + ", venta=" + venta + '}';
    }

    
    
    
}
