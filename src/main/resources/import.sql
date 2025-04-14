-- FABRICANTE
INSERT INTO Fabricante (nome, cnpj, paisSede) VALUES
('Samsung', '12345678000199', 'Coreia do Sul'),
('LG', '98765432000188', 'Coreia do Sul'),
('Sony', '45678912000177', 'Japão'),
('Panasonic', '65432198000166', 'Japão'),
('Philips', '32165487000155', 'Holanda');



-- TELEFONE
INSERT INTO Telefone(ddd, numero, fabricante_id) VALUES
('63', '123456789', 2),
('66', '985275291', 2),
('12', '987654321', 3),
('11', '909090909', 3);



-- TELEVISAO
INSERT INTO Televisao (marca, modelo, polegada, resolucao, tipoTela, id_fabricante) VALUES
('LG', 'OLED Evo', 65, '4K', 'OLED', 2),
('Sony', 'Bravia XR', 75, '8K', 'LED', 3),
('Philips', 'Ambilight', 50, 'Full HD', 'LCD', 5),
('Panasonic', 'Viera', 42, 'HD', 'PLASMA', 4),
('Samsung', 'QLED Ultra HD', 55, '4K', 'QLED', 1),
('LG', 'OLED Ultra HD', 65, '8K', 'OLED', 2),
('Sony', 'Bravia QLED', 75, '4K', 'QLED', 3);













