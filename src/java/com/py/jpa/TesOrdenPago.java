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
@Table(name = "tes_orden_pago")
@NamedQueries({
    @NamedQuery(name = "TesOrdenPago.findAll", query = "SELECT t FROM TesOrdenPago t")
    , @NamedQuery(name = "TesOrdenPago.findById", query = "SELECT t FROM TesOrdenPago t WHERE t.id = :id")
    , @NamedQuery(name = "TesOrdenPago.findByFecAlta", query = "SELECT t FROM TesOrdenPago t WHERE t.fecAlta = :fecAlta")
    , @NamedQuery(name = "TesOrdenPago.findByUsuAlta", query = "SELECT t FROM TesOrdenPago t WHERE t.usuAlta = :usuAlta")
    , @NamedQuery(name = "TesOrdenPago.findByFechaOp", query = "SELECT t FROM TesOrdenPago t WHERE t.fechaOp = :fechaOp")
    , @NamedQuery(name = "TesOrdenPago.findByObservacion", query = "SELECT t FROM TesOrdenPago t WHERE t.observacion = :observacion")})
public class TesOrdenPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "ORDENCAB_SEQ_GENERATOR", sequenceName = "tes_orden_pago_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDENCAB_SEQ_GENERATOR")
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "fec_alta")
    @Temporal(TemporalType.DATE)
    private Date fecAlta;
    @Column(name = "usu_alta")
    private Integer usuAlta;
    @Column(name = "fecha_op")
    @Temporal(TemporalType.DATE)
    private Date fechaOp;
    @Size(max = 100)
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "nro_op")
    private BigDecimal nroOp;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tesOrdenPago", fetch = FetchType.LAZY)
    private List<TesOrdenPagoDet> tesOrdenPagoDetList;
   
    @JoinColumn(name = "com_proveedores_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ComProveedores comProveedores;

    public TesOrdenPago() {
    }

    public TesOrdenPago(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ComProveedores getComProveedores() {
        return comProveedores;
    }

    public void setComProveedores(ComProveedores comProveedores) {
        this.comProveedores = comProveedores;
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

    public Date getFechaOp() {
        return fechaOp;
    }

    public void setFechaOp(Date fechaOp) {
        this.fechaOp = fechaOp;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<TesOrdenPagoDet> getTesOrdenPagoDetList() {
        return tesOrdenPagoDetList;
    }

    public void setTesOrdenPagoDetList(List<TesOrdenPagoDet> tesOrdenPagoDetList) {
        this.tesOrdenPagoDetList = tesOrdenPagoDetList;
    }

    public BigDecimal getNroOp() {
        return nroOp;
    }

    public void setNroOp(BigDecimal nroOp) {
        this.nroOp = nroOp;
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
        if (!(object instanceof TesOrdenPago)) {
            return false;
        }
        TesOrdenPago other = (TesOrdenPago) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.TesOrdenPago[ id=" + id + " ]";
    }
    
}
