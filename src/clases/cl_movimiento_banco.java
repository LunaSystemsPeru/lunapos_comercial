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
public class cl_movimiento_banco {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    private int id_movimiento;
    private int id_banco;
    private String fecha;
    private String descripcion;
    private double ingresa;
    private double sale;

    public cl_movimiento_banco() {
    }

    public int getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(int id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    public int getId_banco() {
        return id_banco;
    }

    public void setId_banco(int id_banco) {
        this.id_banco = id_banco;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getIngresa() {
        return ingresa;
    }

    public void setIngresa(double ingresa) {
        this.ingresa = ingresa;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    public void obtener_codigo() {
        try {
            Statement st = c_conectar.conexion();
            String query = "select ifnull(max(id_movimiento) + 1, 1) as codigo "
                    + "from bancos_movimientos ";
            ResultSet rs = c_conectar.consulta(st, query);
            if (rs.next()) {
                id_movimiento = rs.getInt("codigo");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public double obtener_saldo() {
        double saldo = 0;
        try {
            Statement st = c_conectar.conexion();
            String query = "select ifnull(sum(ingresa-sale), 0) as saldo "
                    + "from bancos_movimientos "
                    + "where date_format(fecha, '%Y%m') < date_format(curdate(), '%Y%m') and id_bancos = '" + id_banco + "'";
            ResultSet rs = c_conectar.consulta(st, query);
            if (rs.next()) {
                saldo = rs.getDouble("saldo");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return saldo;
    }

    public boolean registrar() {
        boolean registrado = false;
        Statement st = c_conectar.conexion();
        String query = "insert into bancos_movimientos "
                + "values ('" + id_movimiento + "', '" + id_banco + "', '" + fecha + "', '" + ingresa + "', '" + sale + "', '" + descripcion + "')";
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
        String query = "delete from bancos_movimientos "
                + "where id_movimiento = '" + id_movimiento + "'";
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
            tmodelo.addColumn("Ingresa");
            tmodelo.addColumn("Sale");
            tmodelo.addColumn("Saldo");

            int contar = 0;
            double saldo = obtener_saldo();

            double dingresa = 0;
            double dsale = 0;

            if (saldo > 0) {
                dingresa = saldo;
            } else {
                dsale = saldo;
            }

            Object[] fila = new Object[6];
            fila[0] = 0;
            fila[1] = c_varios.getFechaActual();
            fila[2] = "SALDO ANTERIOR";
            fila[3] = c_varios.formato_totales(dingresa);
            fila[4] = c_varios.formato_totales(dsale);
            fila[5] = c_varios.formato_totales(saldo);
            tmodelo.addRow(fila);

            //Creando las filas para el JTable
            while (rs.next()) {
                saldo += (rs.getDouble("ingresa") - rs.getDouble("sale"));
                contar++;
                fila = new Object[6];
                fila[0] = rs.getString("id_movimiento");
                fila[1] = rs.getString("fecha");
                fila[2] = rs.getString("descripcion").toUpperCase();
                fila[3] = c_varios.formato_numero(rs.getDouble("ingresa"));
                fila[4] = c_varios.formato_numero(rs.getDouble("sale"));
                fila[5] = c_varios.formato_numero(saldo);
                tmodelo.addRow(fila);
            }

            if (contar == 0) {
                JOptionPane.showMessageDialog(null, "NO SE HA ENCONTRADO RESULTADOS");
            }
            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
            tabla.setModel(tmodelo);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(420);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(80);
            c_varios.centrar_celda(tabla, 1);
            c_varios.derecha_celda(tabla, 3);
            c_varios.derecha_celda(tabla, 4);
            c_varios.derecha_celda(tabla, 5);
            tabla.setRowSorter(sorter);
            //c_varios.derecha_celda(tabla, 4);

        } catch (SQLException e) {
            System.out.print(e);
        }
    }

}
