creat table categoria(
    id      uuid             primary key,
    nome    varchar(50)      not null unique
);

creat table produto(
      id          uuuid           primary key,
      nome        varchar(100)    not null;
      preco       decimal(10,2)   not null,
      quantidade  int             not null,
      categoria_id  uuid          not null,
  foreign key(categoria_id)
    references categoria(id)
);

insert into categoria(id, nome) values(gen_random_uuid(), 'Informática');
insert into categoria(id, nome) values(gen_random_uuid(), 'Eletrônicos');
insert into categoria(id, nome) values(gen_random_uuid(), 'Livraria');
insert into categoria(id, nome) values(gen_random_uuid(), 'Vestuário');
insert into categoria(id, nome) values(gen_random_uuid(), 'Outros');

select * from categoria;

select
	p.id as idproduto, p.nome as nomeproduto, p.preco, p.quantidade,
	c.id as idcategoria, c.nome as nomecategoria,
from produto p
inner join categoria c
on c.id = p.categoria_id
where p.nome ilike ?
order by p.nome;

SELECT
	c.nome as nomecategoria,
	SUM(p.quantidade) as qtdprodutos
FROM produto p 
INNER JOIN categoria c
ON c.id = p.categoria_id
GROUP BY c.nome;