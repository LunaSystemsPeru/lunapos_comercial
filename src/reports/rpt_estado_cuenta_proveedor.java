/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports;

import clases.cl_conectar;
import clases.cl_ingresos;
import clases.cl_proveedor;
import clases.cl_proveedor_pago;
import clases.cl_varios;
import clases_autocomplete.cla_estado_cliente;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 *
 * @author luis
 */
public class rpt_estado_cuenta_proveedor {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    cl_ingresos c_ingreso = new cl_ingresos();
    cl_proveedor c_proveedor = new cl_proveedor();
    cl_proveedor_pago c_pago = new cl_proveedor_pago();

    private int id_proveedor;
    private String inicio;

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }


    public rpt_estado_cuenta_proveedor() {
    }

    private void llenar_comparator(ArrayList myList) {
        Collections.sort(myList, (cla_estado_cliente t, cla_estado_cliente t1)
                -> t.getFecha().compareTo(t1.getFecha())
        );
        //return myList;
    }

    public ArrayList generar_arraylist() throws SQLException {
        /*
        rs_ventas.getString("descripcion") + " | CANT: " + rs_ventas.getString("cantidad") + " x P.U. " + rs_ventas.getString("precio")
         */
        c_ingreso.setId_proveedor(id_proveedor);
        c_pago.setId_proveedor(id_proveedor);

        ArrayList mylista = new ArrayList();

        double saldo_compra = c_ingreso.ingresos_cuenta_anterior(inicio);
        double saldo_pago = c_pago.pagos_cuenta_anterior(inicio);
        double saldo_actual = saldo_compra - saldo_pago;
        double ingresa = 0;
        double sale = 0;
        
        if (saldo_actual > 0) {
            sale = saldo_actual;
        } else {
            ingresa = saldo_actual;
        }

        //array con saldo anterior;
        mylista.add(new cla_estado_cliente(
                Date.valueOf(inicio),
                "",
                "SALDO ANTERIOR",
                ingresa,
                sale
        ));

        ResultSet rs_ingresos = c_ingreso.ingresos_periodo_cuenta(inicio);
        while (rs_ingresos.next()) {
            mylista.add(new cla_estado_cliente(
                    rs_ingresos.getDate("fecha"),
                    rs_ingresos.getString("abreviado") + "-" + rs_ingresos.getString("serie") + "-" + c_varios.ceros_izquieda_letras(5, rs_ingresos.getString("numero")),
                    rs_ingresos.getString("descripcion") + " | " + rs_ingresos.getString("cantidad") + " x " + c_varios.formato_numero(rs_ingresos.getDouble("costo")),
                    0,
                    rs_ingresos.getDouble("cantidad") * rs_ingresos.getDouble("costo")
            )
            );
        }

        ResultSet rs_pagos = c_pago.pagos_periodo(inicio);
        while (rs_pagos.next()) {
            mylista.add(new cla_estado_cliente(
                    rs_pagos.getDate("fecha"),
                    "-",
                    "PAGO A CUENTA",
                    rs_pagos.getDouble("sale"),
                    0
            )
            );
        }

        llenar_comparator(mylista);

        return mylista;
    }

    public void crear_reporte() throws FileNotFoundException, DocumentException {
        //iniciar datos del cliente
        c_proveedor.setId_proveedor(id_proveedor);
        c_proveedor.cargar_datos();

        // Se crea el documento
        Document documento = new Document();
        
         java.util.Date date = new java.util.Date();
        DateFormat hourdateFormat = new SimpleDateFormat("_dd_MM_yyyy_HH_mm_ss");
        String fechahora = hourdateFormat.format(date);

        // Se crea el OutputStream para el fichero donde queremos dejar el pdf.
        FileOutputStream ficheroPdf = new FileOutputStream("fichero_proveedor_"+fechahora+".pdf");

        // Se asocia el documento al OutputStream y se indica que el espaciado entre
        // lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
        PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20);

        // Se abre el documento.
        documento.open();

        documento.add(new Paragraph("Reporte de Estado de Cuenta del Proveedor", FontFactory.getFont("Arial Narrow", 16)));
        documento.add(new Paragraph("Proveedor: " + c_proveedor.getRazon_social(), FontFactory.getFont("Arial Narrow", 12)));
        documento.add(new Paragraph("Fecha Inicio : " + c_varios.fecha_usuario(inicio) + " - Fecha Fin: " + c_varios.fecha_usuario(c_varios.getFechaActual()), FontFactory.getFont("Arial Narrow", 12)));
        documento.add(new Paragraph(Chunk.NEWLINE));

        PdfPTable tabla = new PdfPTable(5);

        Font fuente_celda = FontFactory.getFont("Arial Narrow", 10, Font.BOLD);

        tabla.getDefaultCell().setBorder(0);
        tabla.setWidthPercentage(100.0f);

        tabla.setWidths(new int[]{12, 52, 12, 12, 12});

        //titulos
        tabla.addCell(new PdfPCell((Phrase) new Paragraph("Fecha", fuente_celda)));
        tabla.addCell(new PdfPCell((Phrase) new Paragraph("Descripcion", fuente_celda)));
        tabla.addCell(new PdfPCell((Phrase) new Paragraph("Pagado", fuente_celda)));
        tabla.addCell(new PdfPCell((Phrase) new Paragraph("Deuda", fuente_celda)));
        tabla.addCell(new PdfPCell((Phrase) new Paragraph("Saldo", fuente_celda)));
        //c_venta.setId_cliente(c_cliente.getCodigo());
        fuente_celda = FontFactory.getFont("Arial Narrow", 10, Font.NORMAL);

        try {
            ArrayList milista = this.generar_arraylist();
            Iterator<cla_estado_cliente> it = milista.iterator();
            double saldo = 0;

            while (it.hasNext()) {
                cla_estado_cliente cla_estado = it.next();
                saldo += (cla_estado.getVenta() - cla_estado.getPagado());

                String pagado = c_varios.formato_totales(cla_estado.getPagado());
                String descripcion = cla_estado.getDocumento() + " " + cla_estado.getDescripcion();
                if (cla_estado.getPagado() == 0) {
                    pagado = "";
                    fuente_celda = FontFactory.getFont("Arial Narrow", 10, Font.NORMAL);
                }

                String compra = c_varios.formato_totales(cla_estado.getVenta());
                if (cla_estado.getVenta() == 0) {
                    compra = "";
                    descripcion = cla_estado.getDescripcion();
                    fuente_celda = FontFactory.getFont("Arial Narrow", 10, Font.BOLD);
                }

                PdfPCell celda_1 = new PdfPCell((Phrase) new Paragraph(c_varios.fecha_usuario(cla_estado.getFecha().toString()), fuente_celda));
                PdfPCell celda_2 = new PdfPCell((Phrase) new Paragraph(descripcion, fuente_celda));
                PdfPCell celda_3 = new PdfPCell((Phrase) new Paragraph(pagado, fuente_celda));
                PdfPCell celda_4 = new PdfPCell((Phrase) new Paragraph(compra, fuente_celda));
                PdfPCell celda_5 = new PdfPCell((Phrase) new Paragraph(c_varios.formato_totales(saldo), fuente_celda));

                celda_3.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                celda_4.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                celda_5.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

                tabla.addCell(celda_1);
                tabla.addCell(celda_2);
                tabla.addCell(celda_3);
                tabla.addCell(celda_4);
                tabla.addCell(celda_5);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        documento.add(tabla);
        documento.close();

        try {
            String direccion = c_varios.obtenerDireccionCarpeta();
            File file = new File(direccion + File.separator + "temp" + File.separator + "fichero_proveedor_"+fechahora+".pdf");
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            System.out.print(e + " -- error io");
            JOptionPane.showMessageDialog(null, "Error al Generar el PDF -- \n" + e.getLocalizedMessage());
        }

    }

}
