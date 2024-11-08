CREATE SCHEMA `e-commerce`;

use `e-commerce`;
CREATE TABLE customer (
    customer_id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    password VARCHAR(255),
    confrim_password VARCHAR(255),
    email VARCHAR(100),
    image VARCHAR(255),
    district VARCHAR(100),
    province VARCHAR(100),
    ward VARCHAR(100),
    birthday DATE,
    PRIMARY KEY (customer_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
CREATE TABLE role (
    role_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (role_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- Insert default roles
INSERT INTO role (name) VALUES ('ROLE_USER');
INSERT INTO role (name) VALUES ('ROLE_ADMIN');
CREATE TABLE customer_role (
    role_id INT,
    customer_id INT,
    PRIMARY KEY (role_id, customer_id),
    FOREIGN KEY (role_id) REFERENCES role(role_id),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `order` (
    order_id INT NOT NULL AUTO_INCREMENT,
    product_quantity INT,
    date DATETIME,
    total_price DECIMAL(10, 2),
    customer_id INT,
    PRIMARY KEY (order_id),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE category (
    category_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100),
    is_active BOOLEAN,
    is_delete BOOLEAN,
    PRIMARY KEY (category_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



CREATE TABLE cart (
    id INT NOT NULL AUTO_INCREMENT,
    total_items INT,
    total_price DECIMAL(10, 2),
    customer_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;





CREATE TABLE product (
    product_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100),
    original_price DECIMAL(10, 2),
    sale_price DECIMAL(10, 2),
    image VARCHAR(255),
    quantity INT,
    category_id INT,
    PRIMARY KEY (product_id),
    FOREIGN KEY (category_id) REFERENCES category(category_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE order_detail (
    id INT NOT NULL AUTO_INCREMENT,
    quantity INT,
    total_price DECIMAL(10, 2),
    unit_price DECIMAL(10, 2),
    order_id INT,
    product_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES `order`(order_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE cart_items (
    id INT NOT NULL AUTO_INCREMENT,
    quantity INT,
    total_price DECIMAL(10, 2),
    unit_price DECIMAL(10, 2),
    cart_id INT,
    product_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (cart_id) REFERENCES cart(id),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

INSERT INTO role (name) VALUES ('ROLE_USER');
INSERT INTO role (name) VALUES ('ROLE_ADMIN');