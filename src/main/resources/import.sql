-- Fabricantes
insert into fabricante (nome, cnpj, telefone, data_cadastro) values ('Gabriel', '4785983579437', '63 9999-1121', '2026-04-09 08:00:00');
insert into fabricante (nome, cnpj, telefone, data_cadastro) values ('Xadrez Brasil', '1234567890123', '61 3333-4444', '2026-04-09 08:05:00');
insert into fabricante (nome, cnpj, telefone, data_cadastro) values ('Chess Master', '9876543210987', '11 2222-5555', '2026-04-09 08:06:00');
insert into fabricante (nome, cnpj, telefone, data_cadastro) values ('Royal Chess', '5555666677778', '21 9888-7777', '2026-04-09 08:07:00');
insert into fabricante (nome, cnpj, telefone, data_cadastro) values ('Estratégia Jogos', '1111222233334', '31 8765-4321', '2026-04-09 08:08:00');

-- Materiais
insert into material (nome, data_cadastro) values ('madeira', '2026-04-09 08:10:00');
insert into material (nome, data_cadastro) values ('vidro', '2026-04-09 08:10:00');
insert into material (nome, data_cadastro) values ('mármore', '2026-04-09 08:10:00');

-- Tabuleiros
insert into tabuleiro (tamanho, id_material, id_fabricante, data_cadastro) values ('8x8', 1, 1, '2026-04-09 08:20:00');
insert into tabuleiro (tamanho, id_material, id_fabricante, data_cadastro) values ('10x10', 2, 2, '2026-04-09 08:20:00');
insert into tabuleiro (tamanho, id_material, id_fabricante, data_cadastro) values ('8x8 premium', 3, 3, '2026-04-09 08:20:00');

-- Peças: 6 tipos × 2 cores × 3 materiais = 36 peças
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (1, 1, 1, 3.5, 5.0, '2026-04-09 08:30:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (1, 1, 2, 3.5, 5.0, '2026-04-09 08:30:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (1, 1, 3, 3.5, 5.0, '2026-04-09 08:30:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (2, 1, 1, 3.5, 5.0, '2026-04-09 08:30:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (2, 1, 2, 3.5, 5.0, '2026-04-09 08:30:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (2, 1, 3, 3.5, 5.0, '2026-04-09 08:30:00');

insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (1, 2, 1, 3.5, 7.0, '2026-04-09 08:31:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (1, 2, 2, 3.5, 7.0, '2026-04-09 08:31:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (1, 2, 3, 3.5, 7.0, '2026-04-09 08:31:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (2, 2, 1, 3.5, 7.0, '2026-04-09 08:31:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (2, 2, 2, 3.5, 7.0, '2026-04-09 08:31:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (2, 2, 3, 3.5, 7.0, '2026-04-09 08:31:00');

insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (1, 3, 1, 3.5, 6.0, '2026-04-09 08:32:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (1, 3, 2, 3.5, 6.0, '2026-04-09 08:32:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (1, 3, 3, 3.5, 6.0, '2026-04-09 08:32:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (2, 3, 1, 3.5, 6.0, '2026-04-09 08:32:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (2, 3, 2, 3.5, 6.0, '2026-04-09 08:32:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (2, 3, 3, 3.5, 6.0, '2026-04-09 08:32:00');

insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (1, 4, 1, 3.5, 6.5, '2026-04-09 08:33:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (1, 4, 2, 3.5, 6.5, '2026-04-09 08:33:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (1, 4, 3, 3.5, 6.5, '2026-04-09 08:33:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (2, 4, 1, 3.5, 6.5, '2026-04-09 08:33:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (2, 4, 2, 3.5, 6.5, '2026-04-09 08:33:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (2, 4, 3, 3.5, 6.5, '2026-04-09 08:33:00');

insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (1, 5, 1, 3.5, 6.0, '2026-04-09 08:34:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (1, 5, 2, 3.5, 6.0, '2026-04-09 08:34:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (1, 5, 3, 3.5, 6.0, '2026-04-09 08:34:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (2, 5, 1, 3.5, 6.0, '2026-04-09 08:34:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (2, 5, 2, 3.5, 6.0, '2026-04-09 08:34:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (2, 5, 3, 3.5, 6.0, '2026-04-09 08:34:00');

insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (1, 6, 1, 3.5, 4.0, '2026-04-09 08:35:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (1, 6, 2, 3.5, 4.0, '2026-04-09 08:35:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (1, 6, 3, 3.5, 4.0, '2026-04-09 08:35:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (2, 6, 1, 3.5, 4.0, '2026-04-09 08:35:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (2, 6, 2, 3.5, 4.0, '2026-04-09 08:35:00');
insert into peca (cor, tipo, id_material, diametro_cm, altura_cm, data_cadastro) values (2, 6, 3, 3.5, 4.0, '2026-04-09 08:35:00');

-- Kits de peças
insert into kit_peca (id_fabricante, data_cadastro) values (1, '2026-04-09 08:40:00');
insert into kit_peca (id_fabricante, data_cadastro) values (2, '2026-04-09 08:41:00');

-- Itens dos kits
insert into item_kit (kit_id, peca_id, quantidade, data_cadastro) values (1, 1, 1, '2026-04-09 08:42:00');
insert into item_kit (kit_id, peca_id, quantidade, data_cadastro) values (1, 4, 1, '2026-04-09 08:42:00');
insert into item_kit (kit_id, peca_id, quantidade, data_cadastro) values (1, 7, 2, '2026-04-09 08:42:00');
insert into item_kit (kit_id, peca_id, quantidade, data_cadastro) values (1, 13, 2, '2026-04-09 08:42:00');
insert into item_kit (kit_id, peca_id, quantidade, data_cadastro) values (1, 19, 2, '2026-04-09 08:42:00');
insert into item_kit (kit_id, peca_id, quantidade, data_cadastro) values (1, 31, 8, '2026-04-09 08:42:00');

insert into item_kit (kit_id, peca_id, quantidade, data_cadastro) values (2, 2, 1, '2026-04-09 08:43:00');
insert into item_kit (kit_id, peca_id, quantidade, data_cadastro) values (2, 5, 1, '2026-04-09 08:43:00');
insert into item_kit (kit_id, peca_id, quantidade, data_cadastro) values (2, 8, 2, '2026-04-09 08:43:00');
insert into item_kit (kit_id, peca_id, quantidade, data_cadastro) values (2, 14, 2, '2026-04-09 08:43:00');
insert into item_kit (kit_id, peca_id, quantidade, data_cadastro) values (2, 20, 2, '2026-04-09 08:43:00');
insert into item_kit (kit_id, peca_id, quantidade, data_cadastro) values (2, 32, 8, '2026-04-09 08:43:00');


-- RELÓGIO 1 (Digital)
insert into relogio (id, modelo, dimensoes, id_fabricante, data_cadastro)
values (1, 'R2D2', '10x8x3', 1, '2026-04-09 08:50:00');

insert into relogio_digital (id, incremento, display_duplo, tem_buzzer)
values (1, 1, true, true);

insert into relogio_digital_modo_tempo (relogio_digital_id, modo_tempo)
values (1, 'SIMPLES');


-- RELÓGIO 2 (Digital)
insert into relogio (id, modelo, dimensoes, id_fabricante, data_cadastro)
values (2, 'R2D3', '12x9x4', 2, '2026-04-09 08:51:00');

insert into relogio_digital (id, incremento, display_duplo, tem_buzzer)
values (2, 2, true, false);

insert into relogio_digital_modo_tempo (relogio_digital_id, modo_tempo)
values (2, 'DELAY');

insert into relogio_digital_modo_tempo (relogio_digital_id, modo_tempo)
values (2, 'FISCHER');
-- Relógios Analógicos

-- RELÓGIO 3 (Analógico)
insert into relogio (id, modelo, dimensoes, id_fabricante, data_cadastro)
values (3, 'R2D4', '8x8x2', 3, '2026-04-09 08:52:00');

insert into relogio_analogico (id, tem_bandeira, necessita_pilha, mecanismo)
values (3, true, false, 1); -- CORDA


-- RELÓGIO 4 (Analógico)
insert into relogio (id, modelo, dimensoes, id_fabricante, data_cadastro)
values (4, 'R2D5', '7x7x1.5', 4, '2026-04-09 08:53:00');

insert into relogio_analogico (id, tem_bandeira, necessita_pilha, mecanismo)
values (4, true, true, 2); -- QUARTZ

-- Jogos xadrez
insert into jogo_xadrez (nome, preco, descricao, estoque_disponivel, id_kit_peca, id_tabuleiro, id_relogio, data_cadastro) values ('Jogo Clássico Madeira', 449.90, 'Jogo completo com kit clássico e tabuleiro de madeira', 12, 1, 1, 1, '2026-04-09 09:00:00');
insert into jogo_xadrez (nome, preco, descricao, estoque_disponivel, id_kit_peca, id_tabuleiro, id_relogio, data_cadastro) values ('Jogo Premium Mármore', 899.90, 'Jogo completo premium para coleção e torneio', 4, 2, 3, 3, '2026-04-09 09:01:00');
insert into jogo_xadrez (nome, preco, descricao, estoque_disponivel, id_kit_peca, id_tabuleiro, data_cadastro) values ('Jogo Básico', 299.90, 'Jogo simples sem relógio incluso', 20, 1, 2, '2026-04-09 09:02:00');

-- Usuarios
insert into usuario (email, senha_hash, perfil, data_cadastro) values ('admin@mail.com', '$2a$10$RQR2kuvM/IVZZZJudQykCO8ldWgZuXeTr/yAAhZ/1XVy8AG6./VNy', 'ADMIN', '2026-04-09 09:10:00');
insert into usuario (email, senha_hash, perfil, data_cadastro) values ('cliente@mail.com', '$2a$10$3oHeQh4DQzXDLSrs.Vu.1Ofit/H9.wbMqlnsFaMbIvohtcr4ctq8q', 'CLIENTE', '2026-04-09 09:11:00');
