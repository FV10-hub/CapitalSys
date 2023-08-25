package com.py.controller;

import com.py.jpa.BsEmpresas;
import com.py.jpa.BsPersonas;
import com.py.jpa.CobClientes;
import com.py.jpa.CobTipoCliente;
import com.py.utils.GenericBigLazyList;
import com.py.utils.GenericController;
import com.py.utils.GenericMensajes;
import com.py.utils.GenericoEJB;
import com.py.utils.ImprimirReportes;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Fernando
 */
@Named
@ViewScoped
public class BsRptEmpresasController extends GenericMensajes implements Serializable {

    Logger logger = Logger.getLogger(BsTimbradosController.class);
    @PersistenceContext
    private EntityManager em;

    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyEmpresaDG;
    private BsEmpresas bsEmpresa;
    private ImprimirReportes imprimirReporte;
    private String formato;

    @Inject
    private BsLogin login;

    public void limpiar() {
        bsEmpresa = null;
        imprimirReporte = new ImprimirReportes();
        ayuda = null;
        acercaDe = null;
    }

    @PostConstruct
    private void ini() {
        limpiar();
    }

    public void codEmpresaChange(ValueChangeEvent emp) {
        if (emp.getNewValue() != null && !(emp.getOldValue() == null && emp.getNewValue().toString().equals(""))) {
            try {
                Query listener = em.createQuery("SELECT b FROM BsEmpresas b WHERE b.id = :id", BsEmpresas.class);
                listener.setParameter("id", emp.getNewValue());
                bsEmpresa = (BsEmpresas) listener.getSingleResult();

            } catch (Exception ex) {
                mensajeError("Ocurrio Un Error al Listener Empresa");
                ex.printStackTrace(System.err);
                logger.error("Ocurrio un error Listener Empresa Loger ", ex);
            }
        }
    }

    public void onRowSelectEmpresa(SelectEvent em) {
        bsEmpresa = (BsEmpresas) em.getObject();
    }

    public void imprimir() {

        HashMap<Object, Object> parametros = new HashMap<Object, Object>();
        parametros.put("p_id_empresa", bsEmpresa.getId() != null ? bsEmpresa.getId() : null);
        parametros.put("descEmpresa", bsEmpresa.getId() != null ? bsEmpresa.getRepLegal() : null);
        parametros.put("impresoPor", login.getBsUsuario() != null ? login.getBsUsuario().getCodUsuario() : null);

        imprimirReporte.setParametros(parametros);
        imprimirReporte.setNombreReporte("BsRptEmpresas");
        if (formato.equals("PDF")) {
            imprimirReporte.imprimir();
        } else {
            imprimirReporte.imprimirXLS();
        }
    }

//<editor-fold defaultstate="collapsed" desc="Getters&Setters">
    public ArrayList<String> getAyuda() {
        if (ayuda == null || ayuda.isEmpty()) {
            ayuda = new ArrayList<>();
            ayuda.add("Esta pantalla puede ser utilizada para Reportes de Clientes del sistema.");
            ayuda.add("");
            ayuda.add(" Para agregar debe hacar un click en el boton nuevo.");
            ayuda.add(" Para modificar, seleccionar en la grilla con un click, realizar los cambios y presionar guardar.");
            ayuda.add(" Para eliminar, seleccionar en la grilla con un click, luego presionar eliminar.");
        }
        return ayuda;
    }

    public void setAyuda(ArrayList<String> ayuda) {
        this.ayuda = ayuda;
    }

    public ArrayList<String> getAcercaDe() {
        if (acercaDe == null || acercaDe.isEmpty()) {
            acercaDe = new ArrayList<>();
            acercaDe.add("Datos de la pantalla:");
            acercaDe.add("- Pantalla: CobRptClientes.xhtml");
            acercaDe.add("- Controlador: CobRptClientesController.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public BsEmpresas getBsEmpresa() {
        if (bsEmpresa == null) {
            bsEmpresa = new BsEmpresas();
            bsEmpresa.setBsPersonas(new BsPersonas());
        }
        return bsEmpresa;
    }

    public void setBsEmpresa(BsEmpresas bsEmpresa) {
        this.bsEmpresa = bsEmpresa;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    public LazyDataModel getLazyEmpresaDG() {
        if (lazyEmpresaDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyEmpresaDG = genericLazy.getLazyDataModel(BsEmpresas.class, campos);
        }
        return lazyEmpresaDG;
    }

    public void setLazyEmpresaDG(LazyDataModel lazyEmpresaDG) {
        this.lazyEmpresaDG = lazyEmpresaDG;
    }

//</editor-fold>
}
