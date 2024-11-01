-- Exemplo de View: Combina informações das tabelas Users e Addresses
USE petshop;
CREATE VIEW user_details AS
SELECT u.id, u.name, u.email, a.street, a.city, a.state, a.country
FROM users u
JOIN addresses a ON u.id = a.user_id;

-- combina informações de usuários e seus endereços, facilitando a consulta para realizar entregas.
USE petshop;
CREATE VIEW user_delivery_info AS
SELECT
    u.id AS user_id,
    u.name AS user_name,
    u.email AS user_email,
    u.phone AS user_phone,
    a.street,
    a.complement,
    a.unit,
    a.neighborhood,
    a.city,
    a.state,
    a.country,
    a.zip_code
FROM
    users u
LEFT JOIN
    addresses a ON u.id = a.user_id;

-- Cria uma view para Pet
USE petshop;
CREATE VIEW pet_view AS
     SELECT p.id, p.name, p.species, p.gender, p.castrated
     FROM pets p;

-- Criação da VIEW para listar todos os usuários ativos
USE petshop;
CREATE VIEW active_users AS
    SELECT * FROM users
    WHERE enabled = TRUE;
