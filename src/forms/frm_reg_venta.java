/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import clases.cl_cliente;
import clases.cl_cliente_pago;
import clases.cl_cobros_ventas;
import clases.cl_conectar;
import clases.cl_documento_almacen;
import clases.cl_movimiento_banco;
import clases.cl_producto;
import clases.cl_productos_almacen;
import clases.cl_productos_presentacion;
import clases.cl_productos_ventas;
import clases.cl_varios;
import clases.cl_venta;
import clases.cl_venta_eliminada;
import clases_autocomplete.cla_cliente;
import clases_autocomplete.cla_mis_documentos;
import clases_autocomplete.cla_presentaciones;
import clases_autocomplete.cla_producto;
import clases_hilos.cl_enviar_venta;
import clases_varios.Configuracion;
import com.mxrck.autocompleter.AutoCompleterCallback;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import models.m_mis_documentos;
import models.m_ubigeo;
import comercial.frm_principal;
import java.awt.Color;
import models.m_presentaciones;

/**
 *
 * @author luis
 */
public class frm_reg_venta extends javax.swing.JInternalFrame {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    //clases secundarias
    public static cl_cliente c_cliente = new cl_cliente();
    cl_producto c_producto = new cl_producto();
    cl_productos_almacen c_producto_almacen = new cl_productos_almacen();
    cl_documento_almacen c_doc_almacen = new cl_documento_almacen();
    cl_productos_presentacion c_presentacion = new cl_productos_presentacion();

    //clases para llenar combobox
    m_mis_documentos m_t_documentos = new m_mis_documentos();
    m_ubigeo m_ubigeo = new m_ubigeo();
    m_presentaciones m_presentacion = new m_presentaciones();

    //clases principales
    cl_venta c_venta = new cl_venta();
    cl_productos_ventas c_detalle = new cl_productos_ventas();
    cl_cobros_ventas c_cobro = new cl_cobros_ventas();
    cl_venta_eliminada c_cupon = new cl_venta_eliminada();
    cl_cliente_pago c_pago = new cl_cliente_pago();
    cl_movimiento_banco c_movimiento = new cl_movimiento_banco();

    //variables publicas
    static DefaultTableModel detalle;

    TextAutoCompleter tac_productos = null;
    TextAutoCompleter tac_clientes = null;

    double final_efectivo = 0;
    double final_tarjeta = 0;
    double final_total = 0;
    double final_vale = 0;
    int final_estado = 1;

    int id_empresa = frm_principal.c_empresa.getId();
    int id_almacen = frm_principal.c_almacen.getId();
    int id_usuario = frm_principal.c_usuario.getId_usuario();

    int fila_seleccionada = -1;

    /**
     * Creates new form frm_reg_venta
     */
    public frm_reg_venta() {
        initComponents();
        txt_serie.setDisabledTextColor(Color.black);
        txt_jd_descripcion.setDisabledTextColor(Color.black);
        txt_fecha.setText(c_varios.fecha_usuario(c_varios.getFechaActual()));
        c_producto_almacen.setAlmacen(id_almacen);
        modelo_venta();

        m_t_documentos.cbx_documentos_venta(cbx_tipo_doc);
        getContentPane().setBackground(Configuracion.COLOR_FORMULARIO_1);
        jd_modificar_item.getContentPane().setBackground(Configuracion.COLOR_FORMULARIO_1);
        jd_fin_venta.getContentPane().setBackground(Configuracion.COLOR_FORMULARIO_1);
        jPanel7.setBackground(Configuracion.COLOR_FORMULARIO_1);
    }

    private void modelo_venta() {
        //formato de tabla detalle de venta
        detalle = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        detalle.addColumn("Id");
        detalle.addColumn("Descripcion");
        detalle.addColumn("Cant.");
        detalle.addColumn("Precio");
        detalle.addColumn("Parcial");
        t_detalle.setModel(detalle);
        t_detalle.getColumnModel().getColumn(0).setPreferredWidth(20);
        t_detalle.getColumnModel().getColumn(1).setPreferredWidth(400);
        t_detalle.getColumnModel().getColumn(2).setPreferredWidth(50);
        t_detalle.getColumnModel().getColumn(3).setPreferredWidth(50);
        t_detalle.getColumnModel().getColumn(4).setPreferredWidth(70);
        c_varios.centrar_celda(t_detalle, 0);
        c_varios.derecha_celda(t_detalle, 2);
        c_varios.derecha_celda(t_detalle, 3);
        c_varios.derecha_celda(t_detalle, 4);
    }

    private double calcular_total() {
        double total = 0;
        int contar_filas = t_detalle.getRowCount();
        for (int i = 0; i < contar_filas; i++) {
            total = total + Double.parseDouble(t_detalle.getValueAt(i, 4).toString());
        }
        lbl_total_venta.setText("S/ " + c_varios.formato_totales(total));
        lbl_pago_venta.setText("S/ " + c_varios.formato_totales(total));
        return total;
    }

    private void calcular_vuelto() {
        double efectivo = Double.parseDouble(txt_j_efectivo.getText());
        double deuda = Double.parseDouble(text_deudatotal.getText());
        txt_j_por_pagar.setText(deuda - efectivo + "");
        /*
        double total = calcular_total();
        double suma_pago = efectivo;

        final_efectivo = efectivo;
        final_estado = 1;

        double vuelto = 0;
        double faltante = 0;

        if (suma_pago > total) {
            if (efectivo > total) {
                final_efectivo = total;
            }
            vuelto = suma_pago - total;
           // lbl_alerta.setText("Alerta: --");
        }
        if (total > suma_pago) {
            final_estado = 2;
            faltante = total - suma_pago;
            //lbl_alerta.setText("Alerta: El monto es menor al total. Vta. al credito");
        }

        txt_j_por_pagar.setText(c_varios.formato_numero(vuelto));
        txt_j_faltante.setText(c_varios.formato_numero(faltante));*/

    }

    private void cargar_productos(int tipo_documento) {
        try {
            if (tac_productos != null) {
                tac_productos.removeAllItems();
            }
            tac_productos = new TextAutoCompleter(txt_buscar_producto, new AutoCompleterCallback() {
                @Override
                public void callback(Object selectedItem) {
                    Object itemSelected = selectedItem;
                    c_producto.setId(0);
                    c_producto_almacen.setProducto(0);
                    if (itemSelected instanceof cla_producto) {
                        int pcodigo = ((cla_producto) itemSelected).getId_producto();
                        String pnombre = ((cla_producto) itemSelected).getDescripcion();
                        System.out.println("producto seleccionado " + pnombre);
                        c_producto.setId(pcodigo);
                        c_producto_almacen.setProducto(pcodigo);
                    } else {
                        System.out.println("El item es de un tipo desconocido");
                    }
                }
            });

            tac_productos.setMode(0);
            tac_productos.setCaseSensitive(false);
            Statement st = c_conectar.conexion();
            String sql = "select p.descripcion, pa.cactual, pa.csunat, p.precio, p.id_producto, p.marca, um.nombre as und_medida, pc.nombre as clasificacion "
                    + "from productos as p "
                    + "inner join productos_almacen as pa on pa.id_producto = p.id_producto "
                    + "inner join unidades_medida um on p.id_unidad = um.id_unidad "
                    + "inner join productos_clasificacion pc on p.id_clasificacion = pc.id_clasificacion "
                    + "where pa.id_almacen = '" + id_almacen + "' "; //and pa.cactual > 0
            ResultSet rs = c_conectar.consulta(st, sql);
            while (rs.next()) {
                int id_producto = rs.getInt("id_producto");
                /*String descripcion = rs.getString("descripcion") + " | " + rs.getString("und_medida")
                        + "    |    Cant: " + rs.getInt("cactual") + "    |    Precio: S/ " + c_varios.formato_numero(rs.getDouble("precio"));*/
                String descripcion = rs.getString("descripcion") + " | " + rs.getString("und_medida")
                        + "    |    Precio: S/ " + c_varios.formato_numero(rs.getDouble("precio"));
                tac_productos.addItem(new cla_producto(id_producto, descripcion));
            }
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e.getLocalizedMessage());
            System.out.println(e.getLocalizedMessage());
        }
    }

    private boolean valida_tabla(int producto) {
        //estado de ingreso
        boolean ingresar = false;
        int cuenta_iguales = 0;

        //verificar fila no se repite
        int contar_filas = t_detalle.getRowCount();
        if (contar_filas == 0) {
            ingresar = true;
        } else {
            for (int j = 0; j < contar_filas; j++) {
                int id_producto_fila = Integer.parseInt(t_detalle.getValueAt(j, 0).toString());
                if (producto == id_producto_fila) {
                    cuenta_iguales++;
                    JOptionPane.showMessageDialog(null, "El Producto a Ingresar ya existe en la lista");
                }
            }

            if (cuenta_iguales == 0) {
                ingresar = true;
            }
        }

        return ingresar;
    }

    private void cargar_clientes(int tipo) {
        try {
            if (tac_clientes != null) {
                tac_clientes.removeAllItems();
            }
            //TIPO:
            //1 para separacion
            // 2 para facturas
            // 3 para boleta
            JTextField text_box = txt_nom_cliente;
            String sql = "";
            if (tipo == 1) {
                txt_nom_cliente.setEnabled(true);
                txt_doc_cliente.setEnabled(false);
                txt_nom_cliente.requestFocus();
                text_box = txt_nom_cliente;
                sql = "select id_cliente, documento, nombre "
                        + "from clientes "
                        + "where LENGTH(documento) != 11 ";
            }

            if (tipo == 2) {
                txt_nom_cliente.setEnabled(false);
                txt_doc_cliente.setEnabled(true);
                txt_doc_cliente.requestFocus();
                text_box = txt_doc_cliente;
                sql = "select id_cliente, documento, nombre "
                        + "from clientes "
                        + "where LENGTH(documento) = 11 ";
            }

            if (tipo == 3) {
                txt_nom_cliente.setEnabled(true);
                txt_doc_cliente.setEnabled(false);
                text_box = txt_nom_cliente;
                sql = "select id_cliente, documento, nombre "
                        + "from clientes ";
                txt_nom_cliente.requestFocus();
            }

            tac_clientes = new TextAutoCompleter(text_box, new AutoCompleterCallback() {
                @Override
                public void callback(Object selectedItem) {
                    Object itemSelected = selectedItem;
                    c_cliente.setCodigo(-1);
                    if (itemSelected instanceof cla_cliente) {
                        int ccodigo = ((cla_cliente) itemSelected).getId_cliente();
                        String cnombre = ((cla_cliente) itemSelected).getNombre();
                        System.out.println("cliente seleccionado " + cnombre);
                        c_cliente.setCodigo(ccodigo);
                        c_cliente.comprobar_cliente();
                        lab_tatal_deuda.setText((c_cliente.getVenta() - c_cliente.getPago()) + "");
                    } else {
                        System.out.println("El item es de un tipo desconocido");
                    }
                }
            });

            tac_clientes.setMode(0);
            tac_clientes.setCaseSensitive(false);
            Statement st = c_conectar.conexion();
            ResultSet rs = c_conectar.consulta(st, sql);
            System.out.println(sql);
            while (rs.next()) {
                int id_cliente = rs.getInt("id_cliente");
                String descripcion = "";
                if (tipo == 3) {
                    descripcion = rs.getString("nombre");
                }
                if (tipo == 2) {
                    descripcion = rs.getString("documento");
                }
                tac_clientes.addItem(new cla_cliente(id_cliente, descripcion));
            }
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e.getLocalizedMessage());
            System.out.println(e.getLocalizedMessage());
        }

        if (tipo == 3) {
            txt_nom_cliente.requestFocus();
        }
        if (tipo == 2) {
            txt_doc_cliente.requestFocus();
        }
        if (tipo == 1) {
            txt_nom_cliente.requestFocus();
        }
    }

    private void limpiar_buscar() {

        txt_buscar_producto.setText("");
        txt_cantidad.setText("");
        //txt_cant_sunat.setText("");
        txt_precioUnitario.setText("");
        txt_cant_actual.setText("");
        txt_precio_total.setText("");
        //cbx_unid_medida.removeAllItems();
        txt_cantidad.setEnabled(false);
        //txt_cant_sunat.setEnabled(false);
        //txt_precio.setEnabled(false);
        txt_precio_total.setEnabled(false);
        //cbx_unid_medida.setEnabled(false);
        txt_buscar_producto.requestFocus();
    }

    private void limpiar_cliente() {
//        btn_crear_cliente.setEnabled(true);
        txt_doc_cliente.setText("");
        txt_nom_cliente.setText("");
        //  txt_dir_cliente.setText("");
        txt_nom_cliente.setEnabled(true);
        txt_nom_cliente.requestFocus();
    }

    private void reinicia_cliente() {
//        btn_actualizar.setEnabled(false);
        // btn_crear_cliente.setEnabled(false);
        txt_doc_cliente.setText("");
        txt_nom_cliente.setText("");
        //   txt_dir_cliente.setText("");
        txt_nom_cliente.setEnabled(false);
    }

    private void llenar_venta() {
        c_venta.setId_almacen(id_almacen);
        c_venta.setId_usuario(id_usuario);
        c_venta.setId_venta(c_venta.obtener_codigo());
        c_venta.setId_cliente(c_cliente.getCodigo());
        //c_venta.setFecha(c_varios.getFechaActual());
        c_venta.setFecha(c_varios.fecha_myql(txt_fecha.getText()));
        c_venta.setId_tido(c_doc_almacen.getId_tido());
        c_venta.setSerie(c_doc_almacen.getSerie());
        c_venta.setNumero(c_doc_almacen.getNumero());
        c_venta.setTotal(final_total);
        c_venta.setId_tipo_venta(cbx_tipo_venta.getSelectedIndex() + 1);
        c_venta.setPagado(final_efectivo + final_tarjeta);
        c_venta.setEstado(2);
        c_venta.setEnviado_sunat(0);
    }

    private void llenar_cobros() {
        c_movimiento.setId_banco(1);
        c_movimiento.setDescripcion("SALDO A CUENTA DE " + c_cliente.getNombre());
        c_movimiento.setFecha(c_varios.fecha_myql(txt_fecha.getText()));
        c_movimiento.setIngresa(Double.parseDouble(txt_j_efectivo.getText()));
        c_movimiento.setSale(0);
        c_movimiento.obtener_codigo();
        c_movimiento.registrar();

        c_pago.setId_cliente(c_cliente.getCodigo());
        c_pago.setId_movimiento(c_movimiento.getId_movimiento());
        c_pago.registrar();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jd_fin_venta = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lbl_pago_venta = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txt_j_efectivo = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_j_faltante = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txt_j_por_pagar = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        text_deudatotal = new javax.swing.JTextField();
        btn_pago = new javax.swing.JButton();
        jd_modificar_item = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txt_jd_idproducto = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txt_jd_descripcion = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txt_jd_cantidad = new javax.swing.JTextField();
        txt_jd_precio = new javax.swing.JTextField();
        btn_jd_actualizar = new javax.swing.JButton();
        btn_jd_eliminar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_buscar_producto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_cantidad = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        txt_cant_actual = new javax.swing.JTextField();
        txt_precio_total = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_precioUnitario = new javax.swing.JTextField();
        boton_agregar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_fecha = new javax.swing.JFormattedTextField();
        cbx_tipo_venta = new javax.swing.JComboBox<>();
        cbx_tipo_doc = new javax.swing.JComboBox<>();
        txt_serie = new javax.swing.JTextField();
        txt_numero = new javax.swing.JTextField();
        txt_doc_cliente = new javax.swing.JTextField();
        txt_nom_cliente = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lbl_total_venta = new javax.swing.JLabel();
        btn_grabar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lab_tatal_deuda = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_detalle = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        lbl_ayuda = new javax.swing.JLabel();

        jd_fin_venta.setTitle("Grabar Venta");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Total Venta:");

        lbl_pago_venta.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        lbl_pago_venta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_pago_venta.setText("S/ 2,500.00");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_pago_venta, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_pago_venta, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBackground(Configuracion.COLOR_FORMULARIO_1);

        jLabel12.setText("A Cuenta:");

        txt_j_efectivo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_j_efectivo.setText("0.00");
        txt_j_efectivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_j_efectivoKeyPressed(evt);
            }
        });

        jLabel15.setText("Deuda Total:");

        txt_j_faltante.setEditable(false);
        txt_j_faltante.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_j_faltante.setText("0.00");
        txt_j_faltante.setFocusable(false);

        jLabel18.setText("Por Pagar");

        txt_j_por_pagar.setEditable(false);
        txt_j_por_pagar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_j_por_pagar.setText("0.00");
        txt_j_por_pagar.setFocusable(false);

        jLabel16.setText("Deuda:");

        text_deudatotal.setEditable(false);
        text_deudatotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        text_deudatotal.setText("0.00");
        text_deudatotal.setFocusable(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(176, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_j_efectivo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txt_j_por_pagar)
                        .addComponent(txt_j_faltante, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(text_deudatotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_j_efectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_j_faltante, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(text_deudatotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_j_por_pagar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btn_pago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        btn_pago.setText("Finalizar Venta");
        btn_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pagoActionPerformed(evt);
            }
        });
        btn_pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_pagoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jd_fin_ventaLayout = new javax.swing.GroupLayout(jd_fin_venta.getContentPane());
        jd_fin_venta.getContentPane().setLayout(jd_fin_ventaLayout);
        jd_fin_ventaLayout.setHorizontalGroup(
            jd_fin_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_fin_ventaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jd_fin_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jd_fin_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btn_pago))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jd_fin_ventaLayout.setVerticalGroup(
            jd_fin_ventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_fin_ventaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jd_modificar_item.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jd_modificar_item.setTitle("Actualizar Item");

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Modificar ITem"));

        jLabel17.setText("Codigo:");

        txt_jd_idproducto.setEnabled(false);

        jLabel20.setText("Descripcion:");

        txt_jd_descripcion.setEnabled(false);

        jLabel21.setText("Cantidad:");

        jLabel22.setText("Precio:");

        txt_jd_cantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_jd_cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_jd_cantidadKeyTyped(evt);
            }
        });

        txt_jd_precio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_jd_precio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_jd_precioKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_jd_idproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(txt_jd_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_jd_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(170, 170, 170))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(txt_jd_descripcion)
                                .addContainerGap())))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_jd_idproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_jd_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_jd_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_jd_precio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btn_jd_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/application_edit.png"))); // NOI18N
        btn_jd_actualizar.setText("Actualizar");
        btn_jd_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_jd_actualizarActionPerformed(evt);
            }
        });

        btn_jd_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cross.png"))); // NOI18N
        btn_jd_eliminar.setText("Elimintar Item");
        btn_jd_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_jd_eliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jd_modificar_itemLayout = new javax.swing.GroupLayout(jd_modificar_item.getContentPane());
        jd_modificar_item.getContentPane().setLayout(jd_modificar_itemLayout);
        jd_modificar_itemLayout.setHorizontalGroup(
            jd_modificar_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_modificar_itemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jd_modificar_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jd_modificar_itemLayout.createSequentialGroup()
                        .addComponent(btn_jd_eliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_jd_actualizar)))
                .addContainerGap())
        );
        jd_modificar_itemLayout.setVerticalGroup(
            jd_modificar_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_modificar_itemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_modificar_itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_jd_actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_jd_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setClosable(true);
        setTitle("Reg. Documento Venta");

        jPanel1.setBackground(Configuracion.COLOR_PANEL_INTERNO);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Agregar Productos"));

        jLabel1.setText("Buscar:");

        txt_buscar_producto.setEnabled(false);
        txt_buscar_producto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_buscar_productoFocusGained(evt);
            }
        });
        txt_buscar_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_buscar_productoKeyPressed(evt);
            }
        });

        jLabel2.setText("Cantidad:");

        txt_cantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_cantidad.setEnabled(false);
        txt_cantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_cantidadFocusGained(evt);
            }
        });
        txt_cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cantidadKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_cantidadKeyTyped(evt);
            }
        });

        jLabel10.setText("Cant. Actual:");

        jLabel34.setText("Precio Total:");

        txt_cant_actual.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_cant_actual.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_cant_actual.setEnabled(false);

        txt_precio_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_precio_total.setText("0");
        txt_precio_total.setToolTipText("");
        txt_precio_total.setEnabled(false);
        txt_precio_total.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_precio_totalFocusGained(evt);
            }
        });
        txt_precio_total.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_precio_totalKeyPressed(evt);
            }
        });

        jLabel3.setText("P. Unitario");

        txt_precioUnitario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_precioUnitario.setToolTipText("");
        txt_precioUnitario.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_precioUnitario.setEnabled(false);
        txt_precioUnitario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_precioUnitarioFocusGained(evt);
            }
        });
        txt_precioUnitario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_precioUnitarioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_precioUnitarioKeyTyped(evt);
            }
        });

        boton_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        boton_agregar.setText("Agregar");
        boton_agregar.setToolTipText("");
        boton_agregar.setEnabled(false);
        boton_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_agregarActionPerformed(evt);
            }
        });
        boton_agregar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                boton_agregarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(33, 33, 33)
                                .addComponent(txt_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_precioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34)
                            .addComponent(jLabel10))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_precio_total, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_cant_actual, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(boton_agregar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_buscar_producto)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_buscar_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_cant_actual, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_precio_total, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_agregar)
                    .addComponent(jLabel3)
                    .addComponent(txt_precioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setBackground(Configuracion.COLOR_PANEL_INTERNO);
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Generales Venta"));

        jLabel4.setText("Fecha:");

        jLabel5.setText("Tipo Venta:");

        jLabel6.setText("Tipo Documento:");

        try {
            txt_fecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_fecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_fecha.setText("10/10/2018");
        txt_fecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_fechaKeyPressed(evt);
            }
        });

        cbx_tipo_venta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "VENTA", "SEPARACION" }));
        cbx_tipo_venta.setEnabled(false);
        cbx_tipo_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_tipo_ventaActionPerformed(evt);
            }
        });
        cbx_tipo_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbx_tipo_ventaKeyPressed(evt);
            }
        });

        cbx_tipo_doc.setEnabled(false);
        cbx_tipo_doc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbx_tipo_docFocusGained(evt);
            }
        });
        cbx_tipo_doc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_tipo_docActionPerformed(evt);
            }
        });
        cbx_tipo_doc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbx_tipo_docKeyPressed(evt);
            }
        });

        txt_serie.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_serie.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_serie.setEnabled(false);

        txt_numero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_numero.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_numero.setEnabled(false);

        txt_doc_cliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_doc_cliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_doc_cliente.setEnabled(false);
        txt_doc_cliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_doc_clienteFocusGained(evt);
            }
        });
        txt_doc_cliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_doc_clienteKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_doc_clienteKeyPressed(evt);
            }
        });

        txt_nom_cliente.setEnabled(false);
        txt_nom_cliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_nom_clienteFocusGained(evt);
            }
        });
        txt_nom_cliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nom_clienteKeyPressed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Total a Pagar:");

        lbl_total_venta.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        lbl_total_venta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_total_venta.setText("S/ 0.00");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_total_venta, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_total_venta, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_grabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/accept.png"))); // NOI18N
        btn_grabar.setText("Grabar");
        btn_grabar.setEnabled(false);
        btn_grabar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btn_grabarFocusGained(evt);
            }
        });
        btn_grabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_grabarActionPerformed(evt);
            }
        });
        btn_grabar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_grabarKeyPressed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cross.png"))); // NOI18N
        jButton3.setText("Cancelar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButton1.setText("Crear Cliente");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel7.setText("Buscar cliente");

        jLabel11.setText("Deuda de Cliente:");

        lab_tatal_deuda.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_nom_cliente)
                    .addComponent(btn_grabar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cbx_tipo_venta, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbx_tipo_doc, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_doc_cliente, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txt_serie, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_numero, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_fecha)))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lab_tatal_deuda, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_tipo_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_tipo_doc, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_serie, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_numero, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_doc_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nom_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lab_tatal_deuda))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(btn_grabar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(Configuracion.COLOR_PANEL_INTERNO);
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Ver Productos en Venta"));

        t_detalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1520", "GUITARRA ACUSTICA VOZZEX", "1", "152.00", "152.00"}
            },
            new String [] {
                "ID.", "Descripcion", "Cant.", "Precio", "Parcial"
            }
        ));
        t_detalle.setShowVerticalLines(false);
        t_detalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_detalleMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(t_detalle);
        if (t_detalle.getColumnModel().getColumnCount() > 0) {
            t_detalle.getColumnModel().getColumn(0).setPreferredWidth(50);
            t_detalle.getColumnModel().getColumn(1).setPreferredWidth(550);
            t_detalle.getColumnModel().getColumn(2).setPreferredWidth(50);
            t_detalle.getColumnModel().getColumn(3).setPreferredWidth(80);
            t_detalle.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        jLabel23.setText("Ayuda:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_ayuda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_ayuda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txt_buscar_productoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscar_productoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_buscar_producto.getText().length() > 25) {
                if (c_producto_almacen.validar_id()) {
                    //validar que no existe en la tabla
                    if (valida_tabla(c_producto.getId())) {
                        c_producto.validar_id();
                        txt_cantidad.setText("1");
                        txt_precioUnitario.setText(c_producto.getPrecio() + "");
                        txt_precioUnitario.setEnabled(true);
                        txt_cant_actual.setText(c_varios.formato_numero(c_producto_almacen.getCtotal()));
                        txt_cantidad.setEnabled(true);
                        txt_cantidad.requestFocus();
                        txt_cantidad.selectAll();
                    } else {
                        c_producto.setId(0);
                        c_producto_almacen.setProducto(0);
                        limpiar_buscar();
                        JOptionPane.showMessageDialog(null, "ESTE PRODUCTO YA ESTA SELECCIONADO");
                    }
                } else {
                    c_producto.setId(0);
                    c_producto_almacen.setProducto(0);
                    limpiar_buscar();
                    JOptionPane.showMessageDialog(null, "ERROR AL SELECCIONAR PRODUCTO");
                }
            }

            if (txt_buscar_producto.getText().length() == 0) {
                //si nro de filas es mayor a 0 entonces ir a datos generales
                int contar_filas = t_detalle.getRowCount();
                if (contar_filas > 0) {
                    btn_grabar.setEnabled(true);
                    btn_grabar.requestFocus();

                }
            }
        }

        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            limpiar_buscar();
        }
    }//GEN-LAST:event_txt_buscar_productoKeyPressed

    private void calcular_subtotal() {
        double cantidad = Double.parseDouble(txt_cantidad.getText());
        double precio = Double.parseDouble(txt_precioUnitario.getText());

        double subtotal = precio * cantidad;
        txt_precio_total.setText(c_varios.formato_precio(subtotal));
    }

    private void txt_cantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cantidadKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String tcantidad = txt_cantidad.getText();
            if (c_varios.esDecimal(tcantidad)) {
                double cantidad_vender = Double.parseDouble(tcantidad);
                //provisionalmente se quita el bloqueo de vender sin cantidad
                // if (cantidad_vender > c_producto_almacen.getCtotal()) {
                //     JOptionPane.showMessageDialog(null, "NO TIENE LA CANTIDAD PARA VENDER ");
                //     limpiar_buscar();
                // } else {
                calcular_subtotal();
                txt_precioUnitario.setEnabled(true);
                txt_precioUnitario.selectAll();
                txt_precioUnitario.requestFocus();

                // }
            }
        }

        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            limpiar_buscar();
        }
    }//GEN-LAST:event_txt_cantidadKeyPressed

    private void cbx_tipo_ventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbx_tipo_ventaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            c_venta.setId_tipo_venta(cbx_tipo_venta.getSelectedIndex() + 1);

            //si es venta seleccionar tipo documento
            if (c_venta.getId_tipo_venta() == 1) {
                reinicia_cliente();
                cbx_tipo_doc.setEnabled(true);
                cbx_tipo_doc.requestFocus();
            }

            //si es separacion saltar a cliente y cargar datos de nota de venta
            if (c_venta.getId_tipo_venta() == 2) {
                //bloquer tipo documento
                cbx_tipo_doc.setEnabled(false);

                //cargar datos de nota de venta
                c_doc_almacen.setId_tido(7);
                c_doc_almacen.setId_almacen(id_almacen);
                c_doc_almacen.comprobar_documento();

                txt_serie.setText(c_doc_almacen.getSerie());
                txt_numero.setText(c_doc_almacen.getNumero() + "");

                //ir a nombre cliente
                limpiar_cliente();
                cargar_clientes(3);

                cargar_productos(1);
            }
        }
    }//GEN-LAST:event_cbx_tipo_ventaKeyPressed
    public void selecion_dedocumento() {
        cla_mis_documentos cla_tido = (cla_mis_documentos) cbx_tipo_doc.getSelectedItem();
        int id_tido = cla_tido.getId_tido();

        //cargar datos de nota de venta
        c_doc_almacen.setId_tido(id_tido);
        c_doc_almacen.setId_almacen(id_almacen);
        c_doc_almacen.comprobar_documento();

        txt_serie.setText(c_doc_almacen.getSerie());
        txt_numero.setText(c_doc_almacen.getNumero() + "");

        //si es nota de venta salta a grabar
        if (id_tido == 6) {
            c_cliente.setCodigo(-1);
            c_cliente.comprobar_cliente();
            txt_doc_cliente.setEnabled(false);
            txt_nom_cliente.setEnabled(true);
            //btn_crear_cliente.setEnabled(true);
            txt_doc_cliente.setText(c_cliente.getDocumento());
            //txt_nom_cliente.setText(c_cliente.getNombre());
            //txt_buscar_producto.setEnabled(true);
            txt_nom_cliente.requestFocus();
            cargar_clientes(3);
        }
        //si es boleta o factura salta a cliente
        if (id_tido == 1 || id_tido == 2) {
            if (id_tido == 1) {
                //ir a nombre cliente
                limpiar_cliente();
                cargar_clientes(3);
            }

            if (id_tido == 2) {
                //ir a ruc cliente
                limpiar_cliente();
                cargar_clientes(2);

            }
        }

        cargar_productos(id_tido);

        if (id_tido == 7) {
            JOptionPane.showMessageDialog(null, "NO SE PUEDE SELECCIONAR ESTE DOCUMENTO");
        }

    }
    private void cbx_tipo_docKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbx_tipo_docKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

        }
    }//GEN-LAST:event_cbx_tipo_docKeyPressed

    private void txt_nom_clienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nom_clienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_nom_cliente.getText().trim().length() > 5) {
                //validar nombre cliente
                c_cliente.setNombre(txt_nom_cliente.getText());
                if (!c_cliente.comprobar_nombre_cliente()){
                    c_cliente.setCodigo(-1);
                }
                
                //validar cliente
                if (c_cliente.getCodigo() != -1) {
                    if (c_cliente.comprobar_cliente()) {
                        txt_doc_cliente.setText(c_cliente.getDocumento());
                        cargar_productos(1);
                        txt_buscar_producto.setEnabled(true);
                        txt_buscar_producto.requestFocus();
                    } else {
                        JOptionPane.showMessageDialog(null, "EL CLIENTE NO EXISTE, \nSELECCIONE CON ENTER");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "EL CLIENTE NO EXISTE, \nSELECCIONE CON ENTER");
                }
            }
        }
    }//GEN-LAST:event_txt_nom_clienteKeyPressed

    private void txt_doc_clienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_doc_clienteKeyPressed
    }//GEN-LAST:event_txt_doc_clienteKeyPressed

    private void btn_grabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_grabarActionPerformed
        //btn_pago.setEnabled(false);
        jd_fin_venta.setModal(true);
        jd_fin_venta.setSize(422, 331);
        jd_fin_venta.setLocationRelativeTo(null);

        final_total = calcular_total();
        txt_j_efectivo.selectAll();
        txt_j_efectivo.requestFocus();
        txt_j_faltante.setText(lab_tatal_deuda.getText());
        text_deudatotal.setText((c_cliente.getVenta() - c_cliente.getPago() + final_total) + "");

        jd_fin_venta.setVisible(true);
    }//GEN-LAST:event_btn_grabarActionPerformed

    private void txt_j_efectivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_j_efectivoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String texto = txt_j_efectivo.getText();
            if (texto.length() > 0 && c_varios.esDecimal(texto)) {
                calcular_vuelto();
                double monto = Double.parseDouble(texto);

                txt_j_por_pagar.setText((Double.parseDouble(text_deudatotal.getText())) - monto + "");
                // final_total = (Double.parseDouble(text_deudatotal.getText()));
                if (monto >= Double.parseDouble(text_deudatotal.getText())) {
                    btn_pago.setEnabled(true);
                    btn_pago.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "VENTA SE PASARA CON CREDITO");
                    btn_pago.setEnabled(true);
                    btn_pago.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "ERROR CON EL MONTO");
            }
        }
    }//GEN-LAST:event_txt_j_efectivoKeyPressed

    private void btn_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pagoActionPerformed
        //btn_pago.setEnabled(false);

        if (final_total > 0) {
            if (true) {
                c_venta.setId_cliente(c_cliente.getCodigo());

                btn_pago.setEnabled(false);
                llenar_venta();
                c_venta.setId_tido(6);
                if (c_venta.registrar()) {
                    int contar_tabla = t_detalle.getRowCount();
                    for (int i = 0; i < contar_tabla; i++) {
                        c_detalle.setId_venta(c_venta.getId_venta());
                        c_detalle.setId_producto(Integer.parseInt(t_detalle.getValueAt(i, 0).toString()));
                        c_detalle.setCantidad(Double.parseDouble(t_detalle.getValueAt(i, 2).toString()));
                        c_producto.setId(c_detalle.getId_producto());
                        c_producto.validar_id();
                        c_detalle.setCosto(c_producto.getCosto());
                        c_detalle.setPrecio(Double.parseDouble(t_detalle.getValueAt(i, 3).toString()));

                        c_detalle.registrar();
                    }

                    llenar_cobros();

                    //generar txt 
                    if (c_venta.getId_tipo_venta() == 1) {
                        if (c_venta.getId_tido() == 1 || c_venta.getId_tido() == 2) {
                            /*
                        int emite_guia = cbx_guia.getSelectedIndex();
                        if (emite_guia == 0) {
                            c_guia = new cl_guia_remision();
                            c_guia.setId_almacen(c_venta.getId_almacen());
                            c_guia.setId_venta(c_venta.getId_venta());
                            c_guia.setId_empresa(id_empresa);
                            c_guia.setLlegada(txt_llegada_guia.getText().toUpperCase());
                            c_guia.setUbigeo(lbl_ubigeo.getText());
                            c_guia.setRuc_transporte(txt_ruc_transportista.getText());
                            c_guia.setRazon_transporte(txt_razon_transportista.getText());
                            c_guia.setDni_chofer(txt_dni_chofer.getText());
                            c_guia.setPlaca(txt_placa_vehiculo.getText());

                            c_doc_guia = new cl_documento_almacen();
                            c_doc_guia.setId_tido(5);
                            c_doc_guia.setId_almacen(c_venta.getId_almacen());
                            c_doc_guia.comprobar_documento();

                            c_guia.setSerie(c_doc_guia.getSerie());
                            c_guia.setNumero(c_doc_guia.getNumero());

                            c_guia.registrar();
                       }
                             */
                            try {
                                //Ponemos a "Dormir" el programa durante los ms que queremos
                                Thread.sleep(2 * 1000);
                                System.out.println("durmiendo app x 2 segundos");
                            } catch (InterruptedException e) {
                                System.out.println(e);
                            }

                            cl_enviar_venta c_enviar = new cl_enviar_venta();
                            //                     c_enviar.setGuia(emite_guia);
                            c_enviar.setId_venta(c_venta.getId_venta());
                            c_enviar.setId_almacen(id_almacen);
                            c_enviar.start();

                        }
                    }

                    if (c_venta.getId_tipo_venta() == 2) {

                        /*si es separacion no imprime, se desabilita porque 
                    * decidieron imprimir al entrega los productos
                    * se genera una boleta o factura.
                         */
 /*
                    Print_Separacion_Ticket ticket = new Print_Separacion_Ticket();
                    ticket.setId_almacen(c_venta.getId_almacen());
                    ticket.setId_venta(c_venta.getId_venta());

                    ticket.generar_ticket();

                    jd_fin_venta.dispose();
                    this.dispose();

                    frm_reg_venta reg_venta = new frm_reg_venta();
                    c_varios.llamar_ventana(reg_venta);*/
                    }

                    jd_fin_venta.dispose();
                    this.dispose();

                    frm_reg_venta reg_venta = new frm_reg_venta();
                    c_varios.llamar_ventana(reg_venta);

                } else {

                    System.out.println("no se pudo registrar la venta");
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "NO ES UNA VENTA VALIDA, MONTO TOTAL MENOR O IGUAL A CERO (0)");
        }
    }//GEN-LAST:event_btn_pagoActionPerformed

    private void dormir_programa() {
        try {
            //Ponemos a "Dormir" el programa durante los ms que queremos
            Thread.sleep(15 * 1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
    private void t_detalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_detalleMouseClicked
        int nro_filas = t_detalle.getRowCount();
        if (nro_filas > 0) {
            fila_seleccionada = t_detalle.getSelectedRow();
            String id_producto = t_detalle.getValueAt(fila_seleccionada, 0).toString();
            String descripcion = t_detalle.getValueAt(fila_seleccionada, 1).toString();
            double cantidad = Double.parseDouble(t_detalle.getValueAt(fila_seleccionada, 2).toString());
            double precio = Double.parseDouble(t_detalle.getValueAt(fila_seleccionada, 3).toString());

            jd_modificar_item.setModal(true);
            jd_modificar_item.setSize(881, 260);
            jd_modificar_item.setLocationRelativeTo(null);

            //cargar datos
            txt_jd_idproducto.setText(id_producto);
            txt_jd_descripcion.setText(descripcion);
            txt_jd_cantidad.setText(cantidad + "");
            txt_jd_precio.setText(c_varios.formato_numero(precio));

            jd_modificar_item.setVisible(true);

            txt_jd_cantidad.requestFocus();
        }
    }//GEN-LAST:event_t_detalleMouseClicked

    private void btn_jd_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_jd_actualizarActionPerformed
        String tcantidad = txt_jd_cantidad.getText();
        String tprecio = txt_jd_precio.getText();

        int suma_error = 0;

        if (!c_varios.esEntero(tcantidad) && !tcantidad.isEmpty()) {
            suma_error++;
            JOptionPane.showMessageDialog(null, "ERROR AL INGRESAR CANTIDAD");
        }

        if (!c_varios.esDecimal(tprecio) && !tprecio.isEmpty()) {
            suma_error++;
            JOptionPane.showMessageDialog(null, "ERROR AL MODIFICAR PRECIO");
        }

        if (suma_error == 0) {
            int cantidad = Integer.parseInt(tcantidad);
            double precio = Double.parseDouble(tprecio);
            double parcial = cantidad * precio;
            t_detalle.setValueAt(cantidad, fila_seleccionada, 2);
            t_detalle.setValueAt(c_varios.formato_numero(precio), fila_seleccionada, 3);
            t_detalle.setValueAt(c_varios.formato_numero(parcial), fila_seleccionada, 4);

            calcular_total();
            jd_modificar_item.dispose();
            txt_buscar_producto.requestFocus();

        }
    }//GEN-LAST:event_btn_jd_actualizarActionPerformed

    private void txt_jd_cantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jd_cantidadKeyTyped
        c_varios.solo_numeros(evt);
    }//GEN-LAST:event_txt_jd_cantidadKeyTyped

    private void txt_jd_precioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jd_precioKeyTyped
        c_varios.solo_precio(evt);
    }//GEN-LAST:event_txt_jd_precioKeyTyped

    private void txt_buscar_productoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_buscar_productoFocusGained
        lbl_ayuda.setText("ESCRIBIR PARA MOSTRAR PRODUCTOS        ENTER: PARA SELECCIONAR PRODUCTO");
    }//GEN-LAST:event_txt_buscar_productoFocusGained

    private void txt_cantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cantidadKeyTyped
        c_varios.solo_precio(evt);
    }//GEN-LAST:event_txt_cantidadKeyTyped

    private void txt_cantidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_cantidadFocusGained
        lbl_ayuda.setText("ESCRIBIR CANTIDAD        ENTER: PARA MODIFICAR PRECIO");
    }//GEN-LAST:event_txt_cantidadFocusGained

    private void btn_jd_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_jd_eliminarActionPerformed
        detalle.removeRow(fila_seleccionada);
        calcular_total();
        jd_modificar_item.dispose();
        txt_buscar_producto.requestFocus();
    }//GEN-LAST:event_btn_jd_eliminarActionPerformed

    private void btn_grabarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btn_grabarFocusGained
        lbl_ayuda.setText("ENTER PARA COBRAR VENTA");
    }//GEN-LAST:event_btn_grabarFocusGained

    private void cbx_tipo_docFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbx_tipo_docFocusGained
        lbl_ayuda.setText("ENTER PARA SELECCIONAR CLIENTE");
    }//GEN-LAST:event_cbx_tipo_docFocusGained

    private void txt_nom_clienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_nom_clienteFocusGained
        lbl_ayuda.setText("BUSCAR CLIENTE POR DNI O APELLIDOS, SI NO EXISTE CLIC EN '+' ");
    }//GEN-LAST:event_txt_nom_clienteFocusGained

    private void txt_doc_clienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_doc_clienteFocusGained
        lbl_ayuda.setText("ESCRIBIR NUMERO DE RUC PARA BUSCAR CLIENTE, SI NO EXISTE CLIC EN '+'");
    }//GEN-LAST:event_txt_doc_clienteFocusGained

    private void txt_fechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fechaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_fecha.getText().length() == 10) {
                txt_nom_cliente.setEnabled(true);
                txt_nom_cliente.requestFocus();
                jButton1.setEnabled(true);
                selecion_dedocumento();
                //cargar_clientes(WIDTH);
            }
        }
    }//GEN-LAST:event_txt_fechaKeyPressed

    private void txt_precio_totalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_precio_totalKeyPressed

    }//GEN-LAST:event_txt_precio_totalKeyPressed

    private void txt_precio_totalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_precio_totalFocusGained
        lbl_ayuda.setText("MODIFICAR CANTIDAD        ENTER: PARA GUARDAR EN DETALLE");
    }//GEN-LAST:event_txt_precio_totalFocusGained

    private void cbx_tipo_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_tipo_ventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_tipo_ventaActionPerformed

    private void cbx_tipo_docActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_tipo_docActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_tipo_docActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Frame f = JOptionPane.getRootFrame();
        frm_reg_cliente.accion = "registrar";
        frm_reg_cliente.origen = "reg_venta";
        frm_reg_cliente.c_cliente.setCodigo(0);
        frm_reg_cliente dialog = new frm_reg_cliente(f, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_doc_clienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_doc_clienteKeyTyped
        c_varios.solo_numeros(evt);
    }//GEN-LAST:event_txt_doc_clienteKeyTyped

    private void txt_precioUnitarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_precioUnitarioKeyPressed
        if (KeyEvent.VK_ENTER == evt.getKeyCode()) {
            if (txt_precioUnitario.getText().length() > 0) {
                //txt_cantidad.setEnabled(true);
                //txt_cantidad.requestFocus();
                boton_agregar.setEnabled(true);
                boton_agregar.requestFocus();
                txt_cantidad.selectAll();
            }
        }
    }//GEN-LAST:event_txt_precioUnitarioKeyPressed

    private void txt_precioUnitarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_precioUnitarioKeyTyped
        c_varios.solo_precio(evt);
    }//GEN-LAST:event_txt_precioUnitarioKeyTyped

    private void boton_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_agregarActionPerformed
        boolean error = false;
        double dcantidad = Double.parseDouble(txt_cantidad.getText());
        double precio = Double.parseDouble(txt_precioUnitario.getText());

        double dsubtotal = precio * dcantidad;

        //validar cantidad
        String tcantidad = txt_cantidad.getText();
        if (!c_varios.esDecimal(tcantidad)) {
            error = true;
        } else {
            if (dcantidad <= 0) {
                JOptionPane.showMessageDialog(null, "NO PUEDE SER CERO (0)");
                error = true;
            } else {
                //dcantidad = dcantidad * precio;
            }
        }

        double cactual = Double.parseDouble(txt_cant_actual.getText());
        /*
                if (cactual <= 0) {
                JOptionPane.showMessageDialog(null, "ERROR NO HAY STOCK PARA ESTE PRODUCTO");
                error = true;
                }
         */

        //formar objeto y agregar para tabla
        if (!error) {
            Object fila[] = new Object[5];
            fila[0] = c_producto.getId();
            fila[1] = c_producto.getDescripcion() + " | " + c_producto.getMarca();
            fila[2] = dcantidad;
            fila[3] = c_varios.formato_numero(precio);
            fila[4] = c_varios.formato_numero(dsubtotal);

            detalle.addRow(fila);
            calcular_total();
            limpiar_buscar();
        } else {
            JOptionPane.showMessageDialog(null, "ERROR CON EL PRECIO O LA CANTIDAD");
        }
        limpiar_buscar();
    }//GEN-LAST:event_boton_agregarActionPerformed

    private void txt_precioUnitarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_precioUnitarioFocusGained
        lbl_ayuda.setText("MODIFICAR PRECIO        ENTER: PARA MODIFICAR CANTIDAD");
    }//GEN-LAST:event_txt_precioUnitarioFocusGained

    private void boton_agregarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_boton_agregarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boton_agregar.doClick();
        }
    }//GEN-LAST:event_boton_agregarKeyPressed

    private void btn_grabarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_grabarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btn_grabar.doClick();
        }
    }//GEN-LAST:event_btn_grabarKeyPressed

    private void btn_pagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_pagoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btn_pago.doClick();
        }
    }//GEN-LAST:event_btn_pagoKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton_agregar;
    private javax.swing.JButton btn_grabar;
    private javax.swing.JButton btn_jd_actualizar;
    private javax.swing.JButton btn_jd_eliminar;
    private javax.swing.JButton btn_pago;
    private javax.swing.JComboBox<String> cbx_tipo_doc;
    private javax.swing.JComboBox<String> cbx_tipo_venta;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JDialog jd_fin_venta;
    private javax.swing.JDialog jd_modificar_item;
    private javax.swing.JLabel lab_tatal_deuda;
    private javax.swing.JLabel lbl_ayuda;
    private javax.swing.JLabel lbl_pago_venta;
    private javax.swing.JLabel lbl_total_venta;
    private javax.swing.JTable t_detalle;
    private javax.swing.JTextField text_deudatotal;
    private javax.swing.JTextField txt_buscar_producto;
    private javax.swing.JTextField txt_cant_actual;
    private javax.swing.JTextField txt_cantidad;
    public static javax.swing.JTextField txt_doc_cliente;
    private javax.swing.JFormattedTextField txt_fecha;
    private javax.swing.JTextField txt_j_efectivo;
    private javax.swing.JTextField txt_j_faltante;
    private javax.swing.JTextField txt_j_por_pagar;
    private javax.swing.JTextField txt_jd_cantidad;
    private javax.swing.JTextField txt_jd_descripcion;
    private javax.swing.JTextField txt_jd_idproducto;
    private javax.swing.JTextField txt_jd_precio;
    public static javax.swing.JTextField txt_nom_cliente;
    private javax.swing.JTextField txt_numero;
    private javax.swing.JTextField txt_precioUnitario;
    private javax.swing.JTextField txt_precio_total;
    private javax.swing.JTextField txt_serie;
    // End of variables declaration//GEN-END:variables
}
