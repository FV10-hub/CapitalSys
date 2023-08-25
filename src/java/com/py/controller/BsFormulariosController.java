package com.py.controller;

import com.py.jpa.BsFormularios;
import com.py.jpa.BsModulos;
import com.py.jpa.BsTipoValor;
import com.py.jpa.CobTipoCliente;
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
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Fernando
 */
@Named
@ViewScoped
public class BsFormulariosController extends GenericMensajes implements Serializable {
    Logger logger = Logger.getLogger(BsTiposComprobantesController.class);
    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();

    private BsFormularios formulario, formularioSelected;
    private BsModulos modulo;
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyFormulariosDT, lazyModuloDG;

    public void limpiar() {
        formulario = null;
        formularioSelected = null;
        modulo = null;
        esModificar = false;
        ayuda = null;
        acercaDe = null;
    }

    @PostConstruct
    private void ini() {
        limpiar();
    }

    public void guardar() {
        try {
            if (genericEJB.insert(formulario)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyFormulariosDT();
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
            genericEJB.update(formulario);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyFormulariosDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Formulario");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un Error logger en modificar ",e);
        }
    }

    public void delete() {
        try {
            genericEJB.delete(formulario);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyFormulariosDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Formulario");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un Error logger en eliminar ",e);
        }
    }

    public void onRowSelectModulo(SelectEvent e) {
        modulo = (BsModulos) e.getObject();
        formulario.setBsModulos(modulo);
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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Forulario del sistema.");
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
            acercaDe.add("- Pantalla: BsFormularios.xhtml");
            acercaDe.add("- Controlador: BsFormulariosController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: BsFormularios.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public BsFormularios getFormulario() {
        if (formulario == null) {
            formulario = new BsFormularios();
            formulario.setBsModulos(new BsModulos());
        }
        return formulario;
    }

    public void setFormulario(BsFormularios formulario) {
        this.formulario = formulario;
    }

    public BsFormularios getFormularioSelected() {
        if (formularioSelected == null) {
            formularioSelected = new BsFormularios();
            formularioSelected.setBsModulos(new BsModulos());
        }
        return formularioSelected;
    }

    public void setFormularioSelected(BsFormularios formularioSelected) {
        this.formularioSelected = formularioSelected;
        if (formularioSelected != null) {
            formulario = formularioSelected;
        }
    }



    public BsModulos getModulo() {
        if (modulo == null) {
            modulo = new BsModulos();
        }
        return modulo;
    }

    public void setModulo(BsModulos modulo) {
        this.modulo = modulo;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">

    public LazyDataModel getLazyFormulariosDT() {
        if (lazyFormulariosDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyFormulariosDT = genericLazy.getLazyDataModel(BsFormularios.class, campos);
        }
        return lazyFormulariosDT;
    }

    public void setLazyFormulariosDT(LazyDataModel lazyFormulariosDT) {
        this.lazyFormulariosDT = lazyFormulariosDT;
    }
    

    public LazyDataModel getLazyModuloDG() {
        if (lazyModuloDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyModuloDG = genericLazy.getLazyDataModel(BsModulos.class, campos);
        }
        return lazyModuloDG;
    }

    public void setLazyModuloDG(LazyDataModel lazyModuloDG) {
        this.lazyModuloDG = lazyModuloDG;
    }

//</editor-fold>
}
