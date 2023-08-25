/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "cre_desembolsos_cab")
@NamedQueries({
    @NamedQuery(name = "CreDesembolsosCab.findAll", query = "SELECT c FROM CreDesembolsosCab c")
    , @NamedQuery(name = "CreDesembolsosCab.findById", query = "SELECT c FROM CreDesembolsosCab c WHERE c.id = :id")
    , @NamedQuery(name = "CreDesembolsosCab.findByFecAlta", query = "SELECT c FROM CreDesembolsosCab c WHERE c.fecAlta = :fecAlta")
    , @NamedQuery(name = "CreDesembolsosCab.findByUsuAlta", query = "SELECT c FROM CreDesembolsosCab c WHERE c.usuAlta = :usuAlta")
    , @NamedQuery(name = "CreDesembolsosCab.findByFechaDesembolso", query = "SELECT c FROM CreDesembolsosCab c WHERE c.fechaDesembolso = :fechaDesembolso")
    , @NamedQuery(name = "CreDesembolsosCab.findByTazaMora", query = "SELECT c FROM CreDesembolsosCab c WHERE c.tazaMora = :tazaMora")
    , @NamedQuery(name = "CreDesembolsosCab.findByTazaAnual", query = "SELECT c FROM CreDesembolsosCab c WHERE c.tazaAnual = :tazaAnual")
    , @NamedQuery(name = "CreDesembolsosCab.findByEstado", query = "SELECT c FROM CreDesembolsosCab c WHERE c.estado = :estado")})
public class CreDesembolsosCab implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "DESEMBOLSOCAB_SEQ_GENERATOR", sequenceName = "cre_desembolsos_cab_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DESEMBOLSOCAB_SEQ_GENERATOR")
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "fec_alta")
    @Temporal(TemporalType.DATE)
    private Date fecAlta;
    @Column(name = "usu_alta")
    private Integer usuAlta;
    @Column(name = "fecha_desembolso")
    @Temporal(TemporalType.DATE)
    private Date fechaDesembolso;
    @Column(name = "taza_mora")
    private BigDecimal tazaMora;
    @Column(name = "taza_anual")
    private BigDecimal tazaAnual;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    @Column(name = "nro_comprobante")
    private BigDecimal nroComprobante;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creDesembolsosCab", fetch = FetchType.LAZY)
    private List<CreDesembolsoDet> creDesembolsoDetList;

    @JoinColumn(name = "cre_solicitudes_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CreSolicitudes creSolicitudes;

    @JoinColumn(name = "cre_tipo_amortizacion_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CreTipoAmortizacion creTipoAmortizacion;
    
    @JoinColumn(name = "id_talonarios", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsTalonarios bsTalonarios;

    public CreDesembolsosCab() {
    }

    public CreDesembolsosCab(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecAlta() {
        return fecAlta;
    }

    public void setFecAlta(Date fecAlta) {
        this.fecAlta = fecAlta;
    }

    public Integer getUsuAlta() {
        return usuAlta;
    }

    public void setUsuAlta(Integer usuAlta) {
        this.usuAlta = usuAlta;
    }

    public Date getFechaDesembolso() {
        return fechaDesembolso;
    }

    public void setFechaDesembolso(Date fechaDesembolso) {
        this.fechaDesembolso = fechaDesembolso;
    }

    public BigDecimal getTazaMora() {
        return tazaMora;
    }

    public void setTazaMora(BigDecimal tazaMora) {
        this.tazaMora = tazaMora;
    }

    public BigDecimal getTazaAnual() {
        return tazaAnual;
    }

    public void setTazaAnual(BigDecimal tazaAnual) {
        this.tazaAnual = tazaAnual;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<CreDesembolsoDet> getCreDesembolsoDetList() {
        return creDesembolsoDetList;
    }

    public void setCreDesembolsoDetList(List<CreDesembolsoDet> creDesembolsoDetList) {
        this.creDesembolsoDetList = creDesembolsoDetList;
    }

    public CreSolicitudes getCreSolicitudes() {
        return creSolicitudes;
    }

    public void setCreSolicitudes(CreSolicitudes creSolicitudes) {
        this.creSolicitudes = creSolicitudes;
    }

    public CreTipoAmortizacion getCreTipoAmortizacion() {
        return creTipoAmortizacion;
    }

    public void setCreTipoAmortizacion(CreTipoAmortizacion creTipoAmortizacion) {
        this.creTipoAmortizacion = creTipoAmortizacion;
    }

    public BsTalonarios getBsTalonarios() {
        return bsTalonarios;
    }

    public void setBsTalonarios(BsTalonarios bsTalonarios) {
        this.bsTalonarios = bsTalonarios;
    }

    public BigDecimal getNroComprobante() {
        return nroComprobante;
    }

    public void setNroComprobante(BigDecimal nroComprobante) {
        this.nroComprobante = nroComprobante;
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
        if (!(object instanceof CreDesembolsosCab)) {
            return false;
        }
        CreDesembolsosCab other = (CreDesembolsosCab) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.CreDesembolsosCab[ id=" + id + " ]";
    }

}
