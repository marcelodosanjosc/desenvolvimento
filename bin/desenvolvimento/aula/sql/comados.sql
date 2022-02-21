create table aluno(
id integer primary key unique not null default nextval('id_seq') ,
nome varchar(200) 
);

CREATE SEQUENCE id_seq
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
 
alter table aluno alter column id set default nextval("id_seq"::regclass) ;

insert into aluno (nome) values('Marcelo');
select * from aluno a ;
