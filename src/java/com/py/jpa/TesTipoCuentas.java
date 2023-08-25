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
@Table(name = "tes_tipo_cuentas")
@NamedQueries({
    @NamedQuery(name = "TesTipoCuentas.findAll", query = "SELECT t FROM TesTipoCuentas t")
    , @NamedQuery(name = "TesTipoCuentas.findById", query = "SELECT t FROM TesTipoCuentas t WHERE t.id = :id")
    , @NamedQuery(name = "TesTipoCuentas.findByCodTipo", query = "SELECT t FROM TesTipoCuentas t WHERE t.codTipo = :codTipo")
    , @NamedQuery(name = "TesTipoCuentas.findByDescripcion", query = "SELECT t FROM TesTipoCuentas t WHERE t.descripcion = :descripcion")})
public class TesTipoCuentas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 10)
    @Column(name = "cod_tipo")
    private String codTipo;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;

    public TesTipoCuentas() {
    }

    public TesTipoCuentas(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(String codTipo) {
        this.codTipo = codTipo;
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
        if (!(object instanceof TesTipoCuentas)) {
            return false;
        }
        TesTipoCuentas other = (TesTipoCuentas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.TesTipoCuentas[ id=" + id + " ]";
    }
    
}
