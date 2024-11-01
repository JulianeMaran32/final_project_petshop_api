-- Buscar usuários e listar por Roles
-- Realiza a junção das tabelas users, user_roles e roles para listar os usuários por Roles
USE petshop;
SELECT u.id, u.name, u.email, r.name AS role_name
    FROM users u
    JOIN user_roles ur ON u.id = ur.user_id
    JOIN roles r ON ur.role_id = r.id
    ORDER BY r.name, u.name;