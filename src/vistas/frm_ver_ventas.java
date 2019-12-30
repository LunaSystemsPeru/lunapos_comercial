/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import clases.cl_cliente;
import clases.cl_cobros_ventas;
import clases.cl_conectar;
import clases.cl_documento_firmado;
import clases.cl_documento_almacen;
import clases.cl_productos_ventas;
import clases.cl_varios;
import clases.cl_venta;
import clases.cl_venta_eliminada;
import clases_autocomplete.cla_mis_documentos;
import clases_varios.Configuracion;
import clases_varios.leer_numeros;
import forms.frm_mod_separacion;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;
import json.cl_envio_server;
import json.cl_json_entidad;
import models.m_mis_documentos;
import org.json.simple.parser.ParseException;
import comercial.frm_principal;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author luis
 */
public class frm_ver_ventas extends javax.swing.JInternalFrame {
    
    cl_varios c_varios = new cl_varios();
    cl_venta c_venta = new cl_venta();
    cl_venta c_separacion = new cl_venta();
    cl_documento_firmado c_hash = new cl_documento_firmado();
    
    cl_venta_eliminada c_cupon = new cl_venta_eliminada();
    cl_productos_ventas c_detalle = new cl_productos_ventas();
    cl_cobros_ventas c_cobros = new cl_cobros_ventas();
    cl_cliente c_cliente = new cl_cliente();
    
    int id_almacen = frm_principal.c_almacen.getId();
    int id_usuario = frm_principal.c_usuario.getId_usuario();
    
    int fila_seleccionada = -1;
    int fila_cobro = -1;
    
    String query = "";

    /**
     * Creates new form frm_ver_ventas
     *
     * @param grphcs
     */
    @Override
    protected void paintComponent(Graphics grphcs) {
        grphcs.setColor(Color.decode("#00b0f0"));
        grphcs.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
    
    public frm_ver_ventas() {
        initComponents();
        this.getContentPane().setBackground(Configuracion.COLOR_FORMULARIO_1);
        query = "select v.id_ventas, v.fecha, c.documento, c.nombre, ds.abreviado, v.serie, v.numero, v.total, v.pagado, u.username, v.estado, v.tipo_venta "
                + "from ventas as v "
                + "inner join clientes as c on c.id_cliente = v.id_cliente "
                + "inner join documentos_sunat as ds on ds.id_tido = v.id_tido "
                + "inner join usuarios as u on u.id_usuarios = v.id_usuarios "
                + "where v.fecha = current_date() and v.id_almacen = '" + id_almacen + "' "
                + "order by v.id_ventas asc";
        c_venta.mostrar(t_ventas, query);
        
        sumar_totales();
    }
    
    private void sumar_totales() {
        double total_ventas = 0;
        double total_pagados = 0;
        
        int contar_filar = t_ventas.getRowCount();
        
        for (int i = 0; i < contar_filar; i++) {
            total_ventas = total_ventas + Double.parseDouble(t_ventas.getValueAt(i, 4).toString());
            total_pagados = total_pagados + Double.parseDouble(t_ventas.getValueAt(i, 5).toString());
        }
        
        txt_total_ventas.setText(c_varios.formato_totales(total_ventas));
        txt_total_pagos.setText(c_varios.formato_totales(total_pagados));
    }
    
    private void activar_botones() {
        String estado = t_ventas.getValueAt(fila_seleccionada, 7).toString();
        if (estado.equals("ANULADO")) {
            btn_ver_cupon.setEnabled(true);
            btn_ver_detalle.setEnabled(false);
            btn_ver_cobros.setEnabled(false);
            btn_entregar_productos.setEnabled(false);
            btn_anular_venta.setEnabled(false);
            btn_imprimir.setEnabled(false);
        } else {
            btn_ver_cupon.setEnabled(false);
            btn_ver_detalle.setEnabled(true);
            btn_ver_cobros.setEnabled(true);
            if (estado.equals("POR ENTREGAR")) {
                btn_entregar_productos.setEnabled(true);
            } else {
                btn_entregar_productos.setEnabled(false);
            }
            if (estado.equals("SEPARADO")) {
                btn_mod_separacion.setEnabled(true);
            } else {
                btn_mod_separacion.setEnabled(false);
            }
            btn_anular_venta.setEnabled(true);
            btn_imprimir.setEnabled(true);
        }
    }
    
    private void desactivar_botones() {
        btn_ver_cupon.setEnabled(false);
        btn_ver_detalle.setEnabled(false);
        btn_ver_cobros.setEnabled(false);
        btn_entregar_productos.setEnabled(false);
        btn_anular_venta.setEnabled(false);
        btn_imprimir.setEnabled(false);
        btn_mod_separacion.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jd_anular_documento = new javax.swing.JDialog();
        jLabel5 = new javax.swing.JLabel();
        txt_jd_motivo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jToolBar2 = new javax.swing.JToolBar();
        btn_jd_grabar = new javax.swing.JButton();
        btn_jd_salir = new javax.swing.JButton();
        txt_jd_total = new javax.swing.JTextField();
        txt_jd_fecha = new javax.swing.JTextField();
        lbl_pagado = new javax.swing.JLabel();
        txt_jd_pagado = new javax.swing.JTextField();
        jd_ver_productos = new javax.swing.JDialog();
        jLabel8 = new javax.swing.JLabel();
        txt_detalle = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        t_detalle = new javax.swing.JTable();
        jd_ver_cobros = new javax.swing.JDialog();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        t_cobros = new javax.swing.JTable();
        jToolBar3 = new javax.swing.JToolBar();
        btn_jd_cobro_graba = new javax.swing.JButton();
        btn_eliminar_cobro = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txt_jd_cobro_documento = new javax.swing.JTextField();
        txt_jd_cobro_cliente = new javax.swing.JTextField();
        jd_reg_cobro = new javax.swing.JDialog();
        jToolBar4 = new javax.swing.JToolBar();
        btn_cobro_guardar = new javax.swing.JButton();
        btn_cobro_salir = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_pago_monto = new javax.swing.JTextField();
        txt_pago_fecha = new javax.swing.JFormattedTextField();
        jd_entrega_separacion = new javax.swing.JDialog();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cbx_doc_venta = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        txt_doc_venta = new javax.swing.JTextField();
        txt_datos_venta = new javax.swing.JTextField();
        txt_cliente_separacion = new javax.swing.JTextField();
        txt_fecha_separacion = new javax.swing.JTextField();
        btn_grabar_venta = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        txt_doc_separacion = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txt_total_separacion = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        txt_buscar = new javax.swing.JTextField();
        cbx_buscar = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_ventas = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txt_total_ventas = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_total_pagos = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        btn_ver_detalle = new javax.swing.JButton();
        btn_ver_cobros = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btn_mod_separacion = new javax.swing.JButton();
        btn_entregar_productos = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btn_imprimir = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btn_anular_venta = new javax.swing.JButton();
        btn_ver_cupon = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton6 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();

        jd_anular_documento.setTitle("Eliminar Venta");

        jLabel5.setText("Motivo:");

        txt_jd_motivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_jd_motivoKeyPressed(evt);
            }
        });

        jLabel6.setText("Fecha:");

        jLabel7.setText("Monto:");

        jToolBar2.setFloatable(false);
        jToolBar2.setOpaque(false);

        btn_jd_grabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/accept.png"))); // NOI18N
        btn_jd_grabar.setText("Guardar");
        btn_jd_grabar.setEnabled(false);
        btn_jd_grabar.setFocusable(false);
        btn_jd_grabar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_jd_grabar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_jd_grabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_jd_grabarActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_jd_grabar);

        btn_jd_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cross.png"))); // NOI18N
        btn_jd_salir.setText("Salir");
        btn_jd_salir.setFocusable(false);
        btn_jd_salir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_jd_salir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_jd_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_jd_salirActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_jd_salir);

        txt_jd_total.setEditable(false);
        txt_jd_total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_jd_total.setText("250.00");

        txt_jd_fecha.setEditable(false);
        txt_jd_fecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_jd_fecha.setText("26/10/2018");

        lbl_pagado.setText("Pagado:");

        txt_jd_pagado.setEditable(false);
        txt_jd_pagado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_jd_pagado.setText("200.00");

        javax.swing.GroupLayout jd_anular_documentoLayout = new javax.swing.GroupLayout(jd_anular_documento.getContentPane());
        jd_anular_documento.getContentPane().setLayout(jd_anular_documentoLayout);
        jd_anular_documentoLayout.setHorizontalGroup(
            jd_anular_documentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jd_anular_documentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jd_anular_documentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jd_anular_documentoLayout.createSequentialGroup()
                        .addGroup(jd_anular_documentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jd_anular_documentoLayout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_jd_total, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jd_anular_documentoLayout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_jd_fecha)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_pagado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_jd_pagado, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 252, Short.MAX_VALUE))
                    .addGroup(jd_anular_documentoLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_jd_motivo)))
                .addContainerGap())
        );
        jd_anular_documentoLayout.setVerticalGroup(
            jd_anular_documentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_anular_documentoLayout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_anular_documentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_jd_motivo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_anular_documentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_jd_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_anular_documentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_jd_total, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_pagado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_jd_pagado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jd_ver_productos.setTitle("Ver Detalle de Venta");

        jLabel8.setText("Venta:");

        txt_detalle.setEditable(false);

        t_detalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(t_detalle);

        javax.swing.GroupLayout jd_ver_productosLayout = new javax.swing.GroupLayout(jd_ver_productos.getContentPane());
        jd_ver_productos.getContentPane().setLayout(jd_ver_productosLayout);
        jd_ver_productosLayout.setHorizontalGroup(
            jd_ver_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_ver_productosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jd_ver_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
                    .addGroup(jd_ver_productosLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_detalle)))
                .addContainerGap())
        );
        jd_ver_productosLayout.setVerticalGroup(
            jd_ver_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_ver_productosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jd_ver_productosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_detalle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                .addContainerGap())
        );

        jd_ver_cobros.setTitle("Ver Cobros de Venta");

        jLabel9.setText("Doc. Venta:");

        jLabel10.setText("Cliente:");

        t_cobros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        t_cobros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_cobrosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(t_cobros);

        jToolBar3.setFloatable(false);
        jToolBar3.setOpaque(false);

        btn_jd_cobro_graba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        btn_jd_cobro_graba.setText("Agregar");
        btn_jd_cobro_graba.setFocusable(false);
        btn_jd_cobro_graba.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_jd_cobro_graba.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_jd_cobro_graba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_jd_cobro_grabaActionPerformed(evt);
            }
        });
        jToolBar3.add(btn_jd_cobro_graba);

        btn_eliminar_cobro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete.png"))); // NOI18N
        btn_eliminar_cobro.setText("Eliminar");
        btn_eliminar_cobro.setEnabled(false);
        btn_eliminar_cobro.setFocusable(false);
        btn_eliminar_cobro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_eliminar_cobro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_eliminar_cobro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_cobroActionPerformed(evt);
            }
        });
        jToolBar3.add(btn_eliminar_cobro);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cross.png"))); // NOI18N
        jButton3.setText("Salir");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar3.add(jButton3);

        txt_jd_cobro_documento.setEditable(false);

        txt_jd_cobro_cliente.setEditable(false);

        javax.swing.GroupLayout jd_ver_cobrosLayout = new javax.swing.GroupLayout(jd_ver_cobros.getContentPane());
        jd_ver_cobros.getContentPane().setLayout(jd_ver_cobrosLayout);
        jd_ver_cobrosLayout.setHorizontalGroup(
            jd_ver_cobrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_ver_cobrosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jd_ver_cobrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jd_ver_cobrosLayout.createSequentialGroup()
                        .addGroup(jd_ver_cobrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(29, 29, 29)
                        .addGroup(jd_ver_cobrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_jd_cobro_cliente)
                            .addComponent(txt_jd_cobro_documento))))
                .addContainerGap())
            .addComponent(jToolBar3, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
        );
        jd_ver_cobrosLayout.setVerticalGroup(
            jd_ver_cobrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_ver_cobrosLayout.createSequentialGroup()
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_ver_cobrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_jd_cobro_documento, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_ver_cobrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_jd_cobro_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addContainerGap())
        );

        jd_reg_cobro.setResizable(false);

        jToolBar4.setFloatable(false);
        jToolBar4.setOpaque(false);

        btn_cobro_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        btn_cobro_guardar.setText("Agregar");
        btn_cobro_guardar.setEnabled(false);
        btn_cobro_guardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_cobro_guardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_cobro_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cobro_guardarActionPerformed(evt);
            }
        });
        jToolBar4.add(btn_cobro_guardar);

        btn_cobro_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cross.png"))); // NOI18N
        btn_cobro_salir.setText("Salir");
        btn_cobro_salir.setFocusable(false);
        btn_cobro_salir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_cobro_salir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_cobro_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cobro_salirActionPerformed(evt);
            }
        });
        jToolBar4.add(btn_cobro_salir);

        jLabel11.setText("Fecha:");

        jLabel12.setText("Monto:");

        txt_pago_monto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_pago_monto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_pago_montoKeyPressed(evt);
            }
        });

        try {
            txt_pago_fecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_pago_fecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_pago_fecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_pago_fechaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jd_reg_cobroLayout = new javax.swing.GroupLayout(jd_reg_cobro.getContentPane());
        jd_reg_cobro.getContentPane().setLayout(jd_reg_cobroLayout);
        jd_reg_cobroLayout.setHorizontalGroup(
            jd_reg_cobroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar4, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
            .addGroup(jd_reg_cobroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jd_reg_cobroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(54, 54, 54)
                .addGroup(jd_reg_cobroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_pago_monto, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                    .addComponent(txt_pago_fecha))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jd_reg_cobroLayout.setVerticalGroup(
            jd_reg_cobroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_reg_cobroLayout.createSequentialGroup()
                .addComponent(jToolBar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jd_reg_cobroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_pago_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_reg_cobroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_pago_monto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jd_entrega_separacion.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jd_entrega_separacion.setTitle("Entregar Separacion");

        jLabel13.setText("Fecha Separacion:");

        jLabel14.setText("Cliente:");

        jLabel15.setText("Documento:");

        cbx_doc_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_doc_ventaActionPerformed(evt);
            }
        });
        cbx_doc_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbx_doc_ventaKeyPressed(evt);
            }
        });

        jLabel16.setText("Datos Documento:");

        txt_doc_venta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_doc_venta.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txt_doc_ventaInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txt_doc_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_doc_ventaKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_doc_ventaKeyPressed(evt);
            }
        });

        txt_datos_venta.setEditable(false);
        txt_datos_venta.setBackground(new java.awt.Color(255, 255, 255));

        txt_cliente_separacion.setEditable(false);
        txt_cliente_separacion.setBackground(new java.awt.Color(255, 255, 255));

        txt_fecha_separacion.setEditable(false);
        txt_fecha_separacion.setBackground(new java.awt.Color(255, 255, 255));
        txt_fecha_separacion.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btn_grabar_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/accept.png"))); // NOI18N
        btn_grabar_venta.setText("Entregar");
        btn_grabar_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_grabar_ventaActionPerformed(evt);
            }
        });

        jLabel17.setText("Nota de Separacion:");

        txt_doc_separacion.setEditable(false);
        txt_doc_separacion.setBackground(new java.awt.Color(255, 255, 255));
        txt_doc_separacion.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel18.setText("Total Separacion:");

        txt_total_separacion.setEditable(false);
        txt_total_separacion.setBackground(new java.awt.Color(255, 255, 255));
        txt_total_separacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jd_entrega_separacionLayout = new javax.swing.GroupLayout(jd_entrega_separacion.getContentPane());
        jd_entrega_separacion.getContentPane().setLayout(jd_entrega_separacionLayout);
        jd_entrega_separacionLayout.setHorizontalGroup(
            jd_entrega_separacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_entrega_separacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jd_entrega_separacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_datos_venta)
                    .addGroup(jd_entrega_separacionLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(30, 30, 30)
                        .addComponent(txt_doc_venta))
                    .addGroup(jd_entrega_separacionLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(29, 29, 29)
                        .addComponent(cbx_doc_venta, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jd_entrega_separacionLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_cliente_separacion)
                        .addGap(1, 1, 1))
                    .addGroup(jd_entrega_separacionLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_fecha_separacion, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jd_entrega_separacionLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_grabar_venta))
                    .addGroup(jd_entrega_separacionLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(txt_doc_separacion, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jd_entrega_separacionLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_total_separacion, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jd_entrega_separacionLayout.setVerticalGroup(
            jd_entrega_separacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_entrega_separacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jd_entrega_separacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_fecha_separacion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_entrega_separacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_doc_separacion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_entrega_separacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_cliente_separacion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_entrega_separacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_total_separacion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jd_entrega_separacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_doc_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jd_entrega_separacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_doc_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_datos_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_grabar_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setTitle("Ver Ventas ");

        jLabel1.setText("Buscar por");

        txt_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_buscarKeyPressed(evt);
            }
        });

        cbx_buscar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FECHA", "CLIENTE", "NRO. DOCUMENTO" }));
        cbx_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_buscarActionPerformed(evt);
            }
        });
        cbx_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbx_buscarKeyPressed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Separaciones", "Creditos", "Por Entregar", "Entregados", "Anulados" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Filtrar por:");

        t_ventas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"2018100001", "2018-10-24", "NS | 0001 - 000001", "SD46598 | LUIS ENRIQUE OYANGUREN GIRON", "268.00", "25.00", "loyangureng", "SEPARACION"},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Fecha", "Documento", "Cliente", "Total", "Pagado", "Vendedor", "Estado"
            }
        ));
        t_ventas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        t_ventas.setShowVerticalLines(false);
        t_ventas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_ventasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(t_ventas);
        if (t_ventas.getColumnModel().getColumnCount() > 0) {
            t_ventas.getColumnModel().getColumn(0).setPreferredWidth(100);
            t_ventas.getColumnModel().getColumn(1).setPreferredWidth(90);
            t_ventas.getColumnModel().getColumn(2).setPreferredWidth(150);
            t_ventas.getColumnModel().getColumn(3).setPreferredWidth(350);
            t_ventas.getColumnModel().getColumn(4).setPreferredWidth(80);
            t_ventas.getColumnModel().getColumn(5).setPreferredWidth(80);
            t_ventas.getColumnModel().getColumn(6).setPreferredWidth(100);
            t_ventas.getColumnModel().getColumn(7).setPreferredWidth(100);
        }

        jLabel3.setText("Suma Total:");

        txt_total_ventas.setEditable(false);
        txt_total_ventas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_total_ventas.setText("0.00");

        jLabel4.setText("Suma Pagado:");

        txt_total_pagos.setEditable(false);
        txt_total_pagos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_total_pagos.setText("0.00");

        jToolBar1.setFloatable(false);
        jToolBar1.setOpaque(false);

        btn_ver_detalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/clipboard_text.png"))); // NOI18N
        btn_ver_detalle.setText("ver Productos");
        btn_ver_detalle.setEnabled(false);
        btn_ver_detalle.setFocusable(false);
        btn_ver_detalle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_ver_detalle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_ver_detalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ver_detalleActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_ver_detalle);

        btn_ver_cobros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/coins.png"))); // NOI18N
        btn_ver_cobros.setText("Ver cobros");
        btn_ver_cobros.setEnabled(false);
        btn_ver_cobros.setFocusable(false);
        btn_ver_cobros.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_ver_cobros.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_ver_cobros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ver_cobrosActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_ver_cobros);
        jToolBar1.add(jSeparator5);

        btn_mod_separacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/application_edit.png"))); // NOI18N
        btn_mod_separacion.setText("Cambiar Productos");
        btn_mod_separacion.setEnabled(false);
        btn_mod_separacion.setFocusable(false);
        btn_mod_separacion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_mod_separacion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_mod_separacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_mod_separacionActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_mod_separacion);

        btn_entregar_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/compra_producto.png"))); // NOI18N
        btn_entregar_productos.setText("Entregar Productos");
        btn_entregar_productos.setEnabled(false);
        btn_entregar_productos.setFocusable(false);
        btn_entregar_productos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_entregar_productos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_entregar_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_entregar_productosActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_entregar_productos);
        jToolBar1.add(jSeparator2);

        btn_imprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/printer.png"))); // NOI18N
        btn_imprimir.setText("Imprimir");
        btn_imprimir.setEnabled(false);
        btn_imprimir.setFocusable(false);
        btn_imprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_imprimir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_imprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimirActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_imprimir);
        jToolBar1.add(jSeparator3);

        btn_anular_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete.png"))); // NOI18N
        btn_anular_venta.setText("Anular Venta");
        btn_anular_venta.setEnabled(false);
        btn_anular_venta.setFocusable(false);
        btn_anular_venta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_anular_venta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_anular_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_anular_ventaActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_anular_venta);

        btn_ver_cupon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/comment.png"))); // NOI18N
        btn_ver_cupon.setText("Ver Anulacion");
        btn_ver_cupon.setEnabled(false);
        btn_ver_cupon.setFocusable(false);
        btn_ver_cupon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_ver_cupon.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_ver_cupon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ver_cuponActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_ver_cupon);
        jToolBar1.add(jSeparator1);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cross.png"))); // NOI18N
        jButton6.setText("Salir");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        jLabel19.setText("Enter para Buscar             Escape para Limpiar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1038, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_total_ventas, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_total_pagos, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbx_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_total_ventas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_total_pagos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void t_ventasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_ventasMouseClicked
        int contar_filas = t_ventas.getRowCount();
        if (contar_filas > 0) {
            fila_seleccionada = t_ventas.getSelectedRow();
            activar_botones();
        }
    }//GEN-LAST:event_t_ventasMouseClicked

    private void btn_anular_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_anular_ventaActionPerformed
        frm_principal.c_permiso.setId_permiso(2);
        boolean permitido = frm_principal.c_permiso.validar();
        
        if (permitido) {
            if (fila_seleccionada > -1) {
                desactivar_botones();
                
                double total = Double.parseDouble(t_ventas.getValueAt(fila_seleccionada, 4).toString());
                double pagado = Double.parseDouble(t_ventas.getValueAt(fila_seleccionada, 5).toString());
                jd_anular_documento.setModal(true);
                jd_anular_documento.setSize(691, 249);
                jd_anular_documento.setLocationRelativeTo(null);
                txt_jd_total.setText(c_varios.formato_numero(total));
                txt_jd_pagado.setText(c_varios.formato_numero(pagado));
                txt_jd_fecha.setText(c_varios.fecha_usuario(c_varios.getFechaActual()));
                
                txt_jd_motivo.requestFocus();
                jd_anular_documento.setVisible(true);
                
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no tiene permiso para realizar esta operacion!!");
        }
    }//GEN-LAST:event_btn_anular_ventaActionPerformed

    private void btn_jd_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_jd_salirActionPerformed
        jd_anular_documento.dispose();
    }//GEN-LAST:event_btn_jd_salirActionPerformed

    private void btn_jd_grabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_jd_grabarActionPerformed
        //cargar datos
        int id_venta = Integer.parseInt(t_ventas.getValueAt(fila_seleccionada, 8).toString());
        double monto_cupon = Double.parseDouble(t_ventas.getValueAt(fila_seleccionada, 5).toString());

        //eliminar productos
        c_detalle.setId_venta(id_venta);
        c_detalle.eliminar();

        //anular venta
        c_venta.setId_venta(id_venta);
        c_venta.setId_almacen(id_almacen);

        //eliminar cobros
        c_venta.validar_venta();
        if (c_venta.getFecha().equals(c_varios.getFechaActual())) {
            c_cobros.setId_venta(id_venta);
            c_cobros.eliminar();
        } else {
            //crear cupon
            c_cupon.setId_venta(id_venta);
            c_cupon.setId_usuario(id_usuario);
            c_cupon.setFecha(c_varios.getFechaActual());
            c_cupon.setEstado(1);
            c_cupon.setMotivo(txt_jd_motivo.getText().toUpperCase().trim());
            c_cupon.setMonto(monto_cupon);
            c_cupon.setUsado(0);
            
            c_cupon.registrar();
            
        }
        
        c_venta.anular();
        
        jd_anular_documento.dispose();
        c_venta.mostrar(t_ventas, query);
        //si es venta dar de baja
        //si es separacion
    }//GEN-LAST:event_btn_jd_grabarActionPerformed

    private void txt_jd_motivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jd_motivoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String motivo = txt_jd_motivo.getText();
            if (motivo.length() > 5) {
                btn_jd_grabar.setEnabled(true);
                btn_jd_grabar.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_jd_motivoKeyPressed

    private void btn_ver_cuponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ver_cuponActionPerformed
        if (fila_seleccionada > -1) {
            desactivar_botones();

            //cargar datos
            int id_venta = Integer.parseInt(t_ventas.getValueAt(fila_seleccionada, 8).toString());
            double monto_cupon = Double.parseDouble(t_ventas.getValueAt(fila_seleccionada, 5).toString());
            
            c_cupon.setId_venta(id_venta);
            c_cupon.validar_cupon();
            
            jd_anular_documento.setModal(true);
            jd_anular_documento.setSize(691, 249);
            jd_anular_documento.setLocationRelativeTo(null);
            txt_jd_total.setText(c_varios.formato_numero(c_cupon.getMonto()));
            txt_jd_pagado.setText(c_varios.formato_numero(c_cupon.getUsado()));
            txt_jd_fecha.setText(c_varios.fecha_usuario(c_cupon.getFecha()));
            txt_jd_motivo.setText(c_cupon.getMotivo());
            lbl_pagado.setText("Monto Usado:");
            txt_jd_motivo.setEditable(false);
            btn_jd_grabar.setEnabled(false);
            jd_anular_documento.setVisible(true);
            
        }
    }//GEN-LAST:event_btn_ver_cuponActionPerformed

    private void btn_ver_detalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ver_detalleActionPerformed
        if (fila_seleccionada > -1) {
            desactivar_botones();
            //cargar datos
            int id_venta = Integer.parseInt(t_ventas.getValueAt(fila_seleccionada, 8).toString());
            
            String venta = t_ventas.getValueAt(fila_seleccionada, 2).toString() + " | " + t_ventas.getValueAt(fila_seleccionada, 3).toString();
            
            c_detalle.setId_venta(id_venta);
            c_detalle.mostrar(t_detalle);
            
            jd_ver_productos.setModal(true);
            jd_ver_productos.setSize(685, 387);
            jd_ver_productos.setLocationRelativeTo(null);
            txt_detalle.setText(venta);
            jd_ver_productos.setVisible(true);
        }
    }//GEN-LAST:event_btn_ver_detalleActionPerformed

    private void btn_ver_cobrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ver_cobrosActionPerformed
        if (fila_seleccionada > -1) {
            desactivar_botones();
            //cargar datos
            int id_venta = Integer.parseInt(t_ventas.getValueAt(fila_seleccionada, 8).toString());
            
            String venta = t_ventas.getValueAt(fila_seleccionada, 2).toString();
            String cliente = t_ventas.getValueAt(fila_seleccionada, 3).toString();
            double total = Double.parseDouble(t_ventas.getValueAt(fila_seleccionada, 4).toString());
            
            c_cobros.setId_venta(id_venta);
            c_cobros.mostrar(t_cobros);
            
            double pagos = 0;
            int contar_filas_cobro = t_cobros.getRowCount();
            System.out.println(contar_filas_cobro);
            for (int i = 0; i < contar_filas_cobro; i++) {
                pagos = pagos + Double.parseDouble(t_cobros.getValueAt(i, 2).toString());
            }
            
            if (pagos == total) {
                btn_jd_cobro_graba.setEnabled(false);
            }
            
            txt_jd_cobro_documento.setText(venta);
            txt_jd_cobro_cliente.setText(cliente);
            
            jd_ver_cobros.setModal(true);
            jd_ver_cobros.setSize(434, 473);
            jd_ver_cobros.setLocationRelativeTo(null);
            
            jd_ver_cobros.setVisible(true);
        }
    }//GEN-LAST:event_btn_ver_cobrosActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jd_ver_cobros.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btn_cobro_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cobro_salirActionPerformed
        jd_reg_cobro.dispose();
    }//GEN-LAST:event_btn_cobro_salirActionPerformed

    private void btn_cobro_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cobro_guardarActionPerformed
        c_cobros.setId_cobro(c_cobros.obtener_codigo());
        c_cobros.setFecha(c_varios.fecha_myql(txt_pago_fecha.getText()));
        double efectivo = Double.parseDouble(txt_pago_monto.getText());
        c_cobros.setMonto(efectivo);
        c_cobros.setTipo_pago(1);
        if (c_cobros.registrar()) {
            jd_reg_cobro.dispose();
            double total = Double.parseDouble(t_ventas.getValueAt(fila_seleccionada, 4).toString());
            c_cobros.mostrar(t_cobros);
            
            double pagos = 0;
            int contar_filas_cobro = t_cobros.getRowCount();
            System.out.println(contar_filas_cobro);
            for (int i = 0; i < contar_filas_cobro; i++) {
                pagos = pagos + Double.parseDouble(t_cobros.getValueAt(i, 2).toString());
            }
            
            if (pagos == total) {
                btn_jd_cobro_graba.setEnabled(false);
            }
        }

    }//GEN-LAST:event_btn_cobro_guardarActionPerformed

    private void txt_pago_montoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pago_montoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String texto = txt_pago_monto.getText();
            if (c_varios.esDecimal(texto)) {
                btn_cobro_guardar.setEnabled(true);
                btn_cobro_guardar.requestFocus();
            } else {
                txt_pago_monto.selectAll();
                txt_pago_monto.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_pago_montoKeyPressed

    private void btn_jd_cobro_grabaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_jd_cobro_grabaActionPerformed
        jd_reg_cobro.setModal(true);
        jd_reg_cobro.setSize(374, 199);
        jd_reg_cobro.setLocationRelativeTo(null);
        txt_pago_fecha.setText(c_varios.fecha_usuario(c_varios.getFechaActual()));
        txt_pago_fecha.setEnabled(true);
        txt_pago_fecha.requestFocus();
        jd_reg_cobro.setVisible(true);
    }//GEN-LAST:event_btn_jd_cobro_grabaActionPerformed

    private void cbx_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_buscarActionPerformed
        txt_buscar.requestFocus();
    }//GEN-LAST:event_cbx_buscarActionPerformed

    private void txt_buscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String texto = txt_buscar.getText();
            if (texto.length() > 0) {
                int tipo_buscar = cbx_buscar.getSelectedIndex();
                if (tipo_buscar == 0) {
                    texto = c_varios.fecha_myql(texto);
                    query = "select v.id_ventas, v.fecha, c.documento, c.nombre, ds.abreviado, v.serie, v.numero, v.total, v.pagado, u.username, v.estado, v.tipo_venta "
                            + "from ventas as v "
                            + "inner join clientes as c on c.id_cliente = v.id_cliente "
                            + "inner join documentos_sunat as ds on ds.id_tido = v.id_tido "
                            + "inner join usuarios as u on u.id_usuarios = v.id_usuarios "
                            + "where v.fecha = '" + texto + "' and v.id_almacen = '" + id_almacen + "' "
                            + "order by v.id_ventas asc";
                    System.out.println(query);
                }
                if (tipo_buscar == 1) {
                    query = "select v.id_ventas, v.fecha, c.documento, c.nombre, ds.abreviado, v.serie, v.numero, v.total, v.pagado, u.username, v.estado, v.tipo_venta "
                            + "from ventas as v "
                            + "inner join clientes as c on c.id_cliente = v.id_cliente "
                            + "inner join documentos_sunat as ds on ds.id_tido = v.id_tido "
                            + "inner join usuarios as u on u.id_usuarios = v.id_usuarios "
                            + "where (c.nombre like '%" + texto + "%' or c.documento = '" + texto + "') and v.id_almacen = '" + id_almacen + "' "
                            + "order by v.id_ventas asc";
                }
                if (tipo_buscar == 2) {
                    texto = c_varios.fecha_myql(texto);
                    query = "select v.id_ventas, v.fecha, c.documento, c.nombre, ds.abreviado, v.serie, v.numero, v.total, v.pagado, u.username, v.estado, v.tipo_venta "
                            + "from ventas as v "
                            + "inner join clientes as c on c.id_cliente = v.id_cliente "
                            + "inner join documentos_sunat as ds on ds.id_tido = v.id_tido "
                            + "inner join usuarios as u on u.id_usuarios = v.id_usuarios "
                            + "where v.numero = '" + texto + "' and v.id_almacen = '" + id_almacen + "' "
                            + "order by v.id_ventas asc";
                    System.out.println(query);
                }
                c_venta.mostrar(t_ventas, query);
                sumar_totales();
            } else {
                JOptionPane.showMessageDialog(null, "INGRESE TEXTO PARA BUSCAR, LUEGO PRESIONE ENTER");
            }
        }
    }//GEN-LAST:event_txt_buscarKeyPressed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        int tipo_muestra = jComboBox2.getSelectedIndex();
        if (tipo_muestra == 0) {
            query = "select v.id_ventas, v.fecha, c.documento, c.nombre, ds.abreviado, v.serie, v.numero, v.total, v.pagado, u.username, v.estado, v.tipo_venta "
                    + "from ventas as v "
                    + "inner join clientes as c on c.id_cliente = v.id_cliente "
                    + "inner join documentos_sunat as ds on ds.id_tido = v.id_tido "
                    + "inner join usuarios as u on u.id_usuarios = v.id_usuarios "
                    + "where v.id_almacen = '" + id_almacen + "' and v.tipo_venta = 2 and v.estado = 2 "
                    + "order by v.id_ventas, v.estado asc";
        }
        if (tipo_muestra == 1) {
            query = "select v.id_ventas, v.fecha, c.documento, c.nombre, ds.abreviado, v.serie, v.numero, v.total, v.pagado, u.username, v.estado, v.tipo_venta "
                    + "from ventas as v "
                    + "inner join clientes as c on c.id_cliente = v.id_cliente "
                    + "inner join documentos_sunat as ds on ds.id_tido = v.id_tido "
                    + "inner join usuarios as u on u.id_usuarios = v.id_usuarios "
                    + "where v.id_almacen = '" + id_almacen + "' and v.tipo_venta = 1 and v.estado = 2 "
                    + "order by v.id_ventas asc";
        }
        if (tipo_muestra == 2) {
            query = "select v.id_ventas, v.fecha, c.documento, c.nombre, ds.abreviado, v.serie, v.numero, v.total, v.pagado, u.username, v.estado, v.tipo_venta "
                    + "from ventas as v "
                    + "inner join clientes as c on c.id_cliente = v.id_cliente "
                    + "inner join documentos_sunat as ds on ds.id_tido = v.id_tido "
                    + "inner join usuarios as u on u.id_usuarios = v.id_usuarios "
                    + "where v.id_almacen = '" + id_almacen + "' and v.tipo_venta = 2 and v.estado = 1 "
                    + "order by v.id_ventas asc";
        }
        if (tipo_muestra == 3) {
            query = "select v.id_ventas, v.fecha, c.documento, c.nombre, ds.abreviado, v.serie, v.numero, v.total, v.pagado, u.username, v.estado, v.tipo_venta "
                    + "from ventas as v "
                    + "inner join clientes as c on c.id_cliente = v.id_cliente "
                    + "inner join documentos_sunat as ds on ds.id_tido = v.id_tido "
                    + "inner join usuarios as u on u.id_usuarios = v.id_usuarios "
                    + "where v.id_almacen = '" + id_almacen + "' and v.tipo_venta = 2 and v.estado = 4 "
                    + "order by v.id_ventas asc";
        }
        c_venta.mostrar(t_ventas, query);
        
        sumar_totales();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void btn_entregar_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_entregar_productosActionPerformed
        if (fila_seleccionada > -1) {
            desactivar_botones();
            //cargar datos
            int id_venta = Integer.parseInt(t_ventas.getValueAt(fila_seleccionada, 8).toString());
            c_separacion.setId_venta(id_venta);
            c_separacion.setId_almacen(id_almacen);
            c_separacion.validar_venta();

            //llenar documentos sunat
            m_mis_documentos m_documentos = new m_mis_documentos();
            m_documentos.cbx_documentos_venta(cbx_doc_venta);
            
            String venta = t_ventas.getValueAt(fila_seleccionada, 2).toString();
            String fecha = t_ventas.getValueAt(fila_seleccionada, 1).toString();
            String cliente = t_ventas.getValueAt(fila_seleccionada, 3).toString();
            double total = Double.parseDouble(t_ventas.getValueAt(fila_seleccionada, 4).toString());
            
            jd_entrega_separacion.setModal(true);
            jd_entrega_separacion.setSize(400, 383);
            jd_entrega_separacion.setLocationRelativeTo(null);
            
            txt_fecha_separacion.setText(fecha);
            txt_doc_separacion.setText(venta);
            txt_cliente_separacion.setText(cliente);
            txt_total_separacion.setText(c_varios.formato_totales(total));
            
            jd_entrega_separacion.setVisible(true);
            
        }
    }//GEN-LAST:event_btn_entregar_productosActionPerformed
    
    private void limpiar_entrega() {
        txt_fecha_separacion.setText("");
        txt_doc_separacion.setText("");
        txt_cliente_separacion.setText("");
        txt_total_separacion.setText("");
        txt_doc_venta.setText("");
        txt_datos_venta.setText("");
    }
    

    private void btn_grabar_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_grabar_ventaActionPerformed
        btn_grabar_venta.setEnabled(false);
        
        c_venta.setId_almacen(c_separacion.getId_almacen());
        c_venta.obtener_codigo();
        c_venta.setFecha(c_varios.getFechaActual());
        
        c_venta.setId_usuario(c_separacion.getId_usuario());
        c_venta.setId_cliente(c_cliente.getCodigo());

        //obtener documento seleccionado por cliente y sus valores de serie y numero
        cla_mis_documentos c_dcumento = (cla_mis_documentos) cbx_doc_venta.getSelectedItem();
        c_venta.setId_tido(c_dcumento.getId_tido());
        
        cl_documento_almacen c_doc_almacen = new cl_documento_almacen();
        c_doc_almacen.setId_almacen(id_almacen);
        c_doc_almacen.setId_tido(c_dcumento.getId_tido());
        c_doc_almacen.comprobar_documento();
        
        c_venta.setSerie(c_doc_almacen.getSerie());
        c_venta.setNumero(c_doc_almacen.getNumero());
        c_venta.setTotal(c_separacion.getTotal());
        c_venta.setId_tipo_venta(1);
        c_venta.setPagado(c_separacion.getTotal());
        c_venta.setEstado(1);
        c_venta.setEnviado_sunat(0);
        
        cl_productos_ventas c_nuevo_detalle = new cl_productos_ventas();
        
        if (c_venta.registrar()) {
            c_separacion.entregar_separacion();

            //copiar productos de la separacion, establacer variables generales
            c_nuevo_detalle.setId_venta(c_venta.getId_venta());

            //recorrer tabla de productos de la separacion
            cl_conectar c_conectar = new cl_conectar();
            try {
                String sql = "select * from productos_ventas "
                        + "where id_ventas = '" + c_separacion.getId_venta() + "'  and id_almacen = '" + c_separacion.getId_almacen() + "'";
                Statement st = c_conectar.conexion();
                ResultSet rs = c_conectar.consulta(st, sql);
                while (rs.next()) {
                    c_nuevo_detalle.setCosto(rs.getDouble("costo"));
                    c_nuevo_detalle.setPrecio(rs.getDouble("precio"));
                    c_nuevo_detalle.setId_producto(rs.getInt("id_producto"));
                    c_nuevo_detalle.setCantidad(rs.getInt("cantidad"));
                    c_nuevo_detalle.registrar();
                }
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
            }

            //generar documento electronico
            try {
                //Ponemos a "Dormir" el programa durante los ms que queremos
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            
            String[] envio_sunat;
            envio_sunat = cl_envio_server.enviar_documento(c_venta.getId_venta(), c_venta.getId_tido(), c_venta.getId_almacen());
            
            String nombre_archivo = envio_sunat[0];
            String url_codigo_qr = envio_sunat[2];
            String hash = envio_sunat[3];
            String estatus = envio_sunat[5];
            if (estatus.equals("error")) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error al recibir el comprobante");
            } else {
                //imprimir boleta o factura
                leer_numeros c_letras = new leer_numeros();
                String letras_numeros = c_letras.Convertir(c_venta.getTotal() + "", true) + " SOLES";
                System.out.println(letras_numeros);
                System.out.println(url_codigo_qr);
                
                File miDir = new File(".");
                try {
                    Map<String, Object> parametros = new HashMap<>();
                    String path = miDir.getCanonicalPath();
                    String direccion = path + "//reports//subreports//";
                    
                    System.out.println(direccion);
                    parametros.put("SUBREPORT_DIR", direccion);
                    parametros.put("JRParameter.REPORT_LOCALE", Locale.ENGLISH);
                    parametros.put("REPORT_LOCALE", Locale.ENGLISH);
                    parametros.put("p_id_venta", c_venta.getId_venta());
                    parametros.put("p_id_almacen", c_venta.getId_almacen());
                    parametros.put("p_letras_numero", letras_numeros);
                    parametros.put("p_codigo_qr", url_codigo_qr);
                    parametros.put("p_hash", hash);
                    //c_varios.imp_reporte("rpt_documento_venta", parametros);
                    c_varios.ver_reporte("rpt_documento_venta", parametros);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                }
            }
            
            jd_entrega_separacion.setVisible(false);
            limpiar_entrega();
            c_venta.mostrar(t_ventas, query);
        }
    }//GEN-LAST:event_btn_grabar_ventaActionPerformed

    private void txt_doc_ventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_doc_ventaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String documento = txt_doc_venta.getText();
            
            cla_mis_documentos c_dcumento = (cla_mis_documentos) cbx_doc_venta.getSelectedItem();
            c_venta.setId_tido(c_dcumento.getId_tido());
            
            if (documento.length() > 0) {
                c_cliente.setDocumento(documento);
                if (c_dcumento.getId_tido() == 1) {
                    if (documento.length() == 8) {
                        if (!c_cliente.comprobar_cliente_doc()) {
                            JOptionPane.showMessageDialog(null, "CLIENTE NO REGISTRADO, SE BUSCARA DATOS EN RENIEC");
                            System.out.println("buscar dni");
                            try {
                                String json = cl_json_entidad.getJSONDNI_LUNASYSTEMS(documento);
                                //Lo mostramos
                                String datos = cl_json_entidad.showJSONDNIL(json);
                                txt_datos_venta.setText(datos);
                                c_cliente.obtener_codigo();
                                c_cliente.setNombre(datos);
                                c_cliente.setDireccion("-");
                                c_cliente.setTelefono("");
                                c_cliente.registrar();
                            } catch (ParseException e) {
                                JOptionPane.showMessageDialog(null, "ERROR EN BUSCAR RUC " + e.getLocalizedMessage());
                            }
                        } else {
                            c_cliente.comprobar_cliente();
                            txt_datos_venta.setText(c_cliente.getNombre());
                        }
                        
                    }
                }
                
                if (c_dcumento.getId_tido() == 2) {
                    if (documento.length() == 11) {
                        if (!c_cliente.comprobar_cliente_doc()) {
                            JOptionPane.showMessageDialog(null, "CLIENTE NO REGISTRADO, SE BUSCARA DATOS EN SUNAT");
                            System.out.println("buscar ruc");
                            try {
                                String json = cl_json_entidad.getJSONRUC_LUNASYSTEMS(documento);
                                //Lo mostramos
                                String[] datos = cl_json_entidad.showJSONRUC_JMP(json);
                                txt_datos_venta.setText(datos[0]);
                                c_cliente.obtener_codigo();
                                c_cliente.setNombre(datos[0]);
                                c_cliente.setDireccion(datos[1]);
                                c_cliente.setTelefono("");
                                c_cliente.registrar();
                            } catch (ParseException e) {
                                JOptionPane.showMessageDialog(null, "ERROR EN BUSCAR RUC " + e.getLocalizedMessage());
                            }
                        } else {
                            c_cliente.comprobar_cliente();
                            txt_datos_venta.setText(c_cliente.getNombre());
                        }
                    }
                    
                }
                
                c_venta.setId_cliente(c_cliente.getCodigo());
            }
        }
    }//GEN-LAST:event_txt_doc_ventaKeyPressed

    private void cbx_doc_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_doc_ventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_doc_ventaActionPerformed

    private void txt_doc_ventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_doc_ventaKeyTyped
        c_varios.limitar_caracteres(evt, txt_doc_venta, 11);
        c_varios.solo_numeros(evt);
    }//GEN-LAST:event_txt_doc_ventaKeyTyped

    private void cbx_doc_ventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbx_doc_ventaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cla_mis_documentos c_dcumento = (cla_mis_documentos) cbx_doc_venta.getSelectedItem();
            if (c_dcumento.getId_tido() == 6) {
                btn_grabar_venta.requestFocus();
            } else {
                txt_doc_venta.selectAll();
                txt_doc_venta.requestFocus();
            }
        }
    }//GEN-LAST:event_cbx_doc_ventaKeyPressed

    private void txt_doc_ventaInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txt_doc_ventaInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_doc_ventaInputMethodTextChanged

    private void btn_imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirActionPerformed
        //cargar datos venta
        int id_venta = Integer.parseInt(t_ventas.getValueAt(fila_seleccionada, 8).toString());
        c_venta.setId_venta(id_venta);
        c_venta.setId_almacen(id_almacen);
        c_venta.validar_venta();
        
        leer_numeros c_letras = new leer_numeros();
        String letras_numeros = c_letras.Convertir(c_venta.getTotal() + "", true) + " SOLES";
        System.out.println(letras_numeros);

        //imprimir boleta o factura
        if (c_venta.getId_tido() == 1 || c_venta.getId_tido() == 2) {
            c_hash.setId_venta(id_venta);
            c_hash.setId_almacen(id_almacen);
            c_hash.validar_firma();
            String url_codigo_qr = "http://www.lunasystemsperu.com/clientes/comercial_penia/greenter/generate_qr/temp/" + c_hash.getNombre() + ".png";
            System.out.println(url_codigo_qr);
            
            File miDir = new File(".");
            try {
                Map<String, Object> parametros = new HashMap<>();
                String path = miDir.getCanonicalPath();
                String direccion = path + "//reports//subreports//";
                
                System.out.println(direccion);
                parametros.put("SUBREPORT_DIR", direccion);
                parametros.put("JRParameter.REPORT_LOCALE", Locale.ENGLISH);
                parametros.put("REPORT_LOCALE", Locale.ENGLISH);
                parametros.put("p_id_venta", c_venta.getId_venta());
                parametros.put("p_id_almacen", c_venta.getId_almacen());
                parametros.put("p_letras_numero", letras_numeros);
                parametros.put("p_codigo_qr", url_codigo_qr);
                parametros.put("p_hash", c_hash.getHash());
                //   c_varios.imp_reporte("rpt_documento_venta", parametros);
                c_varios.ver_reporte("rpt_documento_venta", parametros);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
            }
        }

        //imprimir si es nota de ventaonota de separacion
        if (c_venta.getId_tido() == 6 || c_venta.getId_tido() == 7) {
            
            File miDir = new File(".");
            try {
                Map<String, Object> parametros = new HashMap<>();
                String path = miDir.getCanonicalPath();
                String direccion = path + "//reports//subreports//";
                
                System.out.println(direccion);
                parametros.put("SUBREPORT_DIR", direccion);
                parametros.put("JRParameter.REPORT_LOCALE", Locale.ENGLISH);
                parametros.put("REPORT_LOCALE", Locale.ENGLISH);
                parametros.put("p_id_venta", c_venta.getId_venta());
                parametros.put("p_id_almacen", c_venta.getId_almacen());
                parametros.put("p_letras_numero", letras_numeros);
                //   c_varios.imp_reporte("rpt_documento_venta", parametros);
                if (id_almacen == 1) {
                    c_varios.ver_reporte("rpt_documento_venta_nota", parametros);
                } else {
                    c_varios.ver_reporte("rpt_documento_venta_nota", parametros);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
            }
        }
    }//GEN-LAST:event_btn_imprimirActionPerformed

    private void cbx_buscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbx_buscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_buscar.requestFocus();
        }
    }//GEN-LAST:event_cbx_buscarKeyPressed

    private void btn_mod_separacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_mod_separacionActionPerformed
        
        int id_venta = Integer.parseInt(t_ventas.getValueAt(fila_seleccionada, 8).toString());
        Frame f = JOptionPane.getRootFrame();
        frm_mod_separacion.c_venta.setId_venta(id_venta);
        frm_mod_separacion dialog = new frm_mod_separacion(f, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_mod_separacionActionPerformed

    private void t_cobrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_cobrosMouseClicked
        fila_cobro = t_cobros.getSelectedRow();
        btn_eliminar_cobro.setEnabled(true);
    }//GEN-LAST:event_t_cobrosMouseClicked

    private void btn_eliminar_cobroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminar_cobroActionPerformed
        frm_principal.c_permiso.setId_permiso(2);
        boolean permitido = frm_principal.c_permiso.validar();
        
        if (permitido) {
            int id_cobro = Integer.parseInt(t_cobros.getValueAt(fila_cobro, 0).toString());
            c_cobros.setId_cobro(id_cobro);
            c_cobros.eliminar_cobro();
            c_cobros.mostrar(t_cobros);
            
            btn_jd_cobro_graba.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Usted no tiene permiso para realizar esta operacion!!");
        }
    }//GEN-LAST:event_btn_eliminar_cobroActionPerformed

    private void txt_pago_fechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pago_fechaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_pago_fecha.getText().length() == 10) {
                txt_pago_monto.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Complete la fecha");
                txt_pago_fecha.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_pago_fechaKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_anular_venta;
    private javax.swing.JButton btn_cobro_guardar;
    private javax.swing.JButton btn_cobro_salir;
    private javax.swing.JButton btn_eliminar_cobro;
    private javax.swing.JButton btn_entregar_productos;
    private javax.swing.JButton btn_grabar_venta;
    private javax.swing.JButton btn_imprimir;
    private javax.swing.JButton btn_jd_cobro_graba;
    private javax.swing.JButton btn_jd_grabar;
    private javax.swing.JButton btn_jd_salir;
    private javax.swing.JButton btn_mod_separacion;
    private javax.swing.JButton btn_ver_cobros;
    private javax.swing.JButton btn_ver_cupon;
    private javax.swing.JButton btn_ver_detalle;
    private javax.swing.JComboBox<String> cbx_buscar;
    private javax.swing.JComboBox<String> cbx_doc_venta;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JDialog jd_anular_documento;
    private javax.swing.JDialog jd_entrega_separacion;
    private javax.swing.JDialog jd_reg_cobro;
    private javax.swing.JDialog jd_ver_cobros;
    private javax.swing.JDialog jd_ver_productos;
    private javax.swing.JLabel lbl_pagado;
    private javax.swing.JTable t_cobros;
    private javax.swing.JTable t_detalle;
    private javax.swing.JTable t_ventas;
    private javax.swing.JTextField txt_buscar;
    private javax.swing.JTextField txt_cliente_separacion;
    private javax.swing.JTextField txt_datos_venta;
    private javax.swing.JTextField txt_detalle;
    private javax.swing.JTextField txt_doc_separacion;
    private javax.swing.JTextField txt_doc_venta;
    private javax.swing.JTextField txt_fecha_separacion;
    private javax.swing.JTextField txt_jd_cobro_cliente;
    private javax.swing.JTextField txt_jd_cobro_documento;
    private javax.swing.JTextField txt_jd_fecha;
    private javax.swing.JTextField txt_jd_motivo;
    private javax.swing.JTextField txt_jd_pagado;
    private javax.swing.JTextField txt_jd_total;
    private javax.swing.JFormattedTextField txt_pago_fecha;
    private javax.swing.JTextField txt_pago_monto;
    private javax.swing.JTextField txt_total_pagos;
    private javax.swing.JTextField txt_total_separacion;
    private javax.swing.JTextField txt_total_ventas;
    // End of variables declaration//GEN-END:variables
}
