package com.py.controller;

import com.py.jpa.BsBarrio;
import com.py.jpa.BsCiudad;
import com.py.jpa.BsDepartamentos;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Fernando
 */
@Named
@ViewScoped
public class BsBarrioController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();

    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyCiudadesDG, lazyBarriosDT;

    private BsCiudad bsCiudades;
    private BsBarrio bsBarrio, bsBarrioSelected;

    @PostConstruct
    private void ini() {
        limpiar();
    }

    public void limpiar() {
        bsBarrio = null;
        bsBarrioSelected = null;
        bsCiudades = null;
        getBsBarrio();
        getBsBarrioSelected();
        getBsCiudades();
        esModificar = false;
        ayuda = null;
        acercaDe = null;
    }

//<editor-fold defaultstate="collapsed" desc="Metdodos">
    public void guardar() {
        try {
            if (genericEJB.insert(bsBarrio)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyBarriosDT();
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
            genericEJB.update(bsBarrio);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyBarriosDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar la Ciudad");
            e.printStackTrace(System.err);
        }
    }
    
    public void delete() {
        try {
            genericEJB.delete(bsBarrio);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyBarriosDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar la Ciudad");
            e.printStackTrace(System.err);
        }
    }
    
    public void onRowSelectCiudades(SelectEvent ep) {
        bsCiudades =  (BsCiudad) ep.getObject();
        bsBarrio.setBsCiudad(bsCiudades);
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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Barrios del sistema.");
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
            acercaDe.add("- Pantalla: BsBarrios.xhtml");
            acercaDe.add("- Controlador: BsBarriosController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: BsBarrios.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public BsCiudad getBsCiudades() {
        if (bsCiudades == null) {
            bsCiudades = new BsCiudad();
            bsCiudades.setBsDepartamentos(new BsDepartamentos());
        }
        return bsCiudades;
    }

    public void setBsCiudades(BsCiudad bsCiudades) {
        this.bsCiudades = bsCiudades;
    }

    public BsBarrio getBsBarrio() {
        if (bsBarrio == null) {
            bsBarrio = new BsBarrio();
            bsBarrio.setBsCiudad(new BsCiudad());
        }
        return bsBarrio;
    }

    public void setBsBarrio(BsBarrio bsBarrio) {
        this.bsBarrio = bsBarrio;
    }

    public BsBarrio getBsBarrioSelected() {
        if (bsBarrioSelected == null) {
            bsBarrioSelected = new BsBarrio();
            bsBarrioSelected.setBsCiudad(new BsCiudad());
        }
        return bsBarrioSelected;
    }

    public void setBsBarrioSelected(BsBarrio bsBarrioSelected) {
        this.bsBarrioSelected = bsBarrioSelected;
        if (bsBarrioSelected != null) {
            bsBarrio = bsBarrioSelected;
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">

    public LazyDataModel getLazyCiudadesDG() {
        if (lazyCiudadesDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyCiudadesDG = genericLazy.getLazyDataModel(BsCiudad.class, campos);
        }
        return lazyCiudadesDG;
    }

    public void setLazyCiudadesDG(LazyDataModel lazyCiudadesDG) {
        this.lazyCiudadesDG = lazyCiudadesDG;
    }

    public LazyDataModel getLazyBarriosDT() {
        if (lazyBarriosDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyBarriosDT = genericLazy.getLazyDataModel(BsBarrio.class, campos);
        }
        return lazyBarriosDT;
    }

    public void setLazyBarriosDT(LazyDataModel lazyBarriosDT) {
        this.lazyBarriosDT = lazyBarriosDT;
    }

//</editor-fold>
}
