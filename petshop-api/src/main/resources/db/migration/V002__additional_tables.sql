-- Tabela de relacionamento entre users e roles
USE petshop;
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT UNSIGNED NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT FK_user_role FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT FK_role_user FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

USE petshop;
CREATE TABLE IF NOT EXISTS password_reset_token (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    user_id BIGINT UNSIGNED,
    expiry_date TIMESTAMP NOT NULL,
    CONSTRAINT FK_password_reset_token_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

USE petshop;
CREATE TABLE IF NOT EXISTS pets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    species ENUM('Dog', 'Cat', 'Bird', 'Fish', 'Reptile', 'Rabbit', 'Other') NOT NULL,
    breed VARCHAR(50),
    birth_date DATE,
    color VARCHAR(25),
    size VARCHAR(25),
    gender ENUM('Male', 'Female', 'Other') NOT NULL,
    castrated BOOLEAN,
    weight DECIMAL(5,2),
    health_history VARCHAR(250),
    user_id BIGINT UNSIGNED NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    created_by_role VARCHAR(255) NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(255),
    updated_by_role VARCHAR(255),
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_accessed_date TIMESTAMP,
    CONSTRAINT FK_pet_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

USE petshop;
CREATE TABLE IF NOT EXISTS addresses (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    zip_code VARCHAR(10) NOT NULL,
    street VARCHAR(150) NOT NULL,
    complement VARCHAR(100),
    unit VARCHAR(50),
    neighborhood VARCHAR(150) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL,
    user_id BIGINT UNSIGNED,
    CONSTRAINT FK_user_address FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);