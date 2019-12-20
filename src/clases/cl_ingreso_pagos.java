/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Bruno
 */
public class cl_ingreso_pagos {

    cl_conectar c_conectar = new cl_conectar();
    private int id_ingreso_pago;
    private int id_ingreso;
    private String fecha;
    private double monto;

    public cl_ingreso_pagos() {
    }

    public int getId_ingreso_pago() {
        return id_ingreso_pago;
    }

    public void setId_ingreso_pago(int id_ingreso_pago) {
        this.id_ingreso_pago = id_ingreso_pago;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId_ingreso() {
        return id_ingreso;
    }

    public void setId_ingreso(int id_ingreso) {
        this.id_ingreso = id_ingreso;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void obtener_codigo() {
        try {
            Statement st = c_conectar.conexion();
            String query = "select ifnull(max(id_ingreso_pago) + 1, 1) as codigo "
                    + "from ingreso_pagos ";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                id_ingreso_pago = rs.getInt("codigo");
            }
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
    }
    
    public boolean registrar() {
        boolean registrado = false;
        Statement st = c_conectar.conexion();
        
        String query = "INSERT INTO ingreso_pagos  VALUES ('"+id_ingreso_pago+"', '"+id_ingreso+"', '" + fecha + "', '"+monto+"');";
        int resultado = c_conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        c_conectar.cerrar(st);
        return registrado;
    }
    
    public boolean eliminar() {
        boolean registrado = false;
        Statement st = c_conectar.conexion();
        
        String query = "DELETE FROM ingreso_pagos WHERE id_ingreso_pago = '" + id_ingreso_pago + "'";
        int resultado = c_conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        c_conectar.cerrar(st);
        return registrado;
    }

    public void mostrar(JTable tabla) {
        try {
            DefaultTableModel modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };

            Statement st = c_conectar.conexion();
            String query = "select * from ingreso_pagos  where id_ingreso = '" + id_ingreso + "'  order by fecha asc";
            ResultSet rs = c_conectar.consulta(st, query);

            modelo.addColumn("Id");
            modelo.addColumn("Fecha");
            modelo.addColumn("Monto");

            //Creando las filas para el JTable
            while (rs.next()) {
                Object[] fila = new Object[3];
                fila[0] = rs.getInt("id_ingreso_pago");
                fila[1] = rs.getString("fecha");
                fila[2] = rs.getDouble("monto");

                modelo.addRow(fila);
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);

            tabla.setModel(modelo);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(100);
        } catch (SQLException e) {
            System.out.print(e);
        }

    }

}
