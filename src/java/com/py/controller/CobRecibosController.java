package com.py.controller;

import com.py.jpa.BsEmpresas;
import com.py.jpa.BsIva;
import com.py.jpa.BsMonedas;
import com.py.jpa.BsPersonas;
import com.py.jpa.BsSucursal;
import com.py.jpa.BsTalonarios;
import com.py.jpa.BsTimbrados;
import com.py.jpa.BsTipComprobantes;
import com.py.jpa.CobCajas;
import com.py.jpa.CobClientes;
import com.py.jpa.CobCobrador;
import com.py.jpa.CobCobrosCab;
import com.py.jpa.CobHabilitaciones;
import com.py.jpa.CobReciboDetalle;
import com.py.jpa.CobRecibosCab;
import com.py.jpa.CobSaldos;
import com.py.jpa.CrMotivosPrestamos;
import com.py.jpa.CreDesembolsoDet;
import com.py.jpa.CreDesembolsosCab;
import com.py.jpa.CreSolicitudes;
import com.py.jpa.CreTipoAmortizacion;
import com.py.jpa.StoArticulos;
import com.py.jpa.VenComprobantesCabecera;
import com.py.jpa.VenComprobantesDetalle;
import com.py.jpa.VenCondicionVenta;
import com.py.jpa.VenVendedor;
import com.py.utils.GenericBigLazyList;
import com.py.utils.GenericController;
import com.py.utils.GenericMensajes;
import com.py.utils.GenericoEJB;
import com.py.utils.ImprimirReportes;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class CobRecibosController extends GenericMensajes implements Serializable {

    Logger logger = Logger.getLogger(CobRecibosController.class);
    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    @Inject
    private BsLogin login;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyReciboDT;
    private LazyDataModel lazyMonedaDG;
    private LazyDataModel lazyEmpresaDG;
    private LazyDataModel lazySucursalDG;
    private LazyDataModel lazyTalonarioDG;
    private LazyDataModel lazyClienteDG;
    private LazyDataModel lazyCobradorDG;
    private LazyDataModel lazyHabilitacionDG;
    private LazyDataModel lazySaldosDG;

    private CobClientes cliente;
    private CobCobrador cobrador;
    private BsSucursal sucursal;
    private BsMonedas moneda;
    private BsEmpresas empresa;
    private BsTalonarios talonario;
    private CobHabilitaciones habilitacion;
    
    private CobRecibosCab recibos, recibosSelected;
    private CobReciboDetalle recibosDetalle;
    private CobSaldos saldos;
    private List<CobReciboDetalle> listaDetalle;
    private ImprimirReportes imprimirReporte;
    private boolean esDesembolso, esVentas, esBusqueda;

    @PostConstruct
    private void ini() {
        limpiar();
    }

//<editor-fold defaultstate="collapsed" desc="Metodos">
    public void limpiar() {
        cliente = null;
        cobrador = null;
        sucursal = null;
        moneda = null;
        empresa = null;
        talonario = null;
        recibos = null;
        recibosSelected = null;
        recibosDetalle = null;
        getRecibosDetalle();
        habilitacion = null;
        listaDetalle = null;
        esModificar = false;
        ayuda = null;
        acercaDe = null;
        imprimirReporte = new ImprimirReportes();
        esDesembolso = false;
        esVentas = false;
        esBusqueda = false;
    }

    public void guardar() {
        try {
            BigDecimal nro_comprobante = new BigDecimal(BigInteger.ZERO);
            BigDecimal montoTotal = new BigDecimal(BigInteger.ZERO);
            BigDecimal totalGravada = new BigDecimal(BigInteger.ZERO);
            BigDecimal totalIva = new BigDecimal(BigInteger.ZERO);
            BigDecimal totalexenta = new BigDecimal(BigInteger.ZERO);
            try {
                Query nro_aux = em.createQuery("SELECT MAX(c.nroRecibo) FROM CobRecibosCab c");
                nro_comprobante = (BigDecimal) nro_aux.getSingleResult();
            } catch (Exception e) {
                nro_comprobante = new BigDecimal(BigInteger.ZERO);
            }
            if (recibos.getCobCobrosCab() != null && recibos.getCobCobrosCab().getId() == null) {
                recibos.setCobCobrosCab(null);
            }
            List<CobReciboDetalle> d = new ArrayList<>();
            d = listaDetalle;
            listaDetalle = new ArrayList<>();
            int i = 0;
            if (!d.isEmpty()) {
                try {
                    for (CobReciboDetalle list : d) {
                        list.setCobRecibosCab(recibos);
                        listaDetalle.add(list);
                    }
                } catch (Exception e) {
                    mensajeError("Ocurrio un error al calcular total cabecera");
                    e.printStackTrace(System.err);
                    logger.error("Ocurrio un error al calcular total cabecera Loger ", e);
                }
            } else {
                mensajeError("No puede Guardar un Recibo sin Detalle");
                return;
            }
            if (nro_comprobante == null) {
                nro_comprobante = new BigDecimal(BigInteger.ZERO);
            }
            recibos.setNroRecibo(nro_comprobante.add(new BigDecimal(BigInteger.ONE)));
            recibos.setCobReciboDetalleList(listaDetalle);
            if (genericEJB.insert(recibos)) {
                mensajeAlerta("Se guardo Correctamente");
            } else {
                mensajeAlerta("No se pudo Guardar");
            }
            limpiar();
        } catch (Exception e) {
            mensajeError("Ocurrio un error");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un error Loger ", e);
        }
    }

    public void edit() {
        try {
            genericEJB.update(recibos);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            limpiar();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Recibo");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un error Loger ", e);
        }
    }

    public void delete() {
        try {
            Query detalle = em.createNamedQuery("CobReciboDetalle.findByCab", CobReciboDetalle.class);
            detalle.setParameter("idCab", recibos.getId());
            listaDetalle = detalle.getResultList();
            if (listaDetalle.size() > 0) {
                for (CobReciboDetalle det : listaDetalle) {
                    try {
                        genericEJB.delete(det);
                    } catch (Exception e) {
                        mensajeError("Ocurrio Un Error al Eliminar el Recibo detalle");
                        e.printStackTrace(System.err);
                        logger.error("Ocurrio un error eliminar detalle Loger ", e);
                    }
                }
            }
            genericEJB.delete(recibos);
            mensajeAlerta("Eliminado Correctamente!");
            limpiar();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar la Venta");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un error Loger ", e);
        }
    }

    public void onRowSelectEmpresa(SelectEvent em) {
        empresa = (BsEmpresas) em.getObject();
        recibos.setBsEmpresas(empresa);
    }

    public void onRowSelectHabilitacion(SelectEvent mp) {
        habilitacion = (CobHabilitaciones) mp.getObject();
        recibos.setCobHabilitaciones(habilitacion);
    }

    public void onRowSelectSaldos(SelectEvent mp) {
        saldos = (CobSaldos) mp.getObject();
        recibosDetalle.setCobSaldos(saldos);
        recibosDetalle.setMontoCuota(saldos.getMontoCuota());
        recibosDetalle.setNroCuota(saldos.getNroCuota());
        if (saldos.getCreDesembolsosCab() != null && saldos.getCreDesembolsosCab().getId() != null) {
            esDesembolso = true;
            esVentas = false;
        }
        if (saldos.getVenComprobantesCabecera() != null && saldos.getVenComprobantesCabecera().getId() != null) {
            esVentas = true;
            esDesembolso = false;
        }
    }

    public void codClienteChange(ValueChangeEvent cli) {
        if (cli.getNewValue() != null && !(cli.getOldValue() == null && cli.getNewValue().toString().equals(""))) {
            try {
                Query listener = em.createQuery("SELECT c FROM CobClientes c WHERE c.bsEmpresas.id = :idEmpresa and c.codCliente = :codCliente", CobClientes.class);
                listener.setParameter("idEmpresa", recibos.getBsEmpresas().getId());
                listener.setParameter("codCliente", cli.getNewValue());
                CobClientes clieAux = (CobClientes) listener.getSingleResult();
                recibos.setCobClientes(clieAux);
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
                BsEmpresas empAux = (BsEmpresas) listener.getSingleResult();
                recibos.setBsEmpresas(empAux);
            } catch (Exception ex) {
                mensajeError("Ocurrio Un Error al Listener Empresa");
                ex.printStackTrace(System.err);
                logger.error("Ocurrio un error Listener Empresa Loger ", ex);
            }
        }
    }

    public void codSucursalChange(ValueChangeEvent suc) {
        if (suc.getNewValue() != null && !(suc.getOldValue() == null && suc.getNewValue().toString().equals(""))) {
            try {
                Query listener = em.createQuery("SELECT b FROM BsSucursal b WHERE b.bsEmpresas.id = :idEmpresa and b.codSucursal = :codSucursal", BsSucursal.class);
                listener.setParameter("idEmpresa", recibos.getBsEmpresas().getId());
                listener.setParameter("codSucursal", suc.getNewValue());
                BsSucursal sucAux = (BsSucursal) listener.getSingleResult();
                recibos.setBsSucursal(sucAux);
            } catch (Exception ex) {
                mensajeError("Ocurrio Un Error al Listener Sucursal");
                ex.printStackTrace(System.err);
                logger.error("Ocurrio un error Listener Sucursal Loger ", ex);
            }
        }
    }

    public void codCobradorChange(ValueChangeEvent ve) {
        if (ve.getNewValue() != null && !(ve.getOldValue() == null && ve.getNewValue().toString().equals(""))) {
            try {
                Query listener = em.createQuery("SELECT v FROM CobCobrador v WHERE v.id = :idCobrador and v.bsEmpresas.id = :idEmpresa", CobCobrador.class);
                listener.setParameter("idEmpresa", recibos.getBsEmpresas().getId());
                listener.setParameter("idCobrador", ve.getNewValue());
                CobCobrador cobAux = (CobCobrador) listener.getSingleResult();
                recibos.setCobCobrador(cobAux);
            } catch (Exception ex) {
                mensajeError("Ocurrio Un Error al Listener Cobrador");
                ex.printStackTrace(System.err);
                logger.error("Ocurrio un error Listener Cobrador Loger ", ex);
            }
        }
    }

    public void codMonedaChange(ValueChangeEvent event) {
        if (event.getNewValue() != null && !(event.getOldValue() == null && event.getNewValue().toString().equals(""))) {
            try {
                Query listener = em.createQuery("SELECT b FROM BsMonedas b WHERE b.codMoneda = :codMoneda", BsMonedas.class);
                listener.setParameter("codMoneda", event.getNewValue());
                BsMonedas monAux = (BsMonedas) listener.getSingleResult();
                recibos.setBsMonedas(monAux);
            } catch (Exception ex) {
                mensajeError("Ocurrio Un Error al Listener Moneda");
                ex.printStackTrace(System.err);
                logger.error("Ocurrio un error Listener Moneda Loger ", ex);
            }
        }
    }

    public void onRowSelectCliente(SelectEvent c) {
        cliente = (CobClientes) c.getObject();
        recibos.setCobClientes(cliente);
    }

    public void onRowSelectCobrador(SelectEvent v) {
        cobrador = (CobCobrador) v.getObject();
        recibos.setCobCobrador(cobrador);
    }

    public void onRowSelectSucursal(SelectEvent s) {
        sucursal = (BsSucursal) s.getObject();
        recibos.setBsSucursal(sucursal);
    }

    public void onRowSelectMoneda(SelectEvent m) {
        moneda = (BsMonedas) m.getObject();
        recibos.setBsMonedas(moneda);
    }

    public void onRowSelectTalonario(SelectEvent t) {
        talonario = (BsTalonarios) t.getObject();
        recibos.setBsTalonarios(talonario);
    }

    public void onRowSelectRecibo(SelectEvent r) {
        setRecibosSelected((CobRecibosCab) r.getObject());
    }

    public void refreshDet() {
        saldos = null;
        recibosDetalle = null;
    }

    public void addDetalle() {
        try {
            CobReciboDetalle obj = new CobReciboDetalle();
            obj = recibosDetalle;
            listaDetalle.add(obj);
            refreshDet();
        } catch (Exception e) {
            mensajeError("Error al agregar a la lista de el Detalle");
            logger.error("Error al agregar datos a la lista el Detalle ", e);
        }
    }

    public void eliminarDetalle() {
        try {
            if (!listaDetalle.isEmpty()) {
                for (int i = 0; i < listaDetalle.size(); i++) {
                    if (listaDetalle.get(i).getCobSaldos().getId().equals(recibosDetalle.getCobSaldos().getId())) {
                        listaDetalle.remove(i);
                    }
                }
            }
        } catch (Exception e) {
            mensajeError("Error al eliminar de la lista del Detalle");
            logger.error("Error al eliminar de la lista del Detalle ", e);
        }
    }

    public void imprimir() {

        HashMap<Object, Object> parametros = new HashMap<Object, Object>();
        parametros.put("p_id_empresa", recibos.getBsEmpresas().getId() != null ? recibos.getBsEmpresas().getId() : null);
        parametros.put("descEmpresa", recibos.getBsEmpresas().getId() != null ? recibos.getBsEmpresas().getRepLegal() : null);
        parametros.put("p_id_comprobante", recibos.getId() != null ? recibos.getId() : null);
        parametros.put("impresoPor", login.getBsUsuario() != null ? login.getBsUsuario().getCodUsuario() : null);

        imprimirReporte.setParametros(parametros);
        imprimirReporte.setNombreReporte("CobRecibos");
        imprimirReporte.imprimir();
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
        if (ayuda == null || ayuda.isEmpty()) {
            ayuda = new ArrayList<>();
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Ventas del sistema.");
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
            acercaDe.add("- Pantalla: VenVentas.xhtml");
            acercaDe.add("- Controlador: VenVentasController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: VenComprobantesCabecera.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public CobClientes getCliente() {
        if (cliente == null) {
            cliente = new CobClientes();
            cliente.setBsPersonas(new BsPersonas());
        }
        return cliente;
    }

    public boolean isEsDesembolso() {
        return esDesembolso;
    }

    public void setEsDesembolso(boolean esDesembolso) {
        this.esDesembolso = esDesembolso;
    }

    public boolean isEsVentas() {
        return esVentas;
    }

    public void setEsVentas(boolean esVentas) {
        this.esVentas = esVentas;
    }

    public void setCliente(CobClientes cliente) {
        this.cliente = cliente;
    }

    public boolean isEsBusqueda() {
        return esBusqueda;
    }

    public void setEsBusqueda(boolean esBusqueda) {
        this.esBusqueda = esBusqueda;
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

    public CobHabilitaciones getHabilitacion() {
        if (habilitacion == null) {
            habilitacion = new CobHabilitaciones();
        }
        return habilitacion;
    }

    public void setHabilitacion(CobHabilitaciones habilitacion) {
        this.habilitacion = habilitacion;
    }

    public CobRecibosCab getRecibos() {
        if (recibos == null) {
            recibos = new CobRecibosCab();
            recibos.setBsEmpresas(new BsEmpresas());
            recibos.getBsEmpresas().setBsPersonas(new BsPersonas());
            recibos.setBsMonedas(new BsMonedas());
            recibos.setBsSucursal(new BsSucursal());
            recibos.setBsTalonarios(new BsTalonarios());
            recibos.getBsTalonarios().setBsTipComprobantes(new BsTipComprobantes());
            recibos.setCobClientes(new CobClientes());
            recibos.getCobClientes().setBsPersonas(new BsPersonas());
            recibos.setCobCobrosCab(new CobCobrosCab());
            recibos.setCobHabilitaciones(new CobHabilitaciones());
            recibos.getCobHabilitaciones().setCobCajas(new CobCajas());
            recibos.setCobCobrador(new CobCobrador());
            recibos.getCobCobrador().setBsPersonas(new BsPersonas());
        }
        return recibos;
    }

    public void setRecibos(CobRecibosCab recibos) {
        this.recibos = recibos;
    }

    public CobRecibosCab getRecibosSelected() {
        if (recibosSelected == null) {
            recibosSelected = new CobRecibosCab();
            recibosSelected.setBsEmpresas(new BsEmpresas());
            recibosSelected.getBsEmpresas().setBsPersonas(new BsPersonas());
            recibosSelected.setBsMonedas(new BsMonedas());
            recibosSelected.setBsSucursal(new BsSucursal());
            recibosSelected.setBsTalonarios(new BsTalonarios());
            recibosSelected.getBsTalonarios().setBsTipComprobantes(new BsTipComprobantes());
            recibosSelected.setCobClientes(new CobClientes());
            recibosSelected.getCobClientes().setBsPersonas(new BsPersonas());
            recibosSelected.setCobCobrosCab(new CobCobrosCab());
            recibosSelected.setCobHabilitaciones(new CobHabilitaciones());
            recibosSelected.getCobHabilitaciones().setCobCajas(new CobCajas());
            recibosSelected.setCobCobrador(new CobCobrador());
            recibosSelected.getCobCobrador().setBsPersonas(new BsPersonas());
        }
        return recibosSelected;
    }

    public void setRecibosSelected(CobRecibosCab recibosSelected) {
        this.recibosSelected = recibosSelected;
        if (recibosSelected != null) {
            recibos = recibosSelected;
            Query detalle = em.createNamedQuery("CobReciboDetalle.findByCab", CobReciboDetalle.class);
            detalle.setParameter("idCab", recibos.getId());
            listaDetalle = detalle.getResultList();
        }
    }

    public CobReciboDetalle getRecibosDetalle() {
        if (recibosDetalle == null) {
            recibosDetalle = new CobReciboDetalle();
            recibosDetalle.setCobRecibosCab(new CobRecibosCab());
            recibosDetalle.setCobSaldos(new CobSaldos());
        }
        return recibosDetalle;
    }

    public void setRecibosDetalle(CobReciboDetalle recibosDetalle) {
        this.recibosDetalle = recibosDetalle;
    }

    public CobCobrador getCobrador() {
        if (cobrador == null) {
            cobrador = new CobCobrador();
            cobrador.setBsEmpresas(new BsEmpresas());
            cobrador.setBsPersonas(new BsPersonas());
        }
        return cobrador;
    }

    public void setCobrador(CobCobrador cobrador) {
        this.cobrador = cobrador;
    }

    public CobSaldos getSaldos() {
        if (saldos == null) {
            saldos = new CobSaldos();
            saldos.setCobClientes(new CobClientes());
            saldos.setVenComprobantesCabecera(new VenComprobantesCabecera());
            saldos.setCreDesembolsosCab(new CreDesembolsosCab());
            saldos.setCreDesembolsoDet(new CreDesembolsoDet());
        }
        return saldos;
    }

    public void setSaldos(CobSaldos saldos) {
        this.saldos = saldos;
    }

    public List<CobReciboDetalle> getListaDetalle() {
        if (listaDetalle == null) {
            listaDetalle = new ArrayList<>();
        }
        for (CobReciboDetalle det : listaDetalle) {
            if (det.getCobSaldos() == null) {
                det.setCobSaldos(new CobSaldos());
            }

        }
        return listaDetalle;
    }

    public void setListaDetalle(List<CobReciboDetalle> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    public LazyDataModel getLazyReciboDT() {
        if (lazyReciboDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyReciboDT = genericLazy.getLazyDataModel(CobRecibosCab.class, campos);
        }
        return lazyReciboDT;
    }

    public void setLazyReciboDT(LazyDataModel lazyReciboDT) {
        this.lazyReciboDT = lazyReciboDT;
    }

    public LazyDataModel getLazyHabilitacionDG() {
        if (lazyHabilitacionDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyHabilitacionDG = genericLazy.getLazyDataModel(CobHabilitaciones.class, campos);
        }
        return lazyHabilitacionDG;
    }

    public void setLazyHabilitacionDG(LazyDataModel lazyHabilitacionDG) {
        this.lazyHabilitacionDG = lazyHabilitacionDG;
    }

    public LazyDataModel getLazyCobradorDG() {
        if (lazyCobradorDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyCobradorDG = genericLazy.getLazyDataModel(CobCobrador.class, campos);
        }
        return lazyCobradorDG;
    }

    public void setLazyCobradorDG(LazyDataModel lazyCobradorDG) {
        this.lazyCobradorDG = lazyCobradorDG;
    }

    public LazyDataModel getLazySaldosDG() {
        if (cliente != null && cliente.getId() != null) {
            if (lazySaldosDG == null) {
                genericLazy = new GenericBigLazyList(em);
                campos = new HashMap();
                campos.put("cobClientes.id", cliente.getId());
                campos.put("ORDER BY", "b.nroComprobante , b.nroCuota");
            }
            lazySaldosDG = genericLazy.getLazyDataModel(CobSaldos.class, campos);
        }
        return lazySaldosDG;
    }

    public void setLazySaldosDG(LazyDataModel lazySaldosDG) {
        this.lazySaldosDG = lazySaldosDG;
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
            campos.put("bsTipComprobantes.bsModulos.id", "2");
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

//</editor-fold>
}
