package jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ronan
 */
@Entity
@Table(name = "lancamentoitem")
@SequenceGenerator(name = "LCTOIT_SEQ", sequenceName = "LANCAMENTOITEM_SEQ", initialValue = 1, allocationSize = 1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lancamentoitem.findAll", query = "SELECT l FROM Lancamentoitem l"),
    @NamedQuery(name = "Lancamentoitem.findByOid", query = "SELECT l FROM Lancamentoitem l WHERE l.oid = :oid")})
public class Lancamentoitem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LCTOIT_SEQ")
    @Basic(optional = false)
    @Column(name = "oid")
    private Integer oid;
    @JoinColumn(name = "oid_item", referencedColumnName = "oid")
    @ManyToOne(optional = false)
    private Item oidItem;
    @JoinColumn(name = "oid_lancamento", referencedColumnName = "oid")
    @ManyToOne(optional = false)
    private Lancamento oidLancamento;

    public Lancamentoitem() {
    }

    public Lancamentoitem(Integer oid) {
        this.oid = oid;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Item getOidItem() {
        return oidItem;
    }

    public void setOidItem(Item oidItem) {
        this.oidItem = oidItem;
    }

    public Lancamento getOidLancamento() {
        return oidLancamento;
    }

    public void setOidLancamento(Lancamento oidLancamento) {
        this.oidLancamento = oidLancamento;
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
        if (!(object instanceof Lancamentoitem)) {
            return false;
        }
        Lancamentoitem other = (Lancamentoitem) object;
        if ((this.oid == null && other.oid != null) || (this.oid != null && !this.oid.equals(other.oid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.Lancamentoitem[ oid=" + oid + " ]";
    }
    
}
