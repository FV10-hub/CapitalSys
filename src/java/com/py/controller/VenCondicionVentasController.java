package com.py.controller;

import com.py.jpa.BsEmpresas;
import com.py.jpa.BsPersonas;
import com.py.jpa.VenCondicionVenta;
import com.py.utils.GenericBigLazyList;
import com.py.utils.GenericController;
import com.py.utils.GenericMensajes;
import com.py.utils.GenericoEJB;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Fernando
 */
@Named
@ViewScoped
public class VenCondicionVentasController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private GenericoEJB genericEJB;
    private VenCondicionVenta condicion, condicionSelected;
    private BsEmpresas bsEmpresa;
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyCondicionesDT, lazyEmpresas;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();

    @PostConstruct
    private void ini() {
        limpiar();
    }

//<editor-fold defaultstate="collapsed" desc="Metodos">
    public void limpiar() {
        condicion = null;
        condicionSelected = null;
        bsEmpresa = null;
        esModificar = false;
    }

    public void guardar() {
        try {
            Query count = em.createNativeQuery("select max(c.id) + 1 from ven_condicion_venta c where c.bs_empresas_id = 1");
            Integer id = (Integer) count.getSingleResult();
            condicion.setId(id.longValue());
            condicion.setIntervalo(BigDecimal.ONE);
            if (genericEJB.insert(condicion)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyCondicionesDT();
            } else {
                mensajeAlerta("No se pudo Guardar");
            }
        } catch (Exception e) {
            mensajeError("Ocurrio un error");
            e.printStackTrace(System.err);
        }
    }

    public void edit() {
        try {
            genericEJB.update(condicion);
            mensajeAlerta("Modificado Correctamente!");
            getLazyCondicionesDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar la Condicion");
            e.printStackTrace(System.err);
        }
    }

    public void delete() {
        try {
            genericEJB.delete(condicion);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyCondicionesDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar la Condicion");
            e.printStackTrace(System.err);
        }
    }

    public void onRowSelectEmpresa(SelectEvent e) {
        bsEmpresa = (BsEmpresas) e.getObject();
        condicion.setBsEmpresas(bsEmpresa);
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters&Setters">
    public VenCondicionVenta getCondicion() {
        if (condicion == null) {
            condicion = new VenCondicionVenta();
            condicion.setBsEmpresas(new BsEmpresas());
            condicion.getBsEmpresas().setBsPersonas(new BsPersonas());
        }
        return condicion;
    }

    public void setCondicion(VenCondicionVenta condicion) {
        this.condicion = condicion;
    }

    public VenCondicionVenta getCondicionSelected() {
        if (condicionSelected == null) {
            condicionSelected = new VenCondicionVenta();
            condicionSelected.setBsEmpresas(new BsEmpresas());
            condicionSelected.getBsEmpresas().setBsPersonas(new BsPersonas());
        }
        return condicionSelected;
    }

    public void setCondicionSelected(VenCondicionVenta condicionSelected) {
        this.condicionSelected = condicionSelected;
        if (condicionSelected != null) {
            condicion = condicionSelected;
        }
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

    public boolean isEsModificar() {
        return esModificar;
    }

    public void setEsModificar(boolean esModificar) {
        this.esModificar = esModificar;
    }

    public ArrayList<String> getAyuda() {
        if (ayuda == null || ayuda.isEmpty()) {
            ayuda = new ArrayList<>();
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Condiciones de Venta del sistema.");
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
        if (acercaDe == null || ayuda.isEmpty()) {
            acercaDe = new ArrayList<>();
            acercaDe.add("Datos de la pantalla:");
            acercaDe.add("- Pantalla: VenCondicionVenta.xhtml");
            acercaDe.add("- Controlador: VenCondicionVentaController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: VenCondicionVenta.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">

    public LazyDataModel getLazyCondicionesDT() {
        if (lazyCondicionesDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyCondicionesDT = genericLazy.getLazyDataModel(VenCondicionVenta.class, campos);
        }
        return lazyCondicionesDT;
    }

    public void setLazyCondicionesDT(LazyDataModel lazyCondicionesDT) {
        this.lazyCondicionesDT = lazyCondicionesDT;
    }

    public LazyDataModel getLazyEmpresas() {
        if (lazyEmpresas == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyEmpresas = genericLazy.getLazyDataModel(BsEmpresas.class, campos);
        }
        return lazyEmpresas;
    }

    public void setLazyEmpresas(LazyDataModel lazyEmpresas) {
        this.lazyEmpresas = lazyEmpresas;
    }
//</editor-fold>

}
