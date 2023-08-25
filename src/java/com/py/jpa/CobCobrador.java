/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "cob_cobrador")
@NamedQueries({
    @NamedQuery(name = "CobCobrador.findAll", query = "SELECT c FROM CobCobrador c")
    , @NamedQuery(name = "CobCobrador.findById", query = "SELECT c FROM CobCobrador c WHERE c.id = :id")
    , @NamedQuery(name = "CobCobrador.findByEstado", query = "SELECT c FROM CobCobrador c WHERE c.estado = :estado")})
public class CobCobrador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;

    @JoinColumn(name = "bs_empresas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsEmpresas bsEmpresas;

    @JoinColumn(name = "bs_personas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsPersonas bsPersonas;

    public CobCobrador() {
    }

    public CobCobrador(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BsEmpresas getBsEmpresas() {
        return bsEmpresas;
    }

    public void setBsEmpresas(BsEmpresas bsEmpresas) {
        this.bsEmpresas = bsEmpresas;
    }

    public BsPersonas getBsPersonas() {
        return bsPersonas;
    }

    public void setBsPersonas(BsPersonas bsPersonas) {
        this.bsPersonas = bsPersonas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        if (!(object instanceof CobCobrador)) {
            return false;
        }
        CobCobrador other = (CobCobrador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.CobCobrador[ id=" + id + " ]";
    }

}
