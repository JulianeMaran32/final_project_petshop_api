-- Filtra pelo nome 'Rex'
CALL filter_user_pets(3, 'Rex', NULL, NULL, NULL);

-- Filtra por gênero 'Male' e castrado
CALL filter_user_pets(3, NULL, 'Male', TRUE, NULL);

USE petshop;
-- Defina as variáveis de sessão
SET @id = NULL;
SET @name = NULL;
SET @breed = NULL;
SET @species = 'Dog';
SET @gender = 'Male';
SET @created_date = NULL;

-- Consulta com filtros
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
    p.created_date
FROM
    pets p
WHERE
    (@id IS NULL OR p.id = @id) AND
    (@name IS NULL OR p.name LIKE CONCAT('%', @name, '%')) AND
    (@breed IS NULL OR p.breed LIKE CONCAT('%', @breed, '%')) AND
    (@species IS NULL OR p.species = @species) AND
    (@gender IS NULL OR p.gender = @gender) AND
    (@created_date IS NULL OR p.created_date = @created_date);

USE petshop;

-- Defina as variáveis de sessão
SET @user_id = 5;
SET @id = NULL;
SET @name = NULL;
SET @breed = NULL;
SET @species = 'Dog';
SET @gender = 'Male';
SET @created_date = NULL;

-- Consulta com filtros
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
    p.created_date,
    p.user_id
FROM
    pets p
WHERE
    p.user_id = @user_id AND
    (@id IS NULL OR p.id = @id) AND
    (@name IS NULL OR p.name LIKE CONCAT('%', @name, '%')) AND
    (@breed IS NULL OR p.breed LIKE CONCAT('%', @breed, '%')) AND
    (@species IS NULL OR p.species = @species) AND
    (@gender IS NULL OR p.gender = @gender) AND
    (@created_date IS NULL OR p.created_date = @created_date);