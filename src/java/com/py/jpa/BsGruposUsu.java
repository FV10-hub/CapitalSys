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
@Table(name = "bs_grupos_usu")
@NamedQueries({
    @NamedQuery(name = "BsGruposUsu.findAll", query = "SELECT b FROM BsGruposUsu b")
    , @NamedQuery(name = "BsGruposUsu.findById", query = "SELECT b FROM BsGruposUsu b WHERE b.id = :id")
    , @NamedQuery(name = "BsGruposUsu.findByEstado", query = "SELECT b FROM BsGruposUsu b WHERE b.estado = :estado")})
public class BsGruposUsu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "bs_grupos_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsGrupos bsGrupos;
    @JoinColumn(name = "bs_usuarios_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsUsuarios bsUsuarios;

    public BsGruposUsu() {
    }

    public BsGruposUsu(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BsGrupos getBsGrupos() {
        return bsGrupos;
    }

    public void setBsGrupos(BsGrupos bsGrupos) {
        this.bsGrupos = bsGrupos;
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
        if (!(object instanceof BsGruposUsu)) {
            return false;
        }
        BsGruposUsu other = (BsGruposUsu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsGruposUsu[ id=" + id + " ]";
    }
    
}
