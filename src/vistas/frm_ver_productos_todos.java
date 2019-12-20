/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import clases.cl_producto;
import clases_varios.Configuracion;
import forms.frm_reg_producto;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import comercial.frm_principal;

/**
 *
 * @author luis
 */
public class frm_ver_productos_todos extends javax.swing.JInternalFrame {

    cl_producto c_producto = new cl_producto();

    int fila_seleccionada;

    /**
     * Creates new form frm_ver_productos_todos
     */
    public frm_ver_productos_todos() {
        initComponents();
        this.getContentPane().setBackground(Configuracion.COLOR_FORMULARIO_1);
        String query = "select * from productos "
                + "order by descripcion asc, marca asc "
                + "limit 0";
        c_producto.mostrar(t_productos, query);
        contar_resultados();
    }

    private void activar_botones() {
        btn_modificar.setEnabled(true);
        btn_ubicar.setEnabled(true);
        btn_eliminar.setEnabled(true);
    }

    private void desactivar_botones() {
        btn_modificar.setEnabled(false);
        btn_ubicar.setEnabled(false);
        btn_eliminar.setEnabled(false);
    }

    private void contar_resultados() {
        int conteo = t_productos.getRowCount();
        lbl_encontrados.setText(conteo + " productos encontrados");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txt_buscar = new javax.swing.JTextField();
        cbx_buscar = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_productos = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        btn_modificar = new javax.swing.JButton();
        btn_ubicar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        btn_cerrar = new javax.swing.JButton();
        lbl_encontrados = new javax.swing.JLabel();

        setTitle("Ver Todos los Productos");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/magnifier.png"))); // NOI18N
        jLabel1.setText("Buscar por:");

        txt_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_buscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscarKeyReleased(evt);
            }
        });

        cbx_buscar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DESCRIPCION", "CODIGO", "MARCA", "P. VENTA" }));
        cbx_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_buscarActionPerformed(evt);
            }
        });

        t_productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1542", "GUITARRA ACUSTICA NIÑOS RED", "MAMFEX", "135.00", "110.00", "1.20"},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID.", "Descripcion", "Marca", "P. Venta.", "P. Costo", "Comision"
            }
        ));
        t_productos.setShowVerticalLines(false);
        t_productos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_productosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(t_productos);
        if (t_productos.getColumnModel().getColumnCount() > 0) {
            t_productos.getColumnModel().getColumn(0).setPreferredWidth(50);
            t_productos.getColumnModel().getColumn(1).setPreferredWidth(550);
            t_productos.getColumnModel().getColumn(2).setPreferredWidth(150);
            t_productos.getColumnModel().getColumn(3).setPreferredWidth(80);
            t_productos.getColumnModel().getColumn(4).setPreferredWidth(80);
            t_productos.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        jToolBar1.setFloatable(false);
        jToolBar1.setOpaque(false);

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

        btn_ubicar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/find.png"))); // NOI18N
        btn_ubicar.setText("Donde Hay?");
        btn_ubicar.setEnabled(false);
        btn_ubicar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_ubicar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_ubicar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_ubicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubicarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_ubicar);

        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete.png"))); // NOI18N
        btn_eliminar.setText("Eliminar");
        btn_eliminar.setEnabled(false);
        btn_eliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_eliminar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_eliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_eliminar);

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

        lbl_encontrados.setText("Encontrados");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1060, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbx_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_buscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_encontrados)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_encontrados))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_cerrarActionPerformed

    private void btn_ubicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubicarActionPerformed
        desactivar_botones();
        Frame f = JOptionPane.getRootFrame();
        frm_ver_ubicacion_producto.id_producto = c_producto.getId();
        frm_ver_ubicacion_producto dialog = new frm_ver_ubicacion_producto(f, true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_btn_ubicarActionPerformed

    private void t_productosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_productosMouseClicked
        if (evt.getClickCount() == 2) {
            fila_seleccionada = t_productos.getSelectedRow();
            c_producto.setId(Integer.parseInt(t_productos.getValueAt(fila_seleccionada, 0).toString()));
            activar_botones();
        }
    }//GEN-LAST:event_t_productosMouseClicked

    private void cbx_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_buscarActionPerformed
        txt_buscar.selectAll();
        txt_buscar.requestFocus();
    }//GEN-LAST:event_cbx_buscarActionPerformed

    private void txt_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarKeyReleased

    }//GEN-LAST:event_txt_buscarKeyReleased

    private void txt_buscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String texto = txt_buscar.getText().trim();
            int tipo_busqueda = cbx_buscar.getSelectedIndex();
            String query = "";

            if (tipo_busqueda == 0) {
                query = "select * from productos "
                        + "where concat (descripcion, ' ', marca) like '%" + texto + "%' "
                        + "order by descripcion asc,  marca asc";
            }

            if (tipo_busqueda == 1) {
                query = "select * from productos "
                        + "where id_producto =  '" + texto + "' "
                        + "order by descripcion asc,  marca asc";
            }

            if (tipo_busqueda == 2) {
                query = "select * from productos "
                        + "where marca like  '%" + texto + "%' "
                        + "order by descripcion asc, marca asc";
            }
            if (tipo_busqueda == 3) {
                query = "select * from productos "
                        + "where precio = '" + texto + "' "
                        + "order by descripcion asc, marca asc";
            }

            c_producto.mostrar(t_productos, query);
            contar_resultados();
        }
    }//GEN-LAST:event_txt_buscarKeyPressed

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
        frm_principal.c_permiso.setId_permiso(10);
        boolean permitido = frm_principal.c_permiso.validar();

        if (permitido) {

            desactivar_botones();
            Frame f = JOptionPane.getRootFrame();
            frm_reg_producto.c_producto.setId(c_producto.getId());
            frm_reg_producto.registrar = false;
            frm_reg_producto dialog = new frm_reg_producto(f, true);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Usted no tiene permiso para realizar esta operacion!!");
        }
    }//GEN-LAST:event_btn_modificarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        frm_principal.c_permiso.setId_permiso(11);
        boolean permitido = frm_principal.c_permiso.validar();

        if (permitido) {
        } else {
            JOptionPane.showMessageDialog(null, "Usted no tiene permiso para realizar esta operacion!!");
        }
    }//GEN-LAST:event_btn_eliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cerrar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JButton btn_ubicar;
    private javax.swing.JComboBox<String> cbx_buscar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lbl_encontrados;
    private javax.swing.JTable t_productos;
    private javax.swing.JTextField txt_buscar;
    // End of variables declaration//GEN-END:variables
}
