/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "bs_identificaciones")
@NamedQueries({
    @NamedQuery(name = "BsIdentificaciones.findAll", query = "SELECT b FROM BsIdentificaciones b")
    , @NamedQuery(name = "BsIdentificaciones.findById", query = "SELECT b FROM BsIdentificaciones b WHERE b.id = :id")
    , @NamedQuery(name = "BsIdentificaciones.findByTipoDoc", query = "SELECT b FROM BsIdentificaciones b WHERE b.tipoDoc = :tipoDoc")
    , @NamedQuery(name = "BsIdentificaciones.findByNumeroIden", query = "SELECT b FROM BsIdentificaciones b WHERE b.numeroIden = :numeroIden")
    , @NamedQuery(name = "BsIdentificaciones.findByDv", query = "SELECT b FROM BsIdentificaciones b WHERE b.dv = :dv")})
public class BsIdentificaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 5)
    @Column(name = "tipo_doc")
    private String tipoDoc;
    @Size(max = 50)
    @Column(name = "numero_iden")
    private String numeroIden;
    @Size(max = 5)
    @Column(name = "dv")
    private String dv;
    
    @JoinColumn(name = "bs_personas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsPersonas bsPersonas;

    public BsIdentificaciones() {
    }

    public BsIdentificaciones(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNumeroIden() {
        return numeroIden;
    }

    public void setNumeroIden(String numeroIden) {
        this.numeroIden = numeroIden;
    }

    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }

    public BsPersonas getBsPersonas() {
        return bsPersonas;
    }

    public void setBsPersonas(BsPersonas bsPersonas) {
        this.bsPersonas = bsPersonas;
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
        if (!(object instanceof BsIdentificaciones)) {
            return false;
        }
        BsIdentificaciones other = (BsIdentificaciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsIdentificaciones[ id=" + id + " ]";
    }
    
}
