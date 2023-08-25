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
@Table(name = "cr_motivos_prestamos")
@NamedQueries({
    @NamedQuery(name = "CrMotivosPrestamos.findAll", query = "SELECT c FROM CrMotivosPrestamos c")
    , @NamedQuery(name = "CrMotivosPrestamos.findById", query = "SELECT c FROM CrMotivosPrestamos c WHERE c.id = :id")
    , @NamedQuery(name = "CrMotivosPrestamos.findByCodTipo", query = "SELECT c FROM CrMotivosPrestamos c WHERE c.codTipo = :codTipo")
    , @NamedQuery(name = "CrMotivosPrestamos.findByDescripcion", query = "SELECT c FROM CrMotivosPrestamos c WHERE c.descripcion = :descripcion")})
public class CrMotivosPrestamos implements Serializable {

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
 
    public CrMotivosPrestamos() {
    }

    public CrMotivosPrestamos(Long id) {
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
        if (!(object instanceof CrMotivosPrestamos)) {
            return false;
        }
        CrMotivosPrestamos other = (CrMotivosPrestamos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.CrMotivosPrestamos[ id=" + id + " ]";
    }
    
}
