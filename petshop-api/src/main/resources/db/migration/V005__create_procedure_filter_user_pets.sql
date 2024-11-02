USE petshop;
-- Função para listar e filtrar os pets
DELIMITER //
CREATE PROCEDURE filter_user_pets(
    IN p_user_id BIGINT,
    IN p_name VARCHAR(100),
    IN p_gender ENUM('Male', 'Female', 'Other'),
    IN p_castrated BOOLEAN,
    IN p_breed VARCHAR(50)
)
BEGIN
    SELECT
        id,
        name,
        species,
        breed,
        birth_date,
        color,
        size,
        gender,
        castrated,
        weight,
        health_history,
        user_id,
        created_by,
        created_by_role,
        created_date,
        updated_by,
        updated_by_role,
        updated_date,
        last_accessed_date
    FROM
        user_pets
    WHERE
        user_id = p_user_id
        AND (p_name IS NULL OR name = p_name)
        AND (p_gender IS NULL OR gender = p_gender)
        AND (p_castrated IS NULL OR castrated = p_castrated)
        AND (p_breed IS NULL OR breed = p_breed);
END //
DELIMITER ;