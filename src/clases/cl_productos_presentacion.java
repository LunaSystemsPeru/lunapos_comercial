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
 * @author DELACRUZ
 */
public class cl_productos_presentacion {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    private int id_producto;
    private int id_presentacion;
    private String nombre;
    private double factor;
    private double precio;

    public cl_productos_presentacion() {
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_presentacion() {
        return id_presentacion;
    }

    public void setId_presentacion(int id_presentacion) {
        this.id_presentacion = id_presentacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void obtener_codigo() {

        try {
            Statement st = c_conectar.conexion();
            String query = "select ifnull(max(id_presentacion) + 1, 1) as codigo "
                    + "from productos_presentaciones "
                    + "where id_producto = '" + id_producto + "'";
            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                id_presentacion = rs.getInt("codigo");
            }
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
    }

    public boolean insertar() {
        boolean registrado = false;
        Statement st = c_conectar.conexion();
        String query = "insert into productos_presentaciones "
                + "values ('" + id_producto + "', '" + id_presentacion + "', '" + nombre + "', '" + factor + "', '" + precio + "')";
        //System.out.println(query);
        int resultado = c_conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        c_conectar.cerrar(st);
        return registrado;
    }

    public boolean modificar() {
        boolean registrado = false;
        Statement st = c_conectar.conexion();
        String query = "update productos_presentaciones "
                + "set nombre = '" + nombre + "', factor = '" + factor + "', precio_unitario = '" + precio + "' "
                + "where id_producto = '" + id_producto + "' and id_presentacion = '" + id_presentacion + "'";
        //System.out.println(query);
        int resultado = c_conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        c_conectar.cerrar(st);
        return registrado;
    }
    
      public boolean eliminar_todo() {
        boolean registrado = false;
        Statement st = c_conectar.conexion();
        String query = "delete from productos_presentaciones "
                + "where id_producto = '" + id_producto + "'";
        //System.out.println(query);
        int resultado = c_conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        c_conectar.cerrar(st);
        return registrado;
    }

    public boolean obtener_datos() {
        boolean existe = false;
        try {

            Statement st = c_conectar.conexion();
            String query = "select * "
                    + "from productos_presentaciones "
                    + "where id_producto = '" + id_producto + "' and id_presentacion = '" + id_presentacion + "'";
            System.out.println(query);
            ResultSet rs = c_conectar.consulta(st, query);
            if (rs.next()) {
                nombre = rs.getString("nombre");
                factor = rs.getDouble("factor");
                precio = rs.getDouble("precio_unitario");
                existe = true;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return existe;
    }

    public void mostrar(JTable tabla, DefaultTableModel modelo) {
        try {
            /*DefaultTableModel modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };*/

            Statement st = c_conectar.conexion();
            String query = "select * "
                    + "from productos_presentaciones "
                    + "where id_producto = '" + id_producto + "' "
                    + "order by factor asc";
            ResultSet rs = c_conectar.consulta(st, query);
/*
            modelo.addColumn("Item");
            modelo.addColumn("Descripcion");
            modelo.addColumn("Factor");
            modelo.addColumn("Precio Unit.");
*/
            //Creando las filas para el JTable
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("id_presentacion");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("factor");
                fila[3] = rs.getString("precio_unitario");

                modelo.addRow(fila);
            }

            c_conectar.cerrar(st);
            c_conectar.cerrar(rs);

            tabla.setModel(modelo);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(20);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(300);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
            c_varios.centrar_celda(tabla, 0);
            c_varios.centrar_celda(tabla, 2);
            c_varios.derecha_celda(tabla, 3);

        } catch (SQLException e) {
            System.out.print(e);
        }

    }
}
