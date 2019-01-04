package jpa.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpa.entities.Item;
import jpa.entities.Lancamento;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-18T16:37:39")
@StaticMetamodel(Lancamentoitem.class)
public class Lancamentoitem_ { 

    public static volatile SingularAttribute<Lancamentoitem, Item> oidItem;
    public static volatile SingularAttribute<Lancamentoitem, Lancamento> oidLancamento;
    public static volatile SingularAttribute<Lancamentoitem, Integer> oid;

}