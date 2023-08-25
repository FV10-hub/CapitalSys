package com.py.controller;

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
public class BsCiudadesController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();

    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyCiudadDT, lazyDepartamentoDG;

    private BsDepartamentos bsDepartamentos;
    private BsCiudad bsCiudad, bsCiudadSelected;

    @PostConstruct
    private void ini() {
        limpiar();
    }

//<editor-fold defaultstate="collapsed" desc="Metodos">
    public void limpiar() {
        bsCiudad = null;
        bsCiudadSelected = null;
        bsDepartamentos = null;
        getBsCiudad();
        getBsCiudadSelected();
        getBsDepartamentos();
        esModificar = false;
        ayuda = null;
        acercaDe = null;
    }
    
    public void guardar() {
        try {
            if (genericEJB.insert(bsCiudad)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyCiudadDT();
            } else {
                mensajeAlerta("No se pudo Guardar");
            }
        } catch (Exception e) {
            mensajeError("Ocurrio un error");
            e.printStackTrace(System.err);
        }
    }
    
    public void edit() {
        try {
            genericEJB.update(bsCiudad);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyCiudadDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Departamento");
            e.printStackTrace(System.err);
        }
    }
    
    public void delete() {
        try {
            genericEJB.delete(bsCiudad);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyCiudadDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Departamento");
            e.printStackTrace(System.err);
        }
    }
    
    public void onRowSelectDepartamento(SelectEvent ep){
        bsDepartamentos = (BsDepartamentos) ep.getObject();
        bsCiudad.setBsDepartamentos(bsDepartamentos);
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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Ciudades del sistema.");
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
            acercaDe.add("- Pantalla: BsCiudades.xhtml");
            acercaDe.add("- Controlador: BsCiudadesController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: BsCiudad.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public BsDepartamentos getBsDepartamentos() {
        if (bsDepartamentos == null) {
            bsDepartamentos = new BsDepartamentos();
            bsDepartamentos.setBsPaises(new BsPaises());
        }
        return bsDepartamentos;
    }

    public void setBsDepartamentos(BsDepartamentos bsDepartamentos) {
        this.bsDepartamentos = bsDepartamentos;
    }

    public BsCiudad getBsCiudad() {
        if (bsCiudad == null) {
            bsCiudad = new BsCiudad();
            bsCiudad.setBsDepartamentos(new BsDepartamentos());
        }
        return bsCiudad;
    }

    public void setBsCiudad(BsCiudad bsCiudad) {
        this.bsCiudad = bsCiudad;
    }

    public BsCiudad getBsCiudadSelected() {
        if (bsCiudadSelected == null) {
            bsCiudadSelected = new BsCiudad();
            bsCiudadSelected.setBsDepartamentos(new BsDepartamentos());
        }
        return bsCiudadSelected;
    }

    public void setBsCiudadSelected(BsCiudad bsCiudadSelected) {
        this.bsCiudadSelected = bsCiudadSelected;
        if (bsCiudadSelected != null) {
            bsCiudad = bsCiudadSelected;
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">

    public LazyDataModel getLazyCiudadDT() {
        if (lazyCiudadDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyCiudadDT = genericLazy.getLazyDataModel(BsCiudad.class, campos);

        }
        return lazyCiudadDT;
    }

    public void setLazyCiudadDT(LazyDataModel lazyCiudadDT) {
        this.lazyCiudadDT = lazyCiudadDT;
    }

    public LazyDataModel getLazyDepartamentoDG() {
        if (lazyDepartamentoDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyDepartamentoDG = genericLazy.getLazyDataModel(BsDepartamentos.class, campos);
        }
        return lazyDepartamentoDG;
    }

    public void setLazyDepartamentoDG(LazyDataModel lazyDepartamentoDG) {
        this.lazyDepartamentoDG = lazyDepartamentoDG;
    }
//</editor-fold>

}
