package com.py.controller;

import com.py.jpa.BsCiudad;
import com.py.jpa.BsEmpresas;
import com.py.jpa.BsPersonas;
import com.py.jpa.CobCobrador;
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
public class CobCobradoresController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();

    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyPersonasDG, lazyCobradoresDT;

    private BsPersonas bsPersona;
    private CobCobrador cobCobrador, cobCobradorSelected;

    @PostConstruct
    private void ini() {
        limpiar();
    }

    public void limpiar() {
        bsPersona = null;
        cobCobrador = null;
        cobCobradorSelected = null;
        esModificar = false;
    }

    public void guardar() {
        try {
            BsEmpresas emp = em.createNamedQuery("BsEmpresas.findById", BsEmpresas.class)
                    .setParameter("id", 1)
                    .getSingleResult();
            cobCobrador.setBsEmpresas(emp);
            if (genericEJB.insert(cobCobrador)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyCobradoresDT();
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
            genericEJB.update(cobCobrador);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyCobradoresDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Cobrador");
            e.printStackTrace(System.err);
        }
    }

    public void delete() {
        try {
            genericEJB.delete(cobCobrador);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyCobradoresDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Cobrador");
            e.printStackTrace(System.err);
        }
    }

    public void onRowSelectPersonas(SelectEvent ep) {
        bsPersona = (BsPersonas) ep.getObject();
        cobCobrador.setBsPersonas(bsPersona);
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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Cobradores del sistema.");
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
            acercaDe.add("- Pantalla: CobCobradores.xhtml");
            acercaDe.add("- Controlador: CobCobradoresController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: CobCobrador.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public BsPersonas getBsPersona() {
        return bsPersona;
    }

    public void setBsPersona(BsPersonas bsPersona) {
        this.bsPersona = bsPersona;
    }

    public CobCobrador getCobCobrador() {
        if (cobCobrador == null) {
            cobCobrador = new CobCobrador();
            cobCobrador.setBsEmpresas(new BsEmpresas());
            cobCobrador.setBsPersonas(new BsPersonas());
        }
        return cobCobrador;
    }

    public void setCobCobrador(CobCobrador cobCobrador) {
        this.cobCobrador = cobCobrador;
    }

    public CobCobrador getCobCobradorSelected() {
        if (cobCobradorSelected == null) {
            cobCobradorSelected = new CobCobrador();
            cobCobradorSelected.setBsEmpresas(new BsEmpresas());
            cobCobradorSelected.setBsPersonas(new BsPersonas());
        }
        return cobCobradorSelected;
    }

    public void setCobCobradorSelected(CobCobrador cobCobradorSelected) {
        this.cobCobradorSelected = cobCobradorSelected;
        if (cobCobradorSelected != null) {
            cobCobrador = cobCobradorSelected;
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">

    public LazyDataModel getLazyPersonasDG() {
        if (lazyPersonasDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyPersonasDG = genericLazy.getLazyDataModel(BsPersonas.class, campos);

        }
        return lazyPersonasDG;
    }

    public void setLazyPersonasDG(LazyDataModel lazyPersonasDG) {
        this.lazyPersonasDG = lazyPersonasDG;
    }

    public LazyDataModel getLazyCobradoresDT() {
        if (lazyCobradoresDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyCobradoresDT = genericLazy.getLazyDataModel(CobCobrador.class, campos);
        }
        return lazyCobradoresDT;
    }

    public void setLazyCobradoresDT(LazyDataModel lazyCobradoresDT) {
        this.lazyCobradoresDT = lazyCobradoresDT;
    }
//</editor-fold>

}
