-- View para Quantidade Total de Animais Castrados e Não Castrados
-- Esta view conta o número total de animais castrados (castrated = true) e não castrados (castrated = false).
USE petshop;
CREATE VIEW CastratedStatistics AS
SELECT
    castrated,
    COUNT(*) AS total
FROM
    pets
GROUP BY
    castrated;

-- View para Quantidade Total de Animais por Espécie
-- Esta view conta o número total de animais por cada espécie.
CREATE VIEW SpeciesStatistics AS
SELECT
    species,
    COUNT(*) AS total
FROM
    pets
GROUP BY
    species;

-- View para Quantidade Total de Animais por Gênero
-- Esta view conta o número total de animais por cada gênero.
CREATE VIEW GenderStatistics AS
SELECT
    gender,
    COUNT(*) AS total
FROM
    pets
GROUP BY
    gender;

-- View para Quantidade de Animais por Usuário
-- Esta view conta o número total de animais por cada usuário.
CREATE VIEW UserStatistics AS
SELECT
    user_id,
    COUNT(*) AS total
FROM
    pets
GROUP BY
    user_id;