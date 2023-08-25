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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "cob_habilitaciones")
@NamedQueries({
    @NamedQuery(name = "CobHabilitaciones.findAll", query = "SELECT c FROM CobHabilitaciones c")
    , @NamedQuery(name = "CobHabilitaciones.findById", query = "SELECT c FROM CobHabilitaciones c WHERE c.id = :id")
    , @NamedQuery(name = "CobHabilitaciones.findByFechaHab", query = "SELECT c FROM CobHabilitaciones c WHERE c.fechaHab = :fechaHab")
    , @NamedQuery(name = "CobHabilitaciones.findByFechaCierre", query = "SELECT c FROM CobHabilitaciones c WHERE c.fechaCierre = :fechaCierre")
    , @NamedQuery(name = "CobHabilitaciones.findByHoraHab", query = "SELECT c FROM CobHabilitaciones c WHERE c.horaHab = :horaHab")
    , @NamedQuery(name = "CobHabilitaciones.findByHoraCierre", query = "SELECT c FROM CobHabilitaciones c WHERE c.horaCierre = :horaCierre")
    , @NamedQuery(name = "CobHabilitaciones.findByNroHabilitacion", query = "SELECT c FROM CobHabilitaciones c WHERE c.nroHabilitacion = :nroHabilitacion")})
public class CobHabilitaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "fecha_hab")
    @Temporal(TemporalType.DATE)
    private Date fechaHab;
    @Column(name = "fecha_cierre")
    @Temporal(TemporalType.DATE)
    private Date fechaCierre;
    @Size(max = 10)
    @Column(name = "hora_hab")
    private String horaHab;
    @Size(max = 10)
    @Column(name = "hora_cierre")
    private String horaCierre;
    @Size(max = 20)
    @Column(name = "nro_habilitacion")
    private String nroHabilitacion;
    
    @JoinColumn(name = "bs_usuarios_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsUsuarios bsUsuarios;
    @JoinColumn(name = "cob_cajas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobCajas cobCajas;

    public CobHabilitaciones() {
    }

    public CobHabilitaciones(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaHab() {
        return fechaHab;
    }

    public void setFechaHab(Date fechaHab) {
        this.fechaHab = fechaHab;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getHoraHab() {
        return horaHab;
    }

    public void setHoraHab(String horaHab) {
        this.horaHab = horaHab;
    }

    public String getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(String horaCierre) {
        this.horaCierre = horaCierre;
    }

    public String getNroHabilitacion() {
        return nroHabilitacion;
    }

    public void setNroHabilitacion(String nroHabilitacion) {
        this.nroHabilitacion = nroHabilitacion;
    }

    public BsUsuarios getBsUsuarios() {
        return bsUsuarios;
    }

    public void setBsUsuarios(BsUsuarios bsUsuarios) {
        this.bsUsuarios = bsUsuarios;
    }

    public CobCajas getCobCajas() {
        return cobCajas;
    }

    public void setCobCajas(CobCajas cobCajas) {
        this.cobCajas = cobCajas;
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
        if (!(object instanceof CobHabilitaciones)) {
            return false;
        }
        CobHabilitaciones other = (CobHabilitaciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.CobHabilitaciones[ id=" + id + " ]";
    }
    
}
