/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import clases.cl_inventarios;
import clases.cl_productos_inventarios;
import clases.cl_varios;
import forms.frm_reg_inventario;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import comercial.frm_principal;

/**
 *
 * @author luis
 */
public class frm_ver_inventarios extends javax.swing.JInternalFrame {

    cl_inventarios c_inventario = new cl_inventarios();
    cl_productos_inventarios c_detalle = new cl_productos_inventarios();
    cl_varios c_varios = new cl_varios();

    int id_almacen = frm_principal.c_almacen.getId();
    int fila_seleccionada;

    /**
     * Creates new form frm_ver_inventarios
     */
    public frm_ver_inventarios() {
        initComponents();
        String query = "select i.anio, i.id_inventario, i.fecha, u.username  "
                + "from inventario as i "
                + "inner join usuarios as u on u.id_usuarios = i.id_usuarios "
                + "where concat(year(i.fecha), month(i.fecha)) = '" + c_varios.obtener_periodo() + "' and i.id_almacen = '" + id_almacen + "'";
        c_inventario.mostrar(t_inventarios, query);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jd_detalle = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        t_detalle = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        btn_agregar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btn_detalle = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btn_salir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cbx_buscar = new javax.swing.JComboBox<>();
        txt_buscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_inventarios = new javax.swing.JTable();

        jd_detalle.setTitle("Ver Detalle de Inventarios");

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

        javax.swing.GroupLayout jd_detalleLayout = new javax.swing.GroupLayout(jd_detalle.getContentPane());
        jd_detalle.getContentPane().setLayout(jd_detalleLayout);
        jd_detalleLayout.setHorizontalGroup(
            jd_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jd_detalleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                .addContainerGap())
        );
        jd_detalleLayout.setVerticalGroup(
            jd_detalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jd_detalleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                .addContainerGap())
        );

        setTitle("Ver Inventarios");

        jToolBar1.setFloatable(false);

        btn_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        btn_agregar.setText("Nuevo");
        btn_agregar.setFocusable(false);
        btn_agregar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_agregar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_agregar);
        jToolBar1.add(jSeparator2);

        btn_detalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/clipboard_text.png"))); // NOI18N
        btn_detalle.setText("Ver Detalle");
        btn_detalle.setEnabled(false);
        btn_detalle.setFocusable(false);
        btn_detalle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_detalle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_detalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_detalleActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_detalle);

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

        btn_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cross.png"))); // NOI18N
        btn_salir.setText("Salir");
        btn_salir.setFocusable(false);
        btn_salir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_salir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salirActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_salir);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/magnifier.png"))); // NOI18N
        jLabel1.setText("Buscar por:");

        cbx_buscar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CODIGO (AAAACCC)", "FECHA (DD/MM/AAAA)", "USUARIO" }));
        cbx_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_buscarActionPerformed(evt);
            }
        });

        txt_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_buscarKeyPressed(evt);
            }
        });

        t_inventarios.setModel(new javax.swing.table.DefaultTableModel(
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
        t_inventarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_inventariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(t_inventarios);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbx_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(cbx_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_salirActionPerformed

    private void txt_buscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String query = "";
            int tipo_busqueda = cbx_buscar.getSelectedIndex();
            String texto = txt_buscar.getText().trim();

            if (tipo_busqueda == 0) {
                query = "select i.anio, i.id_inventario, i.fecha, u.username  "
                        + "from inventario as i "
                        + "inner join usuarios as u on u.id_usuarios = i.id_usuarios "
                        + "where concat(i.anio, i.id_inventario) = '" + texto + "' and i.id_almacen = '" + id_almacen + "'";
            }

            if (tipo_busqueda == 1) {
                texto = c_varios.fecha_myql(texto);
                query = "select i.anio, i.id_inventario, i.fecha, u.username  "
                        + "from inventario as i "
                        + "inner join usuarios as u on u.id_usuarios = i.id_usuarios "
                        + "where i.fecha = '" + texto + "' and i.id_almacen = '" + id_almacen + "'";
            }

            if (tipo_busqueda == 2) {
                query = "select i.anio, i.id_inventario, i.fecha, u.username  "
                        + "from inventario as i "
                        + "inner join usuarios as u on u.id_usuarios = i.id_usuarios "
                        + "where u.username = '" + texto + "' and i.id_almacen = '" + id_almacen + "'";
            }
            System.out.println(query);
            c_inventario.mostrar(t_inventarios, query);

        }
    }//GEN-LAST:event_txt_buscarKeyPressed

    private void cbx_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_buscarActionPerformed
        txt_buscar.requestFocus();
    }//GEN-LAST:event_cbx_buscarActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        frm_principal.c_permiso.setId_permiso(13);
        boolean permitido = frm_principal.c_permiso.validar();

        if (permitido) {
            frm_reg_inventario reg_inventario = new frm_reg_inventario();
            c_varios.llamar_ventana(reg_inventario);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Usted no tiene permiso para realizar esta operacion!!");
        }
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void t_inventariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_inventariosMouseClicked
        if (evt.getClickCount() == 2) {
            btn_detalle.setEnabled(true);
            btn_eliminar.setEnabled(false);
            fila_seleccionada = t_inventarios.getSelectedRow();
            c_detalle.setId_inventario(Integer.parseInt(t_inventarios.getValueAt(fila_seleccionada, 1).toString()));
        }
    }//GEN-LAST:event_t_inventariosMouseClicked

    private void btn_detalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_detalleActionPerformed
        btn_detalle.setEnabled(false);
        btn_eliminar.setEnabled(false);
        jd_detalle.setModal(true);
        jd_detalle.setSize(800, 600);
        jd_detalle.setLocationRelativeTo(null);
        c_detalle.mostrar_productos(t_detalle);
        jd_detalle.setVisible(true);
    }//GEN-LAST:event_btn_detalleActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        frm_principal.c_permiso.setId_permiso(14);
        boolean permitido = frm_principal.c_permiso.validar();

        if (permitido) {
            int confirmado = JOptionPane.showConfirmDialog(null, "¿Esta Seguro de Eliminar el Inventario?");
            btn_detalle.setEnabled(false);
            btn_eliminar.setEnabled(false);

            if (JOptionPane.OK_OPTION == confirmado) {

            }
        } else {
            JOptionPane.showMessageDialog(null, "Usted no tiene permiso para realizar esta operacion!!");
        }
    }//GEN-LAST:event_btn_eliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_detalle;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_salir;
    private javax.swing.JComboBox<String> cbx_buscar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JDialog jd_detalle;
    private javax.swing.JTable t_detalle;
    private javax.swing.JTable t_inventarios;
    private javax.swing.JTextField txt_buscar;
    // End of variables declaration//GEN-END:variables
}
