package com.py.controller;

import com.py.jpa.BsEmpresas;
import com.py.jpa.BsIva;
import com.py.jpa.BsModulos;
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
import com.py.jpa.CobCobrosComprobantes;
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
import com.py.jpa.TesTipoCuentas;
import com.py.jpa.VenComprobantesCabecera;
import com.py.jpa.VenComprobantesDetalle;
import com.py.jpa.VenCondicionVenta;
import com.py.jpa.VenVendedor;
import com.py.jpa.Vwcobcomprobantes;
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
public class CobCobrosController extends GenericMensajes implements Serializable {

    Logger logger = Logger.getLogger(CobCobrosController.class);
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
    private LazyDataModel lazyCobrosoDT;
    private LazyDataModel lazyTipoValorDG;
    private LazyDataModel lazyMonedaDG, lazyMonedaValorDG;
    private LazyDataModel lazyEmpresaDG;
    private LazyDataModel lazySucursalDG;
    private LazyDataModel lazyCuentaBancoDG;
    private LazyDataModel lazyTalonarioDG;
    private LazyDataModel lazyClienteDG;
    private LazyDataModel lazyCobradorDG;
    private LazyDataModel lazyHabilitacionDG;
    private LazyDataModel listaVistaComp;
    private CobClientes cliente;
    private BsSucursal sucursal;
    private BsMonedas moneda, monedaValor;
    private BsEmpresas empresa;
    private BsTalonarios talonario;
    private TesCuentaBancaria cuentaBanco;
    private CobHabilitaciones habilitacion;
    private CobCobrosCab cobros, cobrosSelected;
    private CobCobrosComprobantes comprobantes, comprobantesSelected;
    private CobCobrosValores valores;
    private List<CobCobrosValores> listaDetalle, listaDetalleAux;
    private List<CobCobrosComprobantes> listaComprobante;
    private CobSaldos saldos;
    private Vwcobcomprobantes vwcobcomprobantes;
    private BsTipoValor tipoValor;
    private boolean esValor;
    private BigDecimal totalComprobantes, totalValores;

    private ImprimirReportes imprimirReporte;
    private boolean esRecibos, esVentas, esBusqueda;
    private boolean esModificarValores;

    @PostConstruct
    private void ini() {
        limpiar();
    }

//<editor-fold defaultstate="collapsed" desc="Metodos">
    public void limpiar() {
        cliente = null;
        tipoValor = null;
        sucursal = null;
        moneda = null;
        saldos = null;
        empresa = null;
        talonario = null;
        cobros = null;
        cobrosSelected = null;
        comprobantes = null;
        comprobantesSelected = null;
        valores = null;
        monedaValor = null;
        cuentaBanco = null;
        habilitacion = null;
        listaDetalle = null;
        listaDetalleAux = null;
        listaComprobante = null;
        vwcobcomprobantes = null;
        esModificar = false;
        esModificarValores = false;
        ayuda = null;
        acercaDe = null;
        imprimirReporte = new ImprimirReportes();
        esVentas = false;
        esRecibos = false;
        esBusqueda = false;
        esValor = false;
        totalComprobantes = new BigDecimal(0);
        totalValores = new BigDecimal(0);
    }

    public void guardar() {
        try {
            BigDecimal nro_comprobante = new BigDecimal(0);
            try {
                Query nro_aux = em.createQuery("SELECT MAX(c.nroCobro) FROM CobCobrosCab c");
                nro_comprobante = (BigDecimal) nro_aux.getSingleResult();
            } catch (Exception e) {
                nro_comprobante = new BigDecimal(BigInteger.ZERO);
            }
            if (!listaDetalle.isEmpty()) {
                try {
                    for (CobCobrosValores val : listaDetalle) {
                        val.setCobCobrosCab(cobros);
                    }
                } catch (Exception e) {
                    mensajeError("Ocurrio un error al recorrer los valores");
                    e.printStackTrace(System.err);
                    logger.error("Ocurrio un error al recorrer los valores Loger ", e);
                }
            } else {
                mensajeError("No puede Guardar un Cobro sin Valor");
                return;
            }

            if (!listaComprobante.isEmpty()) {
                try {
                    for (CobCobrosComprobantes comp : listaComprobante) {
                        if (comp.getCobRecibosCab() != null && comp.getCobRecibosCab().getId() == null) {
                            comp.setCobRecibosCab(null);
                        }
                        if (comp.getVenComprobantesCabecera() != null && comp.getVenComprobantesCabecera().getId() == null) {
                            comp.setVenComprobantesCabecera(null);
                        }
                        comp.setCobCobrosCab(cobros);
                    }
                } catch (Exception e) {
                    mensajeError("Ocurrio un error al recorrer los comprobantes");
                    e.printStackTrace(System.err);
                    logger.error("Ocurrio un error al recorrer los comprobantes Loger ", e);
                }
            } else {
                mensajeError("No puede Guardar un Cobro sin Comprobante");
                return;
            }

            if (nro_comprobante == null) {
                nro_comprobante = new BigDecimal(BigInteger.ZERO);
            }
            cobros.setNroCobro(nro_comprobante.add(new BigDecimal(BigInteger.ONE)).longValue());
           // cobros.setCobCobrosComprobantesList(listaComprobante);
            cobros.setCobCobrosValoresList(listaDetalle);
            if (genericEJB.insert(cobros)) {
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
            listaDetalleAux = cobros.getCobCobrosValoresList();
            listaDetalleAux.removeAll(listaDetalle);
            for (int i = 0; i < listaDetalleAux.size(); i++) {
                genericEJB.delete(listaDetalleAux.get(i));
            }
            //cobros.setCobCobrosComprobantesList(listaComprobante);
            cobros.setCobCobrosValoresList(listaDetalle);
            genericEJB.update(cobros);
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
            Query comp = em.createNamedQuery("CobCobrosComprobantes.findByCabid", CobCobrosComprobantes.class
            );
            comp.setParameter("idCab", cobros.getId());
            listaComprobante = comp.getResultList();
            Query val = em.createNamedQuery("CobCobrosValores.findByCabid", CobCobrosValores.class
            );
            val.setParameter("idCab", cobros.getId());
            listaDetalle = val.getResultList();

            if (listaDetalle.size() > 0) {
                try {
                    for (CobCobrosValores det : listaDetalle) {
                        genericEJB.delete(det);
                    }
                } catch (Exception e) {
                    mensajeError("Ocurrio Un Error al Eliminar el Recibo detalle");
                    e.printStackTrace(System.err);
                    logger.error("Ocurrio un error eliminar detalle Loger ", e);
                }
            }
            if (listaComprobante.size() > 0) {
                try {
                    for (CobCobrosComprobantes compd : listaComprobante) {
                        genericEJB.delete(compd);
                    }
                } catch (Exception e) {
                    mensajeError("Ocurrio un error al recorrer los comprobantes");
                    e.printStackTrace(System.err);
                    logger.error("Ocurrio un error al recorrer los comprobantes Loger ", e);
                }
            }
            genericEJB.delete(cobros);
            mensajeAlerta("Eliminado Correctamente!");
            limpiar();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Cobro");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un error Loger ", e);
        }
    }

    public void onRowSelectEmpresa(SelectEvent em) {
        empresa = (BsEmpresas) em.getObject();
        cobros.setBsEmpresas(empresa);
    }

    public void onRowSelectHabilitacion(SelectEvent mp) {
        habilitacion = (CobHabilitaciones) mp.getObject();
        cobros.setCobHabilitaciones(habilitacion);
    }

    public void onRowSelectCliente(SelectEvent c) {
        cliente = (CobClientes) c.getObject();
        cobros.setCobClientes(cliente);
        getListaVistaComp();
    }

    public void onRowSelectSucursal(SelectEvent s) {
        sucursal = (BsSucursal) s.getObject();
        cobros.setBsSucursal(sucursal);
    }

    public void onRowSelectMoneda(SelectEvent m) {
        moneda = (BsMonedas) m.getObject();
        cobros.setBsMonedas(moneda);
    }

    public void onRowSelectMonedaValor(SelectEvent m) {
        monedaValor = (BsMonedas) m.getObject();
        valores.setBsMonedas(monedaValor);
    }

    public void onRowSelectTipoValor(SelectEvent tv) {
        tipoValor = (BsTipoValor) tv.getObject();
        valores.setBsTipoValor(tipoValor);
        if (tipoValor.getUsaEfectivo().equals("S")) {
            esValor = true;
            valores.setTesCuentaBancaria(null);
        } else {
            esValor = false;

        }

    }

    public void onRowSelectCuentaB(SelectEvent cb) {
        cuentaBanco = (TesCuentaBancaria) cb.getObject();
        valores.setTesCuentaBancaria(cuentaBanco);
    }

    public void onRowSelectCobros(SelectEvent c) {
        setCobrosSelected((CobCobrosCab) c.getObject());
    }

    public void onRowSelectSaldo(SelectEvent vw) {
        vwcobcomprobantes = (Vwcobcomprobantes) vw.getObject();
        if (vwcobcomprobantes.getCobReciboCab() != null && vwcobcomprobantes.getCobReciboCab().getId() != null) {
            comprobantes.setCobRecibosCab(vwcobcomprobantes.getCobReciboCab());
            comprobantes.setVenComprobantesCabecera(null);
            comprobantes.setMontoCuota(vwcobcomprobantes.getMontoCobro());
            comprobantes.setMontoCobro(vwcobcomprobantes.getMontoCobro());
            esRecibos = true;
            esVentas = false;
        } else {
            comprobantes.setVenComprobantesCabecera(vwcobcomprobantes.getVenComprobantesCabecera());
            comprobantes.setCobRecibosCab(null);
            comprobantes.setMontoCuota(vwcobcomprobantes.getMontoCobro());
            comprobantes.setMontoCobro(vwcobcomprobantes.getMontoCobro());
            esRecibos = false;
            esVentas = true;
        }
    }

    public void codClienteChange(ValueChangeEvent cli) {
        if (cli.getNewValue() != null && !(cli.getOldValue() == null && cli.getNewValue().toString().equals(""))) {
            try {
                Query listener = em.createQuery("SELECT c FROM CobClientes c WHERE c.bsEmpresas.id = :idEmpresa and c.codCliente = :codCliente", CobClientes.class
                );
                listener.setParameter("idEmpresa", cobros.getBsEmpresas().getId());
                listener.setParameter("codCliente", cli.getNewValue());
                CobClientes clieAux = (CobClientes) listener.getSingleResult();
                cobros.setCobClientes(clieAux);
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
                Query listener = em.createQuery("SELECT b FROM BsEmpresas b WHERE b.id = :id", BsEmpresas.class
                );
                listener.setParameter("id", emp.getNewValue());
                BsEmpresas empAux = (BsEmpresas) listener.getSingleResult();
                cobros.setBsEmpresas(empAux);
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
                Query listener = em.createQuery("SELECT b FROM BsSucursal b WHERE b.bsEmpresas.id = :idEmpresa and b.codSucursal = :codSucursal", BsSucursal.class
                );
                listener.setParameter("idEmpresa", cobros.getBsEmpresas().getId());
                listener.setParameter("codSucursal", suc.getNewValue());
                BsSucursal sucAux = (BsSucursal) listener.getSingleResult();
                cobros.setBsSucursal(sucAux);
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
                Query listener = em.createQuery("SELECT b FROM BsMonedas b WHERE b.codMoneda = :codMoneda", BsMonedas.class
                );
                listener.setParameter("codMoneda", event.getNewValue());
                BsMonedas monAux = (BsMonedas) listener.getSingleResult();
                cobros.setBsMonedas(monAux);
            } catch (Exception ex) {
                mensajeError("Ocurrio Un Error al Listener Moneda");
                ex.printStackTrace(System.err);
                logger.error("Ocurrio un error Listener Moneda Loger ", ex);
            }
        }
    }

    public void refreshDet() {
        //saldos = null;
        valores = null;
    }

    public void refreshDetComp() {
        //saldos = null;
        comprobantes = null;
    }

    public void addDetalle() {
        try {
            CobCobrosValores obj = new CobCobrosValores();
            obj = valores;
            listaDetalle.add(obj);

            refreshDet();
        } catch (Exception e) {
            mensajeError("Error al agregar a la lista de el Detalle");
            logger.error("Error al agregar datos a la lista el Detalle ", e);
        }
    }

    public void addDetalleComp() {
        try {
            CobCobrosComprobantes objc = new CobCobrosComprobantes();
            objc = comprobantes;
            listaComprobante.add(objc);
            totalComprobantes = totalComprobantes.add(objc.getMontoCobro());
            refreshDetComp();
        } catch (Exception e) {
            mensajeError("Error al agregar a la lista de el Detalle Comprobante");
            logger.error("Error al agregar datos a la lista el Detalle Comprobante ", e);
        }
    }

    public void addDetalleValor() {
        try {
            if (!esModificarValores) {
                CobCobrosValores objv = new CobCobrosValores();
                objv = valores;
                objv.setEstado("P");
                objv.setTipValor(tipoValor.getCodTipo());
                objv.setTipoCambio(BigDecimal.ONE);
                totalValores = totalValores.add(objv.getMontoValor());
                listaDetalle.add(objv);
            } else {
                totalValores = new BigDecimal("0");
                valores.setEstado("P");
                valores.setTipValor(tipoValor.getCodTipo());
                valores.setTipoCambio(BigDecimal.ONE);
                totalValores = totalValores.add(valores.getMontoValor());

                for (CobCobrosValores iter : listaDetalle) {
                    if (valores.getTipValor().equals(iter.getTipValor())) {
                        iter = valores;
                        //listaDetalle.set(i, valores);
                    }
                }
            }
            refreshDet();
        } catch (Exception e) {
            mensajeError("Error al agregar a la lista de el Detalle Valor");
            logger.error("Error al agregar datos a la lista el Detalle Valor ", e);
        }
    }

    public void eliminarDetalle() {
        try {
            if (!listaDetalle.isEmpty()) {
                for (int i = 0; i < listaDetalle.size(); i++) {
                    if (listaDetalle.get(i).getBsTipoValor().getId().equals(valores.getBsTipoValor().getId())) {
                        listaDetalle.remove(i);
                    }
                }
            }
        } catch (Exception e) {
            mensajeError("Error al eliminar de la lista del Detalle valor");
            logger.error("Error al eliminar de la lista del Detalle valor ", e);
        }
    }

    public void imprimir() {

        HashMap<Object, Object> parametros = new HashMap<Object, Object>();
        parametros.put("p_id_empresa", cobros.getBsEmpresas().getId() != null ? cobros.getBsEmpresas().getId() : null);
        parametros.put("descEmpresa", cobros.getBsEmpresas().getId() != null ? cobros.getBsEmpresas().getRepLegal() : null);
        parametros.put("p_id_comprobante", cobros.getId() != null ? cobros.getId() : null);
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

    public BsTipoValor getTipoValor() {
        if (tipoValor == null) {
            tipoValor = new BsTipoValor();
            tipoValor.setBsModulos(new BsModulos());
        }
        return tipoValor;
    }

    public void setTipoValor(BsTipoValor tipoValor) {
        this.tipoValor = tipoValor;
    }

    public boolean isEsModificarValores() {
        return esModificarValores;
    }

    public void setEsModificarValores(boolean esModificarValores) {
        this.esModificarValores = esModificarValores;
    }

    public ArrayList<String> getAcercaDe() {
        if (acercaDe == null || acercaDe.isEmpty()) {
            acercaDe = new ArrayList<>();
            acercaDe.add("Datos de la pantalla:");
            acercaDe.add("- Pantalla: CobCobros.xhtml");
            acercaDe.add("- Controlador: CobCobrosController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: CobCobros.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public BigDecimal getTotalComprobantes() {
        return totalComprobantes;
    }

    public void setTotalComprobantes(BigDecimal totalComprobantes) {
        this.totalComprobantes = totalComprobantes;
    }

    public BigDecimal getTotalValores() {
        return totalValores;
    }

    public void setTotalValores(BigDecimal totalValores) {
        this.totalValores = totalValores;
    }

    public CobClientes getCliente() {
        if (cliente == null) {
            cliente = new CobClientes();
            cliente.setBsPersonas(new BsPersonas());
        }
        return cliente;
    }

    public TesCuentaBancaria getCuentaBanco() {
        if (cuentaBanco == null) {
            cuentaBanco = new TesCuentaBancaria();
            cuentaBanco.setBsEmpresas(new BsEmpresas());
            cuentaBanco.setBsMonedas(new BsMonedas());
            cuentaBanco.setBsPersonas(new BsPersonas());
            cuentaBanco.setTesTipoCuentas(new TesTipoCuentas());
        }
        return cuentaBanco;
    }

    public void setCuentaBanco(TesCuentaBancaria cuentaBanco) {
        this.cuentaBanco = cuentaBanco;
    }

    public Vwcobcomprobantes getVwcobcomprobantes() {
        if (vwcobcomprobantes == null) {
            vwcobcomprobantes = new Vwcobcomprobantes();
            vwcobcomprobantes.setCobReciboCab(new CobRecibosCab());
            vwcobcomprobantes.setVenComprobantesCabecera(new VenComprobantesCabecera());
        }
        return vwcobcomprobantes;
    }

    public void setVwcobcomprobantes(Vwcobcomprobantes vwcobcomprobantes) {
        this.vwcobcomprobantes = vwcobcomprobantes;
    }

    public boolean isEsVentas() {
        return esVentas;
    }

    public void setEsVentas(boolean esVentas) {
        this.esVentas = esVentas;
    }

    public boolean isEsValor() {
        return esValor;
    }

    public void setEsValor(boolean esValor) {
        this.esValor = esValor;
    }

    public void setCliente(CobClientes cliente) {
        this.cliente = cliente;
    }

    public boolean isEsBusqueda() {
        return esBusqueda;
    }

    public boolean isEsRecibos() {
        return esRecibos;
    }

    public void setEsRecibos(boolean esRecibos) {
        this.esRecibos = esRecibos;
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

    public BsMonedas getMonedaValor() {
        if (monedaValor == null) {
            monedaValor = new BsMonedas();
        }
        return monedaValor;
    }

    public void setMonedaValor(BsMonedas monedaValor) {
        this.monedaValor = monedaValor;
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

    public CobCobrosCab getCobros() {
        if (cobros == null) {
            cobros = new CobCobrosCab();
            cobros.setBsEmpresas(new BsEmpresas());
            cobros.getBsEmpresas().setBsPersonas(new BsPersonas());
            cobros.setBsMonedas(new BsMonedas());
            cobros.setBsSucursal(new BsSucursal());
            cobros.setCobClientes(new CobClientes());
            cobros.getCobClientes().setBsPersonas(new BsPersonas());
            cobros.setCobHabilitaciones(new CobHabilitaciones());
            cobros.getCobHabilitaciones().setCobCajas(new CobCajas());
        }
        return cobros;
    }

    public void setCobros(CobCobrosCab cobros) {
        this.cobros = cobros;
    }

    public CobCobrosCab getCobrosSelected() {
        if (cobrosSelected == null) {
            cobrosSelected = new CobCobrosCab();
            cobrosSelected.setBsEmpresas(new BsEmpresas());
            cobrosSelected.getBsEmpresas().setBsPersonas(new BsPersonas());
            cobrosSelected.setBsMonedas(new BsMonedas());
            cobrosSelected.setBsSucursal(new BsSucursal());
            cobrosSelected.setCobClientes(new CobClientes());
            cobrosSelected.getCobClientes().setBsPersonas(new BsPersonas());
            cobrosSelected.setCobHabilitaciones(new CobHabilitaciones());
            cobrosSelected.getCobHabilitaciones().setCobCajas(new CobCajas());
        }
        return cobrosSelected;
    }

    public void setCobrosSelected(CobCobrosCab cobrosSelected) {
        this.cobrosSelected = cobrosSelected;
        if (cobrosSelected != null) {
            cobros = cobrosSelected;
            Query comp = em.createNamedQuery("CobCobrosComprobantes.findByCabid", CobCobrosComprobantes.class
            );
            comp.setParameter("idCab", cobros.getId());
            listaComprobante = comp.getResultList();
            if (!listaComprobante.isEmpty()) {
                for (CobCobrosComprobantes itecomp : listaComprobante) {
                    totalComprobantes = totalComprobantes.add(itecomp.getMontoCobro());
                
                }
            }
            Query val = em.createNamedQuery("CobCobrosValores.findByCabid", CobCobrosValores.class
            );
            val.setParameter("idCab", cobros.getId());
            listaDetalle = val.getResultList();
            listaDetalleAux = new ArrayList<>();
            listaDetalleAux = val.getResultList();
            if (!listaDetalle.isEmpty()) {
                for (CobCobrosValores iteval : listaDetalle) {
                    totalValores = totalValores.add(iteval.getMontoValor());
                }
            }
        }
    }

    public CobCobrosComprobantes getComprobantes() {
        if (comprobantes == null) {
            comprobantes = new CobCobrosComprobantes();
            comprobantes.setCobCobrosCab(new CobCobrosCab());
            comprobantes.setCobRecibosCab(new CobRecibosCab());
            comprobantes.setVenComprobantesCabecera(new VenComprobantesCabecera());
        }
        return comprobantes;
    }

    public void setComprobantes(CobCobrosComprobantes comprobantes) {
        this.comprobantes = comprobantes;
    }

    public CobCobrosComprobantes getComprobantesSelected() {
        if (comprobantesSelected == null) {
            comprobantesSelected = new CobCobrosComprobantes();
            comprobantesSelected.setCobCobrosCab(new CobCobrosCab());
            comprobantesSelected.setCobRecibosCab(new CobRecibosCab());
            comprobantesSelected.setVenComprobantesCabecera(new VenComprobantesCabecera());
        }
        return comprobantesSelected;
    }

    public void setComprobantesSelected(CobCobrosComprobantes comprobantesSelected) {
        this.comprobantesSelected = comprobantesSelected;
    }

    public CobCobrosValores getValores() {
        if (valores == null) {
            valores = new CobCobrosValores();
            valores.setBsMonedas(new BsMonedas());
            valores.setBsTipoValor(new BsTipoValor());
            valores.setCobCobrosCab(new CobCobrosCab());
            valores.setTesCuentaBancaria(new TesCuentaBancaria());
            valores.getTesCuentaBancaria().setBsPersonas(new BsPersonas());
        }
        return valores;
    }

    public void setValores(CobCobrosValores valores) {
        this.valores = valores;
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

    public List<CobCobrosValores> getListaDetalle() {
        if (listaDetalle == null) {
            listaDetalle = new ArrayList<>();
        }
        for (CobCobrosValores det : listaDetalle) {
            if (det.getBsMonedas() == null) {
                det.setBsMonedas(new BsMonedas());
            }
            if (det.getBsTipoValor() == null) {
                det.setBsTipoValor(new BsTipoValor());
            }
            if (det.getCobCobrosCab() == null) {
                det.setCobCobrosCab(new CobCobrosCab());
            }
        }
        return listaDetalle;
    }

    public void setListaDetalle(List<CobCobrosValores> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public List<CobCobrosComprobantes> getListaComprobante() {
        if (listaComprobante == null) {
            listaComprobante = new ArrayList<>();
        }
        for (CobCobrosComprobantes comp : listaComprobante) {
            if (comp.getCobCobrosCab() == null) {
                comp.setCobCobrosCab(new CobCobrosCab());
            }
            if (comp.getCobRecibosCab() == null) {
                comp.setCobRecibosCab(new CobRecibosCab());
            }
            if (comp.getVenComprobantesCabecera() == null) {
                comp.setVenComprobantesCabecera(new VenComprobantesCabecera());
            }
        }
        return listaComprobante;
    }

    public void setListaComprobante(List<CobCobrosComprobantes> listaComprobante) {
        this.listaComprobante = listaComprobante;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    public LazyDataModel getLazyCobrosoDT() {
        if (lazyCobrosoDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyCobrosoDT
                    = genericLazy.getLazyDataModel(CobCobrosCab.class,
                            campos);
        }
        return lazyCobrosoDT;
    }

    public void setLazyCobrosoDT(LazyDataModel lazyCobrosoDT) {
        this.lazyCobrosoDT = lazyCobrosoDT;
    }

    public LazyDataModel getLazyMonedaValorDG() {
        if (lazyMonedaValorDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyMonedaValorDG
                    = genericLazy.getLazyDataModel(BsMonedas.class,
                            campos);
        }
        return lazyMonedaValorDG;
    }

    public void setLazyMonedaValorDG(LazyDataModel lazyMonedaValorDG) {
        this.lazyMonedaValorDG = lazyMonedaValorDG;
    }

    public LazyDataModel getLazyCuentaBancoDG() {
        if (lazyCuentaBancoDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyCuentaBancoDG
                    = genericLazy.getLazyDataModel(TesCuentaBancaria.class,
                            campos);
        }
        return lazyCuentaBancoDG;
    }

    public void setLazyCuentaBancoDG(LazyDataModel lazyCuentaBancoDG) {
        this.lazyCuentaBancoDG = lazyCuentaBancoDG;
    }

    public LazyDataModel getListaVistaComp() {
        if (cliente != null && cliente.getId() != null) {
            if (listaVistaComp == null) {
                genericLazy = new GenericBigLazyList(em);
                campos = new HashMap();
                campos.put("cobClientes.id", cliente.getId());
                listaVistaComp
                        = genericLazy.getLazyDataModel(Vwcobcomprobantes.class,
                                campos);
            }
        }
        return listaVistaComp;
    }

    public void setListaVistaComp(LazyDataModel listaVistaComp) {
        this.listaVistaComp = listaVistaComp;
    }

    public LazyDataModel getLazyHabilitacionDG() {
        if (lazyHabilitacionDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyHabilitacionDG
                    = genericLazy.getLazyDataModel(CobHabilitaciones.class,
                            campos);
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
            lazyCobradorDG
                    = genericLazy.getLazyDataModel(CobCobrador.class,
                            campos);
        }
        return lazyCobradorDG;
    }

    public void setLazyCobradorDG(LazyDataModel lazyCobradorDG) {
        this.lazyCobradorDG = lazyCobradorDG;
    }

    public LazyDataModel getLazyMonedaDG() {
        if (lazyMonedaDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyMonedaDG
                    = genericLazy.getLazyDataModel(BsMonedas.class,
                            campos);
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
            lazyEmpresaDG
                    = genericLazy.getLazyDataModel(BsEmpresas.class,
                            campos);
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
            lazySucursalDG
                    = genericLazy.getLazyDataModel(BsSucursal.class,
                            campos);
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
            lazyTalonarioDG
                    = genericLazy.getLazyDataModel(BsTalonarios.class,
                            campos);
        }
        return lazyTalonarioDG;
    }

    public void setLazyTalonarioDG(LazyDataModel lazyTalonarioDG) {
        this.lazyTalonarioDG = lazyTalonarioDG;
    }

    public LazyDataModel getLazyTipoValorDG() {
        if (lazyTipoValorDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            campos.put("bsModulos.id", "2");
            lazyTipoValorDG
                    = genericLazy.getLazyDataModel(BsTipoValor.class,
                            campos);
        }
        return lazyTipoValorDG;
    }

    public void setLazyTipoValorDG(LazyDataModel lazyTipoValorDG) {
        this.lazyTipoValorDG = lazyTipoValorDG;
    }

    public LazyDataModel getLazyClienteDG() {
        if (lazyClienteDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyClienteDG
                    = genericLazy.getLazyDataModel(CobClientes.class,
                            campos);
        }
        return lazyClienteDG;
    }

    public void setLazyClienteDG(LazyDataModel lazyClienteDG) {
        this.lazyClienteDG = lazyClienteDG;
    }

//</editor-fold>
}
