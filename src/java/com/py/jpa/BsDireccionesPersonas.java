/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "bs_direcciones_personas")
@NamedQueries({
    @NamedQuery(name = "BsDireccionesPersonas.findAll", query = "SELECT b FROM BsDireccionesPersonas b")
    , @NamedQuery(name = "BsDireccionesPersonas.findById", query = "SELECT b FROM BsDireccionesPersonas b WHERE b.id = :id")
    , @NamedQuery(name = "BsDireccionesPersonas.findByTipoDireccion", query = "SELECT b FROM BsDireccionesPersonas b WHERE b.tipoDireccion = :tipoDireccion")
    , @NamedQuery(name = "BsDireccionesPersonas.findByDetalle", query = "SELECT b FROM BsDireccionesPersonas b WHERE b.detalle = :detalle")})
public class BsDireccionesPersonas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 15)
    @Column(name = "tipo_direccion")
    private String tipoDireccion;
    @Size(max = 150)
    @Column(name = "detalle")
    private String detalle;

    @JoinColumn(name = "bs_barrio_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsBarrio bsBarrio;

    @JoinColumn(name = "bs_ciudad_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsCiudad bsCiudad;

    @JoinColumn(name = "bs_departamentos_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsDepartamentos bsDepartamentos;

    @JoinColumn(name = "bs_paises_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsPaises bsPaises;
    
    @JoinColumn(name = "bs_personas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsPersonas bsPersonas;

    public BsDireccionesPersonas() {
    }

    public BsDireccionesPersonas(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public BsBarrio getBsBarrio() {
        return bsBarrio;
    }

    public void setBsBarrio(BsBarrio bsBarrio) {
        this.bsBarrio = bsBarrio;
    }

    public BsCiudad getBsCiudad() {
        return bsCiudad;
    }

    public void setBsCiudad(BsCiudad bsCiudad) {
        this.bsCiudad = bsCiudad;
    }

    public BsDepartamentos getBsDepartamentos() {
        return bsDepartamentos;
    }

    public void setBsDepartamentos(BsDepartamentos bsDepartamentos) {
        this.bsDepartamentos = bsDepartamentos;
    }

    public BsPaises getBsPaises() {
        return bsPaises;
    }

    public void setBsPaises(BsPaises bsPaises) {
        this.bsPaises = bsPaises;
    }

    public BsPersonas getBsPersonas() {
        return bsPersonas;
    }

    public void setBsPersonas(BsPersonas bsPersonas) {
        this.bsPersonas = bsPersonas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BsDireccionesPersonas)) {
            return false;
        }
        BsDireccionesPersonas other = (BsDireccionesPersonas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsDireccionesPersonas[ id=" + id + " ]";
    }
    
}
