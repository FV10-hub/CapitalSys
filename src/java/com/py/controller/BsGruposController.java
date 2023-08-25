package com.py.controller;

import com.py.jpa.BsGrupos;
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
public class BsGruposController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyGruposDT;
    private BsGrupos grupo, gruposelected;

    @PostConstruct
    private void ini() {
        limpiar();
    }

//<editor-fold defaultstate="collapsed" desc="Metodos">
    public void limpiar() {
        grupo = null;
        gruposelected = null;
        esModificar = false;
        ayuda = null;
        acercaDe = null;
    }
    
    public void guardar() {
        try {
            if (genericEJB.insert(grupo)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyGruposDT();
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
            genericEJB.update(grupo);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyGruposDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Grupo");
            e.printStackTrace(System.err);
        }
    }
    
    public void delete() {
        try {
            genericEJB.delete(grupo);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyGruposDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Grupo");
            e.printStackTrace(System.err);
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters&Setters">
    public ArrayList<String> getAyuda() {
        if (ayuda == null) {
            ayuda = new ArrayList<>();
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Grupos del sistema.");
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
            acercaDe.add("- Pantalla: BsGrupos.xhtml");
            acercaDe.add("- Controlador: BsGruposController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: BsGrupos.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public BsGrupos getGrupo() {
        if (grupo == null) {
            grupo = new BsGrupos();
        }
        return grupo;
    }

    public void setGrupo(BsGrupos grupo) {
        this.grupo = grupo;
    }

    public BsGrupos getGruposelected() {
        if (gruposelected == null) {
            gruposelected = new BsGrupos();
        }
        return gruposelected;
    }

    public void setGruposelected(BsGrupos gruposelected) {
        this.gruposelected = gruposelected;
        if (gruposelected != null) {
            grupo = gruposelected;
        }
    }
    
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">

    public LazyDataModel getLazyGruposDT() {
        if (lazyGruposDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyGruposDT = genericLazy.getLazyDataModel(BsGrupos.class, campos);
        }
        return lazyGruposDT;
    }

    public void setLazyGruposDT(LazyDataModel lazyGruposDT) {
        this.lazyGruposDT = lazyGruposDT;
    }

//</editor-fold>

}
