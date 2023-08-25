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
@Table(name = "bs_departamentos")
@NamedQueries({
    @NamedQuery(name = "BsDepartamentos.findAll", query = "SELECT b FROM BsDepartamentos b")
    , @NamedQuery(name = "BsDepartamentos.findById", query = "SELECT b FROM BsDepartamentos b WHERE b.id = :id")
    , @NamedQuery(name = "BsDepartamentos.findByCodDepartamento", query = "SELECT b FROM BsDepartamentos b WHERE b.codDepartamento = :codDepartamento")
    , @NamedQuery(name = "BsDepartamentos.findByDescripcion", query = "SELECT b FROM BsDepartamentos b WHERE b.descripcion = :descripcion")})
public class BsDepartamentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 5)
    @Column(name = "cod_departamento")
    private String codDepartamento;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    
    @JoinColumn(name = "bs_paises_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsPaises bsPaises;


    public BsDepartamentos() {
    }

    public BsDepartamentos(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodDepartamento() {
        return codDepartamento;
    }

    public void setCodDepartamento(String codDepartamento) {
        this.codDepartamento = codDepartamento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BsPaises getBsPaises() {
        return bsPaises;
    }

    public void setBsPaises(BsPaises bsPaises) {
        this.bsPaises = bsPaises;
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
        if (!(object instanceof BsDepartamentos)) {
            return false;
        }
        BsDepartamentos other = (BsDepartamentos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsDepartamentos[ id=" + id + " ]";
    }
    
}
