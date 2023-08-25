/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "tes_deposito_det")
@NamedQueries({
    @NamedQuery(name = "TesDepositoDet.findAll", query = "SELECT t FROM TesDepositoDet t")
    , @NamedQuery(name = "TesDepositoDet.findById", query = "SELECT t FROM TesDepositoDet t WHERE t.id = :id")
    , @NamedQuery(name = "TesDepositoDet.findByIdCab", query = "SELECT t FROM TesDepositoDet t WHERE t.tesDepositosCab.id = :idCab")
    , @NamedQuery(name = "TesDepositoDet.findByTipCambio", query = "SELECT t FROM TesDepositoDet t WHERE t.tipCambio = :tipCambio")
    , @NamedQuery(name = "TesDepositoDet.findByMontoValor", query = "SELECT t FROM TesDepositoDet t WHERE t.montoValor = :montoValor")
    , @NamedQuery(name = "TesDepositoDet.findByTipDocumento", query = "SELECT t FROM TesDepositoDet t WHERE t.tipDocumento = :tipDocumento")})
public class TesDepositoDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tip_cambio")
    private BigDecimal tipCambio;
    @Column(name = "monto_valor")
    private BigDecimal montoValor;
    @Size(max = 10)
    @Column(name = "tip_documento")
    private String tipDocumento;

    @JoinColumn(name = "cob_cobros_valores_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobCobrosValores cobCobrosValores;

    @JoinColumn(name = "tes_cuenta_bancaria_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TesCuentaBancaria tesCuentaBancaria;

    @JoinColumn(name = "tes_depositos_cab_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TesDepositosCab tesDepositosCab;

    @JoinColumn(name = "id_tipo_valor", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsTipoValor bsTipoValor;

    public TesDepositoDet() {
    }

    public TesDepositoDet(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTipCambio() {
        return tipCambio;
    }

    public void setTipCambio(BigDecimal tipCambio) {
        this.tipCambio = tipCambio;
    }

    public BigDecimal getMontoValor() {
        return montoValor;
    }

    public void setMontoValor(BigDecimal montoValor) {
        this.montoValor = montoValor;
    }

    public String getTipDocumento() {
        return tipDocumento;
    }

    public void setTipDocumento(String tipDocumento) {
        this.tipDocumento = tipDocumento;
    }

    public CobCobrosValores getCobCobrosValores() {
        return cobCobrosValores;
    }

    public void setCobCobrosValores(CobCobrosValores cobCobrosValores) {
        this.cobCobrosValores = cobCobrosValores;
    }

    public TesCuentaBancaria getTesCuentaBancaria() {
        return tesCuentaBancaria;
    }

    public void setTesCuentaBancaria(TesCuentaBancaria tesCuentaBancaria) {
        this.tesCuentaBancaria = tesCuentaBancaria;
    }

    public TesDepositosCab getTesDepositosCab() {
        return tesDepositosCab;
    }

    public void setTesDepositosCab(TesDepositosCab tesDepositosCab) {
        this.tesDepositosCab = tesDepositosCab;
    }

    public BsTipoValor getBsTipoValor() {
        return bsTipoValor;
    }

    public void setBsTipoValor(BsTipoValor bsTipoValor) {
        this.bsTipoValor = bsTipoValor;
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
        if (!(object instanceof TesDepositoDet)) {
            return false;
        }
        TesDepositoDet other = (TesDepositoDet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.TesDepositoDet[ id=" + id + " ]";
    }

}
