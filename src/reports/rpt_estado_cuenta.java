/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports;

import clases.cl_conectar;
import clases.cl_varios;
import clases.cl_venta;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author luis
 */
public class rpt_estado_cuenta {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();
    cl_venta c_venta = new cl_venta();

    public void crear_reporte() throws FileNotFoundException, DocumentException {
        // Se crea el documento
        Document documento = new Document();

        // Se crea el OutputStream para el fichero donde queremos dejar el pdf.
        FileOutputStream ficheroPdf = new FileOutputStream("fichero.pdf");

        // Se asocia el documento al OutputStream y se indica que el espaciado entre
        // lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
        PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(20);

        // Se abre el documento.
        documento.open();

        documento.add(new Paragraph("Reporte de Estado de Cuenta del Cliente", FontFactory.getFont("arial", 22)));

        PdfPTable tabla = new PdfPTable(6);
        c_venta.setId_cliente(311);
        try {
            ResultSet rs_periodo = c_venta.ventas_periodo("2019-01-01", "2020-01-04");
            while (rs_periodo.next()) {
                PdfPCell celdaFinal = new PdfPCell(new Paragraph("Periodo " + rs_periodo.getString("nombremes") + " del " + rs_periodo.getString("anio")));
                celdaFinal.setColspan(6);
                tabla.addCell(celdaFinal);
                //tabla.addCell("Periodo " + rs_periodo.getString("nombremes") + " del " + rs_periodo.getString("anio"));
                ResultSet rs_ventas = c_venta.ventas_periodo_cuenta(rs_periodo.getInt("mes"), rs_periodo.getInt("anio"));
                while (rs_ventas.next()) {
                    tabla.addCell(rs_ventas.getString("fecha"));
                    tabla.addCell(rs_ventas.getString("abreviado") + " - " + rs_ventas.getString("serie")+ " - " + rs_ventas.getString("numero"));
                    tabla.addCell(rs_ventas.getString("descripcion"));
                    tabla.addCell("0.00");
                    tabla.addCell(c_varios.formato_numero(rs_ventas.getDouble("cantidad") * rs_ventas.getDouble("precio")));
                    tabla.addCell("0.00");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }

        documento.add(tabla);
        documento.close();
    }
}
