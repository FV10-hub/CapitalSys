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
@Table(name = "bs_usuarios")
@NamedQueries({
    @NamedQuery(name = "BsUsuarios.findAll", query = "SELECT b FROM BsUsuarios b")
    , @NamedQuery(name = "BsUsuarios.findById", query = "SELECT b FROM BsUsuarios b WHERE b.id = :id")
    , @NamedQuery(name = "BsUsuarios.findByCodUsuario", query = "SELECT b FROM BsUsuarios b WHERE b.codUsuario = :codUsuario")
    , @NamedQuery(name = "BsUsuarios.findBySena", query = "SELECT b FROM BsUsuarios b WHERE b.sena = :sena")
    , @NamedQuery(name = "BsUsuarios.findByEstado", query = "SELECT b FROM BsUsuarios b WHERE b.estado = :estado")})
public class BsUsuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 20)
    @Column(name = "cod_usuario")
    private String codUsuario;
    @Size(max = 80)
    @Column(name = "sena")
    private String sena;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    
    @JoinColumn(name = "bs_personas_id", referencedColumnName = "id")
    @ManyToOne
    private BsPersonas bsPersonas;
    
    public BsUsuarios() {
    }

    public BsUsuarios(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getSena() {
        return sena;
    }

    public void setSena(String sena) {
        this.sena = sena;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        if (!(object instanceof BsUsuarios)) {
            return false;
        }
        BsUsuarios other = (BsUsuarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsUsuarios[ id=" + id + " ]";
    }
    
}
