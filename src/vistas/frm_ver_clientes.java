/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import clases.cl_cliente;
import clases.cl_cliente_pago;
import clases.cl_cobros_ventas;
import clases.cl_movimiento_banco;
import clases.cl_productos_ventas;
import clases.cl_varios;
import clases.cl_venta;
import clases.cl_venta_eliminada;
import clases_varios.Configuracion;
import forms.frm_reg_cliente;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author CALIDAD
 */
public class frm_ver_clientes extends javax.swing.JInternalFrame {

    cl_cliente c_cliente = new cl_cliente();
    cl_varios c_varios = new cl_varios();

    int fila_seleccionada;
    String query;

    public frm_ver_clientes() {
        initComponents();
        this.getContentPane().setBackground(Configuracion.COLOR_FORMULARIO_1);
        query = "select * "
                + "from clientes "
                + "order by nombre asc";

        c_cliente.mostrar(t_clientes, query);
        txt_buscar.requestFocus();
        modalReporteDiario.setSize(400, 180);

    }

    private void activar_botones() {
        btn_modificar.setEnabled(true);
        btn_pago.setEnabled(true);
        btn_eliminar.setEnabled(true);
    }

    private void desactivar_botones() {
        btn_modificar.setEnabled(false);
        btn_pago.setEnabled(false);
        btn_eliminar.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        modalReporteDiario = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txt_buscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_clientes = new javax.swing.JTable();
        cbx_boton = new javax.swing.JComboBox();
        jToolBar1 = new javax.swing.JToolBar();
        jButton3 = new javax.swing.JButton();
        btn_modificar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btn_pago = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btn_cerrar = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Fecha:");

        jDateChooser1.setOpaque(false);

        jButton5.setText("Generar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Cerrar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Reporte Diario ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(96, Short.MAX_VALUE))
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addGap(62, 62, 62))
        );

        javax.swing.GroupLayout modalReporteDiarioLayout = new javax.swing.GroupLayout(modalReporteDiario.getContentPane());
        modalReporteDiario.getContentPane().setLayout(modalReporteDiarioLayout);
        modalReporteDiarioLayout.setHorizontalGroup(
            modalReporteDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modalReporteDiarioLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        modalReporteDiarioLayout.setVerticalGroup(
            modalReporteDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setTitle("Ver Clientes");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/find.png"))); // NOI18N
        jLabel1.setText("Buscar por RUC o Razon Social:");

        txt_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_buscarActionPerformed(evt);
            }
        });
        txt_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_buscarKeyPressed(evt);
            }
        });

        t_clientes.setModel(new javax.swing.table.DefaultTableModel(
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
        t_clientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_clientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(t_clientes);

        cbx_boton.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TODOS", "DEUDORES", "INACTIVOS" }));
        cbx_boton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_botonActionPerformed(evt);
            }
        });
        cbx_boton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbx_botonKeyPressed(evt);
            }
        });

        jToolBar1.setFloatable(false);
        jToolBar1.setOpaque(false);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        jButton3.setText("Nuevo");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        btn_modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/application_edit.png"))); // NOI18N
        btn_modificar.setText("Modificar");
        btn_modificar.setEnabled(false);
        btn_modificar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_modificar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_modificar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_modificar);

        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete.png"))); // NOI18N
        btn_eliminar.setText("Eliminar");
        btn_eliminar.setEnabled(false);
        btn_eliminar.setFocusable(false);
        btn_eliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_eliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_eliminar);
        jToolBar1.add(jSeparator1);

        btn_pago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/coins.png"))); // NOI18N
        btn_pago.setText("Ver Cobros");
        btn_pago.setEnabled(false);
        btn_pago.setFocusable(false);
        btn_pago.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_pago.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pagoActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_pago);
        jToolBar1.add(jSeparator2);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/clipboard_text.png"))); // NOI18N
        jButton4.setText("Rpt cliente diario");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/clipboard_text.png"))); // NOI18N
        jButton2.setText("por Cobrar Todos");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/currency.png"))); // NOI18N
        jButton1.setText("Re Calcular");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        btn_cerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cross.png"))); // NOI18N
        btn_cerrar.setText("Cerrar");
        btn_cerrar.setFocusable(false);
        btn_cerrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_cerrar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_cerrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerrarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_cerrar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbx_boton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 203, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_boton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_cerrarActionPerformed

    private void txt_buscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String t_buscar = txt_buscar.getText().trim();
            String query = "select * "
                    + "from clientes "
                    + "where documento like '%" + t_buscar + "%' or nombre like '%" + t_buscar + "%' "
                    + "order by nombre asc";
            System.out.println(query);
            c_cliente.mostrar(t_clientes, query);
            txt_buscar.setText("");
        }
    }//GEN-LAST:event_txt_buscarKeyPressed

    private void t_clientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_clientesMouseClicked
        if (evt.getClickCount() == 1) {
            fila_seleccionada = t_clientes.getSelectedRow();
            c_cliente.setCodigo(Integer.parseInt(t_clientes.getValueAt(fila_seleccionada, 0).toString()));
            activar_botones();
        }
    }//GEN-LAST:event_t_clientesMouseClicked

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
        int id_cliente = Integer.parseInt(t_clientes.getValueAt(fila_seleccionada, 0).toString());
        desactivar_botones();
        Frame f = JOptionPane.getRootFrame();
        frm_reg_cliente.accion = "modificar";
        frm_reg_cliente.origen = "ver_clientes";
        frm_reg_cliente.c_cliente.setCodigo(id_cliente);
        frm_reg_cliente dialog = new frm_reg_cliente(f, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_modificarActionPerformed

    private void cbx_botonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbx_botonKeyPressed

    }//GEN-LAST:event_cbx_botonKeyPressed

    private void cbx_botonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_botonActionPerformed
        int tipo_cliente = cbx_boton.getSelectedIndex();
        if (tipo_cliente == 0) {
            query = "select * "
                    + "from clientes "
                    + "order by nombre asc";
        }
        if (tipo_cliente == 1) {
            query = "select * "
                    + "from clientes "
                    + "where pago<venta "
                    + "order by nombre asc";

        }
        if (tipo_cliente == 2) {
            query = "select * "
                    + "from clientes "
                    + "where ultima_venta='1000-01-01' "
                    + "order by nombre asc";

        }

        c_cliente.mostrar(t_clientes, query);
    }//GEN-LAST:event_cbx_botonActionPerformed

    private void txt_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_buscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_buscarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        File miDir = new File(".");
        try {
            Map<String, Object> parametros = new HashMap<>();
            String path = miDir.getCanonicalPath();
            String direccion = path + File.separator + "reports" + File.separator;//+ "subreports" + File.separator;

            System.out.println(direccion);
            parametros.put("SUBREPORT_DIR", direccion);
            parametros.put("JRParameter.REPORT_LOCALE", Locale.ENGLISH);
            parametros.put("REPORT_LOCALE", Locale.ENGLISH);
            c_varios.ver_reporte("rpt_deudas_clientes_todos", parametros);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Frame f = JOptionPane.getRootFrame();
        frm_reg_cliente.accion = "registrar";
        frm_reg_cliente.origen = "ver_clientes";
        frm_reg_cliente.c_cliente.setCodigo(0);
        frm_reg_cliente dialog = new frm_reg_cliente(f, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btn_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pagoActionPerformed
        desactivar_botones();
        Frame f = JOptionPane.getRootFrame();
        frm_ver_clientes_pagos.c_cliente.setCodigo(c_cliente.getCodigo());
        frm_ver_clientes_pagos dialog = new frm_ver_clientes_pagos(f, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_pagoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        c_cliente.resumar_ventas();
        c_cliente.resumar_clientes();
        c_cliente.mostrar(t_clientes, query);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        modalReporteDiario.setModal(true);
        modalReporteDiario.setLocationRelativeTo(null);
        modalReporteDiario.setVisible(true);

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Date fecha = jDateChooser1.getDate();
        if (fecha != null) {

            String nombre_reporte = "rep_detalle_venta_cliente";
            String fechaS = new SimpleDateFormat("yyyy-MM-dd").format(fecha);

            //ver reporte en excel;        
            File miDir = new File(".");
            try {
                Map<String, Object> parametros = new HashMap<>();
                String path = miDir.getCanonicalPath();
                String direccion = path + File.separator + "reports" + File.separator + "subreports" + File.separator;

                System.out.println(direccion);
                parametros.put("SUBREPORT_DIR", direccion);
                parametros.put("JRParameter.REPORT_LOCALE", Locale.ENGLISH);
                parametros.put("REPORT_LOCALE", Locale.ENGLISH);
                parametros.put("fecha", fechaS);

                c_varios.ver_reporte(nombre_reporte, parametros);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "No ha selecionado la fecha o la ingresada no es valida");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        modalReporteDiario.setVisible(false);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        int id_cliente = Integer.parseInt(t_clientes.getValueAt(fila_seleccionada, 0).toString());
        desactivar_botones();
        if (fila_seleccionada > -1) {
            int confirmado = JOptionPane.showConfirmDialog(null, "¿Esta Seguro de Eliminar el Cliente?");

            if (JOptionPane.OK_OPTION == confirmado) {
                cl_productos_ventas c_detalle = new cl_productos_ventas();
                c_detalle.eliminar_cliente(id_cliente);
                
                cl_cobros_ventas c_cobro = new cl_cobros_ventas();
                c_cobro.eliminar_cliente(id_cliente);

                cl_venta_eliminada c_cupon = new cl_venta_eliminada();
                c_cupon.eliminar_cliente(id_cliente);

                cl_venta c_venta = new cl_venta();
                c_venta.setId_cliente(id_cliente);
                c_venta.eliminar_cliente();

                c_cliente.setCodigo(id_cliente);
                c_cliente.eliminar();

                c_cliente.mostrar(t_clientes, query);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No ha seleccionado una fila");

        }
    }//GEN-LAST:event_btn_eliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cerrar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JButton btn_pago;
    private javax.swing.JComboBox cbx_boton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JDialog modalReporteDiario;
    private javax.swing.JTable t_clientes;
    private javax.swing.JTextField txt_buscar;
    // End of variables declaration//GEN-END:variables
}
