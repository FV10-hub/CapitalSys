package com.py.controller;

import com.py.ejb.BsParametrizacionesFacade;
import com.py.jpa.BsEmpresas;
import com.py.jpa.BsModulos;
import com.py.jpa.BsParametrizaciones;
import com.py.utils.GenericBigLazyList;
import com.py.utils.GenericController;
import com.py.utils.GenericMensajes;
import com.py.utils.GenericoEJB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Ac3r
 */
@Named
@SessionScoped
public class BsParametrizacionesController extends GenericMensajes implements Serializable {

    private BsParametrizaciones bsParametrizaciones, bsParametrizacionesSelected;
    private GenericBigLazyList<BsParametrizaciones> genericLazy;
    private LazyDataModel lazyParametrosDT, lazyModulos;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();

    @PersistenceContext
    private EntityManager em;

    @EJB
    private GenericoEJB genericEJB;

    public void limpiar() {
        bsParametrizaciones = null;
        getBsParametrizaciones();
        bsParametrizacionesSelected = null;
        getBsParametrizacionesSelected();
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
            //      if (bsParametrizaciones != null && bsParametrizaciones.getId() == null) {
            BsEmpresas emp = em.createNamedQuery("BsEmpresas.findById", BsEmpresas.class)
                    .setParameter("id", 1)
                    .getSingleResult();
            bsParametrizaciones.setBsEmpresas(emp);
            System.out.println("op " + bsParametrizaciones);
            if (genericEJB.insert(bsParametrizaciones)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyParametrosDT();
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
            genericEJB.update(bsParametrizacionesSelected);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyParametrosDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar las Notificaciones");
            e.printStackTrace(System.err);
        }
    }

    public void delete() {
        try {
            genericEJB.delete(bsParametrizacionesSelected);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyParametrosDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar la Notificacion");
            e.printStackTrace(System.err);
        }
    }

    public void onRowSelectModulo(SelectEvent e) {
        if (e.getObject() != null) {
            try {
                BsModulos obj = new BsModulos();
                obj = (BsModulos) e.getObject();
                bsParametrizaciones.setBsModulos(obj);
            } catch (Exception ex) {
                mensajeError("Ocurrio un Error en el Lazy Modulos");
                ex.printStackTrace(System.err);
            }

        }
    }

    public BsParametrizaciones getBsParametrizaciones() {
        if (bsParametrizaciones == null) {
            bsParametrizaciones = new BsParametrizaciones();
            bsParametrizaciones.setBsEmpresas(new BsEmpresas());
            bsParametrizaciones.setBsModulos(new BsModulos());
        }
        return bsParametrizaciones;
    }

    public void setBsParametrizaciones(BsParametrizaciones bsParametrizaciones) {
        this.bsParametrizaciones = bsParametrizaciones;
    }

    public BsParametrizaciones getBsParametrizacionesSelected() {
        if (bsParametrizacionesSelected == null) {
            bsParametrizacionesSelected = new BsParametrizaciones();
            bsParametrizacionesSelected.setBsEmpresas(new BsEmpresas());
            bsParametrizacionesSelected.setBsModulos(new BsModulos());
        }
        return bsParametrizacionesSelected;
    }

    public void setBsParametrizacionesSelected(BsParametrizaciones bsParametrizacionesSelected) {
        this.bsParametrizacionesSelected = bsParametrizacionesSelected;
        if (bsParametrizacionesSelected != null) {
            bsParametrizaciones = bsParametrizacionesSelected;
        }

    }

    public boolean isEsModificar() {
        return esModificar;
    }

    public void setEsModificar(boolean esModificar) {
        this.esModificar = esModificar;
    }

    public ArrayList<String> getAyuda() {
        if (ayuda == null) {
            ayuda = new ArrayList<>();
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Parametrizaciones del sistema.");
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
            acercaDe.add("- Pantalla:BsParametrizaciones.xhtml");
            acercaDe.add("- Controlador: BsParametrizacionesController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: BsParametrizaciones.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }


    public LazyDataModel getLazyParametrosDT() {
        if (lazyParametrosDT == null) {
            try {
                campos = new HashMap();
                genericLazy = new GenericBigLazyList(em);
                lazyParametrosDT = genericLazy.getLazyDataModel(BsParametrizaciones.class, campos);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }

        }
        return lazyParametrosDT;
    }

    public void setLazyParametrosDT(LazyDataModel lazyParametrosDT) {
        this.lazyParametrosDT = lazyParametrosDT;
    }

    public LazyDataModel getLazyModulos() {
        if (lazyModulos == null) {
            campos = new HashMap();
            genericLazy = new GenericBigLazyList(em);
            lazyModulos = genericLazy.getLazyDataModel(BsModulos.class, campos);
        }
        return lazyModulos;
    }

    public void setLazyModulos(LazyDataModel lazyModulos) {
        this.lazyModulos = lazyModulos;
    }

}
