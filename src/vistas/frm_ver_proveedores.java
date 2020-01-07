/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import clases.cl_proveedor;
import clases.cl_varios;
import clases_varios.Configuracion;
import forms.frm_reg_proveedor;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author luis
 */
public class frm_ver_proveedores extends javax.swing.JInternalFrame {

    int fila_seleccionada;
    cl_varios c_varios = new cl_varios();
    cl_proveedor c_proveedor = new cl_proveedor();

    /**
     * Creates new form frm_ver_proveedores
     */
    public frm_ver_proveedores() {
        initComponents();
        this.getContentPane().setBackground(Configuracion.COLOR_FORMULARIO_1);
        String query = "select * "
                + "from proveedor "
                + "order by razon_social asc";
        c_proveedor.mostrar(t_proveedores, query);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btn_agregar = new javax.swing.JButton();
        btn_modificar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_buscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_proveedores = new javax.swing.JTable();

        setTitle("Ver proveedores");

        jToolBar1.setFloatable(false);

        btn_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        btn_agregar.setText("Agregar");
        btn_agregar.setFocusable(false);
        btn_agregar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_agregar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_agregar);

        btn_modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/application_edit.png"))); // NOI18N
        btn_modificar.setText("Modificar");
        btn_modificar.setEnabled(false);
        btn_modificar.setFocusable(false);
        btn_modificar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_modificar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_modificar);
        jToolBar1.add(jSeparator2);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/clipboard_text.png"))); // NOI18N
        jButton1.setText("ver Pagos");
        jButton1.setEnabled(false);
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/clipboard_text.png"))); // NOI18N
        jButton3.setText("ver estado de Cuenta");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);
        jToolBar1.add(jSeparator1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cross.png"))); // NOI18N
        jButton2.setText("Salir");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/find.png"))); // NOI18N
        jLabel1.setText("Buscar:");

        txt_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_buscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscarKeyReleased(evt);
            }
        });

        t_proveedores.setModel(new javax.swing.table.DefaultTableModel(
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
        t_proveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_proveedoresMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(t_proveedores);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_buscar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarKeyReleased
        String texto = txt_buscar.getText();
        String query = "select * "
                + "from proveedor "
                + "where razon_social like '%" + texto + "%' "
                + "order by razon_social asc";
        c_proveedor.mostrar(t_proveedores, query);
    }//GEN-LAST:event_txt_buscarKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_buscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_buscarKeyPressed

    private void t_proveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_proveedoresMouseClicked
        if (evt.getClickCount() == 2) {
            fila_seleccionada = t_proveedores.getSelectedRow();
            btn_modificar.setEnabled(true);
            jButton1.setEnabled(true);
        }

    }//GEN-LAST:event_t_proveedoresMouseClicked

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed

        int id_proveedor = Integer.parseInt(t_proveedores.getValueAt(fila_seleccionada, 0).toString());
        btn_modificar.setEnabled(false);
        Frame f = JOptionPane.getRootFrame();
        frm_reg_proveedor.accion = "modificar";
        frm_reg_proveedor.origen = "ver_proveedor";
        frm_reg_proveedor.c_proveedor.setId_proveedor(id_proveedor);
        frm_reg_proveedor dialog = new frm_reg_proveedor(f, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_modificarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Frame f = JOptionPane.getRootFrame();
        int id_proveedor = Integer.parseInt(t_proveedores.getValueAt(fila_seleccionada, 0).toString());
        frm_ver_proveedores_pagos.c_proveedor.setId_proveedor(id_proveedor);
        frm_ver_proveedores_pagos dialog = new frm_ver_proveedores_pagos(f, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        File miDir = new File(".");
        try {
            Map<String, Object> parametros = new HashMap<>();
            String path = miDir.getCanonicalPath();
            String direccion = path + File.separator + "reports" + File.separator;//+ "subreports" + File.separator;

            System.out.println(direccion);
            parametros.put("SUBREPORT_DIR", direccion);
            parametros.put("JRParameter.REPORT_LOCALE", Locale.ENGLISH);
            parametros.put("REPORT_LOCALE", Locale.ENGLISH);
            c_varios.ver_reporte("rpt_deudas_proveedores_todos", parametros);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        frm_reg_proveedor modal = new frm_reg_proveedor(null, true);
        frm_reg_proveedor.accion = "registrar";
        modal.setLocationRelativeTo(this);
        modal.setVisible(true);
    }//GEN-LAST:event_btn_agregarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable t_proveedores;
    private javax.swing.JTextField txt_buscar;
    // End of variables declaration//GEN-END:variables
}
