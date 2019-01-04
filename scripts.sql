CREATE TABLE public.item
(
    oid integer NOT NULL DEFAULT nextval('"Item_oid_seq"'::regclass),
    descricao character varying(255) COLLATE pg_catalog."default" NOT NULL,
    valor numeric(8, 2) NOT NULL,
    CONSTRAINT "PK_Item" PRIMARY KEY (oid)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.item
    OWNER to postgres;

===============================================================================

CREATE TABLE public.lancamento
(
    oid integer NOT NULL DEFAULT nextval('"Lancamento_oid_seq"'::regclass),
    dt_inicial timestamp without time zone NOT NULL,
    dt_final timestamp without time zone NOT NULL,
    vl_total numeric(8, 2) NOT NULL,
    observacao character varying(1000) COLLATE pg_catalog."default",
    CONSTRAINT "PK_Lancamento" PRIMARY KEY (oid)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.lancamento
    OWNER to postgres;

=================================================================================

CREATE TABLE public.lancamentoitem
(
    oid integer NOT NULL DEFAULT nextval('"LancamentoItem_oid_seq"'::regclass),
    oid_lancamento integer NOT NULL,
    oid_item integer NOT NULL,
    CONSTRAINT "PK_LancamentoItem" PRIMARY KEY (oid),
    CONSTRAINT "FK_Item" FOREIGN KEY (oid_item)
        REFERENCES public.item (oid) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT "FK_Lancamento" FOREIGN KEY (oid_lancamento)
        REFERENCES public.lancamento (oid) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.lancamentoitem
    OWNER to postgres;

================================================================================

CREATE SEQUENCE ITEM_SEQ START 1;
CREATE SEQUENCE LANCAMENTO_SEQ START 1;
CREATE SEQUENCE LANCAMENTOITEM_SEQ START 1;

================================================================================

select 
	count(*) 
from 
	(
		select 
			count(i.*) as quant_itens, 
			l.vl_total 
		from 
			lancamento l 
		join lancamentoitem li on li.oid_lancamento = l.oid 
        join item i on li.oid_item = i.oid group by l.vl_total
	) as t 

where t.vl_total/t.quant_itens >= 100

===============================================================================
-- Fiquei um pouco confuso na questão, considerei que queria os lançamentos com o maior vl_total.

select 
	l.* 
from 
	lancamento l 
where 
	not exists 
			(
				select 
					* 
				from 
					item i 
				join lancamentoitem li on li.oid_item = i.oid and li.oid_lancamento = l.oid
        		where 
        			upper(substring(i.descricao, 1,1)) <> 'A'
        	) 
    and l.vl_total > 50 
limit 10 
order by l.vl_total desc;

==================================================================================

DO $$
DECLARE
   			
  BEGIN
   
    FOR temprow IN
       SELECT l.* from lancamento l where (select count(*) from lancamentoitem li where li.oid_lancamento = l.oid) > 10
    LOOP
   			
        UPDATE lancamento SET observacao = concat(observacao, ' - Possuem mais que 10 itens') where oid = temprow.oid
   
     END LOOP;
END $$;

================================================================================