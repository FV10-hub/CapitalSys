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
public class CreRptSolicitudesCreditoController extends GenericMensajes implements Serializable {

    Logger logger = Logger.getLogger(CreRptSolicitudesCreditoController.class);
    @PersistenceContext
    private EntityManager em;

    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyEmpresaDG;
    private CobClientes clientesDesde, clienteHasta;
    private BsEmpresas bsEmpresa;
    private LazyDataModel lazyClienteDDG;
    private LazyDataModel lazyClienteHDG;
    private ImprimirReportes imprimirReporte;
    private String formato;
    private Date fechaIni;
    private Date fechaFin;
    private String estado;

    @Inject
    private BsLogin login;
    

    public void limpiar() {
        bsEmpresa = null;
        clientesDesde = null;
        clienteHasta = null;
        imprimirReporte = new ImprimirReportes();
        ayuda = null;
        acercaDe = null;
        fechaIni = null;//new Date();
        fechaFin = null;//new Date();
    }

    @PostConstruct
    private void ini() {
        limpiar();
    }

    public void codClienteChange(ValueChangeEvent cli) {
        if (cli.getNewValue() != null && !(cli.getOldValue() == null && cli.getNewValue().toString().equals(""))) {
            try {
                Query listener = em.createQuery("SELECT c FROM CobClientes c WHERE c.bsEmpresas.id = :idEmpresa and c.codCliente = :codCliente", CobClientes.class);
                listener.setParameter("idEmpresa", bsEmpresa.getId());
                listener.setParameter("codCliente", cli.getNewValue());
                clientesDesde = (CobClientes) listener.getSingleResult();

            } catch (Exception ex) {
                mensajeError("Ocurrio Un Error al Listener cliente");
                ex.printStackTrace(System.err);
                logger.error("Ocurrio un error Listener cliente Loger ", ex);
            }
        }
    }

    public void codClienteChangeHasta(ValueChangeEvent cli) {
        if (cli.getNewValue() != null && !(cli.getOldValue() == null && cli.getNewValue().toString().equals(""))) {
            try {
                Query listener = em.createQuery("SELECT c FROM CobClientes c WHERE c.bsEmpresas.id = :idEmpresa and c.codCliente = :codCliente", CobClientes.class);
                listener.setParameter("idEmpresa", bsEmpresa.getId());
                listener.setParameter("codCliente", cli.getNewValue());
                clienteHasta = (CobClientes) listener.getSingleResult();

            } catch (Exception ex) {
                mensajeError("Ocurrio Un Error al Listener cliente");
                ex.printStackTrace(System.err);
                logger.error("Ocurrio un error Listener cliente Loger ", ex);
            }
        }
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

    public void onRowSelectClienteDesde(SelectEvent ep) {
        clientesDesde = (CobClientes) ep.getObject();
    }

    public void onRowSelectEmpresa(SelectEvent em) {
        bsEmpresa = (BsEmpresas) em.getObject();
    }

    public void onRowSelectClienteHasta(SelectEvent ep) {
        clienteHasta = (CobClientes) ep.getObject();
    }

    public void imprimir() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        HashMap<Object, Object> parametros = new HashMap<Object, Object>();
        parametros.put("p_id_empresa", bsEmpresa.getId() != null ? bsEmpresa.getId() : null);
        parametros.put("descEmpresa", bsEmpresa.getId() != null ? bsEmpresa.getRepLegal() : null);
        parametros.put("p_id_clienteD", clientesDesde.getId() != null ? clientesDesde.getId() : null);
        parametros.put("nomClienteD", clientesDesde.getCodCliente() != null ? clientesDesde.getBsPersonas().getNombreFantasia() : null);
        parametros.put("p_id_clienteH", clienteHasta.getId() != null ? clienteHasta.getId() : null);
        parametros.put("p_fecha_ini", "'"+sdf.format(fechaIni)+"'");
        parametros.put("p_fecha_fin",  "'"+sdf.format(fechaFin)+"'");
         parametros.put("p_estado", "'"+estado+"'");
        parametros.put("nomClienteH", clienteHasta.getCodCliente() != null ? clienteHasta.getBsPersonas().getNombreFantasia() : null);
        parametros.put("impresoPor", login.getBsUsuario() != null ? login.getBsUsuario().getCodUsuario() : null);

        imprimirReporte.setParametros(parametros);
        imprimirReporte.setNombreReporte("CreRptSolicitudCredito");
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

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        System.out.println("paso el set ini "+fechaIni);
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        System.out.println("paso el set fin "+fechaFin);
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public CobClientes getClientesDesde() {
        if (clientesDesde == null) {
            clientesDesde = new CobClientes();
            clientesDesde.setBsPersonas(new BsPersonas());
            clientesDesde.setCobTipoCliente(new CobTipoCliente());
            clientesDesde.setBsEmpresas(new BsEmpresas());
            clientesDesde.getBsEmpresas().setBsPersonas(new BsPersonas());
        }
        return clientesDesde;
    }

    public void setClientesDesde(CobClientes clientesDesde) {
        this.clientesDesde = clientesDesde;
    }

    public CobClientes getClienteHasta() {
        if (clienteHasta == null) {
            clienteHasta = new CobClientes();
            clienteHasta.setBsPersonas(new BsPersonas());
            clienteHasta.setCobTipoCliente(new CobTipoCliente());
            clienteHasta.setBsEmpresas(new BsEmpresas());
            clienteHasta.getBsEmpresas().setBsPersonas(new BsPersonas());
        }
        return clienteHasta;
    }

    public void setClienteHasta(CobClientes clienteHasta) {
        this.clienteHasta = clienteHasta;
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

    public LazyDataModel getLazyClienteDDG() {
        if (lazyClienteDDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyClienteDDG = genericLazy.getLazyDataModel(CobClientes.class, campos);
        }
        return lazyClienteDDG;
    }

    public void setLazyClienteDDG(LazyDataModel lazyClienteDDG) {
        this.lazyClienteDDG = lazyClienteDDG;
    }

    public LazyDataModel getLazyClienteHDG() {
        if (lazyClienteHDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyClienteDDG = genericLazy.getLazyDataModel(CobClientes.class, campos);
        }
        return lazyClienteHDG;
    }

    public void setLazyClienteHDG(LazyDataModel lazyClienteHDG) {
        this.lazyClienteHDG = lazyClienteHDG;
    }

//</editor-fold>
}
