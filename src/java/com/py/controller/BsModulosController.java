package com.py.controller;

import com.py.jpa.BsModulos;
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
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Fernando
 */
@Named
@ViewScoped
public class BsModulosController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyModulosDT;

    private BsModulos bsModulos, bsModuloselected;

    @PostConstruct
    private void ini() {
        limpiar();
    }

//<editor-fold defaultstate="collapsed" desc="Metodos">
    public void limpiar() {
        bsModulos = null;
        bsModuloselected = null;
        getBsModulos();
        getBsModuloselected();
        esModificar = false;
        ayuda = null;
        acercaDe = null;
    }
    
    public void guardar() {
        try {
            if (genericEJB.insert(bsModulos)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyModulosDT();
            } else {
                mensajeAlerta("No se pudo Guardar");
            }
            //       }
        } catch (Exception e) {
            mensajeError("Ocurrio un error");
            e.printStackTrace(System.err);
        }
    }
    
    public void edit() {
        try {
            genericEJB.update(bsModulos);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyModulosDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Modulo");
            e.printStackTrace(System.err);
        }
    }
    
    public void delete() {
        try {
            genericEJB.delete(bsModulos);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyModulosDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Modulo");
            e.printStackTrace(System.err);
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters&Setters">
    public ArrayList<String> getAyuda() {
        if (ayuda == null) {
            ayuda = new ArrayList<>();
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Modulos del sistema.");
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

    public boolean isEsModificar() {
        return esModificar;
    }

    public void setEsModificar(boolean esModificar) {
        this.esModificar = esModificar;
    }

    public ArrayList<String> getAcercaDe() {
        if (acercaDe == null) {
            acercaDe = new ArrayList<>();
            acercaDe.add("Datos de la pantalla:");
            acercaDe.add("- Pantalla:BsModulos.xhtml");
            acercaDe.add("- Controlador: BsModulosController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: BsModulos.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public BsModulos getBsModulos() {
        if (bsModulos == null) {
            bsModulos = new BsModulos();
        }
        return bsModulos;
    }

    public void setBsModulos(BsModulos bsModulos) {
        this.bsModulos = bsModulos;
    }

    public BsModulos getBsModuloselected() {
        if (bsModuloselected == null) {
            bsModuloselected = new BsModulos();
        }
        return bsModuloselected;
    }

    public void setBsModuloselected(BsModulos bsModuloselected) {
        this.bsModuloselected = bsModuloselected;
        if (bsModuloselected != null) {
            bsModulos = bsModuloselected;
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">

    public LazyDataModel getLazyModulosDT() {
        if (lazyModulosDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyModulosDT = genericLazy.getLazyDataModel(BsModulos.class, campos);
        }
        return lazyModulosDT;
    }

    public void setLazyModulosDT(LazyDataModel lazyModulosDT) {
        this.lazyModulosDT = lazyModulosDT;
    }
//</editor-fold>

}
