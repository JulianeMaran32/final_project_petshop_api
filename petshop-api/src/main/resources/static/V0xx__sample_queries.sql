-- Busca Simples por Nome de Produto
USE petshop;
SELECT * FROM products WHERE name LIKE '%nome_do_produto%';

-- Busca complexa por Produtos com filtros multiplos
USE petshop;
SELECT * FROM products
WHERE category = 'categoria_exemplo'
  AND price BETWEEN 10.00 AND 50.00
  AND quantity_in_stock > 0;

-- Functions
SELECT total_purchase_value(1) AS total_value;