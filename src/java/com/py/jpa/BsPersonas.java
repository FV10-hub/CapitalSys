/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.jpa;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "bs_personas")
@NamedQueries({
    @NamedQuery(name = "BsPersonas.findAll", query = "SELECT b FROM BsPersonas b")
    , @NamedQuery(name = "BsPersonas.findById", query = "SELECT b FROM BsPersonas b WHERE b.id = :id")
    , @NamedQuery(name = "BsPersonas.findByNombre", query = "SELECT b FROM BsPersonas b WHERE b.nombre = :nombre")
    , @NamedQuery(name = "BsPersonas.findByApellidos", query = "SELECT b FROM BsPersonas b WHERE b.apellidos = :apellidos")
    , @NamedQuery(name = "BsPersonas.findByNombreFantasia", query = "SELECT b FROM BsPersonas b WHERE b.nombreFantasia = :nombreFantasia")
    , @NamedQuery(name = "BsPersonas.findByFecNacimiento", query = "SELECT b FROM BsPersonas b WHERE b.fecNacimiento = :fecNacimiento")
    , @NamedQuery(name = "BsPersonas.findByEmail", query = "SELECT b FROM BsPersonas b WHERE b.email = :email")})
public class BsPersonas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "nombre_fantasia")
    private String nombreFantasia;
    @Column(name = "fec_nacimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecNacimiento;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 80)
    @Column(name = "email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bsPersonas")
    private List<BsDireccionesPersonas> bsDirecPersonas;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bsPersonas")
    private List<BsIdentificaciones> bsIdentificaciones;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bsPersonas")
    private List<BsTelefPersonas> bsTelefPersonas;

    public BsPersonas() {
    }

    public BsPersonas(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }

    public Date getFecNacimiento() {
        return fecNacimiento;
    }

    public void setFecNacimiento(Date fecNacimiento) {
        this.fecNacimiento = fecNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<BsDireccionesPersonas> getBsDirecPersonas() {
        return bsDirecPersonas;
    }

    public void setBsDirecPersonas(List<BsDireccionesPersonas> bsDirecPersonas) {
        this.bsDirecPersonas = bsDirecPersonas;
    }

    public List<BsIdentificaciones> getBsIdentificaciones() {
        return bsIdentificaciones;
    }

    public void setBsIdentificaciones(List<BsIdentificaciones> bsIdentificaciones) {
        this.bsIdentificaciones = bsIdentificaciones;
    }

    public List<BsTelefPersonas> getBsTelefPersonas() {
        return bsTelefPersonas;
    }

    public void setBsTelefPersonas(List<BsTelefPersonas> bsTelefPersonas) {
        this.bsTelefPersonas = bsTelefPersonas;
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
        if (!(object instanceof BsPersonas)) {
            return false;
        }
        BsPersonas other = (BsPersonas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsPersonas[ id=" + id + " ]";
    }

}
