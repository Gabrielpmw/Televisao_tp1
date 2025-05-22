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
INSERT INTO Televisao (marca, modelo, resolucao, tipoTela, id_fabricante, id_dimensao) VALUES
                                                                                               ('LG', 'Crystal HUD', 'HD', 'LED', 1, 1),
                                                                                               ('Samsung', 'Bravia XR', 'FULL_HD', 'OLED', 2, 2),
                                                                                               ('Philips', 'Viera TH', 'FULL_HD', 'PLASMA', 3, 3),
                                                                                               ('Sony', 'OLED Evo', 'FULL_HD', 'OLED', 4, 4),
                                                                                               ('Samsung', 'QLED QN90A', 'HD', 'QLED', 1, 5),
                                                                                               ('LG', 'OLED C1', 'HD', 'OLED', 2, 6),
                                                                                               ('Sony', 'Bravia X90J', 'HD', 'LED', 3, 7),
                                                                                               ('Philips', 'Ambilight OLED806', 'HD', 'OLED', 5, 3);


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



-- ENDERECO
INSERT INTO Endereco (cep, bairro, numero, complemento, id_municipio) VALUES
-- Endereços em São Paulo-SP
('01001000', 'Sé', 100, 'Edifício Commercial', 1),
('01311000', 'Bela Vista', 200, 'Apartamento 302', 1),
('01415000', 'Cerqueira César', 300, 'Sala 501', 1),
-- Endereços em Campinas-SP
('13010000', 'Centro', 400, 'Casa Azul', 2),
('13010000', 'Cambuí', 500, 'Loja 10', 2),
-- Endereços em Santos-SP
('11015000', 'Gonzaga', 600, 'Cobertura', 3),
-- Endereços no Rio de Janeiro-RJ
('20010000', 'Centro', 700, 'Andar 15', 4),
('22010000', 'Copacabana', 800, 'Bloco B', 4),
-- Endereços em Niterói-RJ
('24020010', 'Icaraí', 900, 'Casa 2', 5),
-- Endereços em Belo Horizonte-MG
('30110010', 'Savassi', 1000, 'Sala 201', 7),
-- Endereços em Salvador-BA
('40010010', 'Comércio', 1100, 'Loja 5', 10),
-- Endereços em Curitiba-PR
('80010010', 'Centro', 1200, 'Conjunto 304', 13),
('80060000', 'Batel', 1300, 'Casa dos Fundos', 13);










