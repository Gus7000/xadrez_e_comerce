-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

insert into relogio (modelo,tipo) values('R2D2', 1);
insert into relogio (modelo,tipo) values('R2D3', 1);
insert into relogio (modelo,tipo) values('R2D4', 2);
insert into relogio (modelo,tipo) values('R2D5', 2);
insert into fabricante (nome,cnpj,telefone) values('Gabriel','4785983579437','63 9999-1121');
insert into material (tipo) values('madeira');