USE petshop;
DELIMITER //

CREATE PROCEDURE sp_pet_statistics()
BEGIN
    -- 1. Quantidade de pets agrupados por Espécie
    SELECT species, COUNT(*) AS pet_count
    FROM pets
    GROUP BY species;

    -- 2. Quantidade de pets agrupados por Raça
    SELECT breed, COUNT(*) AS pet_count
    FROM pets
    GROUP BY breed;

     -- 3. Quantidade de pets agrupados por Gênero
    SELECT gender, COUNT(*) AS pet_count
    FROM pets
    GROUP BY gender;

    -- 4. Quantidade de pets agrupados por status de castração
    SELECT
        CASE
            WHEN castrated = TRUE THEN 'Castrated'
            ELSE 'Not Castrated'
        END AS castration_status,
        COUNT(*) AS pet_count
    FROM pets
    GROUP BY castrated;
END //

DELIMITER ;

-- Executa a procedure para visualizar todos os conjuntos de dados
CALL sp_pet_statistics();

-- Contagem por espécie
SELECT species, COUNT(*) AS pet_count FROM pets GROUP BY species;

-- Contagem por raça
SELECT breed, COUNT(*) AS pet_count FROM pets GROUP BY breed;

-- Contagem por castração
SELECT
    CASE
        WHEN castrated = TRUE THEN 'Castrated'
        ELSE 'Not Castrated'
    END AS castration_status,
    COUNT(*) AS pet_count
FROM pets
GROUP BY castrated;

-- Contagem por gênero
SELECT gender, COUNT(*) AS pet_count FROM pets GROUP BY gender;
