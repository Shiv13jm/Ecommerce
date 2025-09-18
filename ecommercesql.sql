create database DB;
use DB;
-- 1. Users (Customers/Admins)
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    role ENUM('CUSTOMER','ADMIN') DEFAULT 'CUSTOMER'
);

-- 2. Categories
CREATE TABLE categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- 3. Products
CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category_id INT,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    description TEXT,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

-- 4. Cart
CREATE TABLE cart (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    product_id INT,
    quantity INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- 5. Orders
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('PLACED','SHIPPED','DELIVERED','CANCELLED') DEFAULT 'PLACED',
    total_amount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- 6. Order Items
CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
INSERT INTO categories (category_id, name) VALUES
(1, 'Accessories'),
(2, 'Audio'),
(3, 'Electronics');

INSERT INTO products (name, category_id, price, stock, description) VALUES
('Wireless Mouse', 1, 599.99, 50, 'Ergonomic wireless mouse with adjustable DPI.'),
('Mechanical Keyboard', 1, 2499.50, 30, 'RGB backlit mechanical keyboard with blue switches.'),
('Gaming Headset', 2, 1499.00, 25, 'Surround sound gaming headset with noise-canceling mic.'),
('Smartphone', 3, 19999.99, 20, 'Latest smartphone with 6.7-inch display and 128GB storage.'),
('Laptop Stand', 1, 899.00, 40, 'Adjustable aluminum laptop stand for better ergonomics.'),
('USB-C Charger', 1, 499.00, 100, 'Fast-charging USB-C charger compatible with most devices.'),
('Bluetooth Speaker', 2, 1299.00, 15, 'Portable Bluetooth speaker with deep bass and 10-hour battery life.'),
('Fitness Tracker', 3, 2999.00, 35, 'Waterproof fitness tracker with heart-rate monitor.'),
('LED Desk Lamp', 1, 799.00, 20, 'Dimmable LED desk lamp with USB charging port.'),
('External Hard Drive', 3, 4999.00, 10, '1TB external hard drive with fast USB 3.0 connectivity.');

use DB;
select* from users;
select* from products;

