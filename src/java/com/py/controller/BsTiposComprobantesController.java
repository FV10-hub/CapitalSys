package com.py.controller;

import com.py.jpa.BsEmpresas;
import com.py.jpa.BsModulos;
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
public class BsTiposComprobantesController extends GenericMensajes implements Serializable {

    Logger logger = Logger.getLogger(BsTiposComprobantesController.class);
    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyModulosDG, lazyEmpresas, lazyTiposDT;
    private BsEmpresas empresas;
    private BsModulos modulos;
    private BsTipComprobantes tipo, tipoSelected;

    public void limpiar() {
        empresas = null;
        modulos = null;
        tipo = null;
        tipoSelected = null;
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
        empresas =  (BsEmpresas) ep.getObject();
        tipo.setBsEmpresas(empresas);
    }
    
    public void onRowSelectModulo(SelectEvent ep) {
        modulos =  (BsModulos) ep.getObject();
        tipo.setBsModulos(modulos);
    }
    
    public void guardar() {
        try {
            if (genericEJB.insert(tipo)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyTiposDT();
            } else {
                mensajeAlerta("No se pudo Guardar");
            }
            //       }
        } catch (Exception e) {
            mensajeError("Ocurrio un error");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un Error logger en guardar ",e);
        }
    }
    
    public void edit() {
        try {
            genericEJB.update(tipo);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyTiposDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Tipo de Comprobante");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un Error logger en editar ",e);
        }
    }
    
    public void delete() {
        try {
            genericEJB.delete(tipo);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyTiposDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Tipo de Comprobante");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un Error logger en Eliminar ",e);
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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Tipos Comprobantes del sistema.");
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
            acercaDe.add("- Pantalla: BsTipComprobantes.xhtml");
            acercaDe.add("- Controlador: BsTipComprobantesController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: BsTipComprobantes.java");
        }
        return acercaDe;
    }
    
    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
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
    
    public BsModulos getModulos() {
        if (modulos == null) {
            modulos = new BsModulos();;
        }
        return modulos;
    }
    
    public void setModulos(BsModulos modulos) {
        this.modulos = modulos;
    }
    
    public BsTipComprobantes getTipo() {
        if (tipo == null) {
            tipo = new BsTipComprobantes();
            tipo.setBsEmpresas(new BsEmpresas());
            tipo.getBsEmpresas().setBsPersonas(new BsPersonas());
            tipo.setBsModulos(new BsModulos());
        }
        return tipo;
    }
    
    public void setTipo(BsTipComprobantes tipo) {
        this.tipo = tipo;
    }
    
    public BsTipComprobantes getTipoSelected() {
        if (tipoSelected == null) {
            tipoSelected = new BsTipComprobantes();
            tipoSelected.setBsEmpresas(new BsEmpresas());
            tipoSelected.getBsEmpresas().setBsPersonas(new BsPersonas());
            tipoSelected.setBsModulos(new BsModulos());
        }
        return tipoSelected;
    }
    
    public void setTipoSelected(BsTipComprobantes tipoSelected) {
        this.tipoSelected = tipoSelected;
        if (tipoSelected != null) {
            tipo = tipoSelected;
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    public LazyDataModel getLazyModulosDG() {
        if (lazyModulosDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyModulosDG = genericLazy.getLazyDataModel(BsModulos.class, campos);
        }
        return lazyModulosDG;
    }
    
    public void setLazyModulosDG(LazyDataModel lazyModulosDG) {
        this.lazyModulosDG = lazyModulosDG;
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
    
    public LazyDataModel getLazyTiposDT() {
        if (lazyTiposDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyTiposDT = genericLazy.getLazyDataModel(BsTipComprobantes.class, campos);
        }
        return lazyTiposDT;
    }
    
    public void setLazyTiposDT(LazyDataModel lazyTiposDT) {
        this.lazyTiposDT = lazyTiposDT;
    }
//</editor-fold>

}
