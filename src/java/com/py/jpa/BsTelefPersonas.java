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
@Table(name = "bs_telef_personas")
@NamedQueries({
    @NamedQuery(name = "BsTelefPersonas.findAll", query = "SELECT b FROM BsTelefPersonas b")
    , @NamedQuery(name = "BsTelefPersonas.findById", query = "SELECT b FROM BsTelefPersonas b WHERE b.id = :id")
    , @NamedQuery(name = "BsTelefPersonas.findByArea", query = "SELECT b FROM BsTelefPersonas b WHERE b.area = :area")
    , @NamedQuery(name = "BsTelefPersonas.findByTipo", query = "SELECT b FROM BsTelefPersonas b WHERE b.tipo = :tipo")
    , @NamedQuery(name = "BsTelefPersonas.findByNumero", query = "SELECT b FROM BsTelefPersonas b WHERE b.numero = :numero")})
public class BsTelefPersonas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 6)
    @Column(name = "area")
    private String area;
    @Size(max = 15)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 50)
    @Column(name = "numero")
    private String numero;
    
    @JoinColumn(name = "bs_personas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsPersonas bsPersonas;

    public BsTelefPersonas() {
    }

    public BsTelefPersonas(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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
        if (!(object instanceof BsTelefPersonas)) {
            return false;
        }
        BsTelefPersonas other = (BsTelefPersonas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsTelefPersonas[ id=" + id + " ]";
    }
    
}
