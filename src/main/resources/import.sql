-- DIMENSÃO
INSERT INTO Dimensao (comprimento, altura, polegada) VALUES
(120, 70, 50),
(90, 55, 40),
(135, 80, 55),
(160, 95, 65),
(75, 45, 32),
(100, 60, 43),
(180, 105, 75);



-- FABRICANTE
INSERT INTO Fabricante (nome, cnpj, paisSede) VALUES
('Samsung', '12345678000199', 'Coreia do Sul'),
('LG', '98765432000188', 'Coreia do Sul'),
('Sony', '45678912000177', 'Japão'),
('Panasonic', '65432198000166', 'Japão'),
('Philips', '32165487000155', 'Holanda');



-- TELEFONE PARAA FABRICANTE
INSERT INTO Telefone(ddd, numero, fabricante_id) VALUES
('63', '123456789', 1),
('66', '985275291', 1),
('12', '987654321', 2),
('11', '909090909', 2);



-- TELEVISAO
INSERT INTO Televisao (marca, modelo, resolucao, tipoTela, id_fabricante, id_dimensao, valor, estoque) VALUES
    ('LG', 'Crystal HUD', 'HD', 'LED', 1, 1, 50, 30),
    ('Samsung', 'Bravia XR', 'FULL_HD', 'OLED', 2, 2, 60, 25),
    ('Philips', 'Viera TH', 'FULL_HD', 'PLASMA', 3, 3, 70, 15),
    ('Sony', 'OLED Evo', 'FULL_HD', 'OLED', 4, 4, 80, 20),
    ('Samsung', 'QLED QN90A', 'HD', 'QLED', 1, 5, 90, 10),
    ('LG', 'OLED C1', 'HD', 'OLED', 2, 6, 100, 12),
    ('Sony', 'Bravia X90J', 'HD', 'LED', 3, 7, 110, 18),
    ('Philips', 'Ambilight OLED806', 'HD', 'OLED', 5, 3, 120, 8);

-- FORNECEDOR
INSERT INTO Fornecedor (nome, cnpj) VALUES
('Eletro Distribuidora', '12345678000190'),
('Mega Importados', '23456789000101'),
('TechHouse Comércio', '34567890000112'),
('Digital Center', '45678901000123'),
('Eletro Sul Atacado', '56789012000134');


-- ADICIONANDO TELEVISÃO PARA FORNECEDORES
INSERT INTO fornecedor_televisao (fornecedor_id, televisao_id) VALUES
(1, 1), (1, 2), (1, 5),  -- Eletro Distribuidora fornece TVs 1, 2 e 5
(2, 3), (2, 6),          -- Mega Importados fornece TVs 3 e 6
(3, 4), (3, 7), (3, 8),  -- TechHouse fornece TVs 4, 7 e 8
(4, 5), (4, 6), (4, 7),  -- Digital Center fornece TVs 5, 6 e 7
(5, 1), (5, 8);          -- Eletro Sul fornece TVs 1 e 8



-- TELEFONE PARA FORNECEDORES
INSERT INTO Telefone (ddd, numero, id_fornecedor) VALUES
('11', '987654321', 1),
('21', '998877665', 1),
('31', '912345678', 2),
('41', '923456789', 3),
('51', '934567890', 4),
('61', '945678901', 5);



-- ESTADO
INSERT INTO Estado (nome, sigla) VALUES
('São Paulo', 'SP'),
('Rio de Janeiro', 'RJ'),
('Minas Gerais', 'MG'),
('Bahia', 'BA'),
('Paraná', 'PR');



-- MUNICIPIO
INSERT INTO Municipio (nome, id_estado) VALUES
-- Municípios de São Paulo
('São Paulo', 1),
('Campinas', 1),
('Santos', 1),
-- Municípios do Rio de Janeiro
('Rio de Janeiro', 2),
('Niterói', 2),
('Petrópolis', 2),
-- Municípios de Minas Gerais
('Belo Horizonte', 3),
('Uberlândia', 3),
('Ouro Preto', 3),
-- Municípios da Bahia
('Salvador', 4),
('Feira de Santana', 4),
('Ilhéus', 4),
-- Municípios do Paraná
('Curitiba', 5),
('Londrina', 5),
('Maringá', 5);

-- ADM
INSERT INTO Funcionario (nome, cpf) VALUES
('Gabriel Vieira', '12345678900');
INSERT INTO Usuario (username, senha, perfil, cpf, id_funcionario) VALUES
('gabriel', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'ADM', '12345678900', 1);

--senha: 123456
--hash: SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==

--USER
INSERT INTO Usuario (username, senha, perfil, cpf)
VALUES ('italo', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER', '98765432100');

INSERT INTO Usuario (username, senha, perfil, cpf)
VALUES ('felipe', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER', '03518783190');

INSERT INTO Endereco (cep, bairro, numero, complemento, id_municipio, id_usuario) VALUES
-- Endereços do usuário 1 (italo)
('01001000', 'Sé', 100, 'Edifício Comercial', 1, 2),
('01311000', 'Bela Vista', 200, 'Apartamento 302', 1, 2),
('01415000', 'Cerqueira César', 300, 'Sala 501', 1, 2),

-- Endereços do usuário 2 (felipe)
('13010000', 'Centro', 400, 'Casa Azul', 2, 3),
('13010000', 'Cambuí', 500, 'Loja 10', 2, 3),
('11015000', 'Gonzaga', 600, 'Cobertura', 3, 3);

-- Pedido 1 (Italo)
INSERT INTO Pedido (dataPedido, valorTotal, statusPedido, id_usuario)
VALUES (NOW(), 200.00, 'PEDIDO_EM_PROCESSO', 2);

-- Endereço de entrega
INSERT INTO EnderecoEntrega (id_pedido, id_endereco)
VALUES (1, 1); -- Endereço: Sé

-- Pagamento pendente
INSERT INTO Pagamento (valor, statusPagamento, dataPagamento, id_pedido)
VALUES (200.00, 'PAGAMENTO_PENDENTE', NOW(), 1);

-- Pix vinculado ao pagamento id = 1
INSERT INTO Pix (id, chave)
VALUES (1, 'pix-italo-001');

-- Itens
INSERT INTO ItemPedido (id_pedido, id_televisao, preco, quantidade)
VALUES (1, 1, 200.00, 1);

-- Pedido 2 (Italo)
INSERT INTO Pedido (dataPedido, valorTotal, statusPedido, id_usuario)
VALUES (NOW(), 300.00, 'PEDIDO_EM_PROCESSO', 2);

-- Endereço de entrega
INSERT INTO EnderecoEntrega (id_pedido, id_endereco)
VALUES (2, 2); -- Endereço: Bela Vista

-- Pagamento pendente
INSERT INTO Pagamento (valor, statusPagamento, dataPagamento, id_pedido)
VALUES (300.00, 'PAGAMENTO_PENDENTE', NOW(), 2);

-- Boleto vinculado ao pagamento id = 2
INSERT INTO Boleto (id, codigoBarras)
VALUES (2, '34191.79001 01043.510047 91020.150008 8 79410000030000');

-- Itens
INSERT INTO ItemPedido (id_pedido, id_televisao, preco, quantidade)
VALUES (2, 2, 300.00, 1);

-- Pedido 3 (Italo)
INSERT INTO Pedido (dataPedido, valorTotal, statusPedido, id_usuario)
VALUES (NOW(), 400.00, 'PEDIDO_EM_PROCESSO', 2);

-- Endereço de entrega
INSERT INTO EnderecoEntrega (id_pedido, id_endereco)
VALUES (3, 3); -- Endereço: Cerqueira César

-- Pagamento pendente
INSERT INTO Pagamento (valor, statusPagamento, dataPagamento, id_pedido)
VALUES (400.00, 'PAGAMENTO_PENDENTE', NOW(), 3);

-- Cartão vinculado ao pagamento id = 3
INSERT INTO Cartao (id, titular, numero, dataValidade, cvv)
VALUES (3, 'Italo Ribeiro', '4111111111111111', NOW() + INTERVAL '1 year', '123');

-- Itens
INSERT INTO ItemPedido (id_pedido, id_televisao, preco, quantidade)
VALUES (3, 3, 400.00, 1);

-- Pedido 4 (Italo)
INSERT INTO Pedido (dataPedido, valorTotal, statusPedido, id_usuario)
VALUES (NOW(), 500.00, 'CANCELADO', 2);

-- Endereço de entrega
INSERT INTO EnderecoEntrega (id_pedido, id_endereco)
VALUES (4, 1); -- Endereço: Sé

-- Pagamento cancelado
INSERT INTO Pagamento (valor, statusPagamento, dataPagamento, id_pedido)
VALUES (500.00, 'PAGAMENTO_PENDENTE', NOW(), 4); -- Você pode criar outro status se desejar

-- Pix vinculado ao pagamento id = 4
INSERT INTO Pix (id, chave)
VALUES (4, 'pix-cancelado-italo');

-- Itens
INSERT INTO ItemPedido (id_pedido, id_televisao, preco, quantidade)
VALUES (4, 4, 500.00, 1);

-- Pedido 5 (Italo)
INSERT INTO Pedido (dataPedido, valorTotal, statusPedido, id_usuario)
VALUES (NOW(), 600.00, 'ENTREGUE', 2);

-- Endereço de entrega
INSERT INTO EnderecoEntrega (id_pedido, id_endereco)
VALUES (5, 2); -- Endereço: Bela Vista

-- Pagamento efetuado
INSERT INTO Pagamento (valor, statusPagamento, dataPagamento, id_pedido)
VALUES (600.00, 'PAGAMENTO_EFETUADO', NOW(), 5);

-- Boleto vinculado ao pagamento id = 5
INSERT INTO Boleto (id, codigoBarras)
VALUES (5, '23793.38127 60007.439370 78000.421010 6 83580000060000');

-- Itens
INSERT INTO ItemPedido (id_pedido, id_televisao, preco, quantidade)
VALUES (5, 5, 600.00, 1);

-- Pedido 6 (Felipe)
INSERT INTO Pedido (dataPedido, valorTotal, statusPedido, id_usuario)
VALUES (NOW(), 150.00, 'PEDIDO_EM_PROCESSO', 3);

INSERT INTO EnderecoEntrega (id_pedido, id_endereco)
VALUES (6, 4);  -- endereço Centro (Felipe)

INSERT INTO Pagamento (valor, statusPagamento, dataPagamento, id_pedido)
VALUES (150.00, 'PAGAMENTO_PENDENTE', NOW(), 6);

INSERT INTO Pix (id, chave)
VALUES (6, 'pix-felipe-001');

INSERT INTO ItemPedido (id_pedido, id_televisao, preco, quantidade)
VALUES (6, 1, 150.00, 1);


-- Pedido 7 (Felipe)
INSERT INTO Pedido (dataPedido, valorTotal, statusPedido, id_usuario)
VALUES (NOW(), 200.00, 'PEDIDO_EM_PROCESSO', 3);

INSERT INTO EnderecoEntrega (id_pedido, id_endereco)
VALUES (7, 5);  -- endereço Cambuí (Felipe)

INSERT INTO Pagamento (valor, statusPagamento, dataPagamento, id_pedido)
VALUES (200.00, 'PAGAMENTO_PENDENTE', NOW(), 7);

INSERT INTO Boleto (id, codigoBarras)
VALUES (7, '23793.38127 60007.439370 78000.421010 6 83580000020000');

INSERT INTO ItemPedido (id_pedido, id_televisao, preco, quantidade)
VALUES (7, 2, 200.00, 1);


-- Pedido 8 (Felipe)
INSERT INTO Pedido (dataPedido, valorTotal, statusPedido, id_usuario)
VALUES (NOW(), 250.00, 'PEDIDO_EM_PROCESSO', 3);

INSERT INTO EnderecoEntrega (id_pedido, id_endereco)
VALUES (8, 6);  -- endereço Gonzaga (Felipe)

INSERT INTO Pagamento (valor, statusPagamento, dataPagamento, id_pedido)
VALUES (250.00, 'PAGAMENTO_PENDENTE', NOW(), 8);

INSERT INTO Cartao (id, titular, numero, dataValidade, cvv)
VALUES (8, 'Felipe Silva', '4222222222222222', DATE_ADD(CURDATE(), INTERVAL 1 YEAR), '456');

INSERT INTO ItemPedido (id_pedido, id_televisao, preco, quantidade)
VALUES (8, 3, 250.00, 1);


-- Pedido 9 (Felipe)
INSERT INTO Pedido (dataPedido, valorTotal, statusPedido, id_usuario)
VALUES (NOW(), 300.00, 'CANCELADO', 3);

INSERT INTO EnderecoEntrega (id_pedido, id_endereco)
VALUES (9, 4);  -- endereço Centro (Felipe)

INSERT INTO Pagamento (valor, statusPagamento, dataPagamento, id_pedido)
VALUES (300.00, 'PAGAMENTO_PENDENTE', NOW(), 9);

INSERT INTO Cartao (id, titular, numero, dataValidade, cvv)
VALUES (9, 'Felipe Silva', '4333333333333333', DATE_ADD(CURDATE(), INTERVAL 2 YEAR), '789');

INSERT INTO ItemPedido (id_pedido, id_televisao, preco, quantidade)
VALUES (9, 4, 300.00, 1);


-- Pedido 10 (Felipe)
INSERT INTO Pedido (dataPedido, valorTotal, statusPedido, id_usuario)
VALUES (NOW(), 350.00, 'ENTREGUE', 3);

INSERT INTO EnderecoEntrega (id_pedido, id_endereco)
VALUES (10, 5);  -- endereço Cambuí (Felipe)

INSERT INTO Pagamento (valor, statusPagamento, dataPagamento, id_pedido)
VALUES (350.00, 'PAGAMENTO_EFETUADO', NOW(), 10);

INSERT INTO Pix (id, chave)
VALUES (10, 'pix-felipe-002');

INSERT INTO ItemPedido (id_pedido, id_televisao, preco, quantidade)
VALUES (10, 5, 350.00, 1);




