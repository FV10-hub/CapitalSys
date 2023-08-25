/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.controller;

//import com.py.ejb.EntidadPersona;
//import com.py.ejb.EntidadPersonaFacade;
//import com.py.jpa.DireccionesEntidades;
//import com.py.jpa.TelefonosEntidades;
//import com.py.jpa.TelefonosEntidadesFacade;
import com.py.utils.GenericController;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Win10
 */
@ManagedBean
@ViewScoped
public class indexController extends GenericController implements Serializable {

//    private List<EntidadPersona> listaPersonas;
//    private EntidadPersona persona;
//    private TelefonosEntidades telef_persona;
//    private List<TelefonosEntidades> lista_tel;
//    private DireccionesEntidades direc_persona;
//    private List<DireccionesEntidades> lista_dir;
//    private String nombres;
//    private String apellido;
//    private String tipoEntida;
//    private String sexo;
//    private Date fecNacimiento;
//    private String correo;
//    private String tipo_tel;
//    private String num_tel;
//    private Boolean pordefecto;
//
//    public indexController() {
//    }
//
//    public void nuevoRegistro() {
//        persona = new EntidadPersona();
//    }
//
//    public void eliminarRegistro(EntidadPersona persona) {
//        ejb.remove(persona);
//        this.listaPersonas = ejb.findAll();
//    }
//
//    @EJB
//    EntidadPersonaFacade ejb;
//
//    @EJB
//    TelefonosEntidadesFacade ejbtel;
//
//    public void guardar() {
//
//        persona.setNombres(getNombres());
//        persona.setApellidos(getApellido());
//        persona.setCorreo(getCorreo());
//        persona.setTipoEntidad(getTipoEntida());
//        persona.setSexo(getSexo());
//        persona.setFecNacimiento(getFecNacimiento());
//
//        if (ejb.insertar(persona)) {
//            mensajeAlerta("Persona creado con exito");
//            listaPersonas = ejb.findAll();
//        } else {
//            mensajeError("Error al guardar");
//        }
//
//    }
//
//    public void modificar() {
//
//        if (ejb.actualizar(persona)) {
//            mensajeAlerta("Persona actualizada con exito");
//            listaPersonas = ejb.findAll();
//        } else {
//            mensajeAlerta("Error al Modificar");
//        }
//
//    }
//
//    public void borrar_registro() {
//        try {
//            ejb.remove(persona);
//            listaPersonas = ejb.findAll();
//            mensajeAlerta("Persona Eliminada con exito");
//        } catch (Exception e) {
//            mensajeAlerta("Error al Eliminar Persona");
//        }
//
////                   {
////            }else{
////            mensajeAlerta("Error al Eliminar Persona");
////            }
//    }
//
//    public List<EntidadPersona> getListaPersonas() {
//        if (listaPersonas == null || listaPersonas.isEmpty()) {
//            listaPersonas = ejb.findAll();
//        }
//        return listaPersonas;
//    }
//
//    public void setListaPersonas(List<EntidadPersona> listaPersonas) {
//        this.listaPersonas = listaPersonas;
//    }
//
//    public EntidadPersona getPersona() {
//        if (persona == null) {
//            persona = new EntidadPersona();
//        }
//        return persona;
//    }
//
//    public void setPersona(EntidadPersona persona) {
//        this.persona = persona;
//    }
//
//    public String getNombres() {
//        return nombres;
//    }
//
//    public void setNombres(String nombres) {
//        this.nombres = nombres;
//    }
//
//    public String getApellido() {
//        return apellido;
//    }
//
//    public void setApellido(String apellido) {
//        this.apellido = apellido;
//    }
//
//    public String getTipoEntida() {
//        return tipoEntida;
//    }
//
//    public void setTipoEntida(String tipoEntida) {
//        this.tipoEntida = tipoEntida;
//    }
//
//    public String getSexo() {
//        return sexo;
//    }
//
//    public void setSexo(String sexo) {
//        this.sexo = sexo;
//    }
//
//    public Date getFecNacimiento() {
//        return fecNacimiento;
//    }
//
//    public void setFecNacimiento(Date fecNacimiento) {
//        this.fecNacimiento = fecNacimiento;
//    }
//
//    public String getCorreo() {
//        return correo;
//    }
//
//    public void setCorreo(String correo) {
//        this.correo = correo;
//    }
//
//    public String getTipo_tel() {
//        return tipo_tel;
//    }
//
//    public void setTipo_tel(String tipo_tel) {
//        this.tipo_tel = tipo_tel;
//    }
//
//    public String getNum_tel() {
//        return num_tel;
//    }
//
//    public void setNum_tel(String num_tel) {
//        this.num_tel = num_tel;
//    }
//
//    public Boolean getPordefecto() {
//        return pordefecto;
//    }
//
//    public void setPordefecto(Boolean pordefecto) {
//        this.pordefecto = pordefecto;
//    }
//
//    public List<TelefonosEntidades> listar_telef() {
//        lista_tel = ejbtel.findAll();
//        return lista_tel;
//    }
//
//    public TelefonosEntidades getTelef_persona() {
//        if (telef_persona == null) {
//            telef_persona = new TelefonosEntidades();
//        }
//        return telef_persona;
//    }
//
//    public void setTelef_persona(TelefonosEntidades telef_persona) {
//        this.telef_persona = telef_persona;
//    }
//
//    public void add_telef() {
//        if (lista_tel == null || lista_tel.isEmpty()) {
//            listar_telef();
//        }
//        telef_persona = new TelefonosEntidades();
//        telef_persona.setIdPersona(persona);
//        telef_persona.setTipoTelefono(tipo_tel);
//        telef_persona.setNroTelefono(num_tel);
//        if (pordefecto) {
//            telef_persona.setPorDefecto("S");
//        } else {
//            telef_persona.setPorDefecto("N");
//        }
//        lista_tel.add(telef_persona);
//    }
//    

}
