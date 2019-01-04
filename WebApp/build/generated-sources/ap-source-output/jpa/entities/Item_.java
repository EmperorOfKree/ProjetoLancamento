package jpa.entities;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpa.entities.Lancamentoitem;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-18T16:37:39")
@StaticMetamodel(Item.class)
public class Item_ { 

    public static volatile CollectionAttribute<Item, Lancamentoitem> lancamentoitemCollection;
    public static volatile SingularAttribute<Item, BigDecimal> valor;
    public static volatile SingularAttribute<Item, Integer> oid;
    public static volatile SingularAttribute<Item, String> descricao;

}