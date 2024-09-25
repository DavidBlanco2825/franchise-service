CREATE TABLE IF NOT EXISTS franchise (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS branch (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    franchise_id BIGINT NOT NULL,
    FOREIGN KEY (franchise_id) REFERENCES franchise(id)
);

CREATE TABLE IF NOT EXISTS product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    stock INT NOT NULL,
    branch_id BIGINT NOT NULL,
    FOREIGN KEY (branch_id) REFERENCES branch(id)
);