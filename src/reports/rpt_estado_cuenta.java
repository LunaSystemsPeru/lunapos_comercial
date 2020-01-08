/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports;

import clases.cl_cliente;
import clases.cl_cliente_pago;
import clases.cl_conectar;
import clases.cl_varios;
import clases.cl_venta;
import clases_autocomplete.cla_estado_cliente;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author luis
 */
public class rpt_estado_cuenta {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    cl_venta c_venta = new cl_venta();
    cl_cliente c_cliente = new cl_cliente();
    cl_cliente_pago c_pago = new cl_cliente_pago();

    private int id_cliente;
    private String inicio;
    private String fin;

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public rpt_estado_cuenta() {
    }

    private void llenar_comparator(ArrayList myList) {
        Collections.sort(myList, (cla_estado_cliente t, cla_estado_cliente t1)
                -> t.getFecha().compareTo(t1.getFecha())
        );
        //return myList;
    }

    public ArrayList generar_arraylist() throws SQLException {
        ArrayList mylista = new ArrayList();
        c_venta.setId_cliente(id_cliente);
        ResultSet rs_ventas = c_venta.ventas_periodo_cuenta(inicio, fin);
        while (rs_ventas.next()) {
            mylista.add(new cla_estado_cliente(
                    rs_ventas.getDate("fecha"),
                    rs_ventas.getString("abreviado") + "-" + rs_ventas.getString("serie") + "-" + rs_ventas.getString("numero"),
                    rs_ventas.getString("descripcion") + " | CANT: " + rs_ventas.getString("cantidad") + " x P.U. " + rs_ventas.getString("precio"),
                    0,
                    rs_ventas.getDouble("cantidad") * rs_ventas.getDouble("precio")
            )
            );
        }

        c_pago.setId_cliente(id_cliente);
        ResultSet rs_pagos = c_pago.pagos_periodo(inicio, fin);
        while (rs_pagos.next()) {
            mylista.add(new cla_estado_cliente(
                    rs_pagos.getDate("fecha"),
                    "-",
                    rs_pagos.getString("descripcion"),
                    rs_pagos.getDouble("ingresa"),
                    0
            )
            );
        }

        llenar_comparator(mylista);

        /*try {
            Iterator<cla_estado_cliente> it = mylista.iterator();
            while (it.hasNext()) {
                //    System.out.println(it.next());

            }
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
         */
        return mylista;
    }

    public void crear_reporte() throws FileNotFoundException, DocumentException {
        //iniciar datos del cliente
        c_cliente.setCodigo(id_cliente);
        c_cliente.comprobar_cliente();

        // Se crea el documento
        Document documento = new Document();

        // Se crea el OutputStream para el fichero donde queremos dejar el pdf.
        FileOutputStream ficheroPdf = new FileOutputStream("fichero.pdf");

        // Se asocia el documento al OutputStream y se indica que el espaciado entre
        // lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
        PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20);

        // Se abre el documento.
        documento.open();

        documento.add(new Paragraph("Reporte de Estado de Cuenta del Cliente", FontFactory.getFont("Arial Narrow", 20)));
        documento.add(new Paragraph("Cliente: " + c_cliente.getNombre(), FontFactory.getFont("Arial Narrow", 16)));
        documento.add(new Paragraph("Fecha Inicio : " + inicio + " - Fecha Fin: " + fin, FontFactory.getFont("Arial Narrow", 14)));
        documento.add(new Paragraph(Chunk.NEWLINE));

        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100.0f);

        tabla.setWidths(new int[]{12, 12, 40, 12, 12, 12});

        //titulos
        tabla.addCell("FECHA");
        tabla.addCell("DOCUMENTO");
        tabla.addCell("DESCRIPCION");
        tabla.addCell("PAGADO");
        tabla.addCell("DEUDA");
        tabla.addCell("SALDO");
        //c_venta.setId_cliente(c_cliente.getCodigo());

        try {
            ArrayList milista = this.generar_arraylist();
            Iterator<cla_estado_cliente> it = milista.iterator();
            double saldo = 0;
            while (it.hasNext()) {
                cla_estado_cliente cla_estado = it.next();
                saldo += (cla_estado.getVenta() - cla_estado.getPagado());
                //System.out.println(it.next());
                tabla.addCell(cla_estado.getFecha().toString());
                tabla.addCell(cla_estado.getDocumento());
                tabla.addCell(cla_estado.getDescripcion());

                PdfPCell celda_4 = new PdfPCell((Phrase) new Paragraph(c_varios.formato_totales(cla_estado.getPagado())));
                celda_4.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                tabla.addCell(celda_4);
                PdfPCell celda_5 = new PdfPCell((Phrase) new Paragraph(c_varios.formato_totales(cla_estado.getVenta())));
                celda_5.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                tabla.addCell(celda_5);
                PdfPCell celda_6 = new PdfPCell((Phrase) new Paragraph(c_varios.formato_totales(saldo)));
                celda_6.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                tabla.addCell(celda_6);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        documento.add(tabla);
        documento.close();

        try {
            String direccion = c_varios.obtenerDireccionCarpeta();
            File file = new File(direccion + File.separator + "fichero.pdf");
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            System.out.print(e + " -- error io");
            JOptionPane.showMessageDialog(null, "Error al Generar el PDF -- \n" + e.getLocalizedMessage());
        }

    }

}
