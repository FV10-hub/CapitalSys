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
import com.py.jpa.CobCobrosCab;
import com.py.jpa.CobHabilitaciones;
import com.py.jpa.CrMotivosPrestamos;
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
public class VenVentasController extends GenericMensajes implements Serializable {

    Logger logger = Logger.getLogger(VenVentasController.class);
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
    private LazyDataModel lazyVentasDT;
    private LazyDataModel lazyMonedaDG;
    private LazyDataModel lazyEmpresaDG;
    private LazyDataModel lazySucursalDG;
    private LazyDataModel lazyTalonarioDG;
    private LazyDataModel lazyClienteDG;
    private LazyDataModel lazyVendedorDG;
    private LazyDataModel lazyHabilitacionDG;
    private LazyDataModel lazyCondicionVentaDG;
    private LazyDataModel lazyArticulosDG;
    private LazyDataModel lazyIvaDG;

    private CobClientes cliente;
    private VenVendedor vendedor;
    private BsSucursal sucursal;
    private BsMonedas moneda;
    private BsEmpresas empresa;
    private BsTalonarios talonario;
    private CobHabilitaciones habilitacion;
    private VenCondicionVenta condicion;
    private StoArticulos articulos;
    private BsIva iva;
    private VenComprobantesCabecera ventas, ventasSelected;
    private VenComprobantesDetalle ventasDetalle, ventasDetalleSelected;
    private List<VenComprobantesDetalle> listaDetalle;
    private boolean renderForm;
    private boolean renderActiv;
    private BigDecimal precioU = new BigDecimal(0);
    private BigDecimal cantDetalle = new BigDecimal(0);
    private ImprimirReportes imprimirReporte;

    @PostConstruct
    private void ini() {
        limpiar();
    }

//<editor-fold defaultstate="collapsed" desc="Metodos">
    public void limpiar() {
        cliente = null;
        vendedor = null;
        sucursal = null;
        moneda = null;
        empresa = null;
        talonario = null;
        ventas = null;
        ventasSelected = null;
        ventasDetalle = null;
        getVentasDetalle();
        ventasDetalleSelected = null;
        getVentasDetalleSelected();
        habilitacion = null;
        condicion = null;
        articulos = null;
        iva = null;
        listaDetalle = null;
        esModificar = false;
        renderForm = false;
        renderActiv = true;
        ayuda = null;
        acercaDe = null;
        imprimirReporte = new ImprimirReportes();
    }

    public void guardar() {
        try {
            BigDecimal nro_comprobante = new BigDecimal(BigInteger.ZERO);
            BigDecimal montoTotal = new BigDecimal(BigInteger.ZERO);
            BigDecimal totalGravada = new BigDecimal(BigInteger.ZERO);
            BigDecimal totalIva = new BigDecimal(BigInteger.ZERO);
            BigDecimal totalexenta = new BigDecimal(BigInteger.ZERO);
            try {
                Query nro_aux = em.createQuery("SELECT MAX(v.nroComprobante) FROM VenComprobantesCabecera v");
                nro_comprobante = (BigDecimal) nro_aux.getSingleResult();
            } catch (Exception e) {
                nro_comprobante = new BigDecimal(BigInteger.ZERO);
            }
            if (ventas.getCobCobrosCab() != null && ventas.getCobCobrosCab().getId() == null) {
                ventas.setCobCobrosCab(null);
            }
            List<VenComprobantesDetalle> d =  new ArrayList<>();
            d = listaDetalle;
            listaDetalle = new ArrayList<>();
             int i = 0;
            if (!d.isEmpty()) {
                try {
                    for (VenComprobantesDetalle list : d) {
                        list.setVenComprobantesCabecera(ventas);
                        listaDetalle.add(list);
                    }
                    for (VenComprobantesDetalle def : listaDetalle) {
                        totalGravada = totalGravada.add(def.getGravada());
                        totalIva = totalIva.add(def.getMontoIva());
                        totalexenta = totalexenta.add(def.getMontoExenta());
                        montoTotal = montoTotal.add(totalGravada.add(totalIva).add(totalexenta));
                        def.setVenComprobantesCabecera(ventas);
                        //listaDetalle.get(i).setVenComprobantesCabecera(ventas);
                        //list.setVenComprobantesCabecera(ventas);
                        //i = i + 1;
                    }

                } catch (Exception e) {
                    mensajeError("Ocurrio un error al calcular total cabecera");
                    e.printStackTrace(System.err);
                    logger.error("Ocurrio un error al calcular total cabecera Loger ", e);
                }
                ventas.setMontoTotal(montoTotal.add(totalexenta));
                ventas.setTotalGravada(totalGravada);
                ventas.setTotalIva(totalIva);
            } else {
                mensajeError("No puede Guardar una Venta sin Detalle");
                return;
            }
            if (nro_comprobante == null) {
                nro_comprobante = new BigDecimal(BigInteger.ZERO);
            }
            ventas.setNroComprobante(nro_comprobante.add(new BigDecimal(BigInteger.ONE)));
            ventas.setSerTimbtado(talonario.getBsTimbrados().getCodEstablecimiento() + "-" + talonario.getBsTimbrados().getCodExpedicion());
            ventas.setVenComprobantesDetalleList(listaDetalle);
            if (genericEJB.insert(ventas)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyVentasDT();
            } else {
                mensajeAlerta("No se pudo Guardar");
            }
            limpiar();
            //       }
        } catch (Exception e) {
            mensajeError("Ocurrio un error");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un error Loger ", e);
        }
    }

    public void edit() {
        try {
            genericEJB.update(ventas);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyVentasDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar la Venta");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un error Loger ", e);
        }
    }

    public void delete() {
        try {
            Query detalle = em.createNamedQuery("VenComprobantesDetalle.findByCab", VenComprobantesDetalle.class);
            detalle.setParameter("idCab", ventas.getId());
            listaDetalle = detalle.getResultList();
            if (listaDetalle.size() > 0) {
                for (VenComprobantesDetalle det : listaDetalle) {
                    try {
                        genericEJB.delete(det);
                    } catch (Exception e) {
                        mensajeError("Ocurrio Un Error al Eliminar la Venta detalle");
                        e.printStackTrace(System.err);
                        logger.error("Ocurrio un error eliminar detalle Loger ", e);
                    }
                }
            }
            genericEJB.delete(ventas);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyVentasDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar la Venta");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un error Loger ", e);
        }
    }

    public void onRowSelectEmpresa(SelectEvent em) {
        empresa = (BsEmpresas) em.getObject();
        ventas.setBsEmpresas(empresa);
    }

    public void onRowSelectHabilitacion(SelectEvent mp) {
        habilitacion = (CobHabilitaciones) mp.getObject();
        ventas.setCobHabilitaciones(habilitacion);
    }

    public void onRowSelectCondicion(SelectEvent mp) {
        condicion = (VenCondicionVenta) mp.getObject();
        ventas.setVenCondicionVenta(condicion);
    }

    public void onRowSelectArticulo(SelectEvent a) {
        articulos = (StoArticulos) a.getObject();
        ventasDetalle.setStoArticulos(articulos);
        ventasDetalle.setBsIva(articulos.getBsIva());
    }

    public void codClienteChange(ValueChangeEvent cli) {
        if (cli.getNewValue() != null && !(cli.getOldValue() == null && cli.getNewValue().toString().equals(""))) {
            try {
                Query listener = em.createQuery("SELECT c FROM CobClientes c WHERE c.bsEmpresas.id = :idEmpresa and c.codCliente = :codCliente", CobClientes.class);
                listener.setParameter("idEmpresa", ventas.getBsEmpresas().getId());
                listener.setParameter("codCliente", cli.getNewValue());
                CobClientes clieAux = (CobClientes) listener.getSingleResult();
                ventas.setCobClientes(clieAux);
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
                ventas.setBsEmpresas(empAux);
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
                listener.setParameter("idEmpresa", ventas.getBsEmpresas().getId());
                listener.setParameter("codSucursal", suc.getNewValue());
                BsSucursal sucAux = (BsSucursal) listener.getSingleResult();
                ventas.setBsSucursal(sucAux);
            } catch (Exception ex) {
                mensajeError("Ocurrio Un Error al Listener Sucursal");
                ex.printStackTrace(System.err);
                logger.error("Ocurrio un error Listener Sucursal Loger ", ex);
            }
        }
    }

    public void codVendedorChange(ValueChangeEvent ve) {
        if (ve.getNewValue() != null && !(ve.getOldValue() == null && ve.getNewValue().toString().equals(""))) {
            try {
                Query listener = em.createQuery("SELECT v FROM VenVendedor v WHERE v.codVendedor = :codVendedor and v.bsEmpresas.id = :idEmpresa", VenVendedor.class);
                listener.setParameter("idEmpresa", ventas.getBsEmpresas().getId());
                listener.setParameter("codVendedor", ve.getNewValue());
                VenVendedor venAux = (VenVendedor) listener.getSingleResult();
                ventas.setVenVendedor(venAux);
            } catch (Exception ex) {
                mensajeError("Ocurrio Un Error al Listener Vendedores");
                ex.printStackTrace(System.err);
                logger.error("Ocurrio un error Listener Vendedores Loger ", ex);
            }
        }
    }

    public void codMonedaChange(ValueChangeEvent event) {
        if (event.getNewValue() != null && !(event.getOldValue() == null && event.getNewValue().toString().equals(""))) {
            try {
                Query listener = em.createQuery("SELECT b FROM BsMonedas b WHERE b.codMoneda = :codMoneda", BsMonedas.class);
                listener.setParameter("codMoneda", event.getNewValue());
                BsMonedas monAux = (BsMonedas) listener.getSingleResult();
                ventas.setBsMonedas(monAux);
            } catch (Exception ex) {
                mensajeError("Ocurrio Un Error al Listener Moneda");
                ex.printStackTrace(System.err);
                logger.error("Ocurrio un error Listener Moneda Loger ", ex);
            }
        }
    }

    public void calculaTotales() {
        try {

            BigDecimal totalLinea = new BigDecimal(0);
            BigDecimal monto_exenta = new BigDecimal(0);
            BigDecimal gravada = new BigDecimal(0);
            BigDecimal monto_iva = new BigDecimal(0);
            BigDecimal gravada_10 = new BigDecimal(0);
            BigDecimal gravada_5 = new BigDecimal(0);
            BigDecimal iva_10 = new BigDecimal(0);
            BigDecimal iva_5 = new BigDecimal(0);
            BigDecimal calcgrav10 = new BigDecimal("1.1");
            BigDecimal calcgrav5 = new BigDecimal("21");
//            BigDecimal cantAux = cantDetalle;
//            BigDecimal precioAux = precioU;
            if (articulos.getBsIva().getCodIva().equals("0")) {
                totalLinea = cantDetalle.multiply(precioU);
                monto_exenta = totalLinea;
                gravada = new BigDecimal(0);
                monto_iva = new BigDecimal(0);
                gravada_10 = new BigDecimal(0);
                gravada_5 = new BigDecimal(0);
                iva_10 = new BigDecimal(0);
                iva_5 = new BigDecimal(0);
            } else if (articulos.getBsIva().getCodIva().equals("1")) {
                totalLinea = cantDetalle.multiply(precioU);
                monto_exenta = new BigDecimal(0);
                gravada = totalLinea.divide(calcgrav10, 10, RoundingMode.HALF_UP);
                monto_iva = totalLinea.divide(new BigDecimal(11), 10, RoundingMode.HALF_UP);
                gravada_10 = totalLinea.divide(calcgrav10, 10, RoundingMode.HALF_UP);
                gravada_5 = new BigDecimal(0);
                iva_10 = totalLinea.divide(new BigDecimal(11), 10, RoundingMode.HALF_UP);
                iva_5 = new BigDecimal(0);
            } else if (articulos.getBsIva().getCodIva().equals("2")) {
                totalLinea = cantDetalle.multiply(precioU);
                monto_exenta = new BigDecimal(0);
                gravada = totalLinea.divide(calcgrav10);
                monto_iva = totalLinea.divide(new BigDecimal(11), 10, RoundingMode.HALF_UP);
                gravada_10 = totalLinea.divide(calcgrav10, 10, RoundingMode.HALF_UP);
                gravada_5 = new BigDecimal(0);
                iva_10 = totalLinea.divide(new BigDecimal(11), 10, RoundingMode.HALF_UP);
                iva_5 = new BigDecimal(0);
            }

            ventasDetalle.setGravada(gravada);
            ventasDetalle.setMontoExenta(monto_exenta);
            ventasDetalle.setMontoIva(monto_iva);
            ventasDetalle.setGravada10(gravada_10);
            ventasDetalle.setGravada5(gravada_5);
            ventasDetalle.setIva10(iva_10);
            ventasDetalle.setIva5(iva_5);
            ventasDetalle.setTotalLinea(totalLinea);
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Calcular Totales");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un error Loger ", e);
        }
    }

    public void onRowSelectIva(SelectEvent i) {
        iva = (BsIva) i.getObject();
        ventasDetalle.setBsIva(iva);
    }

    public void onRowSelectCliente(SelectEvent c) {
        cliente = (CobClientes) c.getObject();
        ventas.setCobClientes(cliente);
    }

    public void onRowSelectVendedor(SelectEvent v) {
        vendedor = (VenVendedor) v.getObject();
        ventas.setVenVendedor(vendedor);
    }

    public void onRowSelectSucursal(SelectEvent s) {
        sucursal = (BsSucursal) s.getObject();
        ventas.setBsSucursal(sucursal);
    }

    public void onRowSelectMoneda(SelectEvent m) {
        moneda = (BsMonedas) m.getObject();
        ventas.setBsMonedas(moneda);
    }

    public void onRowSelectTalonario(SelectEvent t) {
        talonario = (BsTalonarios) t.getObject();
        ventas.setBsTalonarios(talonario);
    }

    public void refreshDet() {
        iva = null;
        articulos = null;
        precioU = new BigDecimal(0);
        cantDetalle = new BigDecimal(0);
        ventasDetalle = null;
    }

    public void addDetalle() {
        try {
            VenComprobantesDetalle obj = new VenComprobantesDetalle();
            obj = ventasDetalle;
            obj.setCantidad(cantDetalle);
            obj.setPrecioUnitario(precioU);
            //obj.setVenComprobantesCabecera(ventas);
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
                    if (listaDetalle.get(i).getStoArticulos().getCodArticulo().equals(ventasDetalleSelected.getStoArticulos().getCodArticulo())) {
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
        parametros.put("p_id_empresa", ventas.getBsEmpresas().getId() != null ? ventas.getBsEmpresas().getId() : null);
        parametros.put("descEmpresa", ventas.getBsEmpresas().getId() != null ? ventas.getBsEmpresas().getRepLegal() : null);
        parametros.put("p_id_comprobante", ventas.getId() != null ? ventas.getId() : null);
        parametros.put("impresoPor", login.getBsUsuario() != null ? login.getBsUsuario().getCodUsuario() : null);

        imprimirReporte.setParametros(parametros);
        imprimirReporte.setNombreReporte("VenRptFacturas");
        imprimirReporte.imprimir();
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters&Setters">
    public boolean isRenderForm() {
        return renderForm;
    }

    public void setRenderForm(boolean renderForm) {
        this.renderForm = renderForm;
    }

    public boolean isRenderActiv() {
        return renderActiv;
    }

    public void setRenderActiv(boolean renderActiv) {
        this.renderActiv = renderActiv;
    }

    public boolean isEsModificar() {
        return esModificar;
    }

    public void setEsModificar(boolean esModificar) {
        this.esModificar = esModificar;
    }

    public BigDecimal getPrecioU() {
        return precioU;
    }

    public void setPrecioU(BigDecimal precioU) {
        System.out.println("paso en el puto SET " + precioU);
        this.precioU = precioU;
    }

    public BigDecimal getCantDetalle() {
        return cantDetalle;
    }

    public void setCantDetalle(BigDecimal cantDetalle) {
        this.cantDetalle = cantDetalle;
    }

    public ArrayList<String> getAyuda() {
        if (ayuda == null) {
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
        if (acercaDe == null) {
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

    public CobHabilitaciones getHabilitacion() {
        if (habilitacion == null) {
            habilitacion = new CobHabilitaciones();
        }
        return habilitacion;
    }

    public void setHabilitacion(CobHabilitaciones habilitacion) {
        this.habilitacion = habilitacion;
    }

    public StoArticulos getArticulos() {
        if (articulos == null) {
            articulos = new StoArticulos();
            articulos.setBsEmpresas(new BsEmpresas());
            articulos.setBsIva(new BsIva());
        }
        return articulos;
    }

    public void setArticulos(StoArticulos articulos) {
        this.articulos = articulos;
    }

    public BsIva getIva() {
        if (iva == null) {
            iva = new BsIva();
        }
        return iva;
    }

    public void setIva(BsIva iva) {
        this.iva = iva;
    }

    public VenComprobantesCabecera getVentas() {
        if (ventas == null) {
            ventas = new VenComprobantesCabecera();
            ventas.setBsEmpresas(new BsEmpresas());
            ventas.getBsEmpresas().setBsPersonas(new BsPersonas());
            ventas.setBsMonedas(new BsMonedas());
            ventas.setBsSucursal(new BsSucursal());
            ventas.setBsTalonarios(new BsTalonarios());
            ventas.getBsTalonarios().setBsTipComprobantes(new BsTipComprobantes());
            ventas.setCobClientes(new CobClientes());
            ventas.getCobClientes().setBsPersonas(new BsPersonas());
            ventas.setVenVendedor(new VenVendedor());
            ventas.getVenVendedor().setBsPersonas(new BsPersonas());
            ventas.setCobCobrosCab(new CobCobrosCab());
            ventas.setCobHabilitaciones(new CobHabilitaciones());
            ventas.getCobHabilitaciones().setCobCajas(new CobCajas());
            ventas.setVenCondicionVenta(new VenCondicionVenta());
        }
        return ventas;
    }

    public void setVentas(VenComprobantesCabecera ventas) {
        this.ventas = ventas;
    }

    public VenComprobantesCabecera getVentasSelected() {
        if (ventasSelected == null) {
            ventasSelected = new VenComprobantesCabecera();
            ventasSelected.setBsEmpresas(new BsEmpresas());
            ventasSelected.getBsEmpresas().setBsPersonas(new BsPersonas());
            ventasSelected.setBsMonedas(new BsMonedas());
            ventasSelected.setBsSucursal(new BsSucursal());
            ventasSelected.setBsTalonarios(new BsTalonarios());
            ventasSelected.getBsTalonarios().setBsTipComprobantes(new BsTipComprobantes());
            ventasSelected.setCobClientes(new CobClientes());
            ventasSelected.getCobClientes().setBsPersonas(new BsPersonas());
            ventasSelected.setVenVendedor(new VenVendedor());
            ventasSelected.getVenVendedor().setBsPersonas(new BsPersonas());
            ventasSelected.setCobCobrosCab(new CobCobrosCab());
            ventasSelected.setCobHabilitaciones(new CobHabilitaciones());
            ventasSelected.getCobHabilitaciones().setCobCajas(new CobCajas());
            ventasSelected.setVenCondicionVenta(new VenCondicionVenta());
        }
        return ventasSelected;
    }

    public void setVentasSelected(VenComprobantesCabecera ventasSelected) {
        this.ventasSelected = ventasSelected;
        if (ventasSelected != null) {
            ventas = ventasSelected;
            Query detalle = em.createNamedQuery("VenComprobantesDetalle.findByCab", VenComprobantesDetalle.class);
            detalle.setParameter("idCab", ventas.getId());
            listaDetalle = detalle.getResultList();
        }
    }

    public VenComprobantesDetalle getVentasDetalle() {
        if (ventasDetalle == null) {
            ventasDetalle = new VenComprobantesDetalle();
            ventasDetalle.setVenComprobantesCabecera(new VenComprobantesCabecera());
            ventasDetalle.setBsIva(new BsIva());
            ventasDetalle.setStoArticulos(new StoArticulos());
        }
        return ventasDetalle;
    }

    public void setVentasDetalle(VenComprobantesDetalle ventasDetalle) {
        this.ventasDetalle = ventasDetalle;
    }

    public VenComprobantesDetalle getVentasDetalleSelected() {
        if (ventasDetalleSelected == null) {
            ventasDetalleSelected = new VenComprobantesDetalle();
            ventasDetalleSelected.setVenComprobantesCabecera(new VenComprobantesCabecera());
            ventasDetalleSelected.setBsIva(new BsIva());
            ventasDetalleSelected.setStoArticulos(new StoArticulos());

        }
        return ventasDetalleSelected;
    }

    public void setVentasDetalleSelected(VenComprobantesDetalle ventasDetalleSelected) {
        this.ventasDetalleSelected = ventasDetalleSelected;
    }

    public VenCondicionVenta getCondicion() {
        if (condicion == null) {
            condicion = new VenCondicionVenta();
            condicion.setBsEmpresas(new BsEmpresas());
        }
        return condicion;
    }

    public void setCondicion(VenCondicionVenta condicion) {
        this.condicion = condicion;
    }

    public List<VenComprobantesDetalle> getListaDetalle() {
        if (listaDetalle == null) {
            listaDetalle = new ArrayList<>();
        }
        for (VenComprobantesDetalle det : listaDetalle) {
            if (det.getBsIva() == null) {
                det.setBsIva(new BsIva());
            }
            if (det.getStoArticulos() == null) {
                det.setStoArticulos(new StoArticulos());
            }
        }
        return listaDetalle;
    }

    public void setListaDetalle(List<VenComprobantesDetalle> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    public LazyDataModel getLazyVentasDT() {
        if (lazyVentasDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyVentasDT = genericLazy.getLazyDataModel(VenComprobantesCabecera.class, campos);
        }
        return lazyVentasDT;
    }

    public void setLazyVentasDT(LazyDataModel lazyVentasDT) {
        this.lazyVentasDT = lazyVentasDT;
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

    public LazyDataModel getLazyCondicionVentaDG() {
        if (lazyCondicionVentaDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyCondicionVentaDG = genericLazy.getLazyDataModel(VenCondicionVenta.class, campos);
        }
        return lazyCondicionVentaDG;
    }

    public void setLazyCondicionVentaDG(LazyDataModel lazyCondicionVentaDG) {
        this.lazyCondicionVentaDG = lazyCondicionVentaDG;
    }

    public LazyDataModel getLazyArticulosDG() {
        if (lazyArticulosDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyArticulosDG = genericLazy.getLazyDataModel(StoArticulos.class, campos);
        }
        return lazyArticulosDG;
    }

    public void setLazyArticulosDG(LazyDataModel lazyArticulosDG) {
        this.lazyArticulosDG = lazyArticulosDG;
    }

    public LazyDataModel getLazyIvaDG() {
        if (lazyIvaDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyIvaDG = genericLazy.getLazyDataModel(BsIva.class, campos);
        }
        return lazyIvaDG;
    }

    public void setLazyIvaDG(LazyDataModel lazyIvaDG) {
        this.lazyIvaDG = lazyIvaDG;
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
            campos.put("bsTipComprobantes.bsModulos.id", "3");
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

//</editor-fold>
}
