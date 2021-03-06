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
 * @author Bruno
 */
public class cl_prestamo {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    private int idPrestamo;
    private String fecha;
    private int idProveedor;
    private double monto;
    private int totCuota;
    private String fechaPago;
    private String montoCuota;
    private Double totalPagado;
    private String estado;
    private int idMovimiento;

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getTotCuota() {
        return totCuota;
    }

    public void setTotCuota(int totCuota) {
        this.totCuota = totCuota;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getMontoCuota() {
        return montoCuota;
    }

    public void setMontoCuota(String montoCuota) {
        this.montoCuota = montoCuota;
    }

    public Double getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(Double totalPagado) {
        this.totalPagado = totalPagado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public boolean eliminar() {
        boolean registrado = false;
        Statement st = c_conectar.conexion();
        String query = "DELETE\n"
                + "FROM  prestamos\n"
                + "WHERE id_prestamo = '" + this.idPrestamo + "';";
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
        String query = "INSERT INTO prestamos \n"
                + "VALUES ('" + this.idPrestamo + "',\n"
                + "        '" + this.fecha + "',\n"
                + "        '" + this.idProveedor + "',\n"
                + "        '" + this.monto + "',\n"
                + "        '" + this.totCuota + "',\n"
                + "        '" + this.fechaPago + "',\n"
                + "        '" + this.montoCuota + "',\n"
                + "        '" + this.totalPagado + "',\n"
                + "        '" + this.estado + "',\n"
                + "        '" + this.idMovimiento + "');";
        int resultado = c_conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        c_conectar.cerrar(st);
        return registrado;
    }

    public int obtener_codigo() {
        int resultado = 0;

        try {
            Statement st = c_conectar.conexion();
            String query = "select ifnull(max(id_prestamo) + 1, 1) as codigo "
                    + "from prestamos ";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                idPrestamo = rs.getInt("codigo");
                resultado = rs.getInt("codigo");
            }
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        return resultado;
    }

    public boolean comprobar() {
        boolean existe = false;

        try {
            Statement st = c_conectar.conexion();
            String query = "SELECT  * FROM  prestamos WHERE id_prestamo="+this.idPrestamo; 
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                this.fecha=rs.getString("fecha");
                this.idProveedor=rs.getInt("id_proveedor");
                this.monto=rs.getDouble("monto"); 
                this.totalPagado=rs.getDouble("total_pagado"); 
                this.idMovimiento=rs.getInt("id_movimiento");
                existe = true; 
            }
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        return existe;
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
            tmodelo.addColumn("Proveedor");
            tmodelo.addColumn("Monto");
            tmodelo.addColumn("Pagado");

            int contar = 0;
            //Creando las filas para el JTable
            while (rs.next()) {
                contar++;
                Object[] fila = new Object[8];
                fila[0] = rs.getInt("id_prestamo");
                fila[1] = rs.getString("fecha");
                fila[2] = rs.getString("razon_social");
                fila[3] = rs.getString("monto");
                fila[4] = rs.getString("total_pagado");

                tmodelo.addRow(fila);
            }

            if (contar == 0) {
                JOptionPane.showMessageDialog(null, "NO SE HA ENCONTRADO RESULTADOS");
            }
            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);
            tabla.setModel(tmodelo);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(120);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(750);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(150);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(150);

            tabla.setRowSorter(sorter);
//            c_varios.derecha_celda(tabla, 4);

        } catch (SQLException e) {
            System.out.print(e);
        }
    }

}
