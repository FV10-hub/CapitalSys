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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "tes_pago_cab")
@NamedQueries({
    @NamedQuery(name = "TesPagoCab.findAll", query = "SELECT t FROM TesPagoCab t")
    , @NamedQuery(name = "TesPagoCab.findById", query = "SELECT t FROM TesPagoCab t WHERE t.id = :id")
    , @NamedQuery(name = "TesPagoCab.findByFecAlta", query = "SELECT t FROM TesPagoCab t WHERE t.fecAlta = :fecAlta")
    , @NamedQuery(name = "TesPagoCab.findByUsuAlta", query = "SELECT t FROM TesPagoCab t WHERE t.usuAlta = :usuAlta")
    , @NamedQuery(name = "TesPagoCab.findByTipoCambio", query = "SELECT t FROM TesPagoCab t WHERE t.tipoCambio = :tipoCambio")
    , @NamedQuery(name = "TesPagoCab.findByFecPago", query = "SELECT t FROM TesPagoCab t WHERE t.fecPago = :fecPago")})
public class TesPagoCab implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fec_alta")
    @Temporal(TemporalType.DATE)
    private Date fecAlta;
    @Column(name = "usu_alta")
    private Integer usuAlta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tipo_cambio")
    private BigDecimal tipoCambio;
    @Column(name = "fec_pago")
    @Temporal(TemporalType.DATE)
    private Date fecPago;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tesPagoCabId", fetch = FetchType.LAZY)
    private List<TesPagoComp> tesPagoCompList;
    @JoinColumn(name = "bs_empresas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsEmpresas bsEmpresasId;
    @JoinColumn(name = "bs_monedas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsMonedas bsMonedasId;
    @JoinColumn(name = "bs_sucursal_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsSucursal bsSucursalId;
    @JoinColumn(name = "bs_talonarios_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsTalonarios bsTalonariosId;
    @JoinColumn(name = "cob_habilitaciones_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobHabilitaciones cobHabilitacionesId;
    @JoinColumn(name = "com_proveedores_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ComProveedores comProveedoresId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tesPagoCabId", fetch = FetchType.LAZY)
    private List<TesPagoValor> tesPagoValorList;

    public TesPagoCab() {
    }

    public TesPagoCab(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public Date getFecPago() {
        return fecPago;
    }

    public void setFecPago(Date fecPago) {
        this.fecPago = fecPago;
    }

    public List<TesPagoComp> getTesPagoCompList() {
        return tesPagoCompList;
    }

    public void setTesPagoCompList(List<TesPagoComp> tesPagoCompList) {
        this.tesPagoCompList = tesPagoCompList;
    }

    public BsEmpresas getBsEmpresasId() {
        return bsEmpresasId;
    }

    public void setBsEmpresasId(BsEmpresas bsEmpresasId) {
        this.bsEmpresasId = bsEmpresasId;
    }

    public BsMonedas getBsMonedasId() {
        return bsMonedasId;
    }

    public void setBsMonedasId(BsMonedas bsMonedasId) {
        this.bsMonedasId = bsMonedasId;
    }

    public BsSucursal getBsSucursalId() {
        return bsSucursalId;
    }

    public void setBsSucursalId(BsSucursal bsSucursalId) {
        this.bsSucursalId = bsSucursalId;
    }

    public BsTalonarios getBsTalonariosId() {
        return bsTalonariosId;
    }

    public void setBsTalonariosId(BsTalonarios bsTalonariosId) {
        this.bsTalonariosId = bsTalonariosId;
    }

    public CobHabilitaciones getCobHabilitacionesId() {
        return cobHabilitacionesId;
    }

    public void setCobHabilitacionesId(CobHabilitaciones cobHabilitacionesId) {
        this.cobHabilitacionesId = cobHabilitacionesId;
    }

    public ComProveedores getComProveedoresId() {
        return comProveedoresId;
    }

    public void setComProveedoresId(ComProveedores comProveedoresId) {
        this.comProveedoresId = comProveedoresId;
    }

    public List<TesPagoValor> getTesPagoValorList() {
        return tesPagoValorList;
    }

    public void setTesPagoValorList(List<TesPagoValor> tesPagoValorList) {
        this.tesPagoValorList = tesPagoValorList;
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
        if (!(object instanceof TesPagoCab)) {
            return false;
        }
        TesPagoCab other = (TesPagoCab) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.TesPagoCab[ id=" + id + " ]";
    }
    
}
