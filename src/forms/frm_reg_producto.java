/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import clases.cl_producto;
import clases.cl_productos_clasificacion;
import clases.cl_productos_presentacion;
import clases.cl_proveedor;
import clases.cl_unidad_medida;
import clases.cl_varios;
import clases_autocomplete.cla_producto_clasificacion;
import clases_autocomplete.cla_unidad_medida;
import clases_varios.Configuracion;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableModel;
import models.m_empresas;
import models.m_producto_clasificacion;
import models.m_unidades;

/**
 *
 * @author luis
 */
public class frm_reg_producto extends javax.swing.JDialog {

    //clases principales    
    public static cl_producto c_producto = new cl_producto();

    //clases secundarias
    cl_varios c_varios = new cl_varios();
    cl_productos_clasificacion c_clasificacion;
    cl_productos_presentacion c_presentacion;
    cl_unidad_medida c_unidad = new cl_unidad_medida();

    //autollenado clasificacon
    m_producto_clasificacion m_clasificacion = new m_producto_clasificacion();
    m_empresas m_empresa = new m_empresas();
    m_unidades m_unidad = new m_unidades();

    //variables publicas    
    static DefaultTableModel detalle;
    static int fila_seleccionada;
    public static boolean registrar;
    boolean modificar_presentacion = false;

    /**
     * Creates new form frm_reg_producto
     */
    public frm_reg_producto(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        getContentPane().setBackground(Configuracion.COLOR_FORMULARIO_1);
        m_clasificacion.cbx_clasificaciones(cbx_clasificacion);
        m_unidad.llenar_combo(cbx_unidad_medida);

        modelo_presentaciones();

        if (!registrar) {
            this.setTitle("Modificar Producto");

            /*cl_proveedor c_proveedor = new cl_proveedor();
            c_presentacion = new cl_productos_presentacion();

            c_producto.validar_id();

            //cargar datos
            c_proveedor.setId_proveedor(c_producto.getId_proveedor());
            c_proveedor.cargar_datos();

            txt_descripcion.setText(c_producto.getDescripcion());
            txt_marca.setText(c_producto.getMarca());
            txt_cod_barra.setText(c_producto.getCod_barra());
            txt_precio_minimo.setText(c_varios.formato_numero(c_producto.getPrecio()));
            txt_proveedor.setText(c_proveedor.getRuc() + " | " + c_proveedor.getRazon_social());

            //obtener modelo unidad
            c_unidad.setId(c_producto.getId_unidad());
            c_unidad.validar_id();
            cbx_unidad_medida.setEnabled(true);
            cbx_unidad_medida.getModel().setSelectedItem(new cla_unidad_medida(c_unidad.getId(), c_unidad.getNombre()));

            //obtener modelo clasificacion
            c_clasificacion = new cl_productos_clasificacion();
            c_clasificacion.setId_clasificacion(c_producto.getId_clasificacion());
            c_clasificacion.obtener_datos();
            cbx_clasificacion.setEnabled(true);
            cbx_clasificacion.getModel().setSelectedItem(new cla_producto_clasificacion(c_clasificacion.getId_clasificacion(), c_clasificacion.getDescripcion()));

            c_presentacion.setId_producto(c_producto.getId());
            c_presentacion.mostrar(t_presentaciones, detalle);

            txt_nombre_presentacion.setEnabled(true);

            txt_descripcion.setEnabled(true);
            txt_cod_barra.setEnabled(true);
            txt_marca.setEnabled(true);
            btn_guardar.setEnabled(true);*/
        }
    }

    private void modelo_presentaciones() {
        //formato de tabla detalle de venta
        detalle = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        detalle.addColumn("Item");
        detalle.addColumn("Descripcion");
        detalle.addColumn("Factor");
        detalle.addColumn("Precio Unit.");
        /*t_presentaciones.setModel(detalle);
        t_presentaciones.getColumnModel().getColumn(0).setPreferredWidth(20);
        t_presentaciones.getColumnModel().getColumn(1).setPreferredWidth(300);
        t_presentaciones.getColumnModel().getColumn(2).setPreferredWidth(50);
        t_presentaciones.getColumnModel().getColumn(3).setPreferredWidth(80);
        c_varios.centrar_celda(t_presentaciones, 0);
        c_varios.centrar_celda(t_presentaciones, 2);
        c_varios.derecha_celda(t_presentaciones, 3);*/
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
        btn_guardar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_descripcion = new javax.swing.JTextField();
        cbx_clasificacion = new javax.swing.JComboBox<>();
        txt_proveedor = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_precio_minimo = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cbx_unidad_medida = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Registrar Producto");
        setResizable(false);

        jToolBar1.setFloatable(false);

        btn_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/accept.png"))); // NOI18N
        btn_guardar.setText("Guardar");
        btn_guardar.setEnabled(false);
        btn_guardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_guardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_guardar);

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

        jLabel1.setText("Descripcion:");

        jLabel8.setText("Clasificacion:");

        jLabel9.setText("Proveedor:");

        txt_descripcion.setEnabled(false);
        txt_descripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_descripcionKeyPressed(evt);
            }
        });

        cbx_clasificacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbx_clasificacion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_clasificacionItemStateChanged(evt);
            }
        });
        cbx_clasificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_clasificacionActionPerformed(evt);
            }
        });
        cbx_clasificacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbx_clasificacionKeyPressed(evt);
            }
        });

        txt_proveedor.setEnabled(false);
        txt_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_proveedorActionPerformed(evt);
            }
        });

        jLabel11.setText("Precio Venta:");

        txt_precio_minimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_precio_minimo.setEnabled(false);
        txt_precio_minimo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_precio_minimoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_precio_minimoKeyTyped(evt);
            }
        });

        jLabel12.setText("Unidad Medida:");

        cbx_unidad_medida.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbx_unidad_medida.setEnabled(false);
        cbx_unidad_medida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbx_unidad_medidaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel1)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_descripcion)
                    .addComponent(txt_proveedor)
                    .addComponent(cbx_clasificacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cbx_unidad_medida, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_precio_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_clasificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txt_precio_minimo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(cbx_unidad_medida, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        c_producto.setDescripcion(txt_descripcion.getText());
        c_producto.setMarca("-");
        c_producto.setCod_barra("");
        c_producto.setComision(0);
        c_producto.setCosto(0);
        c_producto.setPrecio(Double.parseDouble(txt_precio_minimo.getText()));
        
        c_producto.setTipo_producto(0);
        cla_producto_clasificacion cla_clasificacion = (cla_producto_clasificacion) cbx_clasificacion.getSelectedItem();
        c_producto.setId_clasificacion(cla_clasificacion.getId_clasificacion());
        c_producto.setIcbper(0);
        if (cla_clasificacion.getId_clasificacion() == 3) {
            c_producto.setIcbper(1);
        }
        cla_unidad_medida cla_unidad = (cla_unidad_medida) cbx_unidad_medida.getSelectedItem();
        c_producto.setId_unidad(cla_unidad.getId());

        c_presentacion = new cl_productos_presentacion();

        boolean realizado = false;

        if (registrar) {
            c_producto.obtener_codigo();
            realizado = c_producto.registrar();
        } else {
            realizado = c_producto.modificar();
        }

        c_presentacion.setId_producto(c_producto.getId());

        if (realizado) {
            if (!registrar) {
                c_presentacion.eliminar_todo();
            }
           /* int contar_presentaciones = t_presentaciones.getRowCount();
            for (int i = 0; i < contar_presentaciones; i++) {
                c_presentacion.setId_presentacion(i + 1);
                c_presentacion.setNombre(t_presentaciones.getValueAt(i, 1).toString());
                c_presentacion.setFactor(Double.parseDouble(t_presentaciones.getValueAt(i, 2).toString()));
                c_presentacion.setPrecio(Double.parseDouble(t_presentaciones.getValueAt(i, 3).toString()));
                c_presentacion.insertar();
            }*/
            this.dispose();
        }
    }//GEN-LAST:event_btn_guardarActionPerformed

    private void txt_descripcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_descripcionKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String texto = txt_descripcion.getText();
            if (texto.length() > 0) {
                cbx_unidad_medida.setEnabled(true);
                cbx_unidad_medida.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_descripcionKeyPressed

    private void cbx_clasificacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbx_clasificacionKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_descripcion.setEnabled(true);
            txt_descripcion.requestFocus();
        }
    }//GEN-LAST:event_cbx_clasificacionKeyPressed

    private void cbx_clasificacionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_clasificacionItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_clasificacionItemStateChanged

    private void txt_precio_minimoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_precio_minimoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_precio_minimo.isEnabled() && txt_precio_minimo.isEditable()) {
                String texto = txt_precio_minimo.getText();
                if (c_varios.esDecimal(texto)) {
                    txt_precio_minimo.setEditable(false);
                    cla_unidad_medida cla_unidad = (cla_unidad_medida) cbx_unidad_medida.getSelectedItem();

                    //crear objeto unidad 
                    Object fila[] = new Object[4];
                    fila[0] = 1;
                    fila[1] = cla_unidad.getNombre();
                    fila[2] = 1;
                    fila[3] = Double.parseDouble(texto);
                    detalle.addRow(fila);

                    //pasar a presentacion y activar boton grabar
                    btn_guardar.setEnabled(true);
                    //txt_proveedor.setEnabled(true);
                    //txt_nombre_presentacion.selectAll();
                    //txt_proveedor.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_txt_precio_minimoKeyPressed

    private void txt_precio_minimoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_precio_minimoKeyTyped
        c_varios.solo_precio(evt);
    }//GEN-LAST:event_txt_precio_minimoKeyTyped

    private void cbx_unidad_medidaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbx_unidad_medidaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (registrar) {
                if (!txt_precio_minimo.isEditable() && txt_precio_minimo.isEnabled()) {
                   txt_precio_minimo.setEnabled(true);
                    txt_precio_minimo.requestFocus();
                }
                if (!txt_precio_minimo.isEnabled()) {
                    txt_precio_minimo.setEnabled(true);
                    txt_precio_minimo.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_cbx_unidad_medidaKeyPressed

    private void cbx_clasificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_clasificacionActionPerformed
  
    }//GEN-LAST:event_cbx_clasificacionActionPerformed

    private void txt_proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_proveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_proveedorActionPerformed

    /**
     * @param args the command line arguments
                     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frm_reg_producto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_reg_producto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_reg_producto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_reg_producto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frm_reg_producto dialog = new frm_reg_producto(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_guardar;
    private javax.swing.JComboBox<String> cbx_clasificacion;
    private javax.swing.JComboBox<String> cbx_unidad_medida;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField txt_descripcion;
    private javax.swing.JTextField txt_precio_minimo;
    private javax.swing.JTextField txt_proveedor;
    // End of variables declaration//GEN-END:variables
}
