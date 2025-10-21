-- USUÁRIO ADM: 'gabriel', '123456', 'adm'
-- USUÁRIO USER: 'italo', '123456', 'user'
-- USUÁRIO ADM: 'gabriel', '123456', 'adm'
-- USUÁRIO USER: 'italo', '123456', 'user'
-- USUÁRIO ADM: 'gabriel', '123456', 'adm'
-- USUÁRIO USER: 'italo', '123456', 'user'
-- USUÁRIO ADM: 'gabriel', '123456', 'adm'
-- USUÁRIO USER: 'italo', '123456', 'user'
-- USUÁRIO ADM: 'gabriel', '123456', 'adm'
-- USUÁRIO USER: 'italo', '123456', 'user'
-- USUÁRIO ADM: 'gabriel', '123456', 'adm'
-- USUÁRIO USER: 'italo', '123456', 'user'

-- PESSOA JURÍDICA PARA FABRICANTE
INSERT INTO PessoaJuridica (id, razaoSocial, cnpj, status) VALUES
(1, 'Samsung Electronics', '12345678000199', true),
(2, 'LG Electronics', '98765432000188', true),
(3, 'Sony Corporation', '45678912000177', true),
(4, 'Panasonic Corporation', '65432198000166', true),
(5, 'Philips N.V.', '32165487000155', true);

-- FABRICANTE
INSERT INTO Fabricante (id, anoFundacao, paisSede) VALUES
(1, '1938-03-01', 'Coreia do Sul'),
(2, '1958-10-01', 'Coreia do Sul'),
(3, '1946-05-07', 'Japão'),
(4, '1918-03-13', 'Japão'),
(5, '1891-05-15', 'Holanda');

-- TELEFONE PARA FABRICANTE
INSERT INTO Telefone(ddd, numero, fabricante_id) VALUES
('63', '123456789', 1),
('66', '985275291', 1),
('12', '987654321', 2),
('11', '909090909', 2);

-- PESSOA JURÍDICA PARA FORNECEDOR
INSERT INTO PessoaJuridica (id, razaoSocial, cnpj, status) VALUES
(6, 'FastShop Eletrônicos S.A.', '11222333000144', true),
(7, 'Magazine Luiza S/A', '44555666000177', true),
(8, 'Via Varejo S/A', '77888999000100', true),
(9, 'B2W Digital', '00111222000133', true),
(10, 'Importadora Eletro XYZ Ltda', '12121212000155', true);

-- FORNECEDOR
INSERT INTO Fornecedor (id, email) VALUES
(6, 'compras@fastshop.com.br'),
(7, 'parceiros@magalu.com.br'),
(8, 'contato@via.com.br'),
(9, 'comercial@b2w.digital'),
(10, 'import@eletroxyz.com');

INSERT INTO Telefone (ddd, numero, id_fornecedor) VALUES
('11', '111111111', 6), -- Telefone do Fornecedor 6 (FastShop)
('11', '222222222', 6), -- Outro telefone do Fornecedor 6
('16', '333333333', 7), -- Telefone do Fornecedor 7 (Magalu)
('11', '444444444', 8), -- Telefone do Fornecedor 8 (Via Varejo)
('21', '555555555', 9), -- Telefone do Fornecedor 9 (B2W)
('41', '666666666', 10); -- Telefone do Fornecedor 10 (Importadora XYZ)

-- MARCA
INSERT INTO Marca (id, nomemarca, descricao, id_fabricante) VALUES
(1, 'Samsung Electronics', 'Marca principal de TVs e eletrônicos da Samsung', 1),
(2, 'LG Electronics', 'Marca principal de TVs e eletrônicos da LG', 2),
(3, 'Sony', 'Marca de eletrônicos, incluindo TVs Bravia', 3),
(4, 'Panasonic', 'Marca de eletrônicos, incluindo TVs Viera', 4),
(5, 'Philips', 'Marca de eletrônicos e TVs (agora licenciada)', 5);

INSERT INTO fornecedor_marca (fornecedor_id, marca_id) VALUES
-- Fornecedor 6 (FastShop) vende Samsung, LG e Sony
(6, 1),
(6, 2),
(6, 3),
-- Fornecedor 7 (Magazine Luiza) vende Samsung, LG, Sony e Philips
(7, 1),
(7, 2),
(7, 3),
(7, 5),
-- Fornecedor 8 (Via Varejo) vende Samsung, LG e Panasonic
(8, 1),
(8, 2),
(8, 4),
-- Fornecedor 9 (B2W Digital) vende todas as 5 marcas
(9, 1),
(9, 2),
(9, 3),
(9, 4),
(9, 5),
-- Fornecedor 10 (Importadora XYZ) vende apenas Sony e Panasonic
(10, 3),
(10, 4);

-- CARACTERÍSTICAS GERAIS PARA MODELO
INSERT INTO CaracteristicasGerais (id, sistemaOperacional, quantidadeHDMI, quantidadeUSB, smartTV) VALUES
(1, 'Tizen', 4, 2, true),
(2, 'WebOS', 4, 2, true),
(3, 'Google TV', 3, 2, true),
(4, 'Básico', 2, 1, false);

--MODELO
INSERT INTO Modelo (id, modelo, mesesgarantia, anolancamento, marca_id, caracteristicas_id) VALUES
-- Modelos da Marca 1 (Samsung)
(1, 'QN90C Neo QLED', 12, '2023-03-10', 1, 1), -- Usa Tizen (Carac 1)
(2, 'Crystal UHD CU8000', 12, '2023-04-15', 1, 1), -- Usa Tizen (Carac 1)

-- Modelos da Marca 2 (LG)
(3, 'OLED C3 Evo', 12, '2023-03-15', 2, 2), -- Usa WebOS (Carac 2)
(4, 'QNED80 4K', 12, '2023-05-01', 2, 2), -- Usa WebOS (Carac 2)

-- Modelos da Marca 3 (Sony)
(5, 'Bravia X90L', 12, '2023-04-20', 3, 3), -- Usa Google TV (Carac 3)
(6, 'Bravia X75L', 12, '2023-06-01', 3, 3), -- Usa Google TV (Carac 3)

-- Modelos da Marca 4 (Panasonic)
(7, 'Viera MX800', 12, '2023-05-01', 4, 3), -- Usa Google TV (Carac 3)
(8, 'Viera M300 (Não-Smart)', 6, '2022-10-01', 4, 4), -- Usa Básico (Carac 4)

-- Modelos da Marca 5 (Philips)
(9, 'The One 8808 (Ambilight)', 12, '2023-04-01', 5, 3), -- Usa Google TV (Carac 3)
(10, 'Série 5000 LED (Não-Smart)', 6, '2022-11-15', 5, 4);

-- DIMENSÃO
INSERT INTO Dimensao (id, comprimento, altura, polegada) VALUES
(1, 75, 45, 32),
(2, 90, 55, 40),
(3, 100, 60, 43),
(4, 120, 70, 50),
(5, 135, 80, 55),
(6, 160, 95, 65),
(7, 180, 105, 75);

-- TELEVISÃO
INSERT INTO Televisao (id, valor, resolucao, tipoTela, estoque, descricao, id_modelo, id_dimensao) VALUES
-- TVs do Modelo 1 (Samsung QN90C)
(1, 4599.99, 'UHD_4K', 'QLED', 50, 'TV Samsung 55 Polegadas QN90C Neo QLED 4K', 1, 5),
(2, 6899.99, 'UHD_4K', 'QLED', 30, 'TV Samsung 65 Polegadas QN90C Neo QLED 4K', 1, 6),

-- TVs do Modelo 2 (Samsung CU8000)
(3, 2199.99, 'UHD_4K', 'LED', 100, 'TV Samsung 50 Polegadas Crystal UHD CU8000 4K', 2, 4),
(4, 2499.99, 'UHD_4K', 'LED', 80, 'TV Samsung 55 Polegadas Crystal UHD CU8000 4K', 2, 5),
(5, 3199.99, 'UHD_4K', 'LED', 60, 'TV Samsung 65 Polegadas Crystal UHD CU8000 4K', 2, 6),

-- TVs do Modelo 3 (LG OLED C3)
(6, 4899.99, 'UHD_4K', 'OLED', 45, 'TV LG 55 Polegadas OLED C3 Evo 4K', 3, 5),
(7, 7499.99, 'UHD_4K', 'OLED', 25, 'TV LG 65 Polegadas OLED C3 Evo 4K', 3, 6),

-- TVs do Modelo 4 (LG QNED80)
(8, 3099.99, 'UHD_4K', 'QLED', 70, 'TV LG 50 Polegadas QNED80 4K', 4, 4),
(9, 3599.99, 'UHD_4K', 'QLED', 50, 'TV LG 55 Polegadas QNED80 4K', 4, 5),

-- TVs do Modelo 5 (Sony X90L)
(10, 5999.99, 'UHD_4K', 'LED', 30, 'TV Sony 55 Polegadas Bravia X90L 4K Full Array', 5, 5),
(11, 8499.99, 'UHD_4K', 'LED', 20, 'TV Sony 65 Polegadas Bravia X90L 4K Full Array', 5, 6),

-- TVs do Modelo 6 (Sony X75L)
(12, 2699.99, 'UHD_4K', 'LED', 60, 'TV Sony 50 Polegadas Bravia X75L 4K', 6, 4),
(13, 3099.99, 'UHD_4K', 'LED', 50, 'TV Sony 55 Polegadas Bravia X75L 4K', 6, 5),

-- TVs do Modelo 7 (Panasonic MX800)
(14, 2799.99, 'UHD_4K', 'LED', 40, 'TV Panasonic 55 Polegadas Viera MX800 4K', 7, 5),
(15, 3499.99, 'UHD_4K', 'LED', 30, 'TV Panasonic 65 Polegadas Viera MX800 4K', 7, 6),

-- TVs do Modelo 8 (Panasonic M300)
(16, 1699.99, 'FULL_HD', 'LED', 80, 'TV Panasonic 43 Polegadas Viera M300 (Não-Smart)', 8, 3),

-- TVs do Modelo 9 (Philips The One)
(17, 2899.99, 'UHD_4K', 'LED', 55, 'TV Philips 55 Polegadas The One 8808 Ambilight 4K', 9, 5),
(18, 3699.99, 'UHD_4K', 'LED', 35, 'TV Philips 65 Polegadas The One 8808 Ambilight 4K', 9, 6),

-- TVs do Modelo 10 (Philips Série 5000)
(19, 1499.99, 'FULL_HD', 'LED', 90, 'TV Philips 43 Polegadas Série 5000 (Não-Smart)', 10, 3),
(20, 1899.99, 'FULL_HD', 'LED', 70, 'TV Philips 50 Polegadas Série 5000 (Não-Smart)', 10, 4);

-- ESTADO
INSERT INTO Estado (nome, sigla, regiao) VALUES
('São Paulo', 'SP', 'SUDESTE'),
('Rio de Janeiro', 'RJ', 'SUDESTE'),
('Minas Gerais', 'MG', 'CENTRO_OESTE'),
('Bahia', 'BA', 'NORDESTE'),
('Paraná', 'PR', 'SUL');

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

-- INSERT INTO Usuario (username, senha, perfil, cpf)
-- VALUES ('felipe', 'SiM9w9cv/QHp+fZSykTmN52bUoj++hlYrZoet0hxU8eajwrdo6L5hmWoOm96rYeFQ1YyMKBKLuRE05aC5FKL/Q==', 'USER', '03518783190');

INSERT INTO Endereco (cep, bairro, numero, complemento, id_municipio, id_usuario) VALUES
-- Endereços do usuário 1 (italo)
('01001000', 'Sé', 100, 'Edifício Comercial', 1, 2),
('01311000', 'Bela Vista', 200, 'Apartamento 302', 1, 2),
('01415000', 'Cerqueira César', 300, 'Sala 501', 1, 2);

-- -- Endereços do usuário 2 (felipe)
-- ('13010000', 'Centro', 400, 'Casa Azul', 2, 3),
-- ('13010000', 'Cambuí', 500, 'Loja 10', 2, 3),
-- ('11015000', 'Gonzaga', 600, 'Cobertura', 3, 3);

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
VALUES (3, 'Italo Ribeiro', '4111111111111111', '2026-10-21', '123');

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

-- -- Pedido 6 (Felipe)
-- INSERT INTO Pedido (dataPedido, valorTotal, statusPedido, id_usuario)
-- VALUES (NOW(), 150.00, 'PEDIDO_EM_PROCESSO', 3);
--
-- INSERT INTO EnderecoEntrega (id_pedido, id_endereco)
-- VALUES (6, 4);  -- endereço Centro (Felipe)
--
-- INSERT INTO Pagamento (valor, statusPagamento, dataPagamento, id_pedido)
-- VALUES (150.00, 'PAGAMENTO_PENDENTE', NOW(), 6);
--
-- INSERT INTO Pix (id, chave)
-- VALUES (6, 'pix-felipe-001');
--
-- INSERT INTO ItemPedido (id_pedido, id_televisao, preco, quantidade)
-- VALUES (6, 1, 150.00, 1);
--
--
-- -- Pedido 7 (Felipe)
-- INSERT INTO Pedido (dataPedido, valorTotal, statusPedido, id_usuario)
-- VALUES (NOW(), 200.00, 'PEDIDO_EM_PROCESSO', 3);
--
-- INSERT INTO EnderecoEntrega (id_pedido, id_endereco)
-- VALUES (7, 5);  -- endereço Cambuí (Felipe)
--
-- INSERT INTO Pagamento (valor, statusPagamento, dataPagamento, id_pedido)
-- VALUES (200.00, 'PAGAMENTO_PENDENTE', NOW(), 7);
--
-- INSERT INTO Boleto (id, codigoBarras)
-- VALUES (7, '23793.38127 60007.439370 78000.421010 6 83580000020000');
--
-- INSERT INTO ItemPedido (id_pedido, id_televisao, preco, quantidade)
-- VALUES (7, 2, 200.00, 1);
--
--
-- -- Pedido 8 (Felipe)
-- INSERT INTO Pedido (dataPedido, valorTotal, statusPedido, id_usuario)
-- VALUES (NOW(), 250.00, 'PEDIDO_EM_PROCESSO', 3);
--
-- INSERT INTO EnderecoEntrega (id_pedido, id_endereco)
-- VALUES (8, 6);  -- endereço Gonzaga (Felipe)
--
-- INSERT INTO Pagamento (valor, statusPagamento, dataPagamento, id_pedido)
-- VALUES (250.00, 'PAGAMENTO_PENDENTE', NOW(), 8);
--
-- INSERT INTO Cartao (id, titular, numero, dataValidade, cvv)
-- VALUES (8, 'Felipe Silva', '4222222222222222', DATE_ADD(CURDATE(), INTERVAL 1 YEAR), '456');
--
-- INSERT INTO ItemPedido (id_pedido, id_televisao, preco, quantidade)
-- VALUES (8, 3, 250.00, 1);
--
--
-- -- Pedido 9 (Felipe)
-- INSERT INTO Pedido (dataPedido, valorTotal, statusPedido, id_usuario)
-- VALUES (NOW(), 300.00, 'CANCELADO', 3);
--
-- INSERT INTO EnderecoEntrega (id_pedido, id_endereco)
-- VALUES (9, 4);  -- endereço Centro (Felipe)
--
-- INSERT INTO Pagamento (valor, statusPagamento, dataPagamento, id_pedido)
-- VALUES (300.00, 'PAGAMENTO_PENDENTE', NOW(), 9);
--
-- INSERT INTO Cartao (id, titular, numero, dataValidade, cvv)
-- VALUES (9, 'Felipe Silva', '4333333333333333', DATE_ADD(CURDATE(), INTERVAL 2 YEAR), '789');
--
-- INSERT INTO ItemPedido (id_pedido, id_televisao, preco, quantidade)
-- VALUES (9, 4, 300.00, 1);
--
--
-- -- Pedido 10 (Felipe)
-- INSERT INTO Pedido (dataPedido, valorTotal, statusPedido, id_usuario)
-- VALUES (NOW(), 350.00, 'ENTREGUE', 3);
--
-- INSERT INTO EnderecoEntrega (id_pedido, id_endereco)
-- VALUES (10, 5);  -- endereço Cambuí (Felipe)
--
-- INSERT INTO Pagamento (valor, statusPagamento, dataPagamento, id_pedido)
-- VALUES (350.00, 'PAGAMENTO_EFETUADO', NOW(), 10);
--
-- INSERT INTO Pix (id, chave)
-- VALUES (10, 'pix-felipe-002');
--
-- INSERT INTO ItemPedido (id_pedido, id_televisao, preco, quantidade)
-- VALUES (10, 5, 350.00, 1);
--



