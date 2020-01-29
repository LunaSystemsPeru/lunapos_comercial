/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.Statement;

/**
 *
 * @author Bruno
 */
public class prestamo_pago {
    cl_conectar c_conectar = new cl_conectar();
    private int id_movimiento;
    private int id_prestamo;
    private int nro_cuota;

    public int getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(int id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    public int getId_prestamo() {
        return id_prestamo;
    }

    public void setId_prestamo(int id_prestamo) {
        this.id_prestamo = id_prestamo;
    }

    public int getNro_cuota() {
        return nro_cuota;
    }

    public void setNro_cuota(int nro_cuota) {
        this.nro_cuota = nro_cuota;
    }
    
    public boolean registrar() {
        boolean registrado = false;
        Statement st = c_conectar.conexion();
        String query = "INSERT INTO prestamo_pago \n" +
                        "VALUES ('"+this.id_movimiento+"',\n" +
                        "        '"+this.id_prestamo+"',\n" +
                        "        '"+this.nro_cuota+"');";
        int resultado = c_conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        c_conectar.cerrar(st);
        return registrado;
    }
}
