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
@Table(name = "bs_barrio")
@NamedQueries({
    @NamedQuery(name = "BsBarrio.findAll", query = "SELECT b FROM BsBarrio b")
    , @NamedQuery(name = "BsBarrio.findById", query = "SELECT b FROM BsBarrio b WHERE b.id = :id")
    , @NamedQuery(name = "BsBarrio.findByCodBarrio", query = "SELECT b FROM BsBarrio b WHERE b.codBarrio = :codBarrio")
    , @NamedQuery(name = "BsBarrio.findByDescripcion", query = "SELECT b FROM BsBarrio b WHERE b.descripcion = :descripcion")})
public class BsBarrio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 5)
    @Column(name = "cod_barrio")
    private String codBarrio;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    
    @JoinColumn(name = "bs_ciudad_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsCiudad bsCiudad;

    public BsBarrio() {
    }

    public BsBarrio(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodBarrio() {
        return codBarrio;
    }

    public void setCodBarrio(String codBarrio) {
        this.codBarrio = codBarrio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BsCiudad getBsCiudad() {
        return bsCiudad;
    }

    public void setBsCiudad(BsCiudad bsCiudad) {
        this.bsCiudad = bsCiudad;
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
        if (!(object instanceof BsBarrio)) {
            return false;
        }
        BsBarrio other = (BsBarrio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsBarrio[ id=" + id + " ]";
    }
    
}
