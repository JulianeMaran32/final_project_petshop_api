-- Soft Delete
-- Adicionar campo delete_at na tabela Users
USE petshop;
ALTER TABLE users ADD COLUMN deleted_at DATETIME DEFAULT NULL;

-- Trigger para soft delete de usuários inativos por mais de 7 dias
DELIMITER $$
CREATE TRIGGER soft_delete_inactive_users
BEFORE UPDATE ON users
FOR EACH ROW
BEGIN
    IF NEW.enabled = FALSE AND DATEDIFF(NOW(), NEW.temporary_password_expiry_date) > 7 THEN
        SET NEW.deleted_at = NOW();
    END IF;
END$$
DELIMITER ;