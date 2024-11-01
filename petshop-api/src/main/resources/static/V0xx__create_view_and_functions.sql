-- Quantidade Total de Produtos
USE petshop;
CREATE VIEW total_products AS
SELECT COUNT(*) AS total FROM products;

-- Valor Total de Compras
DELIMITER $$
CREATE FUNCTION total_purchase_value(purchase_id BIGINT)
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE total DECIMAL(10,2);
    SELECT SUM(p.price) INTO total
    FROM purchase_products pp
    JOIN products p ON pp.product_id = p.id
    WHERE pp.purchase_id = purchase_id;
    RETURN total;
END$$
DELIMITER ;