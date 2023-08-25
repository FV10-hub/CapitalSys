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
@Table(name = "bs_modulos")
@NamedQueries({
    @NamedQuery(name = "BsModulos.findAll", query = "SELECT b FROM BsModulos b")
    , @NamedQuery(name = "BsModulos.findById", query = "SELECT b FROM BsModulos b WHERE b.id = :id")
    , @NamedQuery(name = "BsModulos.findByCodModulo", query = "SELECT b FROM BsModulos b WHERE b.codModulo = :codModulo")
    , @NamedQuery(name = "BsModulos.findByDescripcion", query = "SELECT b FROM BsModulos b WHERE b.descripcion = :descripcion")})
public class BsModulos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Size(max = 10)
    @Column(name = "cod_modulo")
    private String codModulo;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;

    
    public BsModulos() {
    }

    public BsModulos(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodModulo() {
        return codModulo;
    }

    public void setCodModulo(String codModulo) {
        this.codModulo = codModulo;
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
        if (!(object instanceof BsModulos)) {
            return false;
        }
        BsModulos other = (BsModulos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsModulos[ id=" + id + " ]";
    }
    
}
