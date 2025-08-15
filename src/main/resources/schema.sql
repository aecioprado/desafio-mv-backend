-- Schema for Spring Boot application with MySQL
-- Drop tables if they exist (for clean restart)
DROP TABLE IF EXISTS breakfast_item;
DROP TABLE IF EXISTS breakfast;

-- Create breakfast table
CREATE TABLE breakfast (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_name VARCHAR(255) NOT NULL,
    social_security_number VARCHAR(11) NOT NULL
);

-- Create breakfast_item table
CREATE TABLE breakfast_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    breakfast_id BIGINT NOT NULL,

    -- Foreign key constraint
    CONSTRAINT fk_breakfast_item_breakfast
        FOREIGN KEY (breakfast_id) REFERENCES breakfast(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Indexes for better performance
-- Index on SSN for unique constraint and fast lookups
CREATE UNIQUE INDEX idx_breakfast_social_security_number ON breakfast(social_security_number);

-- Index on employee_name for search operations
CREATE INDEX idx_breakfast_employee_name ON breakfast(employee_name);

-- Index on breakfast_id in breakfast_item table (foreign key index)
CREATE INDEX idx_breakfast_item_breakfast_id ON breakfast_item(breakfast_id);

-- Index on item name for search operations
CREATE INDEX idx_breakfast_item_name ON breakfast_item(name);

-- Composite index for common queries (breakfast_id + name)
CREATE INDEX idx_breakfast_item_breakfast_name ON breakfast_item(breakfast_id, name);
