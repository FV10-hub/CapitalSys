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
@Table(name = "bs_ciudad")
@NamedQueries({
    @NamedQuery(name = "BsCiudad.findAll", query = "SELECT b FROM BsCiudad b")
    , @NamedQuery(name = "BsCiudad.findById", query = "SELECT b FROM BsCiudad b WHERE b.id = :id")
    , @NamedQuery(name = "BsCiudad.findByCodCiudad", query = "SELECT b FROM BsCiudad b WHERE b.codCiudad = :codCiudad")
    , @NamedQuery(name = "BsCiudad.findByDescripcion", query = "SELECT b FROM BsCiudad b WHERE b.descripcion = :descripcion")})
public class BsCiudad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 5)
    @Column(name = "cod_ciudad")
    private String codCiudad;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    
    @JoinColumn(name = "bs_departamentos_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsDepartamentos bsDepartamentos;

    public BsCiudad() {
    }

    public BsCiudad(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodCiudad() {
        return codCiudad;
    }

    public void setCodCiudad(String codCiudad) {
        this.codCiudad = codCiudad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BsDepartamentos getBsDepartamentos() {
        return bsDepartamentos;
    }

    public void setBsDepartamentos(BsDepartamentos bsDepartamentos) {
        this.bsDepartamentos = bsDepartamentos;
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
        if (!(object instanceof BsCiudad)) {
            return false;
        }
        BsCiudad other = (BsCiudad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsCiudad[ id=" + id + " ]";
    }
    
}
