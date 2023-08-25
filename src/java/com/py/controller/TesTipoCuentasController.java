package com.py.controller;

import com.py.jpa.BsGrupos;
import com.py.jpa.TesTipoCuentas;
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
public class TesTipoCuentasController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyTipoCDT;
    private TesTipoCuentas tipo, tiposelected;

    @PostConstruct
    private void ini() {
        limpiar();
    }

//<editor-fold defaultstate="collapsed" desc="Metodos">
    public void limpiar() {
        tipo = null;
        tiposelected = null;
        esModificar = false;
        ayuda = null;
        acercaDe = null;
    }

    public void guardar() {
        try {
            if (genericEJB.insert(tipo)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyTipoCDT();
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
            genericEJB.update(tipo);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyTipoCDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Tipo de Cuenta");
            e.printStackTrace(System.err);
        }
    }

    public void delete() {
        try {
            genericEJB.delete(tipo);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyTipoCDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Tipo de Cuenta");
            e.printStackTrace(System.err);
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters&Setters">

    public ArrayList<String> getAyuda() {
        if (ayuda == null) {
            ayuda = new ArrayList<>();
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Tipo de Cuenta del sistema.");
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
            acercaDe.add("- Pantalla: TesTipoCuentas.xhtml");
            acercaDe.add("- Controlador: TesTipoCuentasController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: TesTipoCuentas.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public TesTipoCuentas getTipo() {
        if (tipo == null) {
            tipo = new TesTipoCuentas();
        }
        return tipo;
    }

    public void setTipo(TesTipoCuentas tipo) {
        this.tipo = tipo;
    }

    public TesTipoCuentas getTiposelected() {
        if (tiposelected == null) {
            tiposelected = new TesTipoCuentas();
        }
        return tiposelected;
    }

    public void setTiposelected(TesTipoCuentas tiposelected) {
        this.tiposelected = tiposelected;
        if (tiposelected != null) {
            tipo = tiposelected;
        }
    }


//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    public LazyDataModel getLazyTipoCDT() {
        if (lazyTipoCDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyTipoCDT = genericLazy.getLazyDataModel(TesTipoCuentas.class, campos);
        }
        return lazyTipoCDT;
    }

    public void setLazyTipoCDT(LazyDataModel lazyTipoCDT) {
        this.lazyTipoCDT = lazyTipoCDT;
    }

//</editor-fold>
}
