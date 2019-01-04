/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ronan
 */
@Entity
@Table(name = "lancamento")
@SequenceGenerator(name = "LCTO_SEQ", sequenceName = "LANCAMENTO_SEQ", initialValue = 1, allocationSize = 1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lancamento.findAll", query = "SELECT l FROM Lancamento l"),
    @NamedQuery(name = "Lancamento.findByOid", query = "SELECT l FROM Lancamento l WHERE l.oid = :oid"),
    @NamedQuery(name = "Lancamento.findByDtInicial", query = "SELECT l FROM Lancamento l WHERE l.dtInicial = :dtInicial"),
    @NamedQuery(name = "Lancamento.findByDtFinal", query = "SELECT l FROM Lancamento l WHERE l.dtFinal = :dtFinal"),
    @NamedQuery(name = "Lancamento.findByVlTotal", query = "SELECT l FROM Lancamento l WHERE l.vlTotal = :vlTotal"),
    @NamedQuery(name = "Lancamento.findByObservacao", query = "SELECT l FROM Lancamento l WHERE l.observacao = :observacao")})
public class Lancamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LCTO_SEQ")
    @Basic(optional = false)
    @Column(name = "oid")
    private Integer oid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dt_inicial")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtInicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dt_final")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtFinal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "vl_total")
    private BigDecimal vlTotal;
    @Size(max = 1000)
    @Column(name = "observacao")
    private String observacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "oidLancamento")
    private Collection<Lancamentoitem> lancamentoitemCollection;
    
    public Lancamento() {
        
    }

    public Lancamento(Integer oid) {
        this.oid = oid;
    }

    public Lancamento(Integer oid, Date dtInicial, Date dtFinal, BigDecimal vlTotal) {
        this.oid = oid;
        this.dtInicial = dtInicial;
        this.dtFinal = dtFinal;
        this.vlTotal = vlTotal;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Date getDtInicial() {
        return dtInicial;
    }

    public void setDtInicial(Date dtInicial) {
        this.dtInicial = dtInicial;
    }

    public Date getDtFinal() {
        return dtFinal;
    }

    public void setDtFinal(Date dtFinal) {
        this.dtFinal = dtFinal;
    }

    public BigDecimal getVlTotal() {
        return vlTotal;
    }

    public void setVlTotal(BigDecimal vlTotal) {
        this.vlTotal = vlTotal;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    
    @XmlTransient
    public Collection<Lancamentoitem> getLancamentoitemCollection() {
        return lancamentoitemCollection;
    }

    public void setLancamentoitemCollection(Collection<Lancamentoitem> lancamentoitemCollection) {
        this.lancamentoitemCollection = lancamentoitemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (oid != null ? oid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lancamento)) {
            return false;
        }
        Lancamento other = (Lancamento) object;
        if ((this.oid == null && other.oid != null) || (this.oid != null && !this.oid.equals(other.oid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.Lancamento[ oid=" + oid + " ]";
    }
    
}
