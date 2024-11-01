-- O evento deve ser executado diariamente para verificar e inativar usuários que não fizeram login nos últimos 7 dias
USE petshop;
CREATE EVENT inactivate_users
ON SCHEDULE EVERY 1 DAY
DO
    UPDATE users
    SET enabled = FALSE
    WHERE DATEDIFF(CURRENT_DATE, last_accessed_date) > 7;

-- Verifica se o evento está habilitado no MySQL
SHOW VARIABLES LIKE 'event_scheduler';

-- Ativar o evento, caso esteja inativo:
SET GLOBAL event_scheduler = ON;
