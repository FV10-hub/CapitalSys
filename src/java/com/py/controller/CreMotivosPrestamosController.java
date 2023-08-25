package com.py.controller;

import com.py.jpa.BsMonedas;
import com.py.jpa.CrMotivosPrestamos;
import com.py.jpa.CreTipoAmortizacion;
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
public class CreMotivosPrestamosController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyMotivoDT;
    
    private CrMotivosPrestamos motivo,motivoSeleceted;

    @PostConstruct
    private void ini(){
        limpiar();
    }
    
public void limpiar(){
    motivo = null;
    motivoSeleceted = null;
    esModificar = false;
}

    public void guardar() {
        try {
            if (genericEJB.insert(motivo)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyMotivoDT();
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
            genericEJB.update(motivo);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyMotivoDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Motivo de Prestamo");
            e.printStackTrace(System.err);
        }
    }
    
    public void delete() {
        try {
            genericEJB.delete(motivo);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyMotivoDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Motivo de Prestamo");
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
    
    public ArrayList<String> getAyuda() {
        if (ayuda == null) {
            ayuda = new ArrayList<>();
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Motivo de Prestamo del sistema.");
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
            acercaDe.add("- Pantalla: CreMotivosPrestamos.xhtml");
            acercaDe.add("- Controlador: CreMotivosPrestamosController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: CrMotivosPrestamos.java");
        }
        return acercaDe;
    }
    
    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public CrMotivosPrestamos getMotivo() {
        if (motivo == null) {
            motivo = new CrMotivosPrestamos();
        }
        return motivo;
    }

    public void setMotivo(CrMotivosPrestamos motivo) {
        this.motivo = motivo;
    }

    public CrMotivosPrestamos getMotivoSeleceted() {
        if (motivoSeleceted == null) {
            motivoSeleceted = new CrMotivosPrestamos();
        }
        return motivoSeleceted;
    }

    public void setMotivoSeleceted(CrMotivosPrestamos motivoSeleceted) {
        this.motivoSeleceted = motivoSeleceted;
        if (motivoSeleceted != null) {
            motivo = motivoSeleceted;
        }
    }
    
    


//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    
    public LazyDataModel getLazyMotivoDT() {
        if (lazyMotivoDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyMotivoDT = genericLazy.getLazyDataModel(CrMotivosPrestamos.class, campos);
        }
        return lazyMotivoDT;
    }
    public void setLazyMotivoDT(LazyDataModel lazyMotivoDT) {
        this.lazyMotivoDT = lazyMotivoDT;
    }

//</editor-fold>


   
    

}
