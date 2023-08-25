package com.py.controller;

import com.py.jpa.BsPersonas;
import com.py.jpa.BsUsuarios;
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
public class BsUsuariosController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private boolean esModificar;
    private BsUsuarios bsUsuario, bsUsuarioSelected;
    private BsPersonas bsPersonas;
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyUsuariosDT;
    private LazyDataModel lazyPersonaDG;
    private String senaAux;

    public void limpiar() {
        bsUsuario = null;
        bsUsuarioSelected = null;
        bsPersonas = null;
        esModificar = false;
    }

    @PostConstruct
    private void ini() {
        limpiar();
    }

    public void onRowSelectPersona(SelectEvent ep) {
        bsPersonas = (BsPersonas) ep.getObject();
        bsUsuario.setBsPersonas(bsPersonas);
    }

    public void guardar() {

        if (!bsUsuario.getSena().equals(senaAux)) {
            mensajeError("Las Contraseñas no coinciden");
            return;
        } else {
            try {
                if (genericEJB.insert(bsUsuario)) {
                    mensajeAlerta("Se guardo Correctamente");
                    getLazyUsuariosDT();
                } else {
                    mensajeAlerta("No se pudo Guardar");
                }
            } catch (Exception e) {
                mensajeError("Ocurrio un error");
                e.printStackTrace(System.err);
            }
        }
    }

    public void edit() {
        try {
            genericEJB.update(bsUsuario);
            mensajeAlerta("Modificado Correctamente!");
            getLazyUsuariosDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Usuario");
            e.printStackTrace(System.err);
        }
    }

    public void delete() {
        try {
            genericEJB.delete(bsUsuario);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyUsuariosDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Usuario");
            e.printStackTrace(System.err);
        }
    }

//<editor-fold defaultstate="collapsed" desc="Getters&Setters">
    public boolean isEsModificar() {
        return esModificar;
    }

    public void setEsModificar(boolean esModificar) {
        this.esModificar = esModificar;
    }

    public BsUsuarios getBsUsuario() {
        if (bsUsuario == null) {
            bsUsuario = new BsUsuarios();
            bsUsuario.setBsPersonas(new BsPersonas());
        }
        return bsUsuario;
    }

    public void setBsUsuario(BsUsuarios bsUsuario) {
        this.bsUsuario = bsUsuario;
    }

    public String getSenaAux() {
        return senaAux;
    }

    public void setSenaAux(String senaAux) {
        this.senaAux = senaAux;
    }

    public BsUsuarios getBsUsuarioSelected() {
        if (bsUsuarioSelected == null) {
            bsUsuarioSelected = new BsUsuarios();
            bsUsuarioSelected.setBsPersonas(new BsPersonas());
        }
        return bsUsuarioSelected;
    }

    public void setBsUsuarioSelected(BsUsuarios bsUsuarioSelected) {
        this.bsUsuarioSelected = bsUsuarioSelected;
        if (bsUsuarioSelected != null) {
            bsUsuario = bsUsuarioSelected;
        }
    }

    public BsPersonas getBsPersonas() {
        if (bsPersonas == null) {
            bsPersonas = new BsPersonas();
        }
        return bsPersonas;
    }

    public void setBsPersonas(BsPersonas bsPersonas) {
        this.bsPersonas = bsPersonas;
    }

    public ArrayList<String> getAyuda() {
        if (ayuda == null) {
            ayuda = new ArrayList<>();
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Usuario del sistema.");
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
            acercaDe.add("- Pantalla: BsUsuarios.xhtml");
            acercaDe.add("- Controlador: BsUsuariosController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: BsUsuarios.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">

    public LazyDataModel getLazyUsuariosDT() {
        if (lazyUsuariosDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyUsuariosDT = genericLazy.getLazyDataModel(BsUsuarios.class, campos);
        }
        return lazyUsuariosDT;
    }

    public void setLazyUsuariosDT(LazyDataModel lazyUsuariosDT) {
        this.lazyUsuariosDT = lazyUsuariosDT;
    }

    public LazyDataModel getLazyPersonaDG() {
        if (lazyPersonaDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyPersonaDG = genericLazy.getLazyDataModel(BsPersonas.class, campos);
        }
        return lazyPersonaDG;
    }

    public void setLazyPersonaDG(LazyDataModel lazyPersonaDG) {
        this.lazyPersonaDG = lazyPersonaDG;
    }
//</editor-fold>

}
