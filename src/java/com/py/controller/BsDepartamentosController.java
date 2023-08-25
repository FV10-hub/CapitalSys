package com.py.controller;

import com.py.jpa.BsDepartamentos;
import com.py.jpa.BsPaises;
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
public class BsDepartamentosController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();

    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyPaisesDG, lazyDepartamentoDT;
    private BsDepartamentos bsDepartamento, bsDepartamentoSelected;
    private BsPaises bsPais;

    public void limpiar() {
        bsPais = null;
        bsDepartamento = null;
        bsDepartamentoSelected = null;
        getBsDepartamento();
        getBsDepartamentoSelected();
        getBsPais();
        esModificar = false;
        ayuda = null;
        acercaDe = null;
    }

    public void guardar() {
        try {
            if (genericEJB.insert(bsDepartamento)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyDepartamentoDT();
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
            genericEJB.update(bsDepartamento);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyDepartamentoDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Departamento");
            e.printStackTrace(System.err);
        }
    }

    public void delete() {
        try {
            genericEJB.delete(bsDepartamento);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyDepartamentoDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Departamento");
            e.printStackTrace(System.err);
        }
    }
    
    public void onRowSelectPais(SelectEvent ep){
        bsPais = (BsPaises) ep.getObject();
        bsDepartamento.setBsPaises(bsPais);
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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Departamentos del sistema.");
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
            acercaDe.add("- Pantalla: BsDepartamentos.xhtml");
            acercaDe.add("- Controlador: BsDepartamentosController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: BsDepartamentos.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public BsDepartamentos getBsDepartamento() {
        if (bsDepartamento == null) {
            bsDepartamento = new BsDepartamentos();
            bsDepartamento.setBsPaises(new BsPaises());
        }
        return bsDepartamento;
    }

    public void setBsDepartamento(BsDepartamentos bsDepartamento) {
        this.bsDepartamento = bsDepartamento;
    }

    public BsDepartamentos getBsDepartamentoSelected() {
        if (bsDepartamentoSelected == null) {
            bsDepartamentoSelected = new BsDepartamentos();
            bsDepartamentoSelected.setBsPaises(new BsPaises());
        }
        return bsDepartamentoSelected;
    }

    public void setBsDepartamentoSelected(BsDepartamentos bsDepartamentoSelected) {
        this.bsDepartamentoSelected = bsDepartamentoSelected;
        if (bsDepartamentoSelected != null) {
            bsDepartamento = bsDepartamentoSelected;
        }
    }

    public BsPaises getBsPais() {
        if (bsPais == null) {
            bsPais = new BsPaises();
        }
        return bsPais;
    }

    public void setBsPais(BsPaises bsPais) {
        this.bsPais = bsPais;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">

    public LazyDataModel getLazyPaisesDG() {
        if (lazyPaisesDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyPaisesDG = genericLazy.getLazyDataModel(BsPaises.class, campos);
        }
        return lazyPaisesDG;
    }

    public void setLazyPaisesDG(LazyDataModel lazyPaisesDG) {
        this.lazyPaisesDG = lazyPaisesDG;
    }

    public LazyDataModel getLazyDepartamentoDT() {
        if (lazyDepartamentoDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyDepartamentoDT = genericLazy.getLazyDataModel(BsDepartamentos.class, campos);
        }
        return lazyDepartamentoDT;
    }

    public void setLazyDepartamentoDT(LazyDataModel lazyDepartamentoDT) {
        this.lazyDepartamentoDT = lazyDepartamentoDT;
    }
//</editor-fold>
}
