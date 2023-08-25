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
@Table(name = "bs_empresas")
@NamedQueries({
    @NamedQuery(name = "BsEmpresas.findAll", query = "SELECT b FROM BsEmpresas b")
    , @NamedQuery(name = "BsEmpresas.findById", query = "SELECT b FROM BsEmpresas b WHERE b.id = :id")
    , @NamedQuery(name = "BsEmpresas.findByRepLegal", query = "SELECT b FROM BsEmpresas b WHERE b.repLegal = :repLegal")
    , @NamedQuery(name = "BsEmpresas.findByDirecEmpresa", query = "SELECT b FROM BsEmpresas b WHERE b.direcEmpresa = :direcEmpresa")})
public class BsEmpresas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "rep_legal")
    private String repLegal;
    @Column(name = "direc_empresa")
    private String direcEmpresa;
    @JoinColumn(name = "bs_personas_id", referencedColumnName = "id",nullable = false)
    @ManyToOne
    private BsPersonas bsPersonas;

    public BsEmpresas() {
    }

    public BsEmpresas(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRepLegal() {
        return repLegal;
    }

    public void setRepLegal(String repLegal) {
        this.repLegal = repLegal;
    }

    public String getDirecEmpresa() {
        return direcEmpresa;
    }

    public void setDirecEmpresa(String direcEmpresa) {
        this.direcEmpresa = direcEmpresa;
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
        if (!(object instanceof BsEmpresas)) {
            return false;
        }
        BsEmpresas other = (BsEmpresas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsEmpresas[ id=" + id + " ]";
    }

}
