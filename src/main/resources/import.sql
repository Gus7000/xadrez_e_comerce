-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

-- Fabricantes
insert into fabricante (nome,cnpj,telefone) values('Gabriel','4785983579437','63 9999-1121');
insert into fabricante (nome,cnpj,telefone) values('Xadrez Brasil','1234567890123','61 3333-4444');

-- Relógios
insert into relogio (nome,preco,descricao,id_fabricante,modelo,tipo) values('Relógio Clássico', 199.90, 'Relógio analógico para partidas casuais', 1, 'R2D2', 1);
insert into relogio (nome,preco,descricao,id_fabricante,modelo,tipo) values('Relógio Blitz', 249.90, 'Relógio analógico para partidas rápidas', 1, 'R2D3', 1);
insert into relogio (nome,preco,descricao,id_fabricante,modelo,tipo) values('Relógio Digital Pro', 329.90, 'Relógio digital com múltiplos controles de tempo', 2, 'R2D4', 2);
insert into relogio (nome,preco,descricao,id_fabricante,modelo,tipo) values('Relógio Digital Tournament', 399.90, 'Relógio digital para torneios oficiais', 2, 'R2D5', 2);

-- Materiais
insert into material (tipo) values('madeira');
insert into material (tipo) values('vidro');
insert into material (tipo) values('mármore');

-- Tabuleiros
insert into tabuleiro (nome,preco,descricao,id_fabricante,tamanho,cor,id_material) values('Tabuleiro Mármore 8x8', 499.90, 'Tabuleiro premium em mármore', 2, '8x8', 1, 3);
insert into tabuleiro (nome,preco,descricao,id_fabricante,tamanho,cor,id_material) values('Tabuleiro Vidro 10x10', 299.90, 'Tabuleiro moderno em vidro', 1, '10x10', 2, 2);
insert into tabuleiro (nome,preco,descricao,id_fabricante,tamanho,cor,id_material) values('Tabuleiro Madeira 8x8', 179.90, 'Tabuleiro tradicional em madeira', 1, '8x8', 1, 1);

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

-- Kits de peças
insert into kitpeca (nome,preco,descricao,id_fabricante) values('Kit Oficial Clássico', 299.90, 'Kit completo de peças para tabuleiros 8x8', 1);
insert into kitpeca (nome,preco,descricao,id_fabricante) values('Kit Premium Torneio', 459.90, 'Kit de peças premium para competições', 2);

-- Itens dos kits
insert into itemkit (kit_id,peca_id,quantidade) values(1, 1, 1);
insert into itemkit (kit_id,peca_id,quantidade) values(1, 7, 1);
insert into itemkit (kit_id,peca_id,quantidade) values(1, 13, 2);
insert into itemkit (kit_id,peca_id,quantidade) values(1, 19, 2);
insert into itemkit (kit_id,peca_id,quantidade) values(1, 25, 2);
insert into itemkit (kit_id,peca_id,quantidade) values(1, 31, 8);

insert into itemkit (kit_id,peca_id,quantidade) values(2, 2, 1);
insert into itemkit (kit_id,peca_id,quantidade) values(2, 8, 1);
insert into itemkit (kit_id,peca_id,quantidade) values(2, 14, 2);
insert into itemkit (kit_id,peca_id,quantidade) values(2, 20, 2);
insert into itemkit (kit_id,peca_id,quantidade) values(2, 26, 2);
insert into itemkit (kit_id,peca_id,quantidade) values(2, 32, 8);

-- Jogos completos
insert into jogocompleto (nome,preco,descricao,id_fabricante,id_kit_peca,id_tabuleiro) values('Jogo Clássico Madeira', 449.90, 'Jogo completo com kit clássico e tabuleiro de madeira', 1, 1, 3);
insert into jogocompleto (nome,preco,descricao,id_fabricante,id_kit_peca,id_tabuleiro) values('Jogo Premium Mármore', 899.90, 'Jogo completo premium para coleção e torneio', 2, 2, 1);




