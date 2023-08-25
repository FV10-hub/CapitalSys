package com.py.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.apache.log4j.Logger;

/**
 *
 * @author Fernando
 */
public class ImprimirReportes {

    Logger logger = Logger.getLogger(ImprimirReportes.class);
    private String nombreReporte;
    private String nombreReporteSinJasper;
    private Map<Object, Object> parametros;
    private FacesContext faces;
    private HttpServletResponse response;
    SimpleDateFormat sdf;
    private String nombreImagen = ConstantesAplicacion.NOMBRELOGO;
    Connection conn;
    private String reporteDescarga = "attachment; filename=\"";
    private String reporteInline = "inline; filename=\"";

    public ImprimirReportes() {
        nombreReporte = "PonerNombreReporte";
        parametros = new HashMap<>();
        sdf = new SimpleDateFormat("dd/MM/yyyy-hh:mm");
    }

    public void imprimir() {
        String path = "";
        String imagenPath = "";
        String separator = File.separator;
        try {
            if (separator.equals(ConstantesAplicacion.SEPARADOR_WINDOWS)) {
                path = ConstantesAplicacion.CARPETA_REPORTES_WINDOWS;
                imagenPath = ConstantesAplicacion.CARPETA_WINDOWS;
                parametros.put("SUBREPORT_DIR", ConstantesAplicacion.CARPETA_REPORTES_WINDOWS);
            } else {
                path = ConstantesAplicacion.CARPETA_REPORTES_LINUX;
                imagenPath = ConstantesAplicacion.CARPETA_LINUX;
                parametros.put("SUBREPORT_DIR", ConstantesAplicacion.CARPETA_REPORTES_LINUX);
            }

            nombreReporteSinJasper = nombreReporte; // para q al imprimir el nombre del reporte no aparezca con la extension .jasper
            nombreReporte = nombreReporte + ".jasper";
            path += separator + nombreReporte; //path de los reportes
            parametros.put("imagenPath", imagenPath + separator);
            parametros.put("nombreImagen", nombreImagen);
            parametros.put("DiaHora", BaseCalendar.getAhora());
            JasperReport reporte = (JasperReport) JRLoader.loadObject(path);//aca explota
            getConnectionDS();
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, conn);
            generarPdf(jasperPrint);

        } catch (Exception e) {
            e.printStackTrace(System.err);
            logger.error("Error de accesso al archivo", e);
            System.out.println("Error al imprimir reporte " + e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
                logger.error("Error de accesso al archivo", ex);
                System.out.println("Error al imprimir reporte " + ex);
            }
        }
    }

    private void generarPdf(JasperPrint jp) throws Exception {
        faces = FacesContext.getCurrentInstance();
        byte[] report = JasperExportManager.exportReportToPdf(jp);
        response = (HttpServletResponse) faces.getExternalContext().getResponse();
        response.setHeader("Content-disposition", "attachment; filename=\"" + nombreReporteSinJasper + sdf.format(System.currentTimeMillis()) + ".pdf\"");
        response.setContentType("application/pdf");
        response.setContentLength(report.length);
        ServletOutputStream out = response.getOutputStream();
        out.write(report);
        faces.responseComplete();
        out.flush();
        out.close();
    }

    public void imprimirXLS() {
        String path = "";
        String imagenPath = "";
        String separator = File.separator;
        ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
        try {
            if (separator.equals(ConstantesAplicacion.SEPARADOR_WINDOWS)) {
                path = ConstantesAplicacion.CARPETA_REPORTES_WINDOWS;
                imagenPath = ConstantesAplicacion.CARPETA_WINDOWS;
                parametros.put("SUBREPORT_DIR", ConstantesAplicacion.CARPETA_REPORTES_WINDOWS);
            } else {
                path = ConstantesAplicacion.CARPETA_REPORTES_LINUX;
                imagenPath = ConstantesAplicacion.CARPETA_LINUX;
                parametros.put("SUBREPORT_DIR", ConstantesAplicacion.CARPETA_REPORTES_LINUX);
            }

            parametros.put("imagenPath", imagenPath + separator);
            parametros.put("nombreImagen", nombreImagen);
            parametros.put("DiaHora", BaseCalendar.getAhora());

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(path + separator + nombreReporte + ".jasper");
            getConnectionDS();
            JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametros, conn);
            jp.setProperty("net.sf.jasperreports.export.xls.ignore.graphics", "true");

            generarExcel(jp, xlsReport);

        } catch (JRException jre) {
            logger.error("Error al intentar generar el reporte.", jre);
        } catch (Exception e) {
            logger.error("Error al intentar generar el reporte.", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                logger.error("Error al intentar cerrar la conexion.", ex);
            }
        }
    }

    private void generarExcel(JasperPrint jp, ByteArrayOutputStream byteReporte) throws JRException, Exception {
        faces = FacesContext.getCurrentInstance();
        JRXlsExporter xlsExporter = new JRXlsExporter();
        response = (HttpServletResponse) faces.getExternalContext().getResponse();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=\"" + nombreReporte + sdf.format(System.currentTimeMillis()) + ".xls\"");
        xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
        xlsExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, byteReporte);
        xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        xlsExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        xlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);

        xlsExporter.exportReport();

        byte[] report = byteReporte.toByteArray();
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(report);
        faces.responseComplete();
        outputStream.flush();
        outputStream.close();
    }

    public void getConnectionDS() throws Exception {
        // Obtain the initial Java Naming and Directory Interface
        // (JNDI) context.
        InitialContext initCtx = new InitialContext();
        // Perform JNDI lookup to obtain the resource.
        DataSource ds = (DataSource) initCtx.lookup(ConstantesAplicacion.DATASOURCENAMEREP);
        conn = ds.getConnection();
    }

    public Map<Object, Object> getParametros() {
        return parametros;
    }

    public void setParametros(Map<Object, Object> parametros) {
        this.parametros = parametros;
    }

    public String getNombreReporte() {
        return nombreReporte;
    }

    public void setNombreReporte(String nombreReporte) {
        this.nombreReporte = nombreReporte;
    }

}
