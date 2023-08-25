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
@Table(name = "bs_paises")
@NamedQueries({
    @NamedQuery(name = "BsPaises.findAll", query = "SELECT b FROM BsPaises b")
    , @NamedQuery(name = "BsPaises.findById", query = "SELECT b FROM BsPaises b WHERE b.id = :id")
    , @NamedQuery(name = "BsPaises.findByCodPais", query = "SELECT b FROM BsPaises b WHERE b.codPais = :codPais")
    , @NamedQuery(name = "BsPaises.findByDescripcion", query = "SELECT b FROM BsPaises b WHERE b.descripcion = :descripcion")})
public class BsPaises implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 5)
    @Column(name = "cod_pais")
    private String codPais;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
   
    
    public BsPaises() {
    }

    public BsPaises(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodPais() {
        return codPais;
    }

    public void setCodPais(String codPais) {
        this.codPais = codPais;
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
        if (!(object instanceof BsPaises)) {
            return false;
        }
        BsPaises other = (BsPaises) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsPaises[ id=" + id + " ]";
    }
    
}
