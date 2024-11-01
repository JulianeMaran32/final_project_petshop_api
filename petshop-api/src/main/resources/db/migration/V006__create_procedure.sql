-- Procedimento para ativar usuário ao fazer login
DELIMITER //
CREATE PROCEDURE activate_user(IN user_email VARCHAR(150))
BEGIN
    UPDATE users
    SET enabled = TRUE
    WHERE email = user_email;
END //
DELIMITER ;

-- Procedimento para buscar usuários com filtros opcionais
USE petshop;
DELIMITER //
CREATE PROCEDURE search_users(
    IN p_name VARCHAR(150),
    IN p_cpf CHAR(14),
    IN p_email VARCHAR(150),
    IN p_phone VARCHAR(14)
)
BEGIN
    SELECT * FROM active_users
    WHERE (p_name IS NULL OR name LIKE CONCAT('%', p_name, '%'))
      AND (p_cpf IS NULL OR cpf = p_cpf)
      AND (p_email IS NULL OR email = p_email)
      AND (p_phone IS NULL OR phone = p_phone);
END //
DELIMITER ;