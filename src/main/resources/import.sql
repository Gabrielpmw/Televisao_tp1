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

INSERT INTO PessoaJuridica (id, razaoSocial, cnpj, status) VALUES
-- FABRICANTES (1–18)
(1, 'Samsung Electronics', '12345678000199', true),
(2, 'LG Electronics', '98765432000188', true),
(3, 'Sony Corporation', '45678912000177', true),
(4, 'Panasonic Corporation', '65432198000166', true),
(5, 'Philips N.V.', '32165487000155', true),
(6, 'TCL Technology', '11223344000101', true),
(7, 'Hisense Group', '22334455000112', true),
(8, 'Sharp Corporation', '33445566000123', true),
(9, 'Vizio Inc.', '44556677000134', true),
(10, 'Toshiba Corporation', '55667788000145', true),
(11, 'Semp S.A.', '66778899000156', true),
(12, 'AOC International', '77889900000167', true),
(13, 'Philco (licenciada Britânia)', '88990011000178', true),
(14, 'Xiaomi Corporation', '99887766000122', true),
(15, 'Haier Group Corporation', '55443322000199', true),
(16, 'JVCKenwood Corporation', '66778855000111', true),
(17, 'Hitachi Ltd.', '33221144000155', true),
(18, 'Britânia S.A.', '77445566000133', true),

-- FORNECEDORES (19–36)
(19, 'FastShop Eletrônicos S.A.', '11222333000144', true),
(20, 'Magazine Luiza S/A', '44555666000177', true),
(21, 'Via Varejo S/A', '77888999000100', true),
(22, 'B2W Digital', '00111222000133', true),
(23, 'Importadora Eletro XYZ Ltda', '12121212000155', true),
(24, 'Kabum Comércio Eletrônico S/A', '99887766000122', true),
(25, 'Ponto Frio S/A', '55443322000199', true),
(26, 'Casas Bahia Ltda', '66778855000111', true),
(27, 'Submarino S/A', '33221144000155', true),
(28, 'Amazon Brasil Ltda', '77445566000133', true),
(29, 'Mercado Livre do Brasil Ltda', '88667788000144', true),
(30, 'Extra.com.br S/A', '99886655000177', true),
(31, 'Carrefour Comércio e Indústria Ltda', '22331144000188', true),
(32, 'Americanas S/A', '33557788000122', true),
(33, 'ShopTime S/A', '66779900000155', true),
(34, 'Magazine House Imports Ltda', '99884455000177', true),
(35, 'EletroStar Brasil Ltda', '11335577000199', true),
(36, 'NovaTech Global S/A', '77665588000144', true);


-- FABRICANTE
INSERT INTO Fabricante (id, anoFundacao, paisSede) VALUES
(1, '1938-03-01', 'Coreia do Sul'), -- Samsung
(2, '1958-10-01', 'Coreia do Sul'), -- LG
(3, '1946-05-07', 'Japão'),         -- Sony
(4, '1918-03-13', 'Japão'),         -- Panasonic
(5, '1891-05-15', 'Holanda'),       -- Philips
(6, '1981-01-01', 'China'),         -- TCL
(7, '1969-09-01', 'China'),         -- Hisense
(8, '1912-09-15', 'Japão'),         -- Sharp
(9, '2002-10-01', 'Estados Unidos'),-- Vizio
(10, '1875-07-01', 'Japão'),        -- Toshiba
(11, '1942-01-01', 'Brasil'),       -- Semp
(12, '1967-01-01', 'Taiwan'),       -- AOC
(13, '1892-01-01', 'Brasil (Licença)'), -- Philco
(14, '2010-04-06', 'China'),        -- Xiaomi
(15, '1984-12-26', 'China'),        -- Haier
(16, '2008-10-01', 'Japão'),        -- JVCKenwood
(17, '1910-01-01', 'Japão'),        -- Hitachi
(18, '1960-01-01', 'Brasil');       -- Britânia

-- FORNECEDOR
INSERT INTO Fornecedor (id, email) VALUES
                                       (19, 'compras@fastshop.com.br'),
                                       (20, 'parceiros@magalu.com.br'),
                                       (21, 'contato@via.com.br'),
                                       (22, 'comercial@b2w.digital'),
                                       (23, 'import@eletroxyz.com'),
                                       (24, 'vendas@kabum.com.br'),
                                       (25, 'contato@pontofrio.com.br'),
                                       (26, 'suporte@casasbahia.com.br'),
                                       (27, 'atendimento@submarino.com'),
                                       (28, 'parceiros@amazon.com.br'),
                                       (29, 'suporte@mercadolivre.com.br'),
                                       (30, 'vendas@extra.com.br'),
                                       (31, 'comercial@carrefour.com.br'),
                                       (32, 'relacionamento@americanas.com.br'),
                                       (33, 'contato@shoptime.com.br'),
                                       (34, 'compras@magazinehouse.com'),
                                       (35, 'vendas@eletrostar.com.br'),
                                       (36, 'contato@novatech.com.br');

-- TELEFONE PARA FABRICANTE
INSERT INTO Telefone(ddd, numero, fabricante_id) VALUES
('11', '987654321', 1), -- Samsung
('11', '912345678', 1),
('11', '234567890', 2), -- LG
('11', '988776655', 2),
('81', '33334444', 3),  -- Sony
('81', '944445555', 3),
('81', '955556666', 3),
('71', '66667777', 4),  -- Panasonic
('71', '977778888', 4),
('21', '88889999', 5),  -- Philips
('21', '99990000', 5),
('41', '10101010', 6),  -- Apple
('41', '920202020', 6),
('51', '30303030', 7),  -- Dell
('51', '940404040', 7),
('51', '950505050', 7),
('61', '60606060', 8),  -- HP
('61', '970707070', 8),
('19', '80808080', 9),  -- Lenovo
('19', '990909090', 9),
('19', '919191919', 9),
('31', '21212121', 10), -- Asus
('31', '932323232', 10),
('62', '43434343', 11), -- Acer
('62', '954545454', 11),
('62', '965656565', 11),
('92', '76767676', 12), -- Microsoft
('92', '987878787', 12),
('85', '98989898', 13), -- Huawei
('85', '998989898', 13),
('63', '11112222', 14), -- Multilaser (BR)
('63', '922223333', 14),
('63', '933334444', 14),
('48', '55556666', 15), -- Intelbras (BR)
('48', '966667777', 15),
('27', '88881111', 16), -- Semp (BR)
('27', '911112222', 16),
('98', '33339999', 17), -- Positivo (BR)
('98', '944448888', 17),
('98', '955557777', 17),
('79', '66662222', 18); -- Tectoy (BR)


-- TELEFONE PARA FORNECEDOR
INSERT INTO Telefone (ddd, numero, id_fornecedor) VALUES
('11', '30031020', 19), -- FastShop
('11', '910203040', 19),
('16', '34042000', 20), -- Magalu
('16', '981112001', 20),
('11', '40042002', 20),
('11', '40034336', 21), -- Via
('21', '40035544', 22), -- B2W
('21', '988775544', 22),
('41', '33221144', 23), -- Eletroxyz
('19', '40035432', 24), -- Kabum
('19', '991234567', 24),
('11', '40038388', 25), -- Ponto Frio
('11', '40034033', 26), -- Casas Bahia
('11', '988884033', 26),
('21', '40035545', 27), -- Submarino
('11', '40041001', 28), -- Amazon
('11', '910012002', 28),
('11', '40041002', 28),
('11', '40043030', 29), -- Mercado Livre
('11', '930304040', 29),
('11', '40033383', 30), -- Extra
('11', '30032099', 31), -- Carrefour
('11', '920993003', 31),
('21', '40034848', 32), -- Americanas
('21', '40031021', 33), -- Shoptime
('21', '910213041', 33),
('61', '33445566', 34), -- Magazine House
('61', '955667788', 34),
('71', '30013002', 35), -- Eletrostar
('71', '930023003', 35),
('71', '930033004', 35),
('81', '32001000', 36), -- Novatech
('81', '910002000', 36);


-- MARCA
INSERT INTO Marca ( nomemarca, descricao, id_fabricante) VALUES
-- 1. Samsung
( 'Samsung', 'Marca principal de TVs e eletrônicos', 1),
( 'QLED', 'Linha de TVs premium da Samsung', 1),
( 'Crystal UHD', 'Linha de TVs 4K de entrada da Samsung', 1),
-- 2. LG
( 'LG', 'Marca principal de TVs e eletrônicos', 2),
( 'OLED evo', 'Linha de TVs OLED premium da LG', 2),
( 'QNED', 'Linha de TVs Mini LED da LG', 2),
-- 3. Sony
( 'Sony', 'Marca principal de eletrônicos', 3),
( 'Bravia', 'Marca de TVs da Sony', 3),
-- 4. Panasonic
( 'Panasonic', 'Marca principal de eletrônicos', 4),
( 'Viera', 'Antiga marca de TVs da Panasonic', 4),
-- 5. Philips
( 'Philips', 'Marca de TVs (licenciada pela TP Vision)', 5),
( 'Ambilight', 'Tecnologia de iluminação ambiente da Philips', 5),
-- 6. TCL
( 'TCL', 'Marca principal de TVs', 6),
( 'Mini LED', 'Linha de TVs premium da TCL', 6),
-- 7. Hisense
( 'Hisense', 'Marca principal de TVs', 7),
( 'ULED', 'Linha de TVs premium da Hisense', 7),
( 'Laser TV', 'Projetores de curta distância da Hisense', 7),
-- 8. Sharp
( 'Sharp', 'Marca principal de TVs', 8),
( 'Aquos', 'Linha de TVs da Sharp', 8),
-- 9. Vizio
( 'Vizio', 'Marca de TVs popular nos EUA', 9),
( 'SmartCast', 'Plataforma de Smart TV da Vizio', 9),
-- 10. Toshiba
( 'Toshiba', 'Marca de TVs (licenciada, muitas vezes pela Hisense)', 10),
( 'Regza', 'Antiga marca de TVs premium da Toshiba', 10),
-- 11. Semp
( 'Semp', 'Marca de eletrônicos Semp', 11),
( 'Semp TCL', 'Co-branding com a TCL no Brasil', 11),
-- 12. AOC
( 'AOC', 'Marca de monitores e TVs', 12),
( 'Roku TV', 'Parceria para TVs com sistema Roku', 12),
-- 13. Philco
( 'Philco', 'Marca de TVs e eletrônicos (Brasil)', 13),
( 'Hitachi', 'Marca de TVs (licenciada pela Philco no Brasil)', 13),
-- 14. Xiaomi
( 'Xiaomi', 'Marca principal de eletrônicos', 14),
( 'Mi TV', 'Marca de TVs da Xiaomi', 14),
( 'Redmi TV', 'Marca de TVs de entrada da Xiaomi', 14),
-- 15. Haier
( 'Haier', 'Marca principal de eletrodomésticos e TVs', 15),
( 'Candy', 'Marca do grupo Haier', 15),
-- 16. JVC
( 'JVC', 'Marca de TVs e áudio (do grupo JVCKenwood)', 16),
( 'Kenwood', 'Marca de áudio (do grupo JVCKenwood)', 16),
-- 17. Hitachi
( 'Hitachi', 'Marca principal de eletrônicos (licenciada para outras)', 17),
-- 18. Britânia
( 'Britânia', 'Marca de eletrodomésticos e TVs (Brasil)', 18);

-- MARCAS PARA FORNECEDORES
INSERT INTO fornecedor_marca (fornecedor_id, marca_id) VALUES
-- Fornecedor 19 (FastShop) - Vende 3 marcas premium
(19, 1), -- Samsung
(19, 4), -- LG
(19, 6), -- Sony

-- Fornecedor 20 (Magazine Luiza) - Vende 4 marcas populares
(20, 1), -- Samsung
(20, 4), -- LG
(20, 12), -- TCL
(20, 10), -- Philips

-- Fornecedor 21 (Via Varejo) - Vende 3 marcas
(21, 1), -- Samsung
(21, 4), -- LG
(21, 24), -- Philco

-- Fornecedor 22 (B2W Digital) - Vende 4 marcas
(22, 1), -- Samsung
(22, 6), -- Sony
(22, 8), -- Panasonic
(22, 10), -- Philips

-- Fornecedor 23 (Importadora Eletro XYZ) - Vende 2 marcas (nicho)
(23, 14), -- Hisense
(23, 17), -- Sharp

-- Fornecedor 24 (Kabum) - Vende 3 marcas
(24, 1), -- Samsung
(24, 4), -- LG
(24, 22), -- AOC (popular na Kabum)

-- Fornecedor 25 (Ponto Frio) - Vende 3 marcas
(25, 1), -- Samsung
(25, 4), -- LG
(25, 12), -- TCL

-- Fornecedor 26 (Casas Bahia) - Vende 3 marcas
(26, 1), -- Samsung
(26, 4), -- LG
(26, 21), -- Semp

-- Fornecedor 27 (Submarino) - Vende 3 marcas
(27, 6), -- Sony
(27, 8), -- Panasonic
(27, 14), -- Hisense

-- Fornecedor 28 (Amazon) - Vende 4 marcas
(28, 1), -- Samsung
(28, 4), -- LG
(28, 12), -- TCL
(28, 14), -- Hisense

-- Fornecedor 29 (Mercado Livre) - Vende 4 marcas
(29, 1), -- Samsung
(29, 4), -- LG
(29, 24), -- Philco
(29, 28), -- Britânia

-- Fornecedor 30 (Extra.com.br) - Vende 3 marcas
(30, 1), -- Samsung
(30, 10), -- Philips
(30, 21), -- Semp

-- Fornecedor 31 (Carrefour) - Vende 2 marcas
(31, 1), -- Samsung
(31, 4), -- LG

-- Fornecedor 32 (Americanas) - Vende 4 marcas
(32, 1), -- Samsung
(32, 4), -- LG
(32, 10), -- Philips
(32, 12), -- TCL

-- Fornecedor 33 (ShopTime) - Vende 3 marcas
(33, 1), -- Samsung
(33, 22), -- AOC
(33, 24), -- Philco

-- Fornecedor 34 (Magazine House Imports) - Vende 2 marcas (nicho)
(34, 8), -- Panasonic
(34, 17), -- Sharp

-- Fornecedor 35 (EletroStar Brasil) - Vende 3 marcas
(35, 12), -- TCL
(35, 14), -- Hisense
(35, 21), -- Semp

-- Fornecedor 36 (NovaTech Global) - Vende 1 marca (nicho)
(36, 22); -- AOC

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


SELECT setval(pg_get_serial_sequence('pessoajuridica', 'id'), (SELECT MAX(id) FROM pessoajuridica), true);
SELECT setval(pg_get_serial_sequence('marca', 'id'), (SELECT MAX(id) FROM marca), true);
SELECT setval(pg_get_serial_sequence('caracteristicasgerais', 'id'), (SELECT MAX(id) FROM caracteristicasgerais), true);
SELECT setval(pg_get_serial_sequence('modelo', 'id'), (SELECT MAX(id) FROM modelo), true);
SELECT setval(pg_get_serial_sequence('dimensao', 'id'), (SELECT MAX(id) FROM dimensao), true);
SELECT setval(pg_get_serial_sequence('televisao', 'id'), (SELECT MAX(id) FROM televisao), true);
SELECT setval(pg_get_serial_sequence('estado', 'id'), (SELECT MAX(id) FROM estado), true);
SELECT setval(pg_get_serial_sequence('municipio', 'id'), (SELECT MAX(id) FROM municipio), true);
SELECT setval(pg_get_serial_sequence('funcionario', 'id'), (SELECT MAX(id) FROM funcionario), true);
SELECT setval(pg_get_serial_sequence('usuario', 'id'), (SELECT MAX(id) FROM usuario), true);
SELECT setval(pg_get_serial_sequence('endereco', 'id'), (SELECT MAX(id) FROM endereco), true);
SELECT setval(pg_get_serial_sequence('pedido', 'id'), (SELECT MAX(id) FROM pedido), true);
SELECT setval(pg_get_serial_sequence('pagamento', 'id'), (SELECT MAX(id) FROM pagamento), true);
SELECT setval(pg_get_serial_sequence('telefone', 'id'), (SELECT MAX(id) FROM telefone), true);
SELECT setval(pg_get_serial_sequence('enderecoentrega', 'id'), (SELECT MAX(id) FROM enderecoentrega), true);
SELECT setval(pg_get_serial_sequence('itempedido', 'id'), (SELECT MAX(id) FROM itempedido), true);