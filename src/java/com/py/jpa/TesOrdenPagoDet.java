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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "tes_orden_pago_det")
@NamedQueries({
    @NamedQuery(name = "TesOrdenPagoDet.findAll", query = "SELECT t FROM TesOrdenPagoDet t")
    , @NamedQuery(name = "TesOrdenPagoDet.findById", query = "SELECT t FROM TesOrdenPagoDet t WHERE t.id = :id")
    , @NamedQuery(name = "TesOrdenPagoDet.findByIdCab", query = "SELECT t FROM TesOrdenPagoDet t WHERE t.tesOrdenPago = :idCab")
    , @NamedQuery(name = "TesOrdenPagoDet.findByIdComprobante", query = "SELECT t FROM TesOrdenPagoDet t WHERE t.idComprobante = :idComprobante")
    , @NamedQuery(name = "TesOrdenPagoDet.findByTipoCambio", query = "SELECT t FROM TesOrdenPagoDet t WHERE t.tipoCambio = :tipoCambio")
    , @NamedQuery(name = "TesOrdenPagoDet.findByMontoTotal", query = "SELECT t FROM TesOrdenPagoDet t WHERE t.montoTotal = :montoTotal")})
public class TesOrdenPagoDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "ORDENDET_SEQ_GENERATOR", sequenceName = "tes_orden_pago_det_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDENDET_SEQ_GENERATOR")
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "id_comprobante")
    private Integer idComprobante; //Cuando haya comprobantes de compras este campo debera ser sustituido por ese objeto
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tipo_cambio")
    private BigDecimal tipoCambio;
    @Column(name = "monto_total")
    private BigDecimal montoTotal;

    @JoinColumn(name = "bs_monedas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsMonedas bsMonedas;

    @JoinColumn(name = "cre_desembolsos_cab_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CreDesembolsosCab creDesembolsosCab;

    @JoinColumn(name = "tes_orden_pago_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TesOrdenPago tesOrdenPago;

    public TesOrdenPagoDet() {
    }

    public TesOrdenPagoDet(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CreDesembolsosCab getCreDesembolsosCab() {
        return creDesembolsosCab;
    }

    public void setCreDesembolsosCab(CreDesembolsosCab creDesembolsosCab) {
        this.creDesembolsosCab = creDesembolsosCab;
    }

    public TesOrdenPago getTesOrdenPago() {
        return tesOrdenPago;
    }

    public void setTesOrdenPago(TesOrdenPago tesOrdenPago) {
        this.tesOrdenPago = tesOrdenPago;
    }

    public Integer getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(Integer idComprobante) {
        this.idComprobante = idComprobante;
    }

    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public BsMonedas getBsMonedas() {
        return bsMonedas;
    }

    public void setBsMonedas(BsMonedas bsMonedas) {
        this.bsMonedas = bsMonedas;
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
        if (!(object instanceof TesOrdenPagoDet)) {
            return false;
        }
        TesOrdenPagoDet other = (TesOrdenPagoDet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.TesOrdenPagoDet[ id=" + id + " ]";
    }

}
