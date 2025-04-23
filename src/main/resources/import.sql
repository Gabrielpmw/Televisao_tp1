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



-- TELEFONE
INSERT INTO Telefone(ddd, numero, fabricante_id) VALUES
('63', '123456789', 1),
('66', '985275291', 1),
('12', '987654321', 1),
('11', '909090909', 1);



-- TELEVISAO
INSERT INTO Televisao (marca, modelo, resolucao, tipoTela, id_fabricante, id_dimensao) VALUES
('LG', 'Crystal HUD', '8k', 'LED', 1, 1),
('Samsung', 'Bravia XR', '4k', 'OLED', 2, 2),
('Philips', 'Viera TH', '2k', 'PLASMA', 3, 3),
('Sony', 'OLED Evo', 'full hd', 'OLED', 4, 4);














