package com.py.controller;

import com.py.jpa.BsEmpresas;
import com.py.jpa.BsIva;
import com.py.jpa.BsMonedas;
import com.py.jpa.BsPersonas;
import com.py.jpa.BsSucursal;
import com.py.jpa.BsTalonarios;
import com.py.jpa.BsTimbrados;
import com.py.jpa.BsTipComprobantes;
import com.py.jpa.BsTipoValor;
import com.py.jpa.CobCajas;
import com.py.jpa.CobClientes;
import com.py.jpa.CobCobrador;
import com.py.jpa.CobCobrosCab;
import com.py.jpa.CobCobrosValores;
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
import com.py.jpa.TesCuentaBancaria;
import com.py.jpa.TesDepositoDet;
import com.py.jpa.TesDepositosCab;
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
public class TesDepositosController extends GenericMensajes implements Serializable {

    Logger logger = Logger.getLogger(TesDepositosController.class);
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
    private LazyDataModel lazyDepositoDT;
    private LazyDataModel lazyTipoValorDG;
    private LazyDataModel lazyMonedaDG;
    private LazyDataModel lazyEmpresaDG;
    private LazyDataModel lazySucursalDG;
    private LazyDataModel lazyTalonarioDG;
    private LazyDataModel lazyClienteDG;
    private LazyDataModel lazyCobradorDG;
    private LazyDataModel lazyHabilitacionDG;
    private LazyDataModel lazyValoresDG;
    private LazyDataModel lazyCuentaBancoDG;

    private CobClientes cliente;
    private BsSucursal sucursal;
    private BsMonedas moneda;
    private BsEmpresas empresa;
    private BsTalonarios talonario;
    private CobHabilitaciones habilitacion;

    private TesDepositosCab depositoCab, depositoCabSelected;
    private TesDepositoDet depositoDet, depositoDetSelected;
    private List<TesDepositoDet> listaDetalle;
    private ImprimirReportes imprimirReporte;
    private boolean esEfectivo, esBusqueda;

    @PostConstruct
    private void ini() {
        limpiar();
    }

//<editor-fold defaultstate="collapsed" desc="Metodos">
    public void limpiar() {
        cliente = null;
        sucursal = null;
        moneda = null;
        empresa = null;
        talonario = null;
        depositoCab = null;
        depositoCabSelected = null;
        depositoDet = null;
        getDepositoDet();
        depositoDetSelected = null;
        habilitacion = null;
        listaDetalle = null;
        esModificar = false;
        ayuda = null;
        acercaDe = null;
        imprimirReporte = new ImprimirReportes();
        esEfectivo = true;
        esBusqueda = false;
    }

    public void guardar() {
        try {
            BigDecimal nro_comprobante = new BigDecimal(BigInteger.ZERO);
            BigDecimal montoTotal = new BigDecimal(BigInteger.ZERO);

            try {
                Query nro_aux = em.createQuery("SELECT MAX(c.nroDeposito) FROM CobRecibosCab c");
                nro_comprobante = (BigDecimal) nro_aux.getSingleResult();
            } catch (Exception e) {
                nro_comprobante = new BigDecimal(BigInteger.ZERO);
            }
            if (depositoCab.getCobClientes() != null && depositoCab.getCobClientes().getId() == null) {
                depositoCab.setCobClientes(null);
            }
            List<TesDepositoDet> d = new ArrayList<>();
            d = listaDetalle;
            listaDetalle = new ArrayList<>();
            int i = 0;
            if (!d.isEmpty()) {
                try {
                    for (TesDepositoDet list : d) {
                        list.setTesDepositosCab(depositoCab);
                        montoTotal = montoTotal.add(list.getMontoValor());
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
            depositoCab.setTotCredito(montoTotal);
            depositoCab.setTotDebito(montoTotal);
            depositoCab.setNroDeposito(nro_comprobante.add(new BigDecimal(BigInteger.ONE)));
            depositoCab.setTesDepositoDetList(listaDetalle);
            if (genericEJB.insert(depositoCab)) {
                try {
                    for (TesDepositoDet up : depositoCab.getTesDepositoDetList()) {
                        up.getCobCobrosValores().setEstado("D");
                        genericEJB.update(up.getCobCobrosValores());
                    }
                } catch (Exception e) {
                    mensajeError("Ocurrio un error al Actualizar Estado Valor");
                    e.printStackTrace(System.err);
                    logger.error("Ocurrio un error al Actualizar Estado Valor Loger ", e);
                }
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
            genericEJB.update(depositoCab);
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
            Query detalle = em.createNamedQuery("TesDepositoDet.findByIdCab", TesDepositoDet.class);
            detalle.setParameter("idCab", depositoCab.getId());
            listaDetalle = detalle.getResultList();
            if (listaDetalle.size() > 0) {
                for (TesDepositoDet det : listaDetalle) {
                    try {
                        genericEJB.delete(det);
                    } catch (Exception e) {
                        mensajeError("Ocurrio Un Error al Eliminar el Deposito detalle");
                        e.printStackTrace(System.err);
                        logger.error("Ocurrio un error eliminar detalle Loger ", e);
                    }
                }
            }
            try {
                for (TesDepositoDet up : depositoCab.getTesDepositoDetList()) {
                    up.getCobCobrosValores().setEstado("P");
                    genericEJB.update(up.getCobCobrosValores());
                }
            } catch (Exception e) {
                mensajeError("Ocurrio un error al Actualizar Estado Valor");
                e.printStackTrace(System.err);
                logger.error("Ocurrio un error al Actualizar Estado Valor Loger ", e);
            }
            genericEJB.delete(depositoCab);
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
        depositoCab.setBsEmpresas(empresa);
    }

    public void onRowSelectHabilitacion(SelectEvent mp) {
        habilitacion = (CobHabilitaciones) mp.getObject();
        depositoCab.setCobHabilitaciones(habilitacion);
    }

    public void onRowSelectValores(SelectEvent mp) {
        CobCobrosValores aux = new CobCobrosValores();
        aux = (CobCobrosValores) mp.getObject();
        depositoDet.setCobCobrosValores(aux);
        depositoDet.setTesCuentaBancaria(aux.getTesCuentaBancaria());
        depositoDet.setBsTipoValor(aux.getBsTipoValor());
        depositoDet.setTipCambio(BigDecimal.ONE);
        depositoDet.setMontoValor(aux.getMontoValor());
    }

    public void onRowSelectCuentaB(SelectEvent cb) {
        TesCuentaBancaria cuentaBanco = new TesCuentaBancaria();
        cuentaBanco = (TesCuentaBancaria) cb.getObject();
        depositoCab.setTesCuentaBancaria(cuentaBanco);
    }

    public void codClienteChange(ValueChangeEvent cli) {
        if (cli.getNewValue() != null && !(cli.getOldValue() == null && cli.getNewValue().toString().equals(""))) {
            try {
                Query listener = em.createQuery("SELECT c FROM CobClientes c WHERE c.bsEmpresas.id = :idEmpresa and c.codCliente = :codCliente", CobClientes.class);
                listener.setParameter("idEmpresa", depositoCab.getBsEmpresas().getId());
                listener.setParameter("codCliente", cli.getNewValue());
                CobClientes clieAux = (CobClientes) listener.getSingleResult();
                depositoCab.setCobClientes(clieAux);
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
                depositoCab.setBsEmpresas(empAux);
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
                listener.setParameter("idEmpresa", depositoCab.getBsEmpresas().getId());
                listener.setParameter("codSucursal", suc.getNewValue());
                BsSucursal sucAux = (BsSucursal) listener.getSingleResult();
                depositoCab.setBsSucursal(sucAux);
            } catch (Exception ex) {
                mensajeError("Ocurrio Un Error al Listener Sucursal");
                ex.printStackTrace(System.err);
                logger.error("Ocurrio un error Listener Sucursal Loger ", ex);
            }
        }
    }

    public void codMonedaChange(ValueChangeEvent event) {
        if (event.getNewValue() != null && !(event.getOldValue() == null && event.getNewValue().toString().equals(""))) {
            try {
                Query listener = em.createQuery("SELECT b FROM BsMonedas b WHERE b.codMoneda = :codMoneda", BsMonedas.class);
                listener.setParameter("codMoneda", event.getNewValue());
                BsMonedas monAux = (BsMonedas) listener.getSingleResult();
                depositoCab.setBsMonedas(monAux);
            } catch (Exception ex) {
                mensajeError("Ocurrio Un Error al Listener Moneda");
                ex.printStackTrace(System.err);
                logger.error("Ocurrio un error Listener Moneda Loger ", ex);
            }
        }
    }

    public void onRowSelectCliente(SelectEvent c) {
        cliente = (CobClientes) c.getObject();
        depositoCab.setCobClientes(cliente);
    }

    public void onRowSelectSucursal(SelectEvent s) {
        sucursal = (BsSucursal) s.getObject();
        depositoCab.setBsSucursal(sucursal);
    }

    public void onRowSelectMoneda(SelectEvent m) {
        moneda = (BsMonedas) m.getObject();
        depositoCab.setBsMonedas(moneda);
    }

    public void onRowSelectTalonario(SelectEvent t) {
        talonario = (BsTalonarios) t.getObject();
        depositoCab.setBsTalonarios(talonario);
    }

    public void onRowSelectDeposito(SelectEvent r) {
        setDepositoCabSelected((TesDepositosCab) r.getObject());
    }

    public void refreshDet() {
        //saldos = null;
        depositoDet = null;
        getDepositoDet();
    }

    public void onRowSelectTipoValor(SelectEvent tv) {
        depositoDet.setBsTipoValor((BsTipoValor) tv.getObject());
        depositoDet.setCobCobrosValores(null);
        depositoDet.setTesCuentaBancaria(null);
        depositoDet.setTipCambio(BigDecimal.ONE);
        if (depositoDet.getBsTipoValor().getCodTipo().equals("EF")) {
            esEfectivo = true;
        } else {
            esEfectivo = false;
        }
    }

    public void addDetalle() {
        try {
            TesDepositoDet obj = new TesDepositoDet();
            obj = depositoDet;
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
                    if (listaDetalle.get(i).getMontoValor().equals(depositoDetSelected.getMontoValor())) {
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
        parametros.put("p_id_empresa", depositoCab.getBsEmpresas().getId() != null ? depositoCab.getBsEmpresas().getId() : null);
        parametros.put("descEmpresa", depositoCab.getBsEmpresas().getId() != null ? depositoCab.getBsEmpresas().getRepLegal() : null);
        parametros.put("p_id_comprobante", depositoCab.getId() != null ? depositoCab.getId() : null);
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

    public boolean isEsEfectivo() {
        return esEfectivo;
    }

    public void setEsEfectivo(boolean esEfectivo) {
        this.esEfectivo = esEfectivo;
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

    public TesDepositosCab getDepositoCab() {
        if (depositoCab == null) {
            depositoCab = new TesDepositosCab();
            depositoCab.setBsEmpresas(new BsEmpresas());
            depositoCab.getBsEmpresas().setBsPersonas(new BsPersonas());
            depositoCab.setBsMonedas(new BsMonedas());
            depositoCab.setBsSucursal(new BsSucursal());
            depositoCab.setBsTalonarios(new BsTalonarios());
            depositoCab.getBsTalonarios().setBsTipComprobantes(new BsTipComprobantes());
            depositoCab.setCobClientes(new CobClientes());
            depositoCab.getCobClientes().setBsPersonas(new BsPersonas());
            depositoCab.setTesCuentaBancaria(new TesCuentaBancaria());
            depositoCab.getTesCuentaBancaria().setBsPersonas(new BsPersonas());
            depositoCab.setCobHabilitaciones(new CobHabilitaciones());
            depositoCab.getCobHabilitaciones().setCobCajas(new CobCajas());
        }
        return depositoCab;
    }

    public void setDepositoCab(TesDepositosCab depositoCab) {
        this.depositoCab = depositoCab;
    }

    public TesDepositosCab getDepositoCabSelected() {
        if (depositoCabSelected == null) {
            depositoCabSelected = new TesDepositosCab();
            depositoCabSelected.setBsEmpresas(new BsEmpresas());
            depositoCabSelected.getBsEmpresas().setBsPersonas(new BsPersonas());
            depositoCabSelected.setBsMonedas(new BsMonedas());
            depositoCabSelected.setBsSucursal(new BsSucursal());
            depositoCabSelected.setBsTalonarios(new BsTalonarios());
            depositoCabSelected.getBsTalonarios().setBsTipComprobantes(new BsTipComprobantes());
            depositoCabSelected.setCobClientes(new CobClientes());
            depositoCabSelected.getCobClientes().setBsPersonas(new BsPersonas());
            depositoCabSelected.setTesCuentaBancaria(new TesCuentaBancaria());
            depositoCabSelected.getTesCuentaBancaria().setBsPersonas(new BsPersonas());
            depositoCabSelected.setCobHabilitaciones(new CobHabilitaciones());
            depositoCabSelected.getCobHabilitaciones().setCobCajas(new CobCajas());
        }
        return depositoCabSelected;
    }

    public void setDepositoCabSelected(TesDepositosCab depositoCabSelected) {
        this.depositoCabSelected = depositoCabSelected;
        if (depositoCabSelected != null) {
            depositoCab = depositoCabSelected;
            Query detalle = em.createNamedQuery("TesDepositoDet.findByIdCab", TesDepositoDet.class);
            detalle.setParameter("idCab", depositoCab.getId());
            listaDetalle = detalle.getResultList();
        }
    }

    public TesDepositoDet getDepositoDet() {
        if (depositoDet == null) {
            depositoDet = new TesDepositoDet();
            depositoDet.setTesDepositosCab(new TesDepositosCab());
            depositoDet.setCobCobrosValores(new CobCobrosValores());
            depositoDet.getCobCobrosValores().setBsTipoValor(new BsTipoValor());
            depositoDet.getCobCobrosValores().setTesCuentaBancaria(new TesCuentaBancaria());
            depositoDet.getCobCobrosValores().getTesCuentaBancaria().setBsPersonas(new BsPersonas());
            depositoDet.getCobCobrosValores().setBsMonedas(new BsMonedas());
            depositoDet.setTesCuentaBancaria(new TesCuentaBancaria());
            depositoDet.getTesCuentaBancaria().setBsPersonas(new BsPersonas());
            depositoDet.setBsTipoValor(new BsTipoValor());
        }
        return depositoDet;
    }

    public void setDepositoDet(TesDepositoDet depositoDet) {
        this.depositoDet = depositoDet;
    }

    public TesDepositoDet getDepositoDetSelected() {
        if (depositoDetSelected == null) {
            depositoDetSelected = new TesDepositoDet();
            depositoDetSelected.setTesDepositosCab(new TesDepositosCab());
            depositoDetSelected.setCobCobrosValores(new CobCobrosValores());
            depositoDetSelected.getCobCobrosValores().setBsTipoValor(new BsTipoValor());
            depositoDetSelected.getCobCobrosValores().setTesCuentaBancaria(new TesCuentaBancaria());
            depositoDetSelected.getCobCobrosValores().getTesCuentaBancaria().setBsPersonas(new BsPersonas());
            depositoDetSelected.getCobCobrosValores().setBsMonedas(new BsMonedas());
            depositoDetSelected.setTesCuentaBancaria(new TesCuentaBancaria());
            depositoDetSelected.getTesCuentaBancaria().setBsPersonas(new BsPersonas());
            depositoDetSelected.setBsTipoValor(new BsTipoValor());
        }
        return depositoDetSelected;
    }

    public void setDepositoDetSelected(TesDepositoDet depositoDetSelected) {
        this.depositoDetSelected = depositoDetSelected;
    }

    public List<TesDepositoDet> getListaDetalle() {
        if (listaDetalle == null) {
            listaDetalle = new ArrayList<>();
        }
        for (TesDepositoDet det : listaDetalle) {
            if (det.getBsTipoValor() == null) {
                det.setBsTipoValor(new BsTipoValor());
            }
            if (det.getCobCobrosValores() == null) {
                det.setCobCobrosValores(new CobCobrosValores());
            }
            if (det.getTesCuentaBancaria() == null) {
                det.setTesCuentaBancaria(new TesCuentaBancaria());
            }
        }
        return listaDetalle;
    }

    public void setListaDetalle(List<TesDepositoDet> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    public LazyDataModel getLazyDepositoDT() {
        if (lazyDepositoDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyDepositoDT = genericLazy.getLazyDataModel(TesDepositosCab.class, campos);
        }
        return lazyDepositoDT;
    }

    public void setLazyDepositoDT(LazyDataModel lazyDepositoDT) {
        this.lazyDepositoDT = lazyDepositoDT;
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

    public LazyDataModel getLazyCuentaBancoDG() {
        if (lazyCuentaBancoDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyCuentaBancoDG = genericLazy.getLazyDataModel(TesCuentaBancaria.class, campos);
        }
        return lazyCuentaBancoDG;
    }

    public void setLazyCuentaBancoDG(LazyDataModel lazyCuentaBancoDG) {
        this.lazyCuentaBancoDG = lazyCuentaBancoDG;
    }

    public LazyDataModel getLazyTipoValorDG() {
        if (lazyTipoValorDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            campos.put("bsModulos.id", "2");
            lazyTipoValorDG = genericLazy.getLazyDataModel(BsTipoValor.class, campos);
        }
        return lazyTipoValorDG;
    }

    public void setLazyTipoValorDG(LazyDataModel lazyTipoValorDG) {
        this.lazyTipoValorDG = lazyTipoValorDG;
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

    public LazyDataModel getLazyValoresDG() {
        if (lazyValoresDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            if (cliente != null && cliente.getId() != null) {
                campos.put("cobClientes.id", cliente.getId());
            }
            campos.put("WHERE", "b.estado = 'P' and b.tipValor != 'EF'");
            campos.put("ORDER BY", "b.fechaValor , b.nroValor");
            lazyValoresDG = genericLazy.getLazyDataModel(CobCobrosValores.class, campos);
        }
        return lazyValoresDG;
    }

    public void setLazyValoresDG(LazyDataModel lazyValoresDG) {
        this.lazyValoresDG = lazyValoresDG;
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
            campos.put("bsTipComprobantes.bsModulos.id", "4");
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
