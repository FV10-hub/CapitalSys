package com.py.controller;

import com.py.jpa.BsDepartamentos;
import com.py.jpa.BsEmpresas;
import com.py.jpa.BsPaises;
import com.py.jpa.BsPersonas;
import com.py.jpa.StoArticulos;
import com.py.utils.GenericBigLazyList;
import com.py.utils.GenericController;
import com.py.utils.GenericMensajes;
import com.py.utils.GenericoEJB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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
public class StoArticulosController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();

    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyEmpresaDG, lazyArticulosDT;
    private StoArticulos articulo, articuloSelected;
    private BsEmpresas empresa;

    public void limpiar() {
        articulo = null;
        articuloSelected = null;
        empresa = null;
        esModificar = false;
        ayuda = null;
        acercaDe = null;
    }

    public void guardar() {
        try {
            if (genericEJB.insert(articulo)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyArticulosDT();
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
            genericEJB.update(articulo);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyArticulosDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Producto");
            e.printStackTrace(System.err);
        }
    }

    public void delete() {
        try {
            genericEJB.delete(articulo);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyArticulosDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Producto");
            e.printStackTrace(System.err);
        }
    }

    public void onRowSelectEmpresa(SelectEvent ep) {
        empresa = (BsEmpresas) ep.getObject();
        articulo.setBsEmpresas(empresa);
    }

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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Productos del sistema.");
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
            acercaDe.add("- Pantalla: StoArticulos.xhtml");
            acercaDe.add("- Controlador: StoArticulosController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: StoArticulos.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public StoArticulos getArticulo() {
        if (articulo == null) {
            articulo = new StoArticulos();
            articulo.setBsEmpresas(new BsEmpresas());
        }
        return articulo;
    }

    public void setArticulo(StoArticulos articulo) {
        this.articulo = articulo;
    }

    public StoArticulos getArticuloSelected() {
        if (articuloSelected == null) {
            articuloSelected = new StoArticulos();
            articuloSelected.setBsEmpresas(new BsEmpresas());
        }
        return articuloSelected;
    }

    public void setArticuloSelected(StoArticulos articuloSelected) {
        this.articuloSelected = articuloSelected;
        if (articuloSelected != null) {
            articulo = articuloSelected;
        }
    }

    public BsEmpresas getEmpresa() {
        if (empresa == null) {
            empresa = new BsEmpresas();
            empresa.setBsPersonas(new BsPersonas());
        }
        return empresa;
    }

    public void setEmpresa(BsEmpresas empresa) {
        this.empresa = empresa;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    public LazyDataModel getLazyEmpresaDG() {
        if (lazyEmpresaDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyEmpresaDG = genericLazy.getLazyDataModel(BsEmpresas.class, campos);
        }
        return lazyEmpresaDG;
    }

    public void setLazyEmpresaDG(LazyDataModel lazyEmpresaDG) {
        this.lazyEmpresaDG = lazyEmpresaDG;
    }

    public LazyDataModel getLazyArticulosDT() {
        if (lazyArticulosDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyArticulosDT = genericLazy.getLazyDataModel(StoArticulos.class, campos);
        }
        return lazyArticulosDT;
    }

    public void setLazyArticulosDT(LazyDataModel lazyArticulosDT) {
        this.lazyArticulosDT = lazyArticulosDT;
    }

//</editor-fold>
}
