package com.py.controller;

import com.py.jpa.BsGrupos;
import com.py.jpa.BsGruposUsu;
import com.py.jpa.BsModulos;
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
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Fernando
 */
@Named
@ViewScoped
public class BsGruposUsuariosController extends GenericMensajes implements Serializable {

    Logger logger = Logger.getLogger(BsGruposUsuariosController.class);
    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyGruposUsuDT, lazyGrupos, lazyUsuario;
    private BsGrupos grupo;
    private BsUsuarios usuario;
    private BsGruposUsu gruUsu, gruUsuSelected;

    @PostConstruct
    private void ini() {
        limpiar();
    }

//<editor-fold defaultstate="collapsed" desc="Metodos">
    public void limpiar() {
        grupo = null;
        usuario = null;
        gruUsu = null;
        gruUsuSelected = null;
        esModificar = false;
        ayuda = null;
        acercaDe = null;
    }

    public void guardar() {
        try {
            if (genericEJB.insert(gruUsu)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyGruposUsuDT();
            } else {
                mensajeAlerta("No se pudo Guardar");
            }
            //       }
        } catch (Exception e) {
            mensajeError("Ocurrio un error");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un Error logger en guardar Usuario ", e);
        }
    }

    public void edit() {
        try {
            genericEJB.update(gruUsu);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyGruposUsuDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Grupo Usuario");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un Error logger en Modificar ", e);
        }
    }

    public void delete() {
        try {
            genericEJB.delete(gruUsu);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyGruposUsuDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Grupo");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un Error logger en Eliminar ", e);
        }
    }

    public void onrowGrupo(SelectEvent g) {
        grupo = (BsGrupos) g.getObject();
        gruUsu.setBsGrupos(grupo);
    }

    public void onrowUsuario(SelectEvent u) {
        usuario = (BsUsuarios) u.getObject();
        gruUsu.setBsUsuarios(usuario);
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Getters&Setters">

    public ArrayList<String> getAyuda() {
        if (ayuda == null || ayuda.isEmpty()) {
            ayuda = new ArrayList<>();
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Grupos Usuarios del sistema.");
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
        if (acercaDe == null || acercaDe.isEmpty()) {
            acercaDe = new ArrayList<>();
            acercaDe.add("Datos de la pantalla:");
            acercaDe.add("- Pantalla: BsGruposUsuarios.xhtml");
            acercaDe.add("- Controlador: BsGruposUsuariosController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: BsGruposUsuarios.java");
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

    public BsGruposUsu getGruUsu() {
        if (gruUsu == null) {
            gruUsu = new BsGruposUsu();
            gruUsu.setBsGrupos(new BsGrupos());
            gruUsu.setBsUsuarios(new BsUsuarios());
        }
        return gruUsu;
    }

    public void setGruUsu(BsGruposUsu gruUsu) {
        this.gruUsu = gruUsu;
    }

    public BsGruposUsu getGruUsuSelected() {
        if (gruUsuSelected == null) {
            gruUsuSelected = new BsGruposUsu();
            gruUsuSelected.setBsGrupos(new BsGrupos());
            gruUsuSelected.setBsUsuarios(new BsUsuarios());
        }
        return gruUsuSelected;
    }

    public void setGruUsuSelected(BsGruposUsu gruUsuSelected) {
        this.gruUsuSelected = gruUsuSelected;
        if (gruUsuSelected != null) {
            gruUsu = gruUsuSelected;
        }
    }

    public BsUsuarios getUsuario() {
        if (usuario == null) {
            usuario = new BsUsuarios();
            usuario.setBsPersonas(new BsPersonas());
        }
        return usuario;
    }

    public void setUsuario(BsUsuarios usuario) {
        this.usuario = usuario;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    public LazyDataModel getLazyGruposUsuDT() {
        if (lazyGruposUsuDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyGruposUsuDT = genericLazy.getLazyDataModel(BsGruposUsu.class, campos);
        }
        return lazyGruposUsuDT;
    }

    public void setLazyGruposUsuDT(LazyDataModel lazyGruposUsuDT) {
        this.lazyGruposUsuDT = lazyGruposUsuDT;
    }

    public LazyDataModel getLazyGrupos() {
        if (lazyGrupos == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyGrupos = genericLazy.getLazyDataModel(BsGrupos.class, campos);
        }
        return lazyGrupos;
    }

    public void setLazyGrupos(LazyDataModel lazyGrupos) {
        this.lazyGrupos = lazyGrupos;
    }

    public LazyDataModel getLazyUsuario() {
        if (lazyUsuario == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyUsuario = genericLazy.getLazyDataModel(BsUsuarios.class, campos);
        }
        return lazyUsuario;
    }

    public void setLazyUsuario(LazyDataModel lazyUsuario) {
        this.lazyUsuario = lazyUsuario;
    }

//</editor-fold>
}
