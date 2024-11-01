-- CREATE DATABASE IF NOT EXISTS petshop;

-- Roles
USE petshop;
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name ENUM('SUPER_ADMIN', 'ADMIN', 'USER', 'OWNER', 'CUSTOMER', 'EMPLOYEE', 'VETERINARIAN') NOT NULL,
    description VARCHAR(150) NOT NULL,
);

-- Usuário
USE petshop;
CREATE TABLE IF NOT EXISTS users (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    cpf CHAR(14) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(150) NOT NULL,
    phone VARCHAR(14),
    enabled BOOLEAN DEFAULT TRUE,
    birth_date DATE,
    failed_login_attempts INT DEFAULT 0,
    account_non_expired BOOLEAN DEFAULT TRUE,
    created_by VARCHAR(255) NOT NULL,
    created_by_role VARCHAR(255) NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(255),
    updated_by_role VARCHAR(255),
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_accessed_date TIMESTAMP
);

-- Tabela de relacionamento entre users e roles
USE petshop;
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT UNSIGNED NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT FK_user_role FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT FK_role_user FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);
