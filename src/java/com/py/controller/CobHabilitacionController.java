package com.py.controller;

import com.py.jpa.BsPersonas;
import com.py.jpa.BsSucursal;
import com.py.jpa.BsUsuarios;
import com.py.jpa.CobCajas;
import com.py.jpa.CobHabilitaciones;
import com.py.utils.GenericBigLazyList;
import com.py.utils.GenericController;
import com.py.utils.GenericMensajes;
import com.py.utils.GenericoEJB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Fernando
 */
@Named
@ViewScoped
public class CobHabilitacionController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyCajasDG, lazyHabilitaciones, lazyUsuarioDG;
    private CobCajas cajas;
    private BsUsuarios usuario;
    private CobHabilitaciones habilitacion, habSelected;

    public void limpiar() {
        cajas = null;
        usuario = null;
        habSelected = null;
        habilitacion = null;
        esModificar = false;
        ayuda = null;
        acercaDe = null;
    }

    @PostConstruct
    private void ini() {
        limpiar();
    }

    public void onRowSelectCajas(SelectEvent ep) {
        cajas = (CobCajas) ep.getObject();
        habilitacion.setCobCajas(cajas);
    }

    public void onRowSelectUsuario(SelectEvent em) {
        usuario = (BsUsuarios) em.getObject();
        habilitacion.setBsUsuarios(usuario);
    }

    public void guardar() {
        try {
            Calendar calendario = Calendar.getInstance(new Locale("PY"));
            int hora, minutos;
            String horas;
            hora = calendario.get(Calendar.HOUR_OF_DAY);
            minutos = calendario.get(Calendar.MINUTE);
            horas = hora + ":" + minutos;
            habilitacion.setHoraHab(horas);
            Query max = em.createNativeQuery("select (MAX(COALESCE(c.nro_habilitacion::INTEGER)) + 1) from cob_habilitaciones c");
            String nroHab = max.getSingleResult().toString();
            habilitacion.setNroHabilitacion(nroHab);
            if (genericEJB.insert(habilitacion)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyHabilitaciones();
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
            genericEJB.update(habilitacion);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyHabilitaciones();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar la Habilitacion");
            e.printStackTrace(System.err);
        }
    }

    public void delete() {
        try {
            if (habilitacion.getFechaCierre() != null) {
                Calendar calendario = Calendar.getInstance().getInstance(new Locale("PY"));
                int hora, minutos;
                String horas;
                hora = calendario.get(Calendar.HOUR_OF_DAY);
                minutos = calendario.get(Calendar.MINUTE);
                horas = hora + ":" + minutos;
                habilitacion.setHoraCierre(horas);
            }
            genericEJB.delete(habilitacion);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyHabilitaciones();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar la Habilitacion");
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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Habilitaciones del sistema.");
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
            acercaDe.add("- Pantalla: CobHabilitaciones.xhtml");
            acercaDe.add("- Controlador: CobHabilitacionController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: CobHabilitaciones.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public CobCajas getCajas() {
        if (cajas == null) {
            cajas = new CobCajas();
            cajas.setBsSucursal(new BsSucursal());
            cajas.setBsUsuarios(new BsUsuarios());
        }
        return cajas;
    }

    public void setCajas(CobCajas cajas) {
        this.cajas = cajas;
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

    public CobHabilitaciones getHabilitacion() {
        if (habilitacion == null) {
            habilitacion = new CobHabilitaciones();
            habilitacion.setBsUsuarios(new BsUsuarios());
            habilitacion.setCobCajas(new CobCajas());
        }
        return habilitacion;
    }

    public void setHabilitacion(CobHabilitaciones habilitacion) {
        this.habilitacion = habilitacion;
        Calendar calendario = Calendar.getInstance(new Locale("PY"));
        int hora;
        String minutos;
        String horas;
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = String.valueOf(calendario.get(Calendar.MINUTE));
        horas = hora + ":" + minutos;
        this.habilitacion.setHoraCierre(horas);
        this.habilitacion.setFechaCierre(calendario.getTime());
    }

    public CobHabilitaciones getHabSelected() {
        if (habSelected == null) {
            habSelected = new CobHabilitaciones();
            habSelected.setBsUsuarios(new BsUsuarios());
            habSelected.setCobCajas(new CobCajas());
        }
        return habSelected;
    }

    public void setHabSelected(CobHabilitaciones habSelected) {
        this.habSelected = habSelected;
        if (habSelected != null) {
            habilitacion = habSelected;
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">

    public LazyDataModel getLazyCajasDG() {
        if (lazyCajasDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyCajasDG = genericLazy.getLazyDataModel(CobCajas.class, campos);
        }
        return lazyCajasDG;
    }

    public void setLazyCajasDG(LazyDataModel lazyCajasDG) {
        this.lazyCajasDG = lazyCajasDG;
    }

    public LazyDataModel getLazyHabilitaciones() {
        if (lazyHabilitaciones == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyHabilitaciones = genericLazy.getLazyDataModel(CobHabilitaciones.class, campos);
        }
        return lazyHabilitaciones;
    }

    public void setLazyHabilitaciones(LazyDataModel lazyHabilitaciones) {
        this.lazyHabilitaciones = lazyHabilitaciones;
    }

    public LazyDataModel getLazyUsuarioDG() {
        if (lazyUsuarioDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyUsuarioDG = genericLazy.getLazyDataModel(BsUsuarios.class, campos);
        }
        return lazyUsuarioDG;
    }

    public void setLazyUsuarioDG(LazyDataModel lazyUsuarioDG) {
        this.lazyUsuarioDG = lazyUsuarioDG;
    }
//</editor-fold>

}
