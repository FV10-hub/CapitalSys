package com.py.controller;

import com.py.jpa.BsEmpresas;
import com.py.jpa.BsMonedas;
import com.py.jpa.BsPersonas;
import com.py.jpa.BsSucursal;
import com.py.jpa.BsTalonarios;
import com.py.jpa.BsTimbrados;
import com.py.jpa.BsTipComprobantes;
import com.py.jpa.CobClientes;
import com.py.jpa.CrMotivosPrestamos;
import com.py.jpa.CreSolicitudes;
import com.py.jpa.CreTipoAmortizacion;
import com.py.jpa.VenVendedor;
import com.py.utils.GenericBigLazyList;
import com.py.utils.GenericController;
import com.py.utils.GenericMensajes;
import com.py.utils.GenericoEJB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Fernando
 */
@Named
@ViewScoped
public class CreSolcitudCreditoController extends GenericMensajes implements Serializable {

    Logger logger = Logger.getLogger(BsTimbradosController.class);
    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazySolcitudDT;
    private LazyDataModel lazyMonedaDG;
    private LazyDataModel lazyEmpresaDG;
    private LazyDataModel lazySucursalDG;
    private LazyDataModel lazyTalonarioDG;
    private LazyDataModel lazyClienteDG;
    private LazyDataModel lazyVendedorDG;
    private LazyDataModel lazyMotivosPreDG;

    private CrMotivosPrestamos motivoPrestamo;
    private CobClientes cliente;
    private VenVendedor vendedor;
    private BsSucursal sucursal;
    private BsMonedas moneda;
    private BsEmpresas empresa;
    private BsTalonarios talonario;
    private boolean estadoDisabled;
    private CreSolicitudes solicitud, solicitudSelected;

    @PostConstruct
    private void ini() {
        limpiar();
    }

//<editor-fold defaultstate="collapsed" desc="Metodos">
    public void limpiar() {
        motivoPrestamo = null;
        cliente = null;
        vendedor = null;
        sucursal = null;
        moneda = null;
        empresa = null;
        talonario = null;
        solicitud = null;
        solicitudSelected = null;
        esModificar = false;
        estadoDisabled = false;
//        ayuda = null;
//        acercaDe = null;
    }

    public void guardar() {
        try {

            if (genericEJB.insert(solicitud)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazySolcitudDT();
            } else {
                mensajeAlerta("No se pudo Guardar");
            }
            //       }
        } catch (Exception e) {
            mensajeError("Ocurrio un error");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un error Loger ", e);
        }
    }

    public void edit() {
        try {
            genericEJB.update(solicitud);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazySolcitudDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar la Solicitud");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un error Loger ", e);
        }
    }

    public void delete() {
        try {
            genericEJB.delete(solicitud);
            mensajeAlerta("Eliminado Correctamente!");
            getLazySolcitudDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar la Solicitud");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un error Loger ", e);
        }
    }

    public void onRowSelectEmpresa(SelectEvent em) {
        empresa = (BsEmpresas) em.getObject();
        solicitud.setBsEmpresas(empresa);
    }

    public void onRowSelectMotivoPrestamo(SelectEvent mp) {
        motivoPrestamo = (CrMotivosPrestamos) mp.getObject();
        solicitud.setCrMotivosPrestamos(motivoPrestamo);
    }

    public void onRowSelectCliente(SelectEvent c) {
        cliente = (CobClientes) c.getObject();
        solicitud.setCobClientes(cliente);
    }

    public void onRowSelectVendedor(SelectEvent v) {
        vendedor = (VenVendedor) v.getObject();
        solicitud.setVenVendedor(vendedor);
    }

    public void onRowSelectSucursal(SelectEvent s) {
        sucursal = (BsSucursal) s.getObject();
        solicitud.setBsSucursal(sucursal);
    }

    public void onRowSelectMoneda(SelectEvent m) {
        moneda = (BsMonedas) m.getObject();
        solicitud.setBsMonedas(moneda);
    }

    public void onRowSelectTalonario(SelectEvent t) {
        talonario = (BsTalonarios) t.getObject();
        solicitud.setBsTalonarios(talonario);
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters&Setters">

    public boolean isEsModificar() {
        return esModificar;
    }

    public void setEsModificar(boolean esModificar) {
        this.esModificar = esModificar;
    }

    public ArrayList<String> getAyuda() {
        if (ayuda == null) {
            ayuda = new ArrayList<>();
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Solicitudes del sistema.");
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
        if (acercaDe == null) {
            acercaDe = new ArrayList<>();
            acercaDe.add("Datos de la pantalla:");
            acercaDe.add("- Pantalla: CreSolicitudCredito.xhtml");
            acercaDe.add("- Controlador: CreSolcitudCreditoController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: CreSolcitudes.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public CrMotivosPrestamos getMotivoPrestamo() {
        if (motivoPrestamo == null) {
            motivoPrestamo = new CrMotivosPrestamos();
        }
        return motivoPrestamo;
    }

    public void setMotivoPrestamo(CrMotivosPrestamos motivoPrestamo) {
        this.motivoPrestamo = motivoPrestamo;
    }

    public boolean isEstadoDisabled() {
        return estadoDisabled;
    }

    public void setEstadoDisabled(boolean estadoDisabled) {
        this.estadoDisabled = estadoDisabled;
    }

    public CobClientes getCliente() {
        if (cliente == null) {
            cliente = new CobClientes();
            cliente.setBsPersonas(new BsPersonas());
        }
        return cliente;
    }

    public void setCliente(CobClientes cliente) {
        this.cliente = cliente;
    }

    public VenVendedor getVendedor() {
        if (vendedor == null) {
            vendedor = new VenVendedor();
            vendedor.setBsPersonas(new BsPersonas());
        }
        return vendedor;
    }

    public void setVendedor(VenVendedor vendedor) {
        this.vendedor = vendedor;
    }

    public BsSucursal getSucursal() {
        if (sucursal == null) {
            sucursal = new BsSucursal();
        }
        return sucursal;
    }

    public void setSucursal(BsSucursal sucursal) {
        this.sucursal = sucursal;
    }

    public BsMonedas getMoneda() {
        if (moneda == null) {
            moneda = new BsMonedas();
        }
        return moneda;
    }

    public void setMoneda(BsMonedas moneda) {
        this.moneda = moneda;
    }

    public BsEmpresas getEmpresa() {
        if (empresa == null) {
            empresa = new BsEmpresas();
        }
        return empresa;
    }

    public void setEmpresa(BsEmpresas empresa) {
        this.empresa = empresa;
    }

    public BsTalonarios getTalonario() {
        if (talonario == null) {
            talonario = new BsTalonarios();
            talonario.setBsTipComprobantes(new BsTipComprobantes());
            talonario.setBsSucursal(new BsSucursal());
            talonario.setBsTimbrados(new BsTimbrados());
        }
        return talonario;
    }

    public void setTalonario(BsTalonarios talonario) {
        this.talonario = talonario;
    }

    public CreSolicitudes getSolicitud() {
        if (solicitud == null) {
            solicitud = new CreSolicitudes();
            solicitud.setBsEmpresas(new BsEmpresas());
            solicitud.setBsMonedas(new BsMonedas());
            solicitud.setBsSucursal(new BsSucursal());
            solicitud.setBsTalonarios(new BsTalonarios());
            solicitud.getBsTalonarios().setBsTipComprobantes(new BsTipComprobantes());
            solicitud.setCobClientes(new CobClientes());
            solicitud.getCobClientes().setBsPersonas(new BsPersonas());
            solicitud.setVenVendedor(new VenVendedor());
            solicitud.getVenVendedor().setBsPersonas(new BsPersonas());
            solicitud.setCrMotivosPrestamos(new CrMotivosPrestamos());
        }
        return solicitud;
    }

    public void setSolicitud(CreSolicitudes solicitud) {
        this.solicitud = solicitud;
    }

    public CreSolicitudes getSolicitudSelected() {
        if (solicitudSelected == null) {
            solicitudSelected = new CreSolicitudes();
            solicitudSelected.setBsEmpresas(new BsEmpresas());
            solicitudSelected.setBsMonedas(new BsMonedas());
            solicitudSelected.setBsSucursal(new BsSucursal());
            solicitudSelected.setBsTalonarios(new BsTalonarios());
            solicitudSelected.getBsTalonarios().setBsTipComprobantes(new BsTipComprobantes());
            solicitudSelected.setCobClientes(new CobClientes());
            solicitudSelected.getCobClientes().setBsPersonas(new BsPersonas());
            solicitudSelected.setVenVendedor(new VenVendedor());
            solicitudSelected.getVenVendedor().setBsPersonas(new BsPersonas());
            solicitudSelected.setCrMotivosPrestamos(new CrMotivosPrestamos());
        }
        return solicitudSelected;
    }

    public void setSolicitudSelected(CreSolicitudes solicitudSelected) {
        this.solicitudSelected = solicitudSelected;
        if (solicitudSelected != null) {
            solicitud = solicitudSelected;
            if (esModificar) {
                solicitud.setEstado("A");
                solicitud.setEstadoAux(true);
                edit();
            } else {
                solicitud.setDesembolsadoAux(false);
            }
            estadoDisabled = solicitud.getIndDesembolsado().equals("S");
        }
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    public LazyDataModel getLazySolcitudDT() {
        if (lazySolcitudDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazySolcitudDT = genericLazy.getLazyDataModel(CreSolicitudes.class, campos);
        }
        return lazySolcitudDT;
    }

    public void setLazySolcitudDT(LazyDataModel lazySolcitudDT) {
        this.lazySolcitudDT = lazySolcitudDT;
    }

    public LazyDataModel getLazyMonedaDG() {
        if (lazyMonedaDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyMonedaDG = genericLazy.getLazyDataModel(BsMonedas.class, campos);
        }
        return lazyMonedaDG;
    }

    public void setLazyMonedaDG(LazyDataModel lazyMonedaDG) {
        this.lazyMonedaDG = lazyMonedaDG;
    }

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

    public LazyDataModel getLazySucursalDG() {
        if (lazySucursalDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazySucursalDG = genericLazy.getLazyDataModel(BsSucursal.class, campos);
        }
        return lazySucursalDG;
    }

    public void setLazySucursalDG(LazyDataModel lazySucursalDG) {
        this.lazySucursalDG = lazySucursalDG;
    }

    public LazyDataModel getLazyTalonarioDG() {
        if (lazyTalonarioDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            campos.put("bsTipComprobantes.bsModulos.codModulo", "CRE");
            campos.put("bsTipComprobantes.codTipComp", "SOL");
            lazyTalonarioDG = genericLazy.getLazyDataModel(BsTalonarios.class, campos);
        }
        return lazyTalonarioDG;
    }

    public void setLazyTalonarioDG(LazyDataModel lazyTalonarioDG) {
        this.lazyTalonarioDG = lazyTalonarioDG;
    }

    public LazyDataModel getLazyClienteDG() {
        if (lazyClienteDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyClienteDG = genericLazy.getLazyDataModel(CobClientes.class, campos);
        }
        return lazyClienteDG;
    }

    public void setLazyClienteDG(LazyDataModel lazyClienteDG) {
        this.lazyClienteDG = lazyClienteDG;
    }

    public LazyDataModel getLazyVendedorDG() {
        if (lazyVendedorDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyVendedorDG = genericLazy.getLazyDataModel(VenVendedor.class, campos);
        }
        return lazyVendedorDG;
    }

    public void setLazyVendedorDG(LazyDataModel lazyVendedorDG) {
        this.lazyVendedorDG = lazyVendedorDG;
    }

    public LazyDataModel getLazyMotivosPreDG() {
        if (lazyMotivosPreDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyMotivosPreDG = genericLazy.getLazyDataModel(CrMotivosPrestamos.class, campos);
        }
        return lazyMotivosPreDG;
    }

    public void setLazyMotivosPreDG(LazyDataModel lazyMotivosPreDG) {
        this.lazyMotivosPreDG = lazyMotivosPreDG;
    }

//</editor-fold>
}
