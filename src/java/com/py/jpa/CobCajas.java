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
@Table(name = "cob_cajas")
@NamedQueries({
    @NamedQuery(name = "CobCajas.findAll", query = "SELECT c FROM CobCajas c")
    , @NamedQuery(name = "CobCajas.findById", query = "SELECT c FROM CobCajas c WHERE c.id = :id")
    , @NamedQuery(name = "CobCajas.findByCodCaja", query = "SELECT c FROM CobCajas c WHERE c.codCaja = :codCaja")})
public class CobCajas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 10)
    @Column(name = "cod_caja")
    private String codCaja;
    @JoinColumn(name = "bs_sucursal_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsSucursal bsSucursal;
    @JoinColumn(name = "bs_usuarios_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsUsuarios bsUsuarios;
    

    public CobCajas() {
    }

    public CobCajas(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodCaja() {
        return codCaja;
    }

    public void setCodCaja(String codCaja) {
        this.codCaja = codCaja;
    }

    public BsSucursal getBsSucursal() {
        return bsSucursal;
    }

    public void setBsSucursal(BsSucursal bsSucursal) {
        this.bsSucursal = bsSucursal;
    }

    public BsUsuarios getBsUsuarios() {
        return bsUsuarios;
    }

    public void setBsUsuarios(BsUsuarios bsUsuarios) {
        this.bsUsuarios = bsUsuarios;
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
        if (!(object instanceof CobCajas)) {
            return false;
        }
        CobCajas other = (CobCajas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.CobCajas[ id=" + id + " ]";
    }
    
}
