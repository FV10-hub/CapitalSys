package com.py.controller;

import com.py.jpa.BsCotizaciones;
import com.py.jpa.BsEmpresas;
import com.py.jpa.BsModulos;
import com.py.jpa.BsMonedas;
import com.py.jpa.BsPersonas;
import com.py.jpa.BsSucursal;
import com.py.jpa.BsTipComprobantes;
import com.py.jpa.BsUsuarios;
import com.py.jpa.CobCajas;
import com.py.jpa.CobHabilitaciones;
import com.py.utils.GenericBigLazyList;
import com.py.utils.GenericController;
import com.py.utils.GenericMensajes;
import com.py.utils.GenericoEJB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
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
public class BsCotizacionesController extends GenericMensajes implements Serializable {

    Logger logger = Logger.getLogger(BsCotizacionesController.class);
    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyMonedasDG, lazyEmpresas, lazyCotizacionesDT;
    private BsEmpresas empresas;
    private BsMonedas monedas;
    private BsCotizaciones cambios, cambiosSelected;

    public void limpiar() {
        empresas = null;
        monedas = null;
        cambios = null;
        cambiosSelected = null;
        ayuda = null;
        acercaDe = null;
        esModificar = false;
    }

    @PostConstruct
    private void ini() {
        limpiar();
    }
//<editor-fold defaultstate="collapsed" desc="Metodos">

    public void onRowSelectEmpresa(SelectEvent ep) {
        empresas = (BsEmpresas) ep.getObject();
        cambios.setBsEmpresas(empresas);
    }

    public void onRowSelectMoneda(SelectEvent ep) {
        monedas = (BsMonedas) ep.getObject();
        cambios.setBsMonedas(monedas);
    }

    public void guardar() {
        try {
            if (genericEJB.insert(cambios)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyCotizacionesDT();
            } else {
                mensajeAlerta("No se pudo Guardar");
            }
            //       }
        } catch (Exception e) {
            mensajeError("Ocurrio un error");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un Error logger en guardar ", e);
        }
    }

    public void edit() {
        try {
            genericEJB.update(cambios);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyCotizacionesDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar la Cotizacion");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un Error logger en editar ", e);
        }
    }

    public void delete() {
        try {
            genericEJB.delete(cambios);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyCotizacionesDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar la Cotizacion");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un Error logger en Eliminar ", e);
        }
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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Cotizaciones del sistema.");
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
            acercaDe.add("- Pantalla: BsCotizaciones.xhtml");
            acercaDe.add("- Controlador: BsCotizacionesController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: BsCotizaciones.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public BsMonedas getMonedas() {
        if (monedas == null) {
            monedas = new BsMonedas();
        }
        return monedas;
    }

    public void setMonedas(BsMonedas monedas) {
        this.monedas = monedas;
    }

    public BsCotizaciones getCambios() {
        if (cambios == null) {
            cambios = new BsCotizaciones();
            cambios.setBsEmpresas(new BsEmpresas());
            cambios.getBsEmpresas().setBsPersonas(new BsPersonas());
            cambios.setBsMonedas(new BsMonedas());
        }
        return cambios;
    }

    public void setCambios(BsCotizaciones cambios) {
        this.cambios = cambios;
    }

    public BsCotizaciones getCambiosSelected() {
        if (cambiosSelected == null) {
            cambiosSelected = new BsCotizaciones();
            cambiosSelected.setBsEmpresas(new BsEmpresas());
            cambiosSelected.getBsEmpresas().setBsPersonas(new BsPersonas());
            cambiosSelected.setBsMonedas(new BsMonedas());
        }
        return cambiosSelected;
    }

    public void setCambiosSelected(BsCotizaciones cambiosSelected) {
        this.cambiosSelected = cambiosSelected;
        if (cambiosSelected != null) {
           this.cambiosSelected = cambiosSelected;
        }
    }
    
    

    public BsEmpresas getEmpresas() {
        if (empresas == null) {
            empresas = new BsEmpresas();
            empresas.setBsPersonas(new BsPersonas());
        }
        return empresas;
    }

    public void setEmpresas(BsEmpresas empresas) {
        this.empresas = empresas;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">

    public LazyDataModel getLazyMonedasDG() {
        if (lazyMonedasDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyMonedasDG = genericLazy.getLazyDataModel(BsMonedas.class, campos);
        }
        return lazyMonedasDG;
    }

    public void setLazyMonedasDG(LazyDataModel lazyMonedasDG) {
        this.lazyMonedasDG = lazyMonedasDG;
    }

    public LazyDataModel getLazyCotizacionesDT() {
        if (lazyCotizacionesDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyCotizacionesDT = genericLazy.getLazyDataModel(BsCotizaciones.class, campos);
        }
        return lazyCotizacionesDT;
    }

    public void setLazyCotizacionesDT(LazyDataModel lazyCotizacionesDT) {
        this.lazyCotizacionesDT = lazyCotizacionesDT;
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
