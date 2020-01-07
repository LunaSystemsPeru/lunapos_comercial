/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import clases.cl_conectar;
import clases.cl_movimiento_banco;
import clases.cl_proveedor;
import clases.cl_proveedor_pago;
import clases.cl_varios;
import clases_autocomplete.cla_cliente;
import com.mxrck.autocompleter.AutoCompleterCallback;
import com.mxrck.autocompleter.TextAutoCompleter;
import com.sun.glass.events.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Usuario
 */
public class frm_reg_pagos_proveedor extends javax.swing.JDialog {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    cl_proveedor c_proveedor = new cl_proveedor();

    cl_proveedor_pago c_pago = new cl_proveedor_pago();
    cl_movimiento_banco c_movimiento = new cl_movimiento_banco();

    TextAutoCompleter tac_proveedor = null;

    /**
     * Creates new form frm_reg_pagos_cliente
     */
    public frm_reg_pagos_proveedor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        cargar_clientes();
    }

    public frm_reg_pagos_proveedor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void cargar_clientes() {
        try {
            if (tac_proveedor != null) {
                tac_proveedor.removeAllItems();
            }
            //TIPO:
            //1 para separacion
            // 2 para facturas
            // 3 para boleta
            JTextField text_box = txt_buca_clie;
            String sql = "select id_proveedor, razon_social "
                    + "from proveedor ";

            tac_proveedor = new TextAutoCompleter(text_box, new AutoCompleterCallback() {
                @Override
                public void callback(Object selectedItem) {
                    Object itemSelected = selectedItem;
                    c_proveedor.setId_proveedor(0);
                    if (itemSelected instanceof cla_cliente) {
                        int ccodigo = ((cla_cliente) itemSelected).getId_cliente();
                        String cnombre = ((cla_cliente) itemSelected).getNombre();
                        System.out.println("proveeor seleccionado " + cnombre);
                        c_proveedor.setId_proveedor(ccodigo);
                    } else {
                        System.out.println("El item es de un tipo desconocido");
                    }
                }
            });

            tac_proveedor.setMode(0);
            tac_proveedor.setCaseSensitive(false);
            Statement st = c_conectar.conexion();
            ResultSet rs = c_conectar.consulta(st, sql);
            while (rs.next()) {
                int id_cliente = rs.getInt("id_proveedor");
                String descripcion = "";
                descripcion = rs.getString("razon_social");
                tac_proveedor.addItem(new cla_cliente(id_cliente, descripcion));
            }
            c_conectar.cerrar(rs);
            c_conectar.cerrar(st);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e.getLocalizedMessage());
            System.out.println(e.getLocalizedMessage());
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txt_total_deud = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_apagar = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        txt_fecha_pago = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        txt_buca_clie = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        btn_grabar = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pagar Proveedor");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Agregar Pago"));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Total Deuda:");

        txt_total_deud.setForeground(new java.awt.Color(0, 0, 0));
        txt_total_deud.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_total_deud.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_total_deud.setEnabled(false);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Fecha:");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("A Pagar:");

        txt_apagar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_apagar.setEnabled(false);
        txt_apagar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_apagarKeyPressed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/find.png"))); // NOI18N
        jButton3.setText("Ver Deuda");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setEnabled(false);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/find.png"))); // NOI18N
        jButton4.setText("Ver Pagos");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setEnabled(false);

        try {
            txt_fecha_pago.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txt_fecha_pago.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_fecha_pago.setEnabled(false);
        txt_fecha_pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_fecha_pagoKeyPressed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Buscar Proveedor:");

        txt_buca_clie.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_buca_clieKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_fecha_pago, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                            .addComponent(txt_apagar, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_total_deud, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 289, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(txt_buca_clie))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_buca_clie, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_total_deud, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_fecha_pago, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_apagar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jToolBar1.setFloatable(false);

        btn_grabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/accept.png"))); // NOI18N
        btn_grabar.setText("Grabar");
        btn_grabar.setEnabled(false);
        btn_grabar.setFocusable(false);
        btn_grabar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_grabar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_grabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_grabarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_grabar);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cross.png"))); // NOI18N
        jButton7.setText("Salir");
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton7);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_buca_clieKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buca_clieKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (c_proveedor.getId_proveedor() != 0) {
                jButton3.setEnabled(true);
                jButton4.setEnabled(true);
                c_proveedor.cargar_datos();
                txt_total_deud.setText(c_varios.formato_totales(c_proveedor.getTcompra() - c_proveedor.getTpagado()));
                txt_fecha_pago.setEnabled(true);
                txt_fecha_pago.setText(c_varios.fecha_usuario(c_varios.getFechaActual()));
                txt_fecha_pago.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_buca_clieKeyPressed

    private void txt_fecha_pagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fecha_pagoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_fecha_pago.getText().length() == 10) {
                txt_apagar.setEnabled(true);
                txt_apagar.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_fecha_pagoKeyPressed

    private void txt_apagarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_apagarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (c_varios.esDecimal(txt_apagar.getText())) {
                btn_grabar.setEnabled(true);
                btn_grabar.setFocusable(true);
                btn_grabar.requestFocus();

            } else {
                JOptionPane.showMessageDialog(null, "INGRESE UN NUMERO CORRECTO");
                txt_apagar.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_apagarKeyPressed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void btn_grabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_grabarActionPerformed
        c_movimiento.setId_banco(1);
        c_movimiento.setDescripcion("PAGO A CUENTA PARA " + c_proveedor.getRazon_social());
        c_movimiento.setFecha(c_varios.fecha_myql(txt_fecha_pago.getText()));
        c_movimiento.setIngresa(0);
        c_movimiento.setSale(Double.parseDouble(txt_apagar.getText()));
        c_movimiento.obtener_codigo();
        c_movimiento.registrar();

        c_pago.setId_proveedor(c_proveedor.getId_proveedor());
        c_pago.setId_movimiento(c_movimiento.getId_movimiento());
        c_pago.registrar();

        JOptionPane.showMessageDialog(null, "PAGO AGREGADO CORRECTAMENTE");
        this.dispose();
    }//GEN-LAST:event_btn_grabarActionPerformed

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
            java.util.logging.Logger.getLogger(frm_reg_pagos_proveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_reg_pagos_proveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_reg_pagos_proveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_reg_pagos_proveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frm_reg_pagos_proveedor dialog = new frm_reg_pagos_proveedor(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_grabar;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField txt_apagar;
    private javax.swing.JTextField txt_buca_clie;
    private javax.swing.JFormattedTextField txt_fecha_pago;
    private javax.swing.JTextField txt_total_deud;
    // End of variables declaration//GEN-END:variables
}
