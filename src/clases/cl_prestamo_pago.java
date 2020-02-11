/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import render_tablas.render_productos_todos;

/**
 *
 * @author Bruno
 */
public class cl_prestamo_pago {
    cl_conectar c_conectar = new cl_conectar();
    private int id_movimiento;
    private int id_prestamo;
    private int nro_cuota;
    cl_varios c_varios=new cl_varios();
    
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
    public boolean eliminar() {
        boolean registrado = false;
        Statement st = c_conectar.conexion();
        String query = "DELETE \n" +
                        "FROM lsp_comercial.prestamo_pago \n" +
                        "WHERE id_movimiento = '"+this.id_movimiento+"' \n" +
                        "    AND id_prestamo = '"+this.id_prestamo+"';";
        int resultado = c_conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        c_conectar.cerrar(st);
        return registrado;
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
    
    public void mostrar(JTable tabla, String query) {
        try {
            DefaultTableModel tmodelo;
            tmodelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            TableRowSorter sorter = new TableRowSorter(tmodelo);
            Statement st = c_conectar.conexion();
            ResultSet rs = c_conectar.consulta(st, query);

            //Establecer como cabezeras el nombre de las colimnas
            tmodelo.addColumn("id"); 
            tmodelo.addColumn("Fecha");
            tmodelo.addColumn("Monto");
            tmodelo.addColumn("Descripcion");

            //Creando las filas para el JTable
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getObject("id_movimiento");
                //fila[1] = rs.getString("clasificacion") + " " + rs.getString("descripcion").trim() + " x " + rs.getString("id_unidad");
                fila[1] = rs.getString("fecha")   ;
                fila[2] = c_varios.formato_numero(rs.getDouble("sale"));
                fila[3] = rs.getString("descripcion");  
                tmodelo.addRow(fila);
            }
            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
            tabla.setModel(tmodelo);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(8);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(50); 
            tabla.getColumnModel().getColumn(2).setPreferredWidth(30);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(130);

        } catch (SQLException e) {
            System.out.print(e);
        }
    }
}
