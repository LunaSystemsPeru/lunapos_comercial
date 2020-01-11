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
import javax.swing.table.TableRowSorter;

/**
 *
 * @author luis
 */
public class cl_proveedor_pago {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    private int id_proveedor;
    private int id_movimiento;

    public cl_proveedor_pago() {
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public int getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(int id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    public boolean registrar() {
        boolean registrado = false;
        Statement st = c_conectar.conexion();
        String query = "insert into proveedor_pago "
                + "values ('" + id_proveedor + "', '" + id_movimiento + "')";
        System.out.println(query);
        int resultado = c_conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        return registrado;
    }

    public boolean eliminar() {
        boolean registrado = false;
        Statement st = c_conectar.conexion();
        String query = "delete from proveedor_pago "
                + "where id_proveedor = '" + id_proveedor + "' and id_movimiento = '" + id_movimiento + "'";
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
            System.out.println(query);
            //Establecer como cabezeras el nombre de las colimnas
            tmodelo.addColumn("ID.");
            tmodelo.addColumn("Fecha");
            tmodelo.addColumn("Descripcion");
            tmodelo.addColumn("Sale");
            tmodelo.addColumn("Saldo");

            int contar = 0;
            double saldo = 0;
            //Creando las filas para el JTable
            while (rs.next()) {
                contar++;
                saldo += rs.getDouble("sale");
                Object[] fila = new Object[5];
                fila[0] = rs.getString("id_movimiento");
                fila[1] = rs.getString("fecha");
                fila[2] = rs.getString("descripcion");
                fila[3] = c_varios.formato_totales(rs.getDouble("sale"));
                fila[4] = c_varios.formato_totales(saldo);
                tmodelo.addRow(fila);
            }

            if (contar == 0) {
                JOptionPane.showMessageDialog(null, "NO SE HA ENCONTRADO RESULTADOS");
            }
            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
            tabla.setModel(tmodelo);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(320);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(100);
            tabla.setRowSorter(sorter);
            c_varios.derecha_celda(tabla, 4);
            c_varios.derecha_celda(tabla, 3);

        } catch (SQLException e) {
            System.out.print(e);
        }
    }

    public ResultSet pagos_periodo(String inicio) {
        String query = "select bm.descripcion, bm.id_movimiento, b.nombre, bm.fecha, bm.sale "
                + "from proveedor_pago as pp "
                + "         inner join bancos_movimientos bm on pp.id_movimiento = bm.id_movimiento "
                + "         inner join bancos b on bm.id_bancos = b.id_bancos "
                + "where pp.id_proveedor = '" + id_proveedor + "' "
                + "  and bm.fecha >= '" + inicio + "' "
                + "order by bm.fecha asc;";
        Statement st = c_conectar.conexion();
        ResultSet rs = c_conectar.consulta(st, query);
        return rs;
    }

    public double pagos_cuenta_anterior(String inicio) {
        double saldo = 0;
        try {
            String query = "select ifnull(sum(bm.sale),0)  as saldo_pagos "
                    + "from proveedor_pago as pp "
                    + "inner join bancos_movimientos bm on pp.id_movimiento = bm.id_movimiento "
                    + "where pp.id_proveedor = '" + id_proveedor + "' and bm.fecha < '" + inicio + "'";
            System.out.println(query);
            Statement st = c_conectar.conexion();
            ResultSet rs = c_conectar.consulta(st, query);
            if (rs.next()) {
                saldo = rs.getDouble("saldo_pagos");
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return saldo;
    }
}
