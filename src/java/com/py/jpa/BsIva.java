/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "bs_iva")
@NamedQueries({
    @NamedQuery(name = "BsIva.findAll", query = "SELECT b FROM BsIva b")
    , @NamedQuery(name = "BsIva.findById", query = "SELECT b FROM BsIva b WHERE b.id = :id")
    , @NamedQuery(name = "BsIva.findByCodIva", query = "SELECT b FROM BsIva b WHERE b.codIva = :codIva")
    , @NamedQuery(name = "BsIva.findByDescripcion", query = "SELECT b FROM BsIva b WHERE b.descripcion = :descripcion")
    , @NamedQuery(name = "BsIva.findByPorcentaje", query = "SELECT b FROM BsIva b WHERE b.porcentaje = :porcentaje")})
public class BsIva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 5)
    @Column(name = "cod_iva")
    private String codIva;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "porcentaje")
    private BigDecimal porcentaje;

    public BsIva() {
    }

    public BsIva(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodIva() {
        return codIva;
    }

    public void setCodIva(String codIva) {
        this.codIva = codIva;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
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
        if (!(object instanceof BsIva)) {
            return false;
        }
        BsIva other = (BsIva) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsIva[ id=" + id + " ]";
    }
    
}
