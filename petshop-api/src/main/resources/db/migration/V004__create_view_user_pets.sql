USE petshop;
-- Criação da view para listar os pets de um usuário com permissão CUSTOMER
CREATE VIEW user_pets AS
SELECT
    p.id,
    p.name,
    p.species,
    p.breed,
    p.birth_date,
    p.color,
    p.size,
    p.gender,
    p.castrated,
    p.weight,
    p.health_history,
    p.user_id,
    p.created_by,
    p.created_by_role,
    p.created_date,
    p.updated_by,
    p.updated_by_role,
    p.updated_date,
    p.last_accessed_date
FROM
    pets p
JOIN
    users u ON p.user_id = u.id
WHERE
    u.created_by_role = 'CUSTOMER';