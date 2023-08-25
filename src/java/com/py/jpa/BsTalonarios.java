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
@Table(name = "bs_talonarios")
@NamedQueries({
    @NamedQuery(name = "BsTalonarios.findAll", query = "SELECT b FROM BsTalonarios b")
    , @NamedQuery(name = "BsTalonarios.findById", query = "SELECT b FROM BsTalonarios b WHERE b.id = :id")
    , @NamedQuery(name = "BsTalonarios.findByActivo", query = "SELECT b FROM BsTalonarios b WHERE b.activo = :activo")})
public class BsTalonarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 1)
    @Column(name = "activo")
    private String activo;
   
    @JoinColumn(name = "bs_sucursal_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsSucursal bsSucursal;
    @JoinColumn(name = "bs_timbrados_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsTimbrados bsTimbrados;
    @JoinColumn(name = "bs_tip_comprobantes_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsTipComprobantes bsTipComprobantes;


    public BsTalonarios() {
    }

    public BsTalonarios(Long id) {
        this.id = id;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BsSucursal getBsSucursal() {
        return bsSucursal;
    }

    public void setBsSucursal(BsSucursal bsSucursal) {
        this.bsSucursal = bsSucursal;
    }

    public BsTimbrados getBsTimbrados() {
        return bsTimbrados;
    }

    public void setBsTimbrados(BsTimbrados bsTimbrados) {
        this.bsTimbrados = bsTimbrados;
    }

    public BsTipComprobantes getBsTipComprobantes() {
        return bsTipComprobantes;
    }

    public void setBsTipComprobantes(BsTipComprobantes bsTipComprobantes) {
        this.bsTipComprobantes = bsTipComprobantes;
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
        if (!(object instanceof BsTalonarios)) {
            return false;
        }
        BsTalonarios other = (BsTalonarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsTalonarios[ id=" + id + " ]";
    }
    
}
