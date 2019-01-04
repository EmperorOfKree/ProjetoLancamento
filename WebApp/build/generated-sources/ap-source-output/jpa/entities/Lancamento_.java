package jpa.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpa.entities.Lancamentoitem;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-08-18T16:37:39")
@StaticMetamodel(Lancamento.class)
public class Lancamento_ { 

    public static volatile SingularAttribute<Lancamento, Date> dtInicial;
    public static volatile SingularAttribute<Lancamento, BigDecimal> vlTotal;
    public static volatile CollectionAttribute<Lancamento, Lancamentoitem> lancamentoitemCollection;
    public static volatile SingularAttribute<Lancamento, String> observacao;
    public static volatile SingularAttribute<Lancamento, Integer> oid;
    public static volatile SingularAttribute<Lancamento, Date> dtFinal;

}