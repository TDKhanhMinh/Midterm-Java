CREATE SCHEMA `e-commerce`;

use `e-commerce`;

SET FOREIGN_KEY_CHECKS = 0;
CREATE TABLE customer (
  first_name VARCHAR(50) NOT NULL,
  customer_id INT NOT NULL AUTO_INCREMENT,
  last_name VARCHAR(50) NOT NULL,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(100) NOT NULL,
  birthday date,
  gender varchar(10),
  phone varchar(100),
  province VARCHAR(100),
  district varchar(100),
  ward varchar(100),
  image VARCHAR(255),
  PRIMARY KEY (customer_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


CREATE TABLE admin (
  name VARCHAR(50) NOT NULL,
  email VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL,
  admin_id INT NOT NULL AUTO_INCREMENT,
  image VARCHAR(255),
  PRIMARY KEY (admin_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE category (
  category_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
    is_active BOOLEAN,
    is_delete BOOLEAN,
  PRIMARY KEY (category_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE role (
    role_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (role_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
INSERT INTO role (name) VALUES ('ROLE_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_USER');


CREATE TABLE `order` (
    order_id INT NOT NULL AUTO_INCREMENT,
    product_quantity INT,
    date DATE,
    total_price float,
    customer_id INT,
    PRIMARY KEY (order_id),
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


CREATE TABLE cart (
    id INT NOT NULL AUTO_INCREMENT,
    total_items INT,
    total_price DECIMAL(10, 2),
    customer_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE customer_role (
    role_id INT,
    customer_id INT,
    PRIMARY KEY (role_id, customer_id),
    FOREIGN KEY (role_id) REFERENCES role(role_id),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE admin_roles (
    admin_id INT,
    role_id INT,
    PRIMARY KEY (admin_id, role_id),
    FOREIGN KEY (admin_id) REFERENCES admin(admin_id),
    FOREIGN KEY (role_id) REFERENCES role(role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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


