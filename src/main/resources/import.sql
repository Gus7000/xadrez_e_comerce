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

-- Fabricantes
insert into fabricante (nome,cnpj,telefone) values('Gabriel','4785983579437','63 9999-1121');
insert into fabricante (nome,cnpj,telefone) values('Xadrez Brasil','1234567890123','61 3333-4444');

-- Materiais
insert into material (tipo) values('madeira');
insert into material (tipo) values('vidro');
insert into material (tipo) values('mármore');

-- Tabuleiros
insert into tabuleiro (tamanho, cor,id_material) values('8x8', 1,3);
insert into tabuleiro (tamanho, cor,id_material) values('10x10', 2,2);
insert into tabuleiro (tamanho, cor, id_material) values('8x8', 1, 1);

-- Peças: 6 tipos × 2 cores × 3 materiais = 36 peças
-- Tipo 1 (REI), Cor 1 (PRETO), Materiais 1-3
insert into peca (cor, tipo, id_material) values(1, 1, 1);
insert into peca (cor, tipo, id_material) values(1, 1, 2);
insert into peca (cor, tipo, id_material) values(1, 1, 3);
-- Tipo 1 (REI), Cor 2 (BRANCO), Materiais 1-3
insert into peca (cor, tipo, id_material) values(2, 1, 1);
insert into peca (cor, tipo, id_material) values(2, 1, 2);
insert into peca (cor, tipo, id_material) values(2, 1, 3);

-- Tipo 2 (RAINHA), Cor 1 (PRETO), Materiais 1-3
insert into peca (cor, tipo, id_material) values(1, 2, 1);
insert into peca (cor, tipo, id_material) values(1, 2, 2);
insert into peca (cor, tipo, id_material) values(1, 2, 3);
-- Tipo 2 (RAINHA), Cor 2 (BRANCO), Materiais 1-3
insert into peca (cor, tipo, id_material) values(2, 2, 1);
insert into peca (cor, tipo, id_material) values(2, 2, 2);
insert into peca (cor, tipo, id_material) values(2, 2, 3);

-- Tipo 3 (TORRE), Cor 1 (PRETO), Materiais 1-3
insert into peca (cor, tipo, id_material) values(1, 3, 1);
insert into peca (cor, tipo, id_material) values(1, 3, 2);
insert into peca (cor, tipo, id_material) values(1, 3, 3);
-- Tipo 3 (TORRE), Cor 2 (BRANCO), Materiais 1-3
insert into peca (cor, tipo, id_material) values(2, 3, 1);
insert into peca (cor, tipo, id_material) values(2, 3, 2);
insert into peca (cor, tipo, id_material) values(2, 3, 3);

-- Tipo 4 (BISPO), Cor 1 (PRETO), Materiais 1-3
insert into peca (cor, tipo, id_material) values(1, 4, 1);
insert into peca (cor, tipo, id_material) values(1, 4, 2);
insert into peca (cor, tipo, id_material) values(1, 4, 3);
-- Tipo 4 (BISPO), Cor 2 (BRANCO), Materiais 1-3
insert into peca (cor, tipo, id_material) values(2, 4, 1);
insert into peca (cor, tipo, id_material) values(2, 4, 2);
insert into peca (cor, tipo, id_material) values(2, 4, 3);

-- Tipo 5 (CAVALO), Cor 1 (PRETO), Materiais 1-3
insert into peca (cor, tipo, id_material) values(1, 5, 1);
insert into peca (cor, tipo, id_material) values(1, 5, 2);
insert into peca (cor, tipo, id_material) values(1, 5, 3);
-- Tipo 5 (CAVALO), Cor 2 (BRANCO), Materiais 1-3
insert into peca (cor, tipo, id_material) values(2, 5, 1);
insert into peca (cor, tipo, id_material) values(2, 5, 2);
insert into peca (cor, tipo, id_material) values(2, 5, 3);

-- Tipo 6 (PEAO), Cor 1 (PRETO), Materiais 1-3
insert into peca (cor, tipo, id_material) values(1, 6, 1);
insert into peca (cor, tipo, id_material) values(1, 6, 2);
insert into peca (cor, tipo, id_material) values(1, 6, 3);
-- Tipo 6 (PEAO), Cor 2 (BRANCO), Materiais 1-3
insert into peca (cor, tipo, id_material) values(2, 6, 1);
insert into peca (cor, tipo, id_material) values(2, 6, 2);
insert into peca (cor, tipo, id_material) values(2, 6, 3);




