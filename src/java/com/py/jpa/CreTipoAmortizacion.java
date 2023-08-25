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
@Table(name = "cre_tipo_amortizacion")
@NamedQueries({
    @NamedQuery(name = "CreTipoAmortizacion.findAll", query = "SELECT c FROM CreTipoAmortizacion c")
    , @NamedQuery(name = "CreTipoAmortizacion.findById", query = "SELECT c FROM CreTipoAmortizacion c WHERE c.id = :id")
    , @NamedQuery(name = "CreTipoAmortizacion.findByCodTipos", query = "SELECT c FROM CreTipoAmortizacion c WHERE c.codTipos = :codTipos")
    , @NamedQuery(name = "CreTipoAmortizacion.findByDescripcion", query = "SELECT c FROM CreTipoAmortizacion c WHERE c.descripcion = :descripcion")})
public class CreTipoAmortizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 10)
    @Column(name = "cod_tipos")
    private String codTipos;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;

    public CreTipoAmortizacion() {
    }

    public CreTipoAmortizacion(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodTipos() {
        return codTipos;
    }

    public void setCodTipos(String codTipos) {
        this.codTipos = codTipos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(object instanceof CreTipoAmortizacion)) {
            return false;
        }
        CreTipoAmortizacion other = (CreTipoAmortizacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.CreTipoAmortizacion[ id=" + id + " ]";
    }
    
}
