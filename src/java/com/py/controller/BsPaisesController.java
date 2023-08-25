package com.py.controller;

import com.py.jpa.BsPaises;
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
public class BsPaisesController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();

    private BsPaises bsPaises, bsPaiseSelected;
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyPaisesDT;

    @PostConstruct
    private void ini() {
        limpiar();
    }
//<editor-fold defaultstate="collapsed" desc="Metodos">
    
    public void limpiar() {
        bsPaises = null;
        getBsPaises();
        bsPaiseSelected = null;
        getBsPaiseSelected();
        esModificar = false;
        ayuda = null;
        acercaDe = null;
    }
    
        public void guardar() {
        try {
            if (genericEJB.insert(bsPaises)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyPaisesDT();
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
            genericEJB.update(bsPaises);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyPaisesDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Pais");
            e.printStackTrace(System.err);
        }
    }

    public void delete() {
        try {
            genericEJB.delete(bsPaises);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyPaisesDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Pais");
            e.printStackTrace(System.err);
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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Paises del sistema.");
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
            acercaDe.add("- Pantalla:BsPaises.xhtml");
            acercaDe.add("- Controlador: BsPaisesController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: BsPaises.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public BsPaises getBsPaises() {
        if (bsPaises == null) {
            bsPaises = new BsPaises();
        }
        return bsPaises;
    }

    public void setBsPaises(BsPaises bsPaises) {
        this.bsPaises = bsPaises;
    }

    public BsPaises getBsPaiseSelected() {
        if (bsPaiseSelected == null) {
            bsPaiseSelected = new BsPaises();
        }
        return bsPaiseSelected;
    }

    public void setBsPaiseSelected(BsPaises bsPaiseSelected) {
        this.bsPaiseSelected = bsPaiseSelected;
        if (bsPaiseSelected != null) {
            bsPaises = bsPaiseSelected;
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    public LazyDataModel getLazyPaisesDT() {
        if (lazyPaisesDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyPaisesDT = genericLazy.getLazyDataModel(BsPaises.class, campos);
        }
        return lazyPaisesDT;
    }
    
    public void setLazyPaisesDT(LazyDataModel lazyPaisesDT) {
        this.lazyPaisesDT = lazyPaisesDT;
    }
//</editor-fold>
    
    

}
