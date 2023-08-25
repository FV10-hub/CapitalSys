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
@Table(name = "bs_monedas")
@NamedQueries({
    @NamedQuery(name = "BsMonedas.findAll", query = "SELECT b FROM BsMonedas b")
    , @NamedQuery(name = "BsMonedas.findById", query = "SELECT b FROM BsMonedas b WHERE b.id = :id")
    , @NamedQuery(name = "BsMonedas.findByDescripcion", query = "SELECT b FROM BsMonedas b WHERE b.descripcion = :descripcion")
    , @NamedQuery(name = "BsMonedas.findByDecimales", query = "SELECT b FROM BsMonedas b WHERE b.decimales = :decimales")
    , @NamedQuery(name = "BsMonedas.findByCodMoneda", query = "SELECT b FROM BsMonedas b WHERE b.codMoneda = :codMoneda")})
public class BsMonedas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "decimales")
    private Integer decimales;
    @Size(max = 5)
    @Column(name = "cod_moneda")
    private String codMoneda;
  
    public BsMonedas() {
    }

    public BsMonedas(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getDecimales() {
        return decimales;
    }

    public void setDecimales(Integer decimales) {
        this.decimales = decimales;
    }

    public String getCodMoneda() {
        return codMoneda;
    }

    public void setCodMoneda(String codMoneda) {
        this.codMoneda = codMoneda;
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
        if (!(object instanceof BsMonedas)) {
            return false;
        }
        BsMonedas other = (BsMonedas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsMonedas[ id=" + id + " ]";
    }
    
}
