package com.py.controller;

import com.py.jpa.BsEmpresas;
import com.py.jpa.BsMonedas;
import com.py.jpa.BsPersonas;
import com.py.jpa.CobClientes;
import com.py.jpa.ComProveedores;
import com.py.jpa.CreDesembolsosCab;
import com.py.jpa.TesDepositosCab;
import com.py.jpa.TesOrdenPago;
import com.py.jpa.TesOrdenPagoDet;
import com.py.utils.GenericBigLazyList;
import com.py.utils.GenericController;
import com.py.utils.GenericoEJB;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
public class TesOrdenPagoController extends GenericController implements Serializable {

    Logger logger = Logger.getLogger(TesOrdenPagoController.class);
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
    private LazyDataModel lazyOrdenPagoDT;
    private LazyDataModel lazyOrdenPagoDG;
    private LazyDataModel lazyCreditosDG;
    private LazyDataModel lazyProveedoresDG;

    private ComProveedores proveedores;
    private TesOrdenPago ordenPago, ordenSelected;
    private TesOrdenPagoDet detalle, detalleSelected;
    private List<TesOrdenPagoDet> listaDetalle;
    private List<CreDesembolsosCab> listaCreditos;
    private boolean esBusqueda;

    @PostConstruct
    private void ini() {
        limpiar();
    }

    public void limpiar() {
        proveedores = null;
        ordenPago = null;
        ordenSelected = null;
        detalle = null;
        detalleSelected = null;
        listaDetalle = null;
        listaCreditos = null;
        esModificar = false;
        ayuda = null;
        acercaDe = null;
        esBusqueda = false;
    }

    public void guardar() {
        try {
            BigDecimal nro_comprobante = new BigDecimal(BigInteger.ZERO);
            BigDecimal montoTotal = new BigDecimal(BigInteger.ZERO);

            try {
                Query nro_aux = em.createQuery("SELECT MAX(c.nroOp) FROM TesOrdenPago c");
                nro_comprobante = (BigDecimal) nro_aux.getSingleResult();
            } catch (Exception e) {
                nro_comprobante = new BigDecimal(BigInteger.ZERO);
            }
            List<TesOrdenPagoDet> d = new ArrayList<>();
            d = listaDetalle;
            listaDetalle = new ArrayList<>();
            int i = 0;
            if (!d.isEmpty()) {
                try {
                    for (TesOrdenPagoDet list : d) {
                        list.setTesOrdenPago(ordenPago);
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

            ordenPago.setNroOp(nro_comprobante.add(new BigDecimal(BigInteger.ONE)));
            ordenPago.setTesOrdenPagoDetList(listaDetalle);
            if (genericEJB.insert(ordenPago)) {
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

    public void delete() {
        try {
            Query detalleAux = em.createNamedQuery("TesOrdenPagoDet.findByIdCab", TesOrdenPago.class);
            detalleAux.setParameter("idCab", ordenPago);
            listaDetalle = detalleAux.getResultList();
            if (listaDetalle.size() > 0) {
                for (TesOrdenPagoDet det : listaDetalle) {
                    try {
                        genericEJB.delete(det);
                    } catch (Exception e) {
                        mensajeError("Ocurrio Un Error al Eliminar el Orden Pago detalle");
                        e.printStackTrace(System.err);
                        logger.error("Ocurrio un error eliminar detalle Loger ", e);
                    }
                }
            }
            genericEJB.delete(ordenPago);
            mensajeAlerta("Eliminado Correctamente!");
            limpiar();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar la Orden de Pago");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un error Loger ", e);
        }
    }

    public void onRowSelectProveedor(SelectEvent c) {
        proveedores = (ComProveedores) c.getObject();
        ordenPago.setComProveedores(proveedores);
    }

    public void onRowSelectOrden(SelectEvent o) {
        setOrdenSelected((TesOrdenPago) o.getObject());
    }

    public void codProveedorChange(ValueChangeEvent cli) {
        if (cli.getNewValue() != null && !(cli.getOldValue() == null && cli.getNewValue().toString().equals(""))) {
            try {
                Query listener = em.createQuery("SELECT c FROM ComProveedores c WHERE c.codProveedor = :codProveedor", CobClientes.class);
                listener.setParameter("codProveedor", cli.getNewValue());
                ComProveedores provAux = (ComProveedores) listener.getSingleResult();
                ordenPago.setComProveedores(provAux);
            } catch (Exception ex) {
                mensajeError("Ocurrio Un Error al Listener Proveedores");
                ex.printStackTrace(System.err);
                logger.error("Ocurrio un error Listener Proveedores Loger ", ex);
            }
        }
    }

    public void agregarDetalle() {
        for (CreDesembolsosCab iter : listaCreditos) {
            if (iter.getId() != null) {
                detalleSelected = new TesOrdenPagoDet();
                detalleSelected.setCreDesembolsosCab(iter);
                detalleSelected.setBsMonedas(iter.getCreSolicitudes().getBsMonedas());
                detalleSelected.setMontoTotal(iter.getCreSolicitudes().getMontoAprobado());
                detalleSelected.setIdComprobante(null);
                detalleSelected.setTipoCambio(BigDecimal.ONE);
                listaDetalle.add(detalleSelected);
                limpiarDetalle();
            }
        }
    }

    private void limpiarDetalle() {
        detalleSelected = null;
        getDetalleSelected();
    }

    public void eliminarDetalle() {
        try {
            if (!listaDetalle.isEmpty()) {
                for (int i = 0; i < listaDetalle.size(); i++) {
                    if (listaDetalle.get(i).getCreDesembolsosCab().equals(detalle.getCreDesembolsosCab())) {
                        listaDetalle.remove(i);
                    }
                }
            }
        } catch (Exception e) {
            mensajeError("Error al eliminar de la lista del Detalle");
            logger.error("Error al eliminar de la lista del Detalle ", e);
        }
    }

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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Ordenes de Pago del sistema.");
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
            acercaDe.add("- Pantalla: TesOrdenPagos.xhtml");
            acercaDe.add("- Controlador: TesOrdenPagoController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: TesOrdenPago.java, TesOrdenPagoDet.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public boolean isEsBusqueda() {
        return esBusqueda;
    }

    public void setEsBusqueda(boolean esBusqueda) {
        this.esBusqueda = esBusqueda;
    }

    public ComProveedores getProveedores() {
        if (proveedores == null) {
            proveedores = new ComProveedores();
            proveedores.setBsEmpresas(new BsEmpresas());
            proveedores.setBsPersonas(new BsPersonas());
        }
        return proveedores;
    }

    public void setProveedores(ComProveedores proveedores) {
        this.proveedores = proveedores;
    }

    public TesOrdenPago getOrdenPago() {
        if (ordenPago == null) {
            ordenPago = new TesOrdenPago();
            ordenPago.setComProveedores(new ComProveedores());
        }
        return ordenPago;
    }

    public void setOrdenPago(TesOrdenPago ordenPago) {
        this.ordenPago = ordenPago;
    }

    public TesOrdenPago getOrdenSelected() {
        if (ordenSelected == null) {
            ordenSelected = new TesOrdenPago();
            ordenSelected.setComProveedores(new ComProveedores());
        }
        return ordenSelected;
    }

    public void setOrdenSelected(TesOrdenPago ordenSelected) {
        this.ordenSelected = ordenSelected;
        if (ordenSelected != null) {
            ordenPago = ordenSelected;
            Query detalleAux = em.createNamedQuery("TesOrdenPagoDet.findByIdCab", TesOrdenPago.class);
            detalleAux.setParameter("idCab", ordenPago);
            listaDetalle = detalleAux.getResultList();
        }
    }

    public TesOrdenPagoDet getDetalle() {
        if (detalle == null) {
            detalle = new TesOrdenPagoDet();
            detalle.setBsMonedas(new BsMonedas());
            detalle.setCreDesembolsosCab(new CreDesembolsosCab());
            detalle.setTesOrdenPago(new TesOrdenPago());
        }
        return detalle;
    }

    public void setDetalle(TesOrdenPagoDet detalle) {
        this.detalle = detalle;
    }

    public TesOrdenPagoDet getDetalleSelected() {
        if (detalleSelected == null) {
            detalleSelected = new TesOrdenPagoDet();
            detalleSelected.setBsMonedas(new BsMonedas());
            detalleSelected.setCreDesembolsosCab(new CreDesembolsosCab());
            detalleSelected.setTesOrdenPago(new TesOrdenPago());
        }
        return detalleSelected;
    }

    public void setDetalleSelected(TesOrdenPagoDet detalleSelected) {
        this.detalleSelected = detalleSelected;
    }

    public List<TesOrdenPagoDet> getListaDetalle() {
        if (listaDetalle == null) {
            listaDetalle = new ArrayList<>();
        }
        for (TesOrdenPagoDet det : listaDetalle) {
            if (det.getBsMonedas() == null) {
                det.setBsMonedas(new BsMonedas());
            }
            if (det.getCreDesembolsosCab() == null) {
                det.setCreDesembolsosCab(new CreDesembolsosCab());
            }
        }
        return listaDetalle;
    }

    public void setListaDetalle(List<TesOrdenPagoDet> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public List<CreDesembolsosCab> getListaCreditos() {
        if (listaCreditos == null) {
            listaCreditos = new ArrayList<>();
        }
        return listaCreditos;
    }

    public void setListaCreditos(List<CreDesembolsosCab> listaCreditos) {
        this.listaCreditos = listaCreditos;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    public LazyDataModel getLazyOrdenPagoDT() {
        if (lazyOrdenPagoDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyOrdenPagoDT = genericLazy.getLazyDataModel(TesOrdenPago.class, campos);
        }
        return lazyOrdenPagoDT;
    }

    public void setLazyOrdenPagoDT(LazyDataModel lazyOrdenPagoDT) {
        this.lazyOrdenPagoDT = lazyOrdenPagoDT;
    }

    public LazyDataModel getLazyOrdenPagoDG() {
        if (lazyOrdenPagoDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyOrdenPagoDG = genericLazy.getLazyDataModel(TesOrdenPago.class, campos);
        }
        return lazyOrdenPagoDG;
    }

    public void setLazyOrdenPagoDG(LazyDataModel lazyOrdenPagoDG) {
        this.lazyOrdenPagoDG = lazyOrdenPagoDG;
    }

    public LazyDataModel getLazyCreditosDG() {
        if (lazyCreditosDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            campos.put("creSolicitudes.indDesembolsado", "S");
            lazyCreditosDG = genericLazy.getLazyDataModel(CreDesembolsosCab.class, campos);
        }
        return lazyCreditosDG;
    }

    public void setLazyCreditosDG(LazyDataModel lazyCreditosDG) {
        this.lazyCreditosDG = lazyCreditosDG;
    }

    public LazyDataModel getLazyProveedoresDG() {
        if (lazyProveedoresDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyProveedoresDG = genericLazy.getLazyDataModel(ComProveedores.class, campos);
        }
        return lazyProveedoresDG;
    }

    public void setLazyProveedoresDG(LazyDataModel lazyProveedoresDG) {
        this.lazyProveedoresDG = lazyProveedoresDG;
    }

//</editor-fold>
}
